package code.params;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class SystemParams {

    public static void main(String[] args) {
        System.out.println("Start: SystemParams ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        SystemParams test = new SystemParams();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        Properties properties = System.getProperties();
        Set<String>propertyKeys = properties.stringPropertyNames();
        for(String propertyKey : propertyKeys) {
            String value = (String)properties.get(propertyKey);
            System.out.println(propertyKey+"="+value);

//                StringBuilder st = new StringBuilder ();
//                String[]values = value.split(";");
//                for(String sepValue : values )
//                    st.append(sepValue + "<br>\r\n");
        }

        String value = (String)System.getProperties().get("RunGroupId");
        if(value!=null) {
            String[]values = value.split(",");
            for (String groupId : values) {
                System.out.println(groupId);
            }
        }

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("Key", null);

    }

}
