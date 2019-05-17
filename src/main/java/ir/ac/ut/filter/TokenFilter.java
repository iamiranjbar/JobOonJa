package ir.ac.ut.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureException;
import ir.ac.ut.jwt.Jwt;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class TokenFilter extends GenericFilterBean {

    private ArrayList<String> excludedUrls = new ArrayList<String>() {
        {
            add("login");
            add("signup");
            add("validate");
        }
    };

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final String authHeader = request.getHeader("authorization");

        String path =  request.getServletPath();
        String[] pathParts = path.split("/");
        if(pathParts.length > 0 && excludedUrls.contains(pathParts[1])) {
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(servletRequest, servletResponse);
        }
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            if (authHeader == null) {
                System.out.println("null");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().println("header is null.");
                return;
            }

            if (!authHeader.startsWith("Bearer ")) {
                System.out.println("bad header");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().println("header style is not good.");
                return;
            }

            final String token = authHeader.substring(7);
            System.out.println(token);

            try {
                final Claims claims = Jwt.decodeJWT(token);
                request.setAttribute("claims", claims);
            } catch (final SignatureException e) {
                System.out.println("bad header");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().println("SignatureException.");
                return;
            }

            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

//    @Override
//    public void destroy() {
//
//    }
//
//    public void init(FilterConfig filterConfig) {
//        String excludePattern = filterConfig.getInitParameter("excludedUrls");
//        excludedUrls = (ArrayList<String>) Arrays.asList(excludePattern.split(","));
//        for( String a : excludedUrls)
//            System.out.println(a);
//    }

}
