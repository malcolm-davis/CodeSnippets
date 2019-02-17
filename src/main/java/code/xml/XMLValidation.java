package code.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

public class XMLValidation {

    public static void main(String[] args) {
        System.out.println("Start: XMLValidation ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        XMLValidation test = new XMLValidation();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        System.out.println(validateXMLSchema("C:/ecx/sites/3.3.config.xml","C:/ecx/sites/out.xsd"));
    }


    public static boolean validateXMLSchema(String xmlPath, String xsdPath){

        try {
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            javax.xml.validation.Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
        } catch (IOException | SAXException e) {
            System.out.println("Exception: "+e.getMessage());
            return false;
        }
        return true;
    }
}
