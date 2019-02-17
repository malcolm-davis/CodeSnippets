package code.classType;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;

public enum ConfigOptions {

    StringSample("name", String.class, "10.0.1.51", Conditional.Required),
    DoubleSample("pay", Double.class, new Double(0.0), Conditional.Optional),
    LongSample("level", Long.class, new Long(2000), Conditional.Optional),
    BooleanSample("isEdit", Boolean.class, Boolean.FALSE, Conditional.Optional),
    ModeSample("mode", Boolean.class, Boolean.FALSE, Conditional.Optional),
    IntSample("port", Integer.class, Integer.valueOf(9999), Conditional.Optional);

    ConfigOptions(String propertyKey, Class<?> type, Object defaultValue, Conditional condition) {
        m_propertyKey = propertyKey;
        m_class = type;
        m_defaultValue = defaultValue;
        m_conditional = condition;
    }

    public static Properties buildDefaultConfig() {
        Properties properties = new Properties();
        for(ConfigOptions option : ConfigOptions.values() ) {
            if(option.m_conditional==Conditional.Required) {
                properties.put(option.m_propertyKey, option.m_defaultValue);
            }
        }
        return properties;
    }

    /**
     * Interpret the configuration as ConfigOptions.
     * @param config
     * @return
     * Map<ConfigOptions,Object>
     */
    public static Map<ConfigOptions, Object> translate(Properties configuration) {
        if(configuration==null) {
            throw new IllegalArgumentException("configuration parameter is null");
        }

        // converted/decipher/transcribed
        Map<ConfigOptions, Object>converted = new HashMap<ConfigOptions, Object>();
        for(ConfigOptions option : ConfigOptions.values() ) {
            Object value = configuration.get(option.m_propertyKey);
            if(value!=null) {
                try {
                    converted.put(option, option.parse(value.toString()));
                    continue ;
                } catch (Exception excep) {
                }
            }
            converted.put(option, option.m_defaultValue);
        }
        return converted;
    }

    public static  Map<String,String> validateConfig(Properties config) {
        Map<String,String>issues = new TreeMap<String,String>();

        // check for required values
        for(ConfigOptions option : ConfigOptions.values() ) {
            if(option.m_conditional==Conditional.Required) {
                String value = config.getProperty(option.m_propertyKey);
                if( StringUtils.isBlank(value) ) {
                    issues.put(option.m_propertyKey, option.m_propertyKey+" configuration option is missing");
                }
            }
        }

        Set<Object> keys = config.keySet();

        // check key values
        for (Object key : keys) {
            String keyStr = key.toString();
            if( !getConfigOptionsMap().containsKey(keyStr) ) {
                boolean found=false;
                for(ConfigOptions option : ConfigOptions.values() ) {
                    if( option.m_propertyKey.equalsIgnoreCase(keyStr)
                            || option.m_propertyKey.contains(keyStr)
                            || keyStr.contains(option.m_propertyKey) ) {
                        issues.put(key.toString(), "Not a configuration option.   Did you mean '"+option.m_propertyKey+"'");
                        found = true;
                        break ;
                    }
                }
                if(!found) {
                    issues.put(key.toString(), "Not a configuration option.");
                }
            }
        }

        // check key value types
        for (Object key : keys) {
            String value = config.get(key).toString();
            ConfigOptions option = getConfigOptionsMap().get(key);
            if( option!=null ) {
                if(option.m_class==Boolean.class) {
                    // special case.  Boolean will always return false for anything not equal to true
                    if( !value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("false") ) {
                        issues.put(key.toString(), "'"+value+"' is an invalid value.  "+key.toString()+" must be a either true or false");
                        continue ;
                    }
                }
                try {
                    Object obj = DynamicConvert.parse(option.m_class, value.toString());
                } catch (Exception excep) {
                    issues.put(key.toString(), "'"+value+"' is an invalid value.  "+key.toString()+" must be a '"+option.m_class.getSimpleName()+"'");
                }
            }
        }
        return issues;
    }

    public static T convert(ConfigOptions option, String value) {
        return (T) DynamicConvert.parse(option.m_class, value);
    }

    public <T> T parseProperty(Properties config) {
        if(config==null) {
            throw new IllegalArgumentException("Null value for config parameter");
        }
        return (T) parse(config.getProperty(m_propertyKey));
    }

    public <T> T parse(String value) {
        if(StringUtils.isBlank(value)) {
            if(Conditional.Required==m_conditional) {
                throw new IllegalStateException("Configuration option '"+m_propertyKey + "' is a required field");
            }
            return (T) m_defaultValue;
        }
        return (T) DynamicConvert.parse(m_class, value);
    }

    @Override
    public String toString() {
        return m_propertyKey;
    }

    protected static Map<String, ConfigOptions> getConfigOptionsMap() {
        if (m_configKeyOptionsMap == null) {
            m_configKeyOptionsMap = new HashMap<String, ConfigOptions>();
            for(ConfigOptions option : ConfigOptions.values() ) {
                m_configKeyOptionsMap.put(option.m_propertyKey, option);
            }
        }
        return m_configKeyOptionsMap;
    }

    private static Map<String, ConfigOptions>m_configKeyOptionsMap;

    private final String m_propertyKey;

    private final Class<?> m_class;

    private final Object m_defaultValue;

    private final Conditional m_conditional;
}
