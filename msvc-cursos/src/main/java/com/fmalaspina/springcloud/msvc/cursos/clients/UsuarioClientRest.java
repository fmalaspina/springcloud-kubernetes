package com.fmalaspina.springcloud.msvc.cursos.clients;

import com.fmalaspina.springcloud.msvc.cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(name = "msvc-usuarios", url = "http://host.docker.internal:8001")
public interface UsuarioClientRest {
    @GetMapping("/{id}")
    public Usuario detalle(@PathVariable Long id);

    @PostMapping("/")
    public Usuario crear(@RequestBody Usuario usuario);

    @GetMapping("/usuarios-por-curso")
    public List<Usuario> obtenerUsuariosPorCurso(@RequestParam Iterable<Long> ids);

}
