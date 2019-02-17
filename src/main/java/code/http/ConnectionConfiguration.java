package code.http;

import java.io.Serializable;

public class ConnectionConfiguration implements Serializable {

    public static ConnectionConfiguration newConfiguration(String protocal, String host,
            int port, String appPath, String name, String pw) {
        return new ConnectionConfiguration(port, protocal, host, appPath, name, pw);
    }

    public String getPath() {
        if(m_baseUri==null) {
            // String BASE_URI = "http://localhost/myApp";
            m_baseUri = String.format("%s://%s:%d/%s/",
                getProtocal(), getHost(), getPort(), getUrl());
        }
        return m_baseUri;
    }

    public String getPathWithAuth() {
        if(m_baseUriAuth==null) {
            // http://userId:password@uri/product
            // http://johnny:mypassword@localhost/myApp
            m_baseUriAuth = String.format("%s://%s:%s@%s/%s/",
                getProtocal(), getName(), getPassword(), getHost(), getUrl());
        }
        return m_baseUriAuth;
    }

    public int getPort() {
        return m_port;
    }

    public String getProtocal() {
        return m_protocal;
    }

    public String getHost() {
        return m_host;
    }

    public String getUrl() {
        return m_appPath;
    }

    public String getName() {
        return m_name;
    }

    public String getPassword() {
        return m_password;
    }

    private ConnectionConfiguration(int port, String protocal, String host,
            String appPath, String name, String pw) {
        m_port = port;
        m_protocal = protocal;
        m_host = host;
        m_appPath = appPath;
        m_name = name;
        m_password = pw;
    }

    private int m_port = 80;

    private String m_protocal = "http";

    private String m_host = "localhost";

    private String m_appPath = null;

    private String m_name = null;

    private String m_password = null;

    private String m_baseUriAuth;

    private String m_baseUri;
}
