/**
 * Copyright (C), 2008-2017, 杭州迪火科技有限公司
 * FileName: JWTUtil
 * Author:   shugan
 * Date:     2017/12/18 17:52
 * Description: jwt工具
 */
package com.flyingcow.fastgo.server.common.utils;

import com.flyingcow.fastgo.server.common.constant.ExceptionEnum;
import com.flyingcow.fastgo.server.common.exception.BizException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 〈jwt工具〉
 *
 * @author shugan
 * @create 2017/12/18
 * @since 1.0.0
 */
@Component
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    public JwtTokenUtil() {
    }

    public String getUsernameFromToken(String token) {
        Claims claims = this.getClaimsFromToken(token);
        String username = claims.getSubject();
        return username;
    }

    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            Claims claims = this.getClaimsFromToken(token);
            created = new Date((Long)claims.get("created"));
        } catch (Exception var4) {
            created = null;
        }

        return created;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            Claims claims = this.getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception var4) {
            expiration = null;
        }

        return expiration;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = (Claims)Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException var4) {
            System.out.println("token失效");
            throw new BizException(ExceptionEnum.TOKEN_EXPIRED);
        } catch (Exception var5) {
            claims = null;
        }

        return claims;
    }

    private Date generateExpirationDate() {
        Date date = new Date(System.currentTimeMillis() + this.expiration * 1000L);
        return date;
    }

    private Boolean isTokenExpired(String token) {
        Date expiration = this.getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(String account) {
        Map<String, Object> claims = new HashMap();
        claims.put("sub", account);
        claims.put("created", new Date());
        return this.generateToken((Map)claims);
    }

    String generateToken(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims).setExpiration(this.generateExpirationDate()).signWith(SignatureAlgorithm.HS512, this.secret).compact();
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        this.getCreatedDateFromToken(token);
        return !this.isTokenExpired(token);
    }

    public String refreshToken(String token) {
        String refreshedToken;
        try {
            Claims claims = this.getClaimsFromToken(token);
            claims.put("created", new Date());
            refreshedToken = this.generateToken((Map)claims);
        } catch (Exception var4) {
            refreshedToken = null;
        }

        return refreshedToken;
    }

    public Boolean validateToken(String token, String account) {
        String username = this.getUsernameFromToken(token);
        this.getCreatedDateFromToken(token);
        return username.equals(account) && !this.isTokenExpired(token);
    }

    public static void main(String[] args) {
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        String s = jwtTokenUtil.generateToken("jack");
        System.out.println(s);
        System.out.println(jwtTokenUtil.getCreatedDateFromToken(s));
        System.out.println(jwtTokenUtil.getUsernameFromToken(s));
        System.out.println(jwtTokenUtil.isTokenExpired(s));
        System.out.println(jwtTokenUtil.refreshToken(s));
    }
}