package api.admin.filterApi;


import com.google.appengine.api.search.Query;
import com.google.appengine.api.search.*;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;
import static com.googlecode.objectify.ObjectifyService.ofy;

public class SearchApi extends HttpServlet {
    public static Index getIndex(String indexName) {
        IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName).build();
        return SearchServiceFactory.getSearchService().getIndex(indexSpec);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Index index = getIndex("test");
        Document doc = Document.newBuilder()
                .setId("A001")
                .addField(Field.newBuilder().setName("name").setText("some text"))
                .build();
        Document doc3 = Document.newBuilder()
                .setId("A001")
                .addField(Field.newBuilder().setName("name").setText("slowV dsaadwadswew"))
                .build();

        Document doc2 = Document.newBuilder()
                .setId("A002")
                .addField(Field.newBuilder().setName("title").setText("some title"))
                .build();

        index.put(doc);
        index.put(doc2);
        index.put(doc3);
        Document document = index.get("A001");
        Results<ScoredDocument> results = index.search("slow");
        for (ScoredDocument  scoredDocument : results) {
            System.out.println(scoredDocument.getOnlyField("name"));
        }
    }
}
