package api.crawler;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.gson.Gson;
import entity.Article;
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
import java.util.List;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class AddTaskToQueue extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(AddTaskToQueue.class.getSimpleName());

    private static Queue q = QueueFactory.getQueue(StringUtil.QUEUE_NAME);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CrawlerSource> crawlerSources = ofy().load().type(CrawlerSource.class).filter("status", 1).list();
        if (crawlerSources.size() == 0) {
            LOGGER.warning("crawlerSources size: " + crawlerSources.size());
            return;
        }

        for (CrawlerSource crawlerSource : crawlerSources) {
            Document document = Jsoup.connect(crawlerSource.getUrl()).ignoreContentType(true).get();
            LOGGER.info(crawlerSource.getUrl());
            Elements elements = document.select(crawlerSource.getLinkSelector());
            if (elements.size() == 0) return;
            int count = 0;
            for (Element el : elements) {
//                if (count == crawlerSource.getLinkLimit()) break;
                // check link limit.
                String link = el.attr("href").trim();
                LOGGER.info(link);
                if (!link.isEmpty()){
                    Article article = Article.Builder.anArticle()
                            .setUrl(link)
                            .setSourceId(crawlerSource.getId())
                            .build();
                    q.add(TaskOptions.Builder.withMethod(TaskOptions.Method.PULL).payload(new Gson().toJson(article)));
//                    count++;
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
