package api.admin.account;

import com.google.gson.Gson;
import dto.AccountDTO;
import entity.Account;
import entity.ResponseJson;
import util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Calendar;

public class LoginApi extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Account account = new Gson().fromJson(StringUtil.convertInputStreamToString(req.getInputStream()), Account.class);
        Account accountResult = account.login();
        if (accountResult != null) {
            long token = Calendar.getInstance().getTimeInMillis();
            HttpSession session = req.getSession();
//            session.setAttribute("currentLogin", accountResult);
            session.setAttribute("token", token);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                    .setStatus(HttpServletResponse.SC_OK)
                    .setMessage(StringUtil.SUCCESS_MSG)
                    .setObj(new AccountDTO(accountResult, token))
                    .build().parserToJson());
        }

        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                .setStatus(HttpServletResponse.SC_BAD_REQUEST)
                .setMessage(StringUtil.LOGIN_FAIL)
                .build().parserToJson());
    }
}
