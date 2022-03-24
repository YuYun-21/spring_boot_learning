package com.yuyun.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hyh
 */
public class JwtUtils {

    private static final Logger logger= LoggerFactory.getLogger(JwtUtils.class);
    public static  final long EXPIRATION_TIME=60*60*1000;// 令牌环有效期
    public static final String SECRET="abc123456def";//令牌环密钥
    public static final String TOKEN_PREFIX="Bearer";//令牌环头标识
    public static final String HEADER_STRING="Passport";//配置令牌环在http heads中的键值
    public static final String ROLE="ROLE";//自定义字段-角色字段

    //生成令牌环
    public static String generateToken(String userRole,String userid){
        HashMap<String,Object> map=new HashMap<>();
        map.put(ROLE,userRole);
        map.put("userid",userid);
        String jwt= Jwts.builder()
                .setClaims(map)
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512,SECRET)
                .compact();
        return TOKEN_PREFIX+" "+jwt;
    }
    //生成令牌环
    public static String generateToken(String userRole,String userid,long exprationtime){
        HashMap<String,Object> map=new HashMap<>();
        map.put(ROLE,userRole);
        map.put("userid",userid);
        String jwt= Jwts.builder()
                .setClaims(map)
                .setExpiration(new Date(System.currentTimeMillis()+exprationtime))
                .signWith(SignatureAlgorithm.HS512,SECRET)
                .compact();
        return TOKEN_PREFIX+" "+jwt;
    }

    //令牌环校验
    public static Map<String,Object> validateTokenAndGetClaims(HttpServletRequest request){
        String token=request.getHeader(HEADER_STRING);
        if(token==null){
            throw new TokenValidationException("Missing Token");

        }
        else{
            Map<String,Object> body= Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX,""))
                    .getBody();
            return body;
        }
    }


    static class TokenValidationException extends RuntimeException{
        public TokenValidationException(String msg){
            super(msg);
        }
    }
}
