package code.classType;

public class DynamicConvertTest {

    public static void main(String[] args) {
        DynamicConvertTest test = new DynamicConvertTest(Double.class);
        test.run("4.42");

        DynamicConvertTest test2 = new DynamicConvertTest(Long.class);
        test2.run("4000");

        DynamicConvertTest test3 = new DynamicConvertTest(Integer.class);
        test3.run("4.42");
    }

    public DynamicConvertTest(Class class1) {
        m_classType = class1;
    }

    private void run(String toConvert) {
        System.out.println(DynamicConvert.parse(m_classType, toConvert));
    }

    private final Class m_classType;
}
