package co.com.linktic.products.config;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    @Value("${security.api.key}")
    private String apiKey;

    /**
     * Filters incoming HTTP requests and validates the API key for requests targeting API endpoints. Unauthorized requests
     * without a valid API key will result in a 401 response status.
     *
     * @param request the HttpServletRequest object that contains the request the client has made to the servlet
     * @param response the HttpServletResponse object that contains the response the servlet sends to the client
     * @param filterChain the FilterChain object that allows the filter to pass on the request and response to the next entity in the chain
     * @throws ServletException if an exception occurs that interferes with the filter's normal operation
     * @throws IOException if an input or output error occurs while processing the filter chain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (request.getRequestURI().startsWith("/api")) {
            String header = request.getHeader("x-api-key");
            if (!apiKey.equals(header)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid API Key");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
