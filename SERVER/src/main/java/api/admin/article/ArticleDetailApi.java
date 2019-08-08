package api.admin.article;

import com.google.gson.Gson;
import entity.Article;
import entity.Category;
import entity.ResponseJson;
import util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class ArticleDetailApi extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String articleId = req.getParameter("id");

        if (articleId == null || "".equals(articleId)){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                    .setStatus(HttpServletResponse.SC_BAD_REQUEST)
                    .setMessage(StringUtil.INVALID_MSG)
                    .build().parserToJson());
        }else{
            Article article = ofy().load().type(Article.class).id(articleId).now();

            if (article == null || article.isDeactiveAndDeleted()){
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                        .setStatus(HttpServletResponse.SC_BAD_REQUEST)
                        .setMessage(String.format(StringUtil.NOT_FOUND_MSG, "ID Bai viet"))
                        .build().parserToJson());
            }else{
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                        .setStatus(HttpServletResponse.SC_OK)
                        .setMessage(StringUtil.SUCCESS_MSG)
                        .setObj(article)
                        .build().parserToJson());
            }
        }
    }
}
