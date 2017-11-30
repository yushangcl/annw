package top.annwz.base.uitl.sql;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * HMacSHA256 加密
 * Created by huahui.wu on 2017/8/23.
 */
public class HMacSHA256 {

    public static void main(String[] args) {
        byte[] hash1 = desEncrypt("80000000000018613043419157", "CITIC1616".getBytes());
        byte[] hash2 = desEncrypt("1502351806", hash1);
        byte[] hash3 = desEncrypt("353881507", hash2);
        System.out.println(byte2hex(hash3));

    }

    /**
     * HMacSHA256 加密
     *
     * @param message String 加密内容
     * @param key     byte[] 加密密钥
     * @return
     */
    public static byte[] desEncrypt(String message, byte[] key) {
        byte[] bytes = null;
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(key, "HmacSHA256");
            sha256_HMAC.init(secret_key);
            bytes = sha256_HMAC.doFinal(message.getBytes());
            System.out.println("HashKey: " + byte2hex(bytes));
        } catch (Exception e) {
            System.out.println("Error");
        }
        return bytes;
    }

    /**
     * byte转成hex
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString();
    }
}
