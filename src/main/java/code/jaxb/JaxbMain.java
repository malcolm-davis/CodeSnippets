package code.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class JaxbMain {

    public static void main(String[] args) {
        System.out.println("Start: JaxbMain ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        JaxbMain test = new JaxbMain();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {

    }


    protected static Marshaller getMarshller() throws JAXBException {
        if (marshller== null) {
            JAXBContext jabConext = JAXBContext.newInstance(CallDetailReocrd.class);
            marshller= jabConext.createMarshaller();
        }
        return marshller;
    }

    private static Marshaller marshller;
}
