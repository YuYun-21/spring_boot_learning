package com.yuyun.filter;

import com.yuyun.utils.JwtUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import static com.yuyun.utils.JwtUtils.ROLE;

/**
 * @author hyh
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final PathMatcher pathmatcher = new AntPathMatcher();
    private String[] protectUrlPattern = {"/manage/**", "/member/**", "/auth/**"}; //哪  些请求需要进行安全校验

    public JwtAuthenticationFilter() {

    }


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
//是不是可以在这里做多种方式登录呢


        try {
            if (isProtectedUrl(httpServletRequest)) {
                Map<String, Object> claims = JwtUtils.validateTokenAndGetClaims(httpServletRequest);
                String role = String.valueOf(claims.get(ROLE));
                String userid = String.valueOf(claims.get("userid"));
                //最关键的部分就是这里, 我们直接注入了
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                        userid, null, Arrays.asList(() -> role)
                ));

            }
        } catch (Exception e) {
            e.printStackTrace();
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return;
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);


    }

    //是否是保护连接
    private boolean isProtectedUrl(HttpServletRequest request) {

        boolean flag = false;
        for (int i = 0; i < protectUrlPattern.length; i++) {
            if (pathmatcher.match(protectUrlPattern[i], request.getServletPath())) {
                return true;
            }
        }
        return false;
    }
}
