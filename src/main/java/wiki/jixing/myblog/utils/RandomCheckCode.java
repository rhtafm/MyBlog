package wiki.jixing.myblog.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomCheckCode {
    public static String getCheckCode() {
        char[] s = "0123456789abcdefghigklmnopqrstuvwxyz".toCharArray();
        Random random = new Random();
        StringBuilder checkCode = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int ind = random.nextInt(36);
            checkCode.append(s[ind]);
        }
        return checkCode.toString();
    }
}
