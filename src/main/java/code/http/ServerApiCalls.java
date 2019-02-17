package code.http;

import java.io.File;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;


public enum ServerApiCalls {
    INSTANCE;

    public void setConnectionConfig(ConnectionConfiguration config) {
        m_configuration = config;
    }

    public String buildServiceUri(String service) {
        // userId:password@uri/product
        String baseUri = getConfig().getPathWithAuth();
        String updatedUri = baseUri + service;
        return appendRequiredParams(updatedUri);
    }

    public ConnectionConfiguration getConfig() {
        return m_configuration;
    }

    public static String appendRequiredParams(String uri) {
        // Simple formatting with no encoding can & will cause issues.  Avoid the following:
        // String params = String.format("?user=%s&zip=%s", "mkjones", "45443");
        // make sure the values are encoded for weirdness that can occur with data
        List<NameValuePair> nvps = createParameters("user","mkjones");
        nvps.add(new BasicNameValuePair("zip","45443"));
        String params = "?"+URLEncodedUtils.format(nvps, '&', Consts.UTF_8);
        return uri+params;
    }

    public String methodCallGet(String method, NameValuePair... values) {
        List <NameValuePair> nvps = new ArrayList <NameValuePair>();

        String params = "";
        if(values!=null && values.length>0) {
            for (NameValuePair nameValuePair : values) {
                nvps.add(nameValuePair);
            }
            params = "?"+URLEncodedUtils.format(nvps, '&', Consts.UTF_8);
        }

        String fullget = getConfig().getPath() + method + params;
        System.out.println("fullget=" + fullget);
        return makeServerCall(fullget);
    }

    public String methodCallPost(String method, NameValuePair... values) {
        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
        if(values!=null) {
            for (NameValuePair nameValuePair : nvps) {
                nvps.add(nameValuePair);
            }
        }
        return makeServerCallPost(getConfig().getPath() + method, nvps);
    }

    public boolean ping() {
        return ConnectionStatus.Connected.equals(getConnectionStatus());
    }

    public ConnectionStatus getConnectionStatus() {
        verifyState();

        String results = makeServerCall(getConfig().getPath()+"Ping");
        if( results.contains("success") ) {
            return ConnectionStatus.Connected;
        }
        return ConnectionStatus.Disonnected;
    }


    public String getCity(String zipCode) {
        verifyState();
        return makeServerCallPost(getConfig().getPath()+"FindCity", createParameters("zip",zipCode));
    }

