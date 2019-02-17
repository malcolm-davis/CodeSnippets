package code.undertow.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class KillServlet extends HttpServlet {

    @Override
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println("KillServlet.init");
        state = "started";
    }

    @SuppressWarnings("unused")
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse resp) throws ServletException, IOException {
        String kill = request.getParameter("kill");
        if( Boolean.parseBoolean(kill) ) {
            PrintWriter writer = resp.getWriter();
            writer.write("Shutting down service");
            writer.close();
            m_killListener.shutdownServer();
        }
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    public String getState() {
        return state;
    }

    private String state;
    private KillListener m_killListener;

    public void setKillListener(KillListener killListener) {
        m_killListener = killListener;
    }
}
