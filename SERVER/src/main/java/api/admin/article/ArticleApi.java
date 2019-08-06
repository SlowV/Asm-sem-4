package api.admin.article;

import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import dto.ArticleDTO;
import entity.Article;
import entity.Category;
import entity.ResponseJson;
import util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class ArticleApi extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ArticleApi.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {



        List<Article> articleList = ofy().load().type(Article.class).list();
        List<ArticleDTO> articleDTOList = new ArrayList<>();
        for ( Article article : articleList) {
            articleDTOList.add(new ArticleDTO(article));
        }
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(new Gson().toJson(articleDTOList));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Article article = new Gson().fromJson(StringUtil.convertInputStreamToString(req.getInputStream()),Article.class);

        HashMap<String, String> map = article.validArticle();
        // Nếu size > 0 nghĩa là có lỗi.
        if (map.size() > 0){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                    .setStauts(HttpServletResponse.SC_BAD_REQUEST)
                    .setMessage("Du lieu sai!")
                    .setObj(map)
                    .build().parserToJson());
        }

        // select category với ID gửi lên == null nghĩa là
        Category categoryExist;
        if (( categoryExist = ofy().load().type(Category.class).id(article.getCategory().get().getId()).now())== null
                || categoryExist.isDeactiveAndDeleted()){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                    .setStauts(HttpServletResponse.SC_BAD_REQUEST)
                    .setMessage(StringUtil.INVALID_MSG)
                    .setObj(String.format(StringUtil.NOT_FOUND_MSG, "Danh muc"))
                    .build().parserToJson());
        }
        assert categoryExist != null;
        article.setCategory(Ref.create(Key.create(Category.class, categoryExist.getId())));

        resp.setStatus(HttpServletResponse.SC_CREATED);
        Key<Article> articleKey =  ofy().save().entity(article).now();
        resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                .setStauts(HttpServletResponse.SC_CREATED)
                .setMessage(StringUtil.SUCCESS_MSG)
                .setObj(articleKey)
                .build().parserToJson());
    }
}
