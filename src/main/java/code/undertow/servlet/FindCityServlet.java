package code.undertow.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FindCityServlet extends HttpServlet {


    @Override
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println("FindCityServlet init");
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        writer.write(request.getParameter("zip") + "=Helena");
        writer.close();
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}
