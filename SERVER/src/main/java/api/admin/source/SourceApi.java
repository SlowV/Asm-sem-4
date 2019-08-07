package api.admin.source;

import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import entity.CrawlerSource;
import entity.ResponseJson;
import util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class SourceApi extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(SourceApi.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                .setStatus(HttpServletResponse.SC_OK)
                .setMessage(StringUtil.SUCCESS_MSG)
                .setObj(ofy().load().type(CrawlerSource.class).list())
                .build().parserToJson());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CrawlerSource source = new Gson().fromJson(StringUtil.convertInputStreamToString(req.getInputStream()), CrawlerSource.class);

        System.out.println(source.toString());
        HashMap<String, String> map = source.validSource();
        if (map.size() > 0) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                    .setStatus(HttpServletResponse.SC_BAD_REQUEST)
                    .setMessage(StringUtil.INVALID_MSG)
                    .setObj(map)
                    .build().parserToJson());
            return;
        }

        Key key = ofy().save().entity(source).now();
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                .setStatus(HttpServletResponse.SC_CREATED)
                .setMessage(StringUtil.SUCCESS_MSG)
                .setObj(key)
                .build().parserToJson());

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CrawlerSource source = new Gson().fromJson(StringUtil.convertInputStreamToString(req.getInputStream()), CrawlerSource.class);
        HashMap<String, String> errors = source.validSource();
        if (errors.size() > 0){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                    .setStatus(HttpServletResponse.SC_BAD_REQUEST)
                    .setMessage(StringUtil.INVALID_MSG)
                    .setObj(errors)
                    .build().parserToJson());
            return;
        }

        CrawlerSource sourceExist = ofy().load().type(CrawlerSource.class).id(source.getId()).now();

        if (sourceExist == null || sourceExist.isDeactiveAndDeleted()){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                    .setStatus(HttpServletResponse.SC_BAD_REQUEST)
                    .setMessage(String.format(StringUtil.NOT_FOUND_MSG, "Source url"))
                    .build().parserToJson());
            return;
        }

        sourceExist.setNewCrawlerSource(source);

        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                .setStatus(HttpServletResponse.SC_CREATED)
                .setMessage(StringUtil.SUCCESS_MSG)
                .setObj(ofy().save().entity(sourceExist).now())
                .build().parserToJson());
    }
}
