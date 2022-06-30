package wiki.jixing.myblog.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtils {
    public static String md5Encode(String data) {
        try {
            StringBuilder msg = new StringBuilder();
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] dataBytes = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            for (byte b : dataBytes) {
                int c = b & 0xff;
                if (c < 16) {
                    msg.append("0");
                }
                msg.append(Integer.toHexString(c));
            }
            return msg.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("MD5加密失败");
        }
    }
}
