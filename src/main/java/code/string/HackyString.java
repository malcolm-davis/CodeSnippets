package code.string;

import sun.misc.SharedSecrets;
// import jdk.internal.misc.*;

public class HackyString {

    public static void main(String[] args) {
        char[] chars = "hello world".toCharArray();

        String s1 = new String(chars);
        String s2 = SharedSecrets.getJavaLangAccess().newStringUnsafe(chars);

        System.out.println(s1);
        System.out.println(s2);

        System.out.println("Changing first letter to capital");
        chars[0] = 'H';
        System.out.println(s1);
        System.out.println(s2);
    }

}
