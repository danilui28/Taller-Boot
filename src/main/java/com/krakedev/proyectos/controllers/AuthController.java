package com.krakedev.proyectos.controllers;


import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krakedev.proyectos.entidades.Usuario;
import com.krakedev.proyectos.services.UsuarioService;



@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
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

            return ResponseEntity.ok(
                    Map.of(
                            "mensaje", "Login exitoso",
                            "username", usuario.getUsername(),
                            "rol", usuario.getRol()));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            		.body(Map.of(
                            "error", "Error interno durante la autenticación"
                    ));
        }
    }
}
