package api.admin.article.filter;

import com.googlecode.objectify.cmd.Query;
import entity.Article;
import entity.ResponseJson;
import util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class FilterByDateRange extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long startDateMLS = convertDateToLong("25/5/1990 00:00:00");
        long endDateMLS = Calendar.getInstance().getTimeInMillis();
        Query<Article> query = ofy().load().type(Article.class);
        String strStartDate = req.getParameter("startDate");
        String strEndDate = req.getParameter("endDate");

        if (strStartDate != null && strEndDate != null) {
            try {
                startDateMLS = convertDateToLong(strStartDate);
                endDateMLS = convertDateToLong(strEndDate);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        List<Article> articles =  query.filter("createdAtMLS >=", startDateMLS).filter("createdAtMLS < ", endDateMLS).list();
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                .setStatus(HttpServletResponse.SC_OK)
                .setMessage(StringUtil.SUCCESS_MSG)
                .setObj(articles)
                .build().parserToJson());
    }

    public static long convertDateToLong(String strDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert date != null;
        return  date.getTime();
    }

//
//    public static void main(String[] args) throws ParseException {
//        System.out.println(convertDateToLong("25/5/1990"));
//    }
}
