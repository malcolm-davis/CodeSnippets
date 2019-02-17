package code.bytearray;

import org.apache.commons.codec.binary.StringUtils;

public class SampleBtyeArray {
    public static void main(String[] args) {
        String example = "This is an example";
        byte[] bytes = example.getBytes();

        System.out.println("Text : " + example);
        System.out.println("Text [Byte Format] : " + bytes);
        System.out.println("Text [Byte Format] : " + bytes.toString());

        String s = new String(bytes);
        System.out.println("Text Decryted : " + s);

        System.out.println("===================================================");
        byte[]bytes2 = StringUtils.getBytesUtf8(example);
        System.out.println(StringUtils.newStringUtf8(bytes2));
    }
}
