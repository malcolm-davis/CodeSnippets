package code.undertow.wsorchestrator;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.LoggerFactory;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;

public class OrchestratorWsService {

    public static OrchestratorWsService newService(final String serviceUrl, WorkerListener workerListener) throws IOException {
        if (serviceUrl == null) {
            throw new IllegalArgumentException("Null value for serviceUrl");
        }

        OrchestratorWsService service = getService(serviceUrl);
        if (service == null) {
            service = new OrchestratorWsService(serviceUrl, workerListener);
            service.initialize();
            getOrchestratorServiceMap().put(serviceUrl, service);
        }
        return service;
    }


    public void close() {
        if (webSocket != null) {
            webSocket.disconnect();
        }
        webSocket = null;
    }

    public void initialize() throws IOException {
        try {
            webSocket = factory.createSocket("ws://" + m_serviceUrl);
            connectWebsocket();
        } catch (final IOException e) {
            LOG.error("Could not connect to URI=" + m_serviceUrl, e);
            throw e;
        }

        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                webSocket.sendPing("Are you there?");
            }
        }, 1000, 10000);

        webSocket.addListener(new WebSocketAdapter() {

            @Override
            public void onBinaryFrame(final WebSocket websocket,
                    final WebSocketFrame frame) throws Exception {
                LOG.info("Orchestrator received full binary message from worker=" + m_serviceUrl);
                m_workerListener.onFileReceived(frame.getPayloadText());
            }

            @Override
            public void onConnected(final WebSocket websocket,
                    final Map<String, List<String>> headers) throws Exception {
                super.onConnected(websocket, headers);
                LOG.info(websocket.toString() + " connected.");
            }

            @Override
            public void onConnectError(final WebSocket websocket,
                    final WebSocketException exception) throws Exception {
                super.onConnectError(websocket, exception);
                LOG.error("Error connection", exception);
                reconnect();
            }

            @Override
            public void onDisconnected(final WebSocket websocket,
                    final WebSocketFrame serverCloseFrame,
                    final WebSocketFrame clientCloseFrame,
                    final boolean closedByServer) throws Exception {
                reconnect();
            }

            @Override
            public void onTextMessage(final WebSocket websocket,
                    final String text) throws Exception {
                System.out.println("Orchestrator onTextMessage=" + text);
                LOG.info("Orchestrator received message="+text+", from worker=" + m_serviceUrl);
                m_workerListener.onMessageReceived(text);
            }
        });

    }

    public boolean isConnected() {
        return webSocket.isOpen();
    }

    public void postFile(final String filename) throws IOException {
        if (webSocket == null) {
            throw new IllegalStateException("webSocket is not initialized.");
        }
        final byte[] bytes = FileUtils.readFileToByteArray(new File(filename));
        webSocket.sendBinary(bytes);
    }

    public void postMessage(final String message) {
        if (webSocket == null) {
            throw new IllegalStateException("webSocket is not initialized.");
        }
        webSocket.sendText(message);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(webSocket,
            ToStringStyle.MULTI_LINE_STYLE);
    }

    void connectWebsocket() {
        webSocket.connectAsynchronously();
    }

    void reconnect() throws IOException {
        LOG.info("Attempting reconnect");
        webSocket = webSocket.recreate();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                try {
                    connectWebsocket();
                } catch (final Exception e) {
                    LOG.error("Could not reconnect to URI=" + m_serviceUrl, e);
                }
            }
        }, 5000);
    }

    private OrchestratorWsService(final String serviceUrl, WorkerListener workerListener) {
        m_serviceUrl = serviceUrl;
        m_workerListener = workerListener;
    }

    protected static Map<String, OrchestratorWsService> getOrchestratorServiceMap() {
        if (m_orchestratorServiceMap == null) {
            m_orchestratorServiceMap =
                    new ConcurrentHashMap<>();
        }
        return m_orchestratorServiceMap;
    }

    private static OrchestratorWsService getService(final String serviceUrl) {
        return getOrchestratorServiceMap().get(serviceUrl);
    }

    private final WebSocketFactory factory =
            new WebSocketFactory().setConnectionTimeout(5000);

    WebSocket webSocket;

    String m_serviceUrl;

    final Timer timer = new Timer();

    final WorkerListener m_workerListener;

    private static Map<String, OrchestratorWsService> m_orchestratorServiceMap;

    static final org.slf4j.Logger LOG =
            LoggerFactory.getLogger(OrchestratorWsService.class.getName());

}
