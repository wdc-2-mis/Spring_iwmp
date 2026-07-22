package app.filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;

public class SecurityHeadersFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse res = (HttpServletResponse) response;

        // Permissions Policy
        res.setHeader("Permissions-Policy",
                "geolocation=(), microphone=(), camera=(), payment=(), usb=(), accelerometer=(), gyroscope=(), magnetometer=()");

        // Content Security Policy
        res.setHeader(
        	    "Content-Security-Policy",
        	    "default-src 'self'; " +

        	    "script-src 'self' " +
        	    "https://code.jquery.com " +
        	    "https://maxcdn.bootstrapcdn.com " +
        	    "https://cdnjs.cloudflare.com " +
        	    "https://ajax.aspnetcdn.com " +
        	    "'unsafe-inline'; " +

        	    "style-src 'self' " +
        	    "'unsafe-inline' " +
        	    "https://code.jquery.com " +
        	    "https://cdnjs.cloudflare.com " +
        	    "https://maxcdn.bootstrapcdn.com; " +

        	    "img-src 'self' data:; " +

        	    "font-src 'self' data: https://cdnjs.cloudflare.com; " +

        	    "connect-src 'self'; " +

        	    "object-src 'none'; " +

        	    "base-uri 'self'; " +

        	    "frame-ancestors 'self'; " +

        	    "form-action 'self';"
        	);

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) { }

    @Override
    public void destroy() { }
}