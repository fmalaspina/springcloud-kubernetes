package org.fmalaspina.springcloud.msvc.usuarios.controllers;

import jakarta.validation.Valid;
import org.fmalaspina.springcloud.msvc.usuarios.models.enetities.Usuario;
import org.fmalaspina.springcloud.msvc.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UsuariosController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public Map<String,List<Usuario>> listar() {
        return Collections.singletonMap("usuarios",usuarioService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = usuarioService.porId(id);
        if (usuarioOptional.isPresent()) {
            return ResponseEntity.ok(usuarioOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, BindingResult result) {

        if (result.hasErrors()) {
            return validar(result);
        }
        if (!usuario.getEmail().isEmpty() && usuarioService.existsByEmail(usuario.getEmail())) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "El email ya existe para otro usuario."));
        }
        return ResponseEntity.ok(usuarioService.guardar(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validar(result);
        }
        Optional<Usuario> usuarioOptional = usuarioService.porId(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuarioDb = usuarioOptional.get();
            usuarioDb.setNombre(usuario.getNombre());
            if (!usuario.getEmail().isEmpty() && !usuario.getEmail().equalsIgnoreCase(usuarioDb.getEmail()) && usuarioService.buscarPorEmail(usuario.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "El email ya existe para otro usuario."));
            }

            usuarioDb.setEmail(usuario.getEmail());
            usuarioDb.setPassword(usuario.getPassword());
        } else {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuarioService.guardar(usuarioOptional.get()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Usuario> usuarioOpt = usuarioService.porId(id);
        if (usuarioOpt.isPresent()) {
            usuarioService.eliminar(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/usuarios-por-curso")
    public ResponseEntity<?> obtenerUsuariosPorCurso(@RequestParam List<Long> ids) {

        return ResponseEntity.ok(usuarioService.listarPorIds(ids));
    }

    private static ResponseEntity<HashMap<String, String>> validar(BindingResult result) {
        var errores = new HashMap<String, String>();
        result.getFieldErrors().forEach(error -> {
            errores.put(error.getField(), String.format("El campo %s %s", error.getField(), error.getDefaultMessage()));
        });
        return ResponseEntity.badRequest().body(errores);
    }



}
