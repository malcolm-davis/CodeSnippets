package code.xsdgen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.wiztools.xsdgen.ParseException;
import org.wiztools.xsdgen.XsdGen;

public class XmlToXsd {

    public static void main(String[] args) {
        System.out.println("Start: XmlToXsd ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        XmlToXsd test = new XmlToXsd();
        try {
            test.run();
        } catch (IOException | ParseException excep) {
            // TODO Auto-generated catch block
            excep.printStackTrace();
        }

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() throws IOException, ParseException {
        XsdGen gen = new XsdGen();
        gen.parse(new File("C:\\ecx\\sites\\config.xml"));
        File out = new File("C:/ecx/sites/out.xsd");
        gen.write(new FileOutputStream(out));
    }

}
