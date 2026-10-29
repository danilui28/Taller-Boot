
package com.krakedev.proyectos.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtil {

    private static final Logger logger =
            LoggerFactory.getLogger(JwtUtil.class);

    private static final String EMISOR = "Proyecto";

    private static final String SECRET_KEY = "ClaveSecretaJWTProyectoKrakedev2026Segura";

    private static final long TIEMPO_EXPIRACION = 1800000;

    public static String generarToken(String username, String rol) {

        Algorithm algoritmo = Algorithm.HMAC256(SECRET_KEY);

        long tiempoActual = System.currentTimeMillis();

        Date fechaExpiracion = new Date(tiempoActual + TIEMPO_EXPIRACION);

        String tokenGenerado = JWT.create()
                .withIssuer(EMISOR)
                .withSubject(username)
                .withIssuedAt(new Date(tiempoActual))
                .withExpiresAt(fechaExpiracion)
                .withClaim("rol", rol)
                .sign(algoritmo);
        
        return tokenGenerado;
    }

    public static DecodedJWT validarToken(String token) {

        try {
            Algorithm algoritmo = Algorithm.HMAC256(SECRET_KEY);

            JWTVerifier verificador = JWT.require(algoritmo).withIssuer(EMISOR).build();

			DecodedJWT tokenDecodificado = verificador.verify(token);
			
			return tokenDecodificado;

        } catch (JWTVerificationException | IllegalArgumentException e) {
            logger.warn("Token inválido o expirado: {}", e.getMessage());
            return null;
        }
    }
}
