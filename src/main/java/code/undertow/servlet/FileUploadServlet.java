package code.undertow.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

public class FileUploadServlet extends HttpServlet {

    @Override
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println("FileServlet init");
    }

    @SuppressWarnings("unused")
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        // LOG.info(ServletUtil.dumpRequest(request).toString());
        System.out.println(ServletUtil.dumpRequest(request).toString());

        System.out.println(pullValueFromRequest(request, "description"));

        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setRepository(new File(m_uploadFolder));

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // Parse the request
        String msg = "failed";
        try {
            List<FileItem>items = upload.parseRequest(request);
            for (FileItem fileItem : items) {
                Iterator<String>headerItems = fileItem.getHeaders().getHeaderNames();
                while( headerItems.hasNext() ) {
                    String name = headerItems.next();
                    System.out.println("header name="+name + ", header="+fileItem.getHeaders().getHeaders(name));
                }
                if(fileItem.isFormField()) {

                } else {
                    InputStream uploadedStream = fileItem.getInputStream();

                    OutputStream out = new FileOutputStream(new File("C:\\temp\\fileupload\\Test.File.txt"));

                    IOUtils.copy(uploadedStream,out);
                    out.close();
                    uploadedStream.close();

                    fileItem.delete();

                }
                System.out.println("fileItem.getFieldName()=" + fileItem.getFieldName());
                System.out.println("fileItem.getName()=" + fileItem.getName());
            }
            msg = "file count="+items.size();

        } catch (FileUploadException excep) {
            excep.printStackTrace();
        }
        PrintWriter writer = response.getWriter();
        writer.write(msg);
        writer.close();
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    public void setUploadFolder(String folder) {
        m_uploadFolder = folder;
    }

    private static String pullValueFromRequest(HttpServletRequest request, String key) {
        String result = request.getHeader(key);
        if( StringUtils.isBlank(result)) {
            result = request.getParameter(key);
            if( StringUtils.isBlank(result)) {
                Object attribute = request.getAttribute(key);
                result = (attribute==null) ? null : attribute.toString();
            }
        }
        return result==null ? "" : result;
    }


    private String m_uploadFolder;

    private static final org.slf4j.Logger LOG =
            LoggerFactory.getLogger(FileUploadServlet.class.getName());
}
