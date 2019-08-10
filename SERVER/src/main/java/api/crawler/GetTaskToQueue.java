package api.crawler;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskHandle;
import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import entity.Article;
import entity.Category;
import entity.CrawlerSource;
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
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class GetTaskToQueue extends HttpServlet {
    private static Queue q = QueueFactory.getQueue(StringUtil.QUEUE_NAME);
    private static final Logger LOGGER = Logger.getLogger(GetTaskToQueue.class.getSimpleName());
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<TaskHandle> tasks = q.leaseTasks(10, TimeUnit.SECONDS, 1);
        if (tasks.size() > 0) {
            TaskHandle taskHandle = tasks.get(0);
            String articleObjectJson = new String(taskHandle.getPayload());
            Article article = new Gson().fromJson(articleObjectJson, Article.class);
            CrawlerSource crawlerSource = ofy().load().type(CrawlerSource.class).id(article.getSourceId()).now();
            if (crawlerSource == null) {
                return;
            }

            Document document = Jsoup.connect(article.getUrl()).ignoreContentType(true).get();
            String title = document.select(crawlerSource.getTitleSelector()).text();
            String description = document.select(crawlerSource.getDescriptionSelector()).text();
            StringBuilder content = new StringBuilder();
            Elements elements = document.select(crawlerSource.getContentSelector());
            for (Element element: elements) {
                content.append(element.text());
            }

            String author = document.select(crawlerSource.getAuthorSelector()).text();
            String thumbnail = document.select(crawlerSource.getThumbnailSelector()).attr("src").trim();
            if (content.toString().isEmpty() || title.isEmpty() || description.isEmpty()) return;
            if (thumbnail.isEmpty()) {
                thumbnail = "https://resources.overdrive.com/wp-content/uploads/doc-thmb.jpg";
            }
            // tu dong tao moi category neu chua co! => chuc nag dang phat trien, tam thoi fix cung truoc.
            article.setTitle(title).setDescription(description).setContent(content.toString())
                    .setAuthor(author).setThumbnail(thumbnail).setStatus(Article.Status.DEACTIVE.getCode())
                    .setCreatedAtMLS(Calendar.getInstance().getTimeInMillis()).setUpdatedAtMLS(Calendar.getInstance().getTimeInMillis())
                    .setCategory(Ref.create(Key.create(Category.class, ofy().load().type(Category.class).list().get(0).getId())));
            LOGGER.info(article.toString());
            ofy().save().entity(article).now();
        }
    }
}
