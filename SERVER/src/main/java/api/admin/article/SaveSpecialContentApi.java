package api.admin.article;

import com.google.gson.Gson;
import dto.ArticleDTO;
import entity.Article;
import entity.ArticleTypeToJSON;
import entity.ResponseJson;
import util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class SaveSpecialContentApi extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArticleTypeToJSON articleTypeToJSON = new Gson().fromJson(StringUtil.convertInputStreamToString(req.getInputStream()), ArticleTypeToJSON.class);
        Article article = articleTypeToJSON.convertToArticle();
        ofy().save().entity(article).now();
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                .setStatus(HttpServletResponse.SC_CREATED)
                .setMessage(StringUtil.SUCCESS_MSG)
                .setObj(new ArticleDTO(article))
                .build().parserToJson());
    }
}
