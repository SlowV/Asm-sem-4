package api.admin.category;

import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import entity.Category;
import entity.ResponseJson;
import util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class CategoryApi extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(CategoryApi.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        List<Category> list = ofy().load().type(Category.class).list();
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                .setStauts(HttpServletResponse.SC_OK)
                .setMessage(StringUtil.SUCCESS_MSG)
                .setObj(list)
                .build().parserToJson());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Category category =
                new Gson().fromJson(StringUtil.convertInputStreamToString(req.getInputStream()), Category.class);
//        System.out.println(category);
        HashMap<String, String> map = category.validCategory();
        if (map.size() > 0){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                    .setStauts(HttpServletResponse.SC_BAD_REQUEST)
                    .setMessage(StringUtil.INVALID_MSG)
                    .setObj(map)
                    .build().parserToJson());
        }
        LOGGER.warning(category.toString());
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                .setStauts(HttpServletResponse.SC_CREATED)
                .setMessage(StringUtil.SUCCESS_MSG)
                .setObj(ofy().save().entity(category).now())
                .build().parserToJson());
    }


}
