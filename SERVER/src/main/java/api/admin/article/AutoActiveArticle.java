package api.admin.article;

import entity.Article;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class AutoActiveArticle extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Article> list = ofy().load().type(Article.class).filter("status", 0).list();
        for (Article article : list) {
            article.setStatus(Article.Status.ACTIVE.getCode());
            ofy().save().entity(article).now();
        }
        resp.getWriter().println(list.size());
    }
}
