package com.github.mouday.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.github.mouday.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检查用户是否已登录
 */
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

    // 路径匹配器
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    // 白名单
    private static String[] whiteListUrls = new String[]{
            "/employee/login",
            "/employee/logout",
            "/backend/**",
            "/front/**",
    };

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;


        // 1、获取请求uri
        String requestURI = request.getRequestURI();
        log.info("LoginCheckFilter requestURI: {}", requestURI);


        // 2、白名单放行
        if (this.isWhiteUrl(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3、非白名单登录后放行
        if(request.getSession().getAttribute("employee") != null){
            filterChain.doFilter(request, response);
            return;
        }

        // 4、未登录，返回与前端约定好跳转登录的标识
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));

    }

    /**
     * 检查路径是否是白名单
     *
     * @param url
     * @return
     */
    private boolean isWhiteUrl(String url) {
        for (String whiteUrl : whiteListUrls) {
            boolean match = PATH_MATCHER.match(whiteUrl, url);
            if (match) {
                return true;
            }

        }

        return false;
    }
}
