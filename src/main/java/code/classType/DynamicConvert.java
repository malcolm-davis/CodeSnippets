package code.classType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;

public class DynamicConvert {

    private static final Map<Class, Function<String, ? extends Object>> parsers = new HashMap<>();

    static {
        parsers.put(String.class, String::toString);
        parsers.put(Integer.class, Integer::parseInt);
        parsers.put(Long.class, Long::parseLong);
        parsers.put(Double.class, Double::parseDouble);
        parsers.put(Float.class, Float::parseFloat);
        parsers.put(Boolean.class, Boolean::parseBoolean);

        // add your own types here.
    }

    public static <T> T parse(Class<T> klass, String value) {
        if(klass==null) {
            throw new IllegalArgumentException("Null value for Class");
        }

        if(value==null) {
            throw new IllegalArgumentException("Null value to convert");
        }

        if( !klass.isInstance(String.class) && StringUtils.isBlank(value)) {
            throw new IllegalArgumentException("Invalid value convert");
        }

        // add some null-handling logic here? and empty values.
        return (T)parsers.get(klass).apply(value);
    }

    public static void main(String[] args) {
        System.out.println("String="+parse(String.class, "Hello World"));

        Integer iValue = parse(Integer.class, "5");
        System.out.println("Integer="+iValue);

        Long lValue = parse(Long.class, "5000");
        System.out.println("Long="+lValue);

        Double dValue = parse(Double.class, "5.5");
        System.out.println("Double="+dValue);

        Float fValue = parse(Float.class, "5.5332");
        System.out.println("Float="+fValue);

        Boolean bValue = parse(Boolean.class, "true");
        System.out.println("Boolean="+bValue);
    }
}