    public String makeServerCall(String uri) {
        verifyState();

        // Doing a lock on the HTTP connection sense it can be safely reused.
        // The lock is a workaround to some HTTP client limitations.
        m_lock.lock();

        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());
            SSLContext.setDefault(ctx);
        } catch (KeyManagementException | NoSuchAlgorithmException excep) {
            excep.printStackTrace();
        }

        StringBuffer result = new StringBuffer();
        try {
            HttpGet httpGet = new HttpGet(uri);
            CloseableHttpResponse response = getHttpClient().execute(httpGet);
            // The underlying HTTP connection is still held by the response object
            // to allow the response content to be streamed directly from the network socket.
            // In order to ensure correct deallocation of system resources
            // the user MUST call CloseableHttpResponse#close() from a finally clause.
            // Please note that if response content is not fully consumed the underlying
            // connection cannot be safely re-used and will be shut down and discarded
            // by the connection manager.
            try {
                // the status line will return 'HTTP/1.1 200 OK' if everything is working.
                // result.append(response1.getStatusLine());

                HttpEntity entity = response.getEntity();
                result.append(EntityUtils.toString(entity));

                // do something useful with the response body and ensure it is fully consumed
                // EntityUtils.consume(entity1);
            } finally {
                response.close();
            }
        } catch (Throwable ex) {
            LOG.error("Unable to connect to server", ex);
        } finally {
            m_lock.unlock();
        }

        //LOG.debug("uri="+uri +", results="+result.toString());

        return result.toString();
    }

    public String makeServerCallPost(String uri, List <NameValuePair> nvps) {
        verifyState();

        m_lock.lock();

        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());
            SSLContext.setDefault(ctx);
        } catch (KeyManagementException | NoSuchAlgorithmException excep) {
            excep.printStackTrace();
        }

        StringBuffer result = new StringBuffer();
        try {
            // The underlying HTTP connection is still held by the response object
            // to allow the response content to be streamed directly from the network socket.
            // In order to ensure correct deallocation of system resources
            // the user MUST call CloseableHttpResponse#close() from a finally clause.
            // Please note that if response content is not fully consumed the underlying
            // connection cannot be safely re-used and will be shut down and discarded
            // by the connection manager.
            HttpPost httpPost = new HttpPost(uri);
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse response = getHttpClient().execute(httpPost);
            try {
                StatusLine status = response.getStatusLine();
                if( status!=null && status.getStatusCode()==200 ) {
                    HttpEntity entity = response.getEntity();

                    // consume the request
                    result.append(EntityUtils.toString(entity));

                }
            } finally {
                response.close();
            }
        } catch (Throwable ex) {
            LOG.error("Unable to connect to server", ex);
        } finally {
            m_lock.unlock();
        }

        return result.toString();
    }

    public String postFile(String method, String formFieldName, File fileToPost, NameValuePair...nameValuePairs) {
        verifyState();

        m_lock.lock();

        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());
            SSLContext.setDefault(ctx);
        } catch (KeyManagementException | NoSuchAlgorithmException excep) {
            excep.printStackTrace();
        }

        StringBuffer result = new StringBuffer();
        try {
            // The underlying HTTP connection is still held by the response object
            // to allow the response content to be streamed directly from the network socket.
            // In order to ensure correct deallocation of system resources
            // the user MUST call CloseableHttpResponse#close() from a finally clause.
            // Please note that if response content is not fully consumed the underlying
            // connection cannot be safely re-used and will be shut down and discarded
            // by the connection manager.


            List<NameValuePair> nameValues = new ArrayList <NameValuePair>();
            String params = "";
            if(nameValuePairs!=null && nameValuePairs.length>0) {
                for (NameValuePair nameValuePair : nameValuePairs) {
                    nameValues.add(nameValuePair);
                }
                params = "?"+URLEncodedUtils.format(nameValues, '&', Consts.UTF_8);
            }
            // String fullpath = getConfig().getPath()+method+params;

            String fullpath = getConfig().getPath()+method;
            HttpPost httpPost = new HttpPost(fullpath);
            httpPost.setEntity(new UrlEncodedFormEntity(nameValues));


            // FileBody fileToPostBody = new FileBody(fileToPost, ContentType.DEFAULT_BINARY);
            //builder.addPart(fileParam, fileToPostBody);

            // fileParam is the name expected on the server
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.addBinaryBody(formFieldName, fileToPost, ContentType.DEFAULT_BINARY, fileToPost.getName());

            HttpEntity reqEntity = builder.build();
            httpPost.setEntity(reqEntity);

            CloseableHttpResponse response = getHttpClient().execute(httpPost);
            try {
                StatusLine status = response.getStatusLine();
                if( status!=null && status.getStatusCode()==200 ) {
                    HttpEntity entity = response.getEntity();

                    // consume the request
                    result.append(EntityUtils.toString(entity));
                }
            } finally {
                response.close();
            }
        } catch (Throwable ex) {
            LOG.error("Unable to connect to server", ex);
        } finally {
            m_lock.unlock();
        }

        return result.toString();
    }


    static class DefaultTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(
                java.security.cert.X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(
                java.security.cert.X509Certificate[] chain, String authType) {
        }

        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }


    // trusting all certificate
    @SuppressWarnings("restriction")
    static void doTrustToCertificates() throws Exception {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {

                @SuppressWarnings("unused")
                @Override
                public void checkClientTrusted(X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                @SuppressWarnings("unused")
                public void checkServerTrusted(X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HostnameVerifier hv = new HostnameVerifier() {
            @Override
            public boolean verify(String urlHostName, SSLSession session) {
                if (!urlHostName.equalsIgnoreCase(session.getPeerHost())) {
                    System.out.println("Warning: URL host '" + urlHostName + "' is different to SSLSession host '" + session.getPeerHost() + "'.");
                }
                return true;
            }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }

    private CloseableHttpClient getHttpClient() {
        if(m_httpClient==null) {
            // see
            // https://hc.apache.org/httpcomponents-client-ga/examples.html
            // https://hc.apache.org/httpcomponents-client-ga/httpclient/examples/org/apache/http/examples/client/ClientCustomSSL.java
            SSLConnectionSocketFactory sslsf = null;
            try {
                SSLContext sslcontext =
                        SSLContexts.custom().loadTrustMaterial(
                            new TrustSelfSignedStrategy()).build();

                // Allow TLSv1 protocol only
                sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier());

            } catch (KeyManagementException | NoSuchAlgorithmException
                    | KeyStoreException excep) {
                LOG.error("Problem configuring SSL", excep);
            }

            m_httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        }
        return m_httpClient;
    }

    private static List<NameValuePair> createParameters(String param, String value) {
        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
        nvps.add(new BasicNameValuePair(param, value));
        return nvps;
    }

    private void verifyState() {
        if(m_configuration==null) {
            throw new IllegalStateException("ConnectionConfiguration has not been set");
        }
    }

    ConnectionConfiguration m_configuration;

    private CloseableHttpClient m_httpClient;

    private final ReentrantLock m_lock = new ReentrantLock();

    private static final org.slf4j.Logger LOG =
            LoggerFactory.getLogger(ServerApiCalls.class.getName());
}
