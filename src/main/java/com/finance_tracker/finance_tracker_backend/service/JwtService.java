/*
 Esta clase se encarga de todo lo relacionado con el token: generarlo cuando el usuario
 hace login, validarlo cuando llegue una peticion y extraer el email del token
 */


package com.finance_tracker.finance_tracker_backend.service;




import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${JWT_SECRET}")
    private String secretKey;

    @Value("${JWT_EXPIRATION}")
    private long expiration;

    private SecretKey getSignKey(){
        /*
        Este metodo convierte a secretKey en una clave criptografica que JJWT usa internamente
        para firmar y verificar tokens
         */
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }


    public String generateToken(String email){
        /*
        Lo que hace este metodo es crear el token usando el email,
        este email queda adentor del token para despues validar el token
        junto con el email y si el email esta dentro de ese token queda validado
        */
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignKey())
                .compact();
    }

    public String extractEmail (String token){
        /*
        Este metodo extrae el email que esta dentro del token para que despues
        lo valide en el metodo isTokenValid() que recibira el email que devuelve este metodo
        y el token que devuelve el generateToken((
         */
        return  Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(String token, String email){
        /*
        Aqui recibe el token y email para poder validarlos
         */
        try{
          return extractEmail(token).equals(email) && !isTokenExpired(token);
          } catch (Exception e) {
            return false;
        }


        }

    private boolean isTokenExpired(String token){
        return  Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }
}
