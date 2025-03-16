package com.project.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.project.common.CodeEnum;
import com.project.exception.BusinessExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * token的生成与解析
 */
public class TokenUtil {
    //token密钥
    private static final String secret = "campus2025";
    //过期时间（一周）
    private static final long expiration = 7 * 24 * 3600 * 1000;

    /**
     * 生成用户token,设置token超时时间
     */
    public static String createToken(Long userId, String phone) {
        //过期时间
        Date expireDate = new Date(System.currentTimeMillis() + expiration);
        return JWT.create()
                // 添加头部
                .withIssuer("auth")
                //可以将基本信息放到claims中
                .withClaim("userId", userId)
                .withClaim("phone", phone)
                //超时设置,设置过期的日期
                .withExpiresAt(expireDate)
                //SECRET加密
                .sign(Algorithm.HMAC256(secret));
    }

    /**
     * 解析token并获取其中的id和phone
     */
    public static Map<String, Object> parseToken(String token) {
        Map<String, Object> result = new HashMap<>();
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).withIssuer("auth").build();
            DecodedJWT jwt = verifier.verify(token);

            // 获取id
            Claim idClaim = jwt.getClaim("userId");
            if (!idClaim.isNull()) {
                Long userId = idClaim.asLong();
                result.put("userId", userId);
            } else {
                throw new BusinessExceptionHandler(Objects.requireNonNull(CodeEnum.getByCode(401)));
            }

            // 获取phone
            Claim phoneClaim = jwt.getClaim("phone");
            if (!phoneClaim.isNull()) {
                String phone = phoneClaim.asString();
                result.put("phone", phone);
            } else {
                throw new BusinessExceptionHandler(Objects.requireNonNull(CodeEnum.getByCode(401)));
            }

        } catch (Exception e) {
            // 解码异常则抛出异常
            System.out.println("token解析异常: " + e.getMessage());
        }

        return result;
    }
}