package filter;

import entity.ResponseJson;
import util.StringUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        if (request.getMethod().equals("POST") || request.getMethod().equals("PUT") || request.getMethod().equals("DELETE")){
            HttpSession session = request.getSession();
            String token = request.getHeader("Authorization");
            if (token != null){
                if (token.contains("Basic ")){
                    String[] splitted = token.split("\\s{1,}");
                    System.out.println(splitted[1]);
                    if (session.getAttribute("token").equals(splitted[1])){
                        filterChain.doFilter(servletRequest, servletResponse);
                    }else{
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.getWriter().println(ResponseJson.Builder.aResponseJson()
                                .setStatus(HttpServletResponse.SC_UNAUTHORIZED)
                                .setMessage(StringUtil.NOT_TOKEN)
                                .build().parserToJson());
                    }

                }else{
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().println(ResponseJson.Builder.aResponseJson()
                            .setStatus(HttpServletResponse.SC_UNAUTHORIZED)
                            .setMessage(StringUtil.NOT_TOKEN)
                            .build().parserToJson());
                }
            }else{
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().println(ResponseJson.Builder.aResponseJson()
                        .setStatus(HttpServletResponse.SC_UNAUTHORIZED)
                        .setMessage(StringUtil.NOT_TOKEN)
                        .build().parserToJson());
            }
        }else{
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }

    @Override
    public void destroy() {

    }
}
