package util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class StringUtil {
    public static String QUEUE_NAME = "queue-crawl-source";
    public static String INVALID_MSG = "Du lieu sai!";
    public static String SUCCESS_MSG = "Thanh cong!";
    public static String NOT_FOUND_MSG = "Khong tim thay %s";
    public static String FIELD_EMPTY_ERROR_MSG = "%s khong duoc de trong.";

//    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("language");

    public static String convertInputStreamToString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString(StandardCharsets.UTF_8.name());
    }

//    public static String getBundle(String key) {
//        return resourceBundle.getString(key);
//    }
//
//    public static void changeLanguage(HttpServletRequest request) {
//        resourceBundle = ResourceBundle.getBundle("language");
//        ServletContext context = request.getSession().getServletContext();
//        context.setAttribute("bundle", resourceBundle);
//    }

}
