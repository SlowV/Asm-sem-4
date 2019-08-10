package api.lang;

import entity.ResponseJson;
import util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class LanguageApi extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(StringUtil.convertInputStreamToString(Objects.requireNonNull(getClass()
                .getClassLoader()
                .getResourceAsStream("language.json"))));
    }

//    public static void main(String[] args) {
//        LanguageApi languageApi = new LanguageApi();
//
//        languageApi.getFile();
//    }
//
//    public void getFile(){
//        String jsonContent = null;
//        try {
//            jsonContent = StringUtil.convertInputStreamToString(Objects.requireNonNull(getClass()
//                    .getClassLoader()
//                    .getResourceAsStream("language.json")));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println(jsonContent);
//    }
}
