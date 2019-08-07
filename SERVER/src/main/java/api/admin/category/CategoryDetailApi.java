package api.admin.category;

import entity.Category;
import entity.ResponseJson;
import util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class CategoryDetailApi extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(CategoryDetailApi.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String strCategoryId = req.getParameter("id");
        long categoryId = 0;
        if (strCategoryId == null || "".equals(strCategoryId)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                    .setStatus(HttpServletResponse.SC_BAD_REQUEST)
                    .setMessage(StringUtil.INVALID_MSG)
                    .build().parserToJson());
        } else {
            try {
                categoryId = Long.parseLong(strCategoryId);
            } catch (NumberFormatException ex) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                        .setStatus(HttpServletResponse.SC_BAD_REQUEST)
                        .setMessage(StringUtil.INVALID_MSG)
                        .build().parserToJson());
                LOGGER.log(Level.WARNING, String.format("ERROR: %s", ex.getMessage()));
            }

            Category category = ofy().load().type(Category.class).id(categoryId).now();
            if (category == null || category.isDeactiveAndDeleted()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                        .setStatus(HttpServletResponse.SC_BAD_REQUEST)
                        .setMessage(String.format(StringUtil.NOT_FOUND_MSG, "ID Danh muc"))
                        .build().parserToJson());
            } else {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                        .setStatus(HttpServletResponse.SC_OK)
                        .setMessage(StringUtil.SUCCESS_MSG)
                        .setObj(category)
                        .build().parserToJson());
            }
        }
    }
}
