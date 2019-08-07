package api.admin.category;

import com.google.gson.Gson;
import com.googlecode.objectify.cmd.LoadType;
import entity.Category;
import entity.ResponseJson;
import util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class CategoryApi extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(CategoryApi.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoadType<Category> categoryLoadType = ofy().load().type(Category.class);
        String strStatus = req.getParameter("status");
        int status = 100;
        List<Category> list;
        if (strStatus != null) {
            try {
                status = Integer.parseInt(strStatus);
            } catch (NumberFormatException ex) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                        .setStatus(HttpServletResponse.SC_BAD_REQUEST)
                        .setMessage(StringUtil.INVALID_MSG)
                        .build().parserToJson());
                LOGGER.log(Level.WARNING, String.format("ERROR: %s", ex.getMessage()));
            }
            System.out.println("Status: " + status);
            if (status == Category.Status.ACTIVE.getCode()) {
                System.out.println("Vao day r ma!");
                list = categoryLoadType.filter("status", Category.Status.ACTIVE.getCode()).list();
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                        .setStatus(HttpServletResponse.SC_OK)
                        .setMessage(StringUtil.SUCCESS_MSG)
                        .setObj(list)
                        .build().parserToJson());
                return;
            }else if (status == Category.Status.DELETED.getCode()) {
                list = categoryLoadType.filter("status", Category.Status.DELETED.getCode()).list();
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                        .setStatus(HttpServletResponse.SC_OK)
                        .setMessage(StringUtil.SUCCESS_MSG)
                        .setObj(list)
                        .build().parserToJson());
                return;
            }else if (status == Category.Status.DEACTIVE.getCode()) {
                list = categoryLoadType.filter("status", Category.Status.DEACTIVE.getCode()).list();
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                        .setStatus(HttpServletResponse.SC_OK)
                        .setMessage(StringUtil.SUCCESS_MSG)
                        .setObj(list)
                        .build().parserToJson());
                return;
            }

            list = categoryLoadType.list();
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                    .setStatus(HttpServletResponse.SC_OK)
                    .setMessage(StringUtil.SUCCESS_MSG)
                    .setObj(list)
                    .build().parserToJson());

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Category category =
                new Gson().fromJson(StringUtil.convertInputStreamToString(req.getInputStream()), Category.class);
//        System.out.println(category);
        HashMap<String, String> map = category.validCategory();
        if (map.size() > 0) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                    .setStatus(HttpServletResponse.SC_BAD_REQUEST)
                    .setMessage(StringUtil.INVALID_MSG)
                    .setObj(map)
                    .build().parserToJson());
        }
        LOGGER.warning(category.toString());
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                .setStatus(HttpServletResponse.SC_CREATED)
                .setMessage(StringUtil.SUCCESS_MSG)
                .setObj(ofy().save().entity(category).now())
                .build().parserToJson());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Category category = new Gson().fromJson(StringUtil.convertInputStreamToString(req.getInputStream()), Category.class);
        Category categoryExist = ofy().load()
                .type(Category.class)
                .id(category.getId()).now();

        if (categoryExist == null || categoryExist.isDeactiveAndDeleted()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                    .setStatus(HttpServletResponse.SC_BAD_REQUEST)
                    .setMessage(String.format(StringUtil.NOT_FOUND_MSG, "ID Danh muc"))
                    .build().parserToJson());
        }

        assert categoryExist != null;
        categoryExist.setName(category.getName());
        categoryExist.setDescription(category.getDescription());
        categoryExist.setUpdatedAtMLS(Calendar.getInstance().getTimeInMillis());

        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                .setStatus(HttpServletResponse.SC_CREATED)
                .setMessage(StringUtil.SUCCESS_MSG)
                .setObj(ofy().save().entity(category).now())
                .build().parserToJson());
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long categoryId = new Gson().fromJson(StringUtil.convertInputStreamToString(req.getInputStream()), Category.class).getId();
        if (categoryId == 0) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                    .setStatus(HttpServletResponse.SC_BAD_REQUEST)
                    .setMessage(StringUtil.INVALID_MSG)
                    .build().parserToJson());
        }
        System.out.println(categoryId);

        Category categoryExist = ofy().load().type(Category.class).id(categoryId).now();
//        if (categoryExist == null){
//            System.out.println("Null");
//            return;
//        }
        LOGGER.log(Level.WARNING, categoryExist.toString());
        if (categoryExist == null || categoryExist.isDeactiveAndDeleted()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                    .setStatus(HttpServletResponse.SC_BAD_REQUEST)
                    .setMessage(String.format(StringUtil.NOT_FOUND_MSG, "Danh muc"))
                    .build().parserToJson());
        }

        assert categoryExist != null;
        categoryExist.setStatus(Category.Status.DELETED.getCode()).setDeletedAtMLS(Calendar.getInstance().getTimeInMillis());
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                .setStatus(HttpServletResponse.SC_OK)
                .setMessage(StringUtil.SUCCESS_MSG)
                .setObj(ofy().save().entity(categoryExist).now())
                .build().parserToJson());
    }
}
