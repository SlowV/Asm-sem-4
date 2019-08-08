package api.admin.account;

import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import entity.Account;
import entity.Category;
import entity.ResponseJson;
import entity.Role;
import util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class AccountApi extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Account account = new Gson().fromJson(StringUtil.convertInputStreamToString(req.getInputStream()), Account.class);
        Account accountExist = ofy().load().type(Account.class).id(account.getUsername()).now();
        if (accountExist != null){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                    .setStatus(HttpServletResponse.SC_BAD_REQUEST)
                    .setMessage(StringUtil.EXIST_USERNAME)
                    .build().parserToJson());
            return;
        }

        HashMap<String, String> errors = account.validAccount();
        if (errors.size() > 0){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                    .setStatus(HttpServletResponse.SC_BAD_REQUEST)
                    .setMessage(StringUtil.INVALID_MSG)
                    .setObj(errors)
                    .build().parserToJson());
            return;
        }
        account.setPasswordHash(account.hashPassword(account.getPasswordHash()));
//        account.setRole(Ref.create(Key.create(Role.class, ofy().load().type(Role.class).list().get(0).getId())));
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().println(ResponseJson.Builder.aResponseJson()
                .setStatus(HttpServletResponse.SC_CREATED)
                .setMessage(StringUtil.SUCCESS_MSG)
                .setObj(ofy().save().entity(account).now())
                .build().parserToJson());
    }

}
