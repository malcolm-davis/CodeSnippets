package code.xstream;

import org.apache.commons.lang3.StringUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;

import code.mockdata.Address;
import code.mockdata.Gender;
import code.mockdata.User;

public class XStreamOverrideDemo {

    public static void main(String[] args) {
        System.out.println("Start: XStreamOverrideDemo ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        XStreamOverrideDemo test = new XStreamOverrideDemo();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis() - start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        User user = User.newUser("Frank", "Wright", Gender.Male, Address.newAddress("2512 Bridlewood", "Helena", "Alabama", "35080"));
        System.out.println(getXmlStream().toXML(user));
    }

    private XStream getXmlStream() {
        if(m_xstream==null) {
            // Note: rather than using alias, using method mapper technique
            // m_xstream = new XStream();
            // addAlias(m_xstream);
            m_xstream = new XStream(new DomDriver()) {
                @Override
                protected MapperWrapper wrapMapper(final MapperWrapper mapper) {
                    // return new MethodMapper(next);
                    // return new MethodMapper(next, "com.elasolutions.mockdata");
                    // return new PackageMapper(next);
                    return new MapperWrapper(mapper) {
                        @Override
                        public String serializedClass(Class type) {
                            String[] packages = StringUtils.split(type.getName(), ".");
                            if( packages==null || packages.length==0 ) {
                                return super.serializedClass(type);
                            }
                            return packages[packages.length-1];
                        }
                    };
                }
            };
        }
        return m_xstream;
    }

    private XStream m_xstream;
}
