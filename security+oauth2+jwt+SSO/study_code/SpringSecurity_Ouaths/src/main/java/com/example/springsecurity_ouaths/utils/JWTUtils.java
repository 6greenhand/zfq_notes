package com.example.springsecurity_ouaths.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于生成和解析JWT
 */
public class JWTUtils {

    /**
     * 声明一个秘钥
     */
    private static final String SECRET = "zfq";


    /**
     * 生成JWT
     *
     * @param userId   用户编号
     * @param username 用户名
     * @param auth     用户权限
     */
    public String createToken(Integer userId, String username, List<String> auth) {
        //得到当前的系统时间
        Date currentDate = new Date();
        //根据当前时间计算出过期时间 定死为5分钟
        Date expTime = new Date(currentDate.getTime() + (1000 * 60 * 5));
        //组装头数据
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        return JWT.create()
                .withHeader(header) //头
                .withClaim("userId", userId) //自定义数据
                .withClaim("username", username) //自定义数据
                .withClaim("auth", auth) //自定义数据
                .withIssuedAt(currentDate) //创建时间
                .withExpiresAt(expTime)//过期时间
                .sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * 验证JWT并解析
     *
     * @param token 要验证的jwt的字符串
     */
    public static Boolean verifyToken(String token) {
        try{
            // 使用秘钥创建一个解析对象
            JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256(SECRET)).build();
            //验证JWT
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
//            String header = decodedJWT.getHeader();
//            String payload = decodedJWT.getPayload();
//            String signature = decodedJWT.getSignature();
//            System.out.println("header = " + header);
//            System.out.println("payload = " + payload);
//            System.out.println("signature = " + signature);
//
//            Date expiresAt = decodedJWT.getExpiresAt();
//            System.out.println("expiresAt = " + expiresAt);
//            Claim userId = decodedJWT.getClaim("userId");
//            System.out.println("userId = " + userId.asInt());
//            Claim username = decodedJWT.getClaim("username");
//            System.out.println("username = " + username.asString());
//            Claim auth = decodedJWT.getClaim("auth");
//            System.out.println("auth = " + auth.asList(String.class));
            return true;
        }catch (TokenExpiredException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取JWT里面相前的用户编号
     */
    public Integer getUserId(String token){
        try{
            // 使用秘钥创建一个解析对象
            JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256(SECRET)).build();
            //验证JWT
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            Claim userId = decodedJWT.getClaim("userId");
            return userId.asInt();
        }catch (TokenExpiredException e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取JWT里面相前的用户名
     */
    public static String getUsername(String token){
        try{
            // 使用秘钥创建一个解析对象
            JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256(SECRET)).build();
            //验证JWT
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            Claim username = decodedJWT.getClaim("username");
            return username.asString();
        }catch (TokenExpiredException e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取JWT里面相前权限
     */
    public List<String> getAuth(String token){
        try{
            // 使用秘钥创建一个解析对象
            JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256(SECRET)).build();
            //验证JWT
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            Claim auth = decodedJWT.getClaim("auth");
            return auth.asList(String.class);
        }catch (TokenExpiredException e){
            e.printStackTrace();
        }
        return null;
    }

}
