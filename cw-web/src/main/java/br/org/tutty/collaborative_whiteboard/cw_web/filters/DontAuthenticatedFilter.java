package br.org.tutty.collaborative_whiteboard.cw_web.filters;

import br.org.tutty.collaborative_whiteboard.cw.service.SecurityService;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by drferreira on 17/12/14.
 */
@WebFilter(
        urlPatterns = "/pages/index.jsf",
        dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ASYNC}
)
public class DontAuthenticatedFilter implements Filter {

    private static final String HOME_LOGIN = "/pages/session/whiteboard.jsf";

    @Inject
    private SecurityService securityService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Implementar LOG
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (securityService.isLogged(httpServletRequest.getSession())) {
            String contextPath = httpServletRequest.getContextPath();
            httpServletResponse.sendRedirect(contextPath + HOME_LOGIN);
        }else {
            chain.doFilter(request, response);
            return;
        }
    }

    @Override
    public void destroy() {
        // TODO Implementar LOG
    }
}
