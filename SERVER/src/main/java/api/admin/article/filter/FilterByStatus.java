package api.admin.article.filter;

import entity.Article;
import entity.ResponseJson;
import util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class FilterByStatus extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String strStatus = null;
        try {
            strStatus = req.getParameter("status");
        }catch (Exception e){
            e.printStackTrace();
        }

        if (strStatus != null){
            int status = 1;
            try{
                status = Integer.parseInt(strStatus);
            }catch (NumberFormatException e){
                e.printStackTrace();
                return;
            }
            List<Article> articles = ofy().load().type(Article.class)
                    .filter("status", status)
                    .order("-createdAtMLS").list();
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                    .setStatus(HttpServletResponse.SC_OK)
                    .setMessage(StringUtil.SUCCESS_MSG)
                    .setObj(articles)
                    .build().parserToJson());
        }else{
            List<Article> articles = ofy().load().type(Article.class)
                    .order("-createdAtMLS").list();
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                    .setStatus(HttpServletResponse.SC_OK)
                    .setMessage(StringUtil.SUCCESS_MSG)
                    .setObj(articles)
                    .build().parserToJson());
        }

    }
}
