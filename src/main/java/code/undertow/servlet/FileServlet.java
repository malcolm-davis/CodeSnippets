package code.undertow.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasolutions.utils.CloseUtil;
import org.slf4j.LoggerFactory;

public class FileServlet extends HttpServlet {

    @Override
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println("FileServlet init");
    }

    @SuppressWarnings("unused")
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        LOG.info(ServletUtil.dumpRequest(request).toString());

        if( m_uploadFolder==null ) {
            LOG.error("The m_outputFolder value has not been set.  The file will not be saved");
            return ;
        }

        response.setContentType("text/plain;charset=UTF-8");
        String description = pullValueFromRequest(request, "description");

        String message = "File saved";
        try {
            Part filePart = request.getPart("PostFile");
            if( filePart!=null ) {
                String fileName = getFileName(filePart);
                InputStream logFileContent = filePart.getInputStream();
                saveFile(fileName, logFileContent);
            } else {
                message = "No file posted in the request.";
                LOG.error(message);
            }
        } catch( Exception e) {
            message = "Unable to process request data.";
            LOG.error(message, e);
        }

        PrintWriter writer = response.getWriter();
        writer.write(message);
        writer.close();
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    public void setUploadFolder(String folder) {
        m_uploadFolder = folder;
    }

    private void saveFile(String filename, InputStream inputStream) throws IOException {
        String storageFileFolder = FilenameUtils.concat(m_uploadFolder, filename);
        FileOutputStream outStream = new FileOutputStream(storageFileFolder);
        IOUtils.copy(inputStream, outStream);
        CloseUtil.close(outStream);
    }

    private static String getFileName(Part part) {
        for(String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String fileName =
                        cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return fileName.substring(fileName.lastIndexOf('/') + 1).substring(
                    fileName.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
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
            LoggerFactory.getLogger(FileServlet.class.getName());


}
