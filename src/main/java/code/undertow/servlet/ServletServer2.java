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

public class ServletServer2 implements KillListener {

    public static final String APPNAME = "/dempApp";

    public static void main(final String[] args) {
        ServletServer2 server = new ServletServer2();
        server.run();
        System.out.println("main end");
    }

    private void run() {
        try {
            DeploymentInfo servletBuilder = deployment()
                    .setClassLoader(ServletServer2.class.getClassLoader())
                    .setContextPath(APPNAME)
                    .setDeploymentName("server2.war")
                    .addServlets(
                        servlet("EchoServlet", EchoServlet.class).addMapping("/echo/*"), // http://localhost:8080/dempApp/echo?echo=test
                        servlet("DumpServlet", DumpServlet.class).addMapping("/dump/*"), // http://localhost:8080/dempApp/dump

                        // http://localhost:8080/dempApp/kill?kill=true
                        servlet("KillServlet", KillServlet.class).addMapping("/kill/*").setLoadOnStartup(1),

                        servlet("FindCityServlet", FindCityServlet.class).addMapping("/FindCity/*").setLoadOnStartup(2), // http://localhost:8080/dempApp/FindCity?zip=23456

                        // http://localhost:8080/dempApp/Ping
                        servlet("PingServlet", PingServlet.class).addMapping("/Ping/*").setLoadOnStartup(3),

                        // http://localhost:8080/dempApp/fileupload
                        servlet("FileUploadServlet", FileUploadServlet.class).addMapping("/FileUploadServlet/*"),

                        // http://localhost:8080/dempApp/hello
                        servlet("MessageServlet", MessageServlet.class).addInitParam("message", "Hello World Again").addMapping("/hello/*"),

                        // http://localhost:8080/dempApp/myservlet
                        servlet("MyServlet", MessageServlet.class).addInitParam("message", "MyServlet").addMapping("/myservlet"));

            System.out.println("servletBuilder build");

            DeploymentManager manager = defaultContainer().addDeployment(servletBuilder);
            manager.deploy();
            System.out.println("manager.deploy();");

            HttpHandler servletHandler = manager.start();
            System.out.println("manager.start();");

            // Special servlet configuration
            KillServlet killServlet = (KillServlet)manager.getDeployment().getServlets().
                    getManagedServlet("KillServlet").getServlet().getInstance();

            FileUploadServlet fileUploadServlet = (FileUploadServlet)manager.getDeployment().getServlets().
                    getManagedServlet("FileUploadServlet").getServlet().getInstance();

            System.out.println("servlet=" + killServlet.getState());
            fileUploadServlet.setUploadFolder("c:/temp/fileupload");
            killServlet.setKillListener(this);

            PathHandler path = Handlers.path(Handlers.redirect(APPNAME))
                    .addPrefixPath(APPNAME, servletHandler);
            System.out.println("PathHandler;");

            server = Undertow.builder()
                    .addHttpListener(8080, "localhost")
                    .setHandler(path)
                    .build();
            System.out.println("Undertow server");

            server.start();
            System.out.println("server.start");
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void shutdownServer() {
        System.out.println("Shutting down");
        server.stop();
    }

    Undertow server;
}
