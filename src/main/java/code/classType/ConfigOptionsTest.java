package code.classType;

import java.util.Map;
import java.util.Properties;

public class ConfigOptionsTest {

    public static void main(String[] args) {
        System.out.println("Start: ConfigOptionsTest ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        ConfigOptionsTest test = new ConfigOptionsTest();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        System.out.println("********************************************************");
        System.out.println("Default");
        Properties defaultConfig = ConfigOptions.buildDefaultConfig();
        for (Object key : defaultConfig.keySet()) {
            System.out.println(key +" = "+defaultConfig.get(key));
        }


        Properties config = new Properties();
        config.setProperty(ConfigOptions.BooleanSample.toString(), "True");
        config.setProperty(ConfigOptions.DoubleSample.toString(), "10.10");
        config.setProperty(ConfigOptions.IntSample.toString(), "44");
        config.setProperty(ConfigOptions.ModeSample.toString(), "truethy");
        config.setProperty("NAME", "a name");
        config.setProperty("isEdits", "an edit");
        config.setProperty("leve", "a level");
        config.setProperty("level", "a level");
        config.setProperty("mockKey", "mockValue");

//        List<String>issues = ConfigOptions.validateConfig(config);
//        System.out.println("********************************************************");
//        for (String issue : issues) {
//            System.out.println(issue);
//        }

        System.out.println("********************************************************");
        System.out.println("validateConfig");
        Map<String,String>issues = ConfigOptions.validateConfig(config);
        for (String issueKey : issues.keySet()) {
            System.out.println(issueKey +" = "+issues.get(issueKey));
        }

        System.out.println("********************************************************");
        System.out.println("translate");
        Map<ConfigOptions, Object>options = ConfigOptions.translate(config);
        for (ConfigOptions key : options.keySet()) {
            System.out.println(key+"="+options.get(key).toString());
        }


        System.out.println("\r\n********************************************************");
        Boolean bConfigValue = ConfigOptions.BooleanSample.parseProperty(config);
        System.out.println("bConfigValue="+bConfigValue);

        Double dConfigValue = ConfigOptions.DoubleSample.parseProperty(config);
        System.out.println("dConfigValue="+dConfigValue);

        Integer iConfigValue = ConfigOptions.IntSample.parseProperty(config);
        System.out.println("iConfigValue="+iConfigValue);

        System.out.println("\r\n********************************************************");
        Boolean bValue = ConfigOptions.BooleanSample.parse(null);
        System.out.println("bValue="+bValue);

        Double dValue = ConfigOptions.DoubleSample.parse(null);
        System.out.println("dValue="+dValue);

        Integer iValue = ConfigOptions.IntSample.parse("");
        System.out.println("iValue="+iValue);

        String sValue = ConfigOptions.StringSample.parse("10.10");
        System.out.println("sValue="+sValue);

        String sValue2 = ConfigOptions.StringSample.parse(null);
        System.out.println("sValue="+sValue);

    }

}
