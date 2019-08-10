package api.admin.article;

import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.sun.org.apache.xpath.internal.operations.Number;
import dto.ArticleDTO;
import entity.Article;
import entity.ArticleTypeToJSON;
import entity.Category;
import entity.ResponseJson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class ArticleApi extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ArticleApi.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ArticleDTO> articleDTOList = new ArrayList<>();
        String strLimit = req.getParameter("limit");
        String strStatus = req.getParameter("status");
        int limit = 9;
        int status = 1;
        if(!strLimit.isEmpty()){
            try{
                limit = Integer.parseInt(strLimit);
            }catch (NumberFormatException ex){
                LOGGER.log(Level.WARNING, ex.getMessage());
            }
        }

        if (!strStatus.isEmpty()){
            try{
                status = Integer.parseInt(strStatus);
            }catch (NumberFormatException ex){
                LOGGER.log(Level.WARNING, ex.getMessage());
            }
        }

        List<Article> articleList = ofy().load().type(Article.class)
                .limit(limit)
                .filter("status", status)
                .list();
        for (Article article : articleList) {
            articleDTOList.add(new ArticleDTO(article));
        }
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                .setStatus(HttpServletResponse.SC_OK)
                .setMessage(StringUtil.SUCCESS_MSG)
                .setObj(articleDTOList)
                .build().parserToJson());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArticleTypeToJSON article = new Gson().fromJson(StringUtil.convertInputStreamToString(req.getInputStream()), ArticleTypeToJSON.class);
        Document document = Jsoup.connect(article.getUrl()).ignoreContentType(true).get();
//        System.out.println(document.html());
        String title = document.select(article.getTitle()).text();
        String description = document.select(article.getDescription()).text();
        String content = document.select(article.getContent()).text() ;
        String author = document.select(article.getAuthor()).text();
        String thumbnail = document.select(article.getThumbnail()).attr("src").trim();
        long categoryId = article.getCategoryId();
        if (content.isEmpty() || title.isEmpty() || description.isEmpty()){
            System.out.println("Moi thu deu null");
            return;
        }
        if (thumbnail.isEmpty()) {
            thumbnail = "https://resources.overdrive.com/wp-content/uploads/doc-thmb.jpg";
        }

        // select category với ID gửi lên == null nghĩa là
        Category categoryExist;
        if ((categoryExist = ofy().load().type(Category.class).id(categoryId).now()) == null
                || categoryExist.isDeactiveAndDeleted()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                    .setStatus(HttpServletResponse.SC_BAD_REQUEST)
                    .setMessage(StringUtil.INVALID_MSG)
                    .setObj(String.format(StringUtil.NOT_FOUND_MSG, "Danh muc"))
                    .build().parserToJson());
            return;
        }
        Article articleResult = Article.Builder.anArticle()
                .setUrl(article.getUrl())
                .setTitle(title)
                .setThumbnail(thumbnail)
                .setDescription(description)
                .setContent(content)
                .setStatus(Article.Status.DEACTIVE.getCode())
                .setCategory(Ref.create(Key.create(Category.class ,categoryId)))
                .setAuthor(author)
                .build();
        LOGGER.info(articleResult.toString());

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                .setStatus(HttpServletResponse.SC_OK)
                .setMessage(StringUtil.SUCCESS_MSG)
                .setObj(new ArticleDTO(articleResult))
                .build().parserToJson());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArticleTypeToJSON articleTypeToJSON = new Gson().fromJson(StringUtil.convertInputStreamToString(req.getInputStream()), entity.ArticleTypeToJSON.class);
        Article articleExist = ofy().load().type(Article.class).id(articleTypeToJSON.getUrl()).now();
        HashMap<String, String> map = new HashMap<>();
        if (articleTypeToJSON.checkNull() || (map = articleTypeToJSON.validArticle()).size() > 0) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                    .setStatus(HttpServletResponse.SC_BAD_REQUEST)
                    .setMessage(StringUtil.INVALID_MSG)
                    .setObj(map)
                    .build().parserToJson());
        }

        if (articleExist == null || articleExist.isDeactiveAndDeleted()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                    .setStatus(HttpServletResponse.SC_BAD_REQUEST)
                    .setMessage(StringUtil.INVALID_MSG)
                    .setObj(String.format(StringUtil.NOT_FOUND_MSG, "Bai viet"))
                    .build().parserToJson());
        }

        Category categoryExist = ofy().load().type(Category.class).id(articleTypeToJSON.getUrl()).now();

        if (categoryExist == null || categoryExist.isDeactiveAndDeleted()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                    .setStatus(HttpServletResponse.SC_BAD_REQUEST)
                    .setMessage(StringUtil.INVALID_MSG)
                    .setObj(String.format(StringUtil.NOT_FOUND_MSG, "Danh muc"))
                    .build().parserToJson());
        }

        Key<Article> key = ofy().save().entity(Article.Builder.anArticle()
                .setUrl(articleExist.getUrl())
                .setCategory(Ref.create(Key.create(Category.class, categoryExist.getId())))
                .setContent(articleTypeToJSON.getContent())
                .setThumbnail(articleTypeToJSON.getThumbnail())
                .setTitle(articleTypeToJSON.getTitle())
                .setAuthor(articleTypeToJSON.getAuthor())
                .setUpdatedAtMLS(Calendar.getInstance().getTimeInMillis())
                .build()).now();
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                .setStatus(HttpServletResponse.SC_CREATED)
                .setMessage(StringUtil.SUCCESS_MSG)
                .setObj(key)
                .build().parserToJson());

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Article article = new Gson().fromJson(StringUtil.convertInputStreamToString(req.getInputStream()), Article.class);
        Article articleExist = ofy().load().type(Article.class).id(article.getUrl()).now();
        if (articleExist == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                    .setStatus(HttpServletResponse.SC_BAD_REQUEST)
                    .setMessage(StringUtil.INVALID_MSG)
                    .setObj(String.format(StringUtil.NOT_FOUND_MSG, "Bai viet"))
                    .build().parserToJson());
        }
        ofy().save().entity(articleExist.setStatus(Article.Status.DELETED.getCode()));
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                .setStatus(HttpServletResponse.SC_OK)
                .setMessage(StringUtil.SUCCESS_MSG)
                .build().parserToJson());

    }
}
