package ir.ac.ut.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureException;
import ir.ac.ut.jwt.Jwt;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final String authHeader = request.getHeader("authorization");

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            if (authHeader == null) {
                System.out.println("null");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().println("header is null.");
            }

            if (!authHeader.startsWith("Bearer ")) {
                System.out.println("bad header");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().println("header style is not good.");
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
            }

            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
