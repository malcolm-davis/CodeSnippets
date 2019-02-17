package code.undertow.servlet;

import static io.undertow.servlet.Servlets.defaultContainer;
import static io.undertow.servlet.Servlets.deployment;
import static io.undertow.servlet.Servlets.servlet;

import javax.servlet.ServletException;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;

public class ServletServer {

    public static final String APPNAME = "/dempApp";

    public static void main(final String[] args) {
        ServletServer server = new ServletServer();
        server.run();
    }

    private void run() {
        try {
            DeploymentInfo servletBuilder = deployment()
                    .setClassLoader(ServletServer.class.getClassLoader())
                    .setContextPath(APPNAME)
                    .setDeploymentName("server.war")
                    .addServlets(
                        servlet("EchoServlet", EchoServlet.class).addMapping("/echo/*"),
                        servlet("DumpServlet", DumpServlet.class).addMapping("/dump/*"),
                        servlet("MessageServlet", MessageServlet.class).addInitParam("message", "Hello World Again").addMapping("/hello/*"),
                        servlet("MyServlet", MessageServlet.class).addInitParam("message", "MyServlet").addMapping("/myservlet"));

            DeploymentManager manager = defaultContainer().addDeployment(servletBuilder);
            manager.deploy();

            HttpHandler servletHandler = manager.start();
            PathHandler path = Handlers.path(Handlers.redirect(APPNAME))
                    .addPrefixPath(APPNAME, servletHandler);
            Undertow server = Undertow.builder()
                    .addHttpListener(8080, "localhost")
                    .setHandler(path)
                    .build();
            server.start();
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
