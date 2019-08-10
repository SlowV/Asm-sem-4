package util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.ResourceBundle;

public class StringUtil {
    public static String QUEUE_NAME = "queue-crawl";
    public static String INVALID_MSG = "Du lieu sai!";
    public static String SUCCESS_MSG = "Thanh cong!";
    public static String NOT_FOUND_MSG = "Khong tim thay %s";
    public static String FIELD_EMPTY_ERROR_MSG = "%s khong duoc de trong.";
    public static String NOT_TOKEN = "Token không đúng hoặc hết hạn vui lòng đăng nhập lại!";
    public static String EXIST_USERNAME = "Tài khoản đã tồn tại vui lòng thử lai!";
    public static String FORMAT_EMAIL = "Email sai định dạng vui lòng thử lại!";
    public static String LENGTH_PASSWORD_ERROR = "Mật khẩu phải trong khoảng 6 ~ 20 ký tự";
    public static String LOGIN_FAIL = "Tài khoản hoặc mật khẩu sai vui lòng thử lại!";

    private static MessageDigest md;
    private static Random rnd;
    private static final String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz";
    private static final int SALT_LENGTH = 7;
    private static final String ALGORITH = "SHA-256";

    public static String convertInputStreamToString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString(StandardCharsets.UTF_8.name());
    }

    public static String generateSalt() {
        if (rnd == null) {
            rnd = new Random();
        }
        StringBuilder salt = new StringBuilder();
        while (salt.length() < SALT_LENGTH) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }

    public static String hashPassword(String password, String salt) {
        try {
            if (md == null) {
                md = MessageDigest.getInstance(ALGORITH);
            }
            md.update(password.getBytes());
            md.update(salt.getBytes());
            final byte[] hash = md.digest();
            final StringBuffer sb = new StringBuffer();
            for (int i = 0; i < hash.length; ++i) {
                sb.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ignored) {
            return "";
        }
    }

    public static boolean comparePasswordWithSalt(String inputPassword, String salt, String hashPassword) {
        return hashPassword(inputPassword, salt).equals(hashPassword);
    }

}
