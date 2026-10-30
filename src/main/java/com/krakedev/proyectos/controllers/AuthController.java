package com.krakedev.proyectos.controllers;


import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.krakedev.proyectos.entidades.Usuario;
import com.krakedev.proyectos.security.JwtUtil;
import com.krakedev.proyectos.services.TokenBlacklistService;
import com.krakedev.proyectos.services.UsuarioService;



@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final TokenBlacklistService blackListService;

    public AuthController(UsuarioService usuarioService,  TokenBlacklistService blackListService) {
        this.usuarioService = usuarioService;
        this.blackListService = blackListService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody Usuario usuario) {

        try {

            Usuario usuarioNuevo = usuarioService.guardar(usuario);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of(
                            "id", usuarioNuevo.getId(),
                            "username", usuarioNuevo.getUsername(),
                            "rol", usuarioNuevo.getRol()));

        } catch (IllegalArgumentException e) {

            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of(
                            "error", "Error al registrar usuario"
                    ));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {

        String username = credenciales.get("username");
        String password = credenciales.get("password");

        if (username == null || password == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "error", "Debe indicar username y password"
                    ));
        }

        try {

            boolean autenticado = usuarioService.autenticar(username, password);

            if (!autenticado) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of(
                                "error", "Usuario o contraseña incorrectos"
                        ));
            }

            Usuario usuario = usuarioService.buscar(username);

            String token = JwtUtil.generarToken(
                    usuario.getUsername(),
                    usuario.getRol()
            );

            return ResponseEntity.ok(
                    Map.of(
                            "mensaje", "Login exitoso",
                            "username", usuario.getUsername(),
                            "rol", usuario.getRol(),
                            "token", token,
                            "tipo", "Bearer"
                    )
            );

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "Error interno durante la autenticación"
                    ));
        }
    }
    
    @GetMapping("/perfil")
    public ResponseEntity<?> verPerfil() {
        try {

        	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        	
        	String usuario = auth.getName();
        	String rol = auth.getAuthorities().iterator().next().getAuthority();
        	
        	return ResponseEntity.ok(Map.of(
        			"Mensaje", "Bienvenido al sistema protegido por Spring Security", 
        			"Usuario", usuario,
        			"rol_detectado", rol,
        			"status", "Autenticado exitosamente"));
        	
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Acceso denegado: Usuario no autenticado");
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(value = "Authorization", required = false) String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ") || authHeader.length() <= 7) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Token no proporcionado o inválido"));
        }

        String token = authHeader.substring(7);

        blackListService.invalidarToken(token);

        return ResponseEntity.ok(Map.of("mensaje","Sesión cerrada exitosamente. Token invalidado")
        );
    }
}

