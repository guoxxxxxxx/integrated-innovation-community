/**
 * @Time: 2025/1/4 17:40
 * @Author: guoxun
 * @File: JwtAuthenticationFilter
 * @Description:
 */

package com.iecas.communityauth.filter;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.iecas.communityauth.entity.LoginUserInfo;
import com.iecas.communityauth.utils.JwtUtils;
import com.iecas.communitycommon.constant.RedisPrefix;
import com.iecas.communitycommon.model.auth.entity.AuthUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // get token from header
        String token = request.getHeader("token");
        if (!StringUtils.hasLength(token)) {
            String bearerToken = request.getHeader("Authorization");
            if (StringUtils.hasLength(bearerToken) && bearerToken.startsWith("Bearer ")) {
                token = bearerToken.substring(7);
            }
        }
        // if token not in header, current user will be set anonymous.
        if (!StringUtils.hasLength(token)){
            SecurityContextHolder.clearContext();
            filterChain.doFilter(request, response);
            return;
        }
        else {
            String username;
            Claims claims = JwtUtils.parseToken(token);
            username = claims.getSubject();
            String userToken = stringRedisTemplate.opsForValue().get(RedisPrefix.AUTH_LOGIN_TOKEN.getPath(username));
            if (!StringUtils.hasLength(userToken)){
                logger.debug("current token maybe is expired.");
                throw new ExpiredJwtException(null, null, "the token is expired");
            }
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(claims.get("data")));
            UserDetails loginUserInfo = userDetailsService.loadUserByUsername(jsonObject.getString("email"));
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(loginUserInfo
                    , loginUserInfo.getPassword(), loginUserInfo.getAuthorities()));
        }
        filterChain.doFilter(request, response);
    }
}
