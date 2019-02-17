package code.undertow;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.BlockingHandler;
import io.undertow.util.Headers;

public class UndertowClient {

    public static void main(String[] args) {
        System.out.println("Start: UndertowClient ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        UndertowClient test = new UndertowClient();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis() - start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run()  {
        getServer().start();

        // wait for input to kill main
        try { System.in.read(); } catch (IOException excep) {}
    }


    Undertow getServer() {
        if(server==null) {
            server = Undertow.builder()
                    .addHttpListener(port, host)
                    .setHandler(new BlockingHandler(new HttpHandler() {
                        @Override
                        public void handleRequest(final HttpServerExchange exchange) throws Exception {
                            if( exchange.getInputStream()!=null ) {
                                final String received = readResponse(exchange.getInputStream());
                                System.out.println(received);
                            } else {
                                System.out.println("Nothing received");
                            }
                            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                            exchange.getResponseSender().send("Received");
                        }
                    })).build();
        }

        return server;
    }

    static String readResponse(InputStream stream) throws IOException {
        byte[] data = new byte[100];
        int read;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        while ((read = stream.read(data)) != -1) {
            out.write(data, 0, read);
        }
        return new String(out.toByteArray(), Charset.forName("UTF-8"));
    }

    Undertow server;

    int port = 9200;

    String host = "localhost";
}
