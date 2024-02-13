package org.fmalaspina.springcloud.msvc.usuarios.services;

import org.fmalaspina.springcloud.msvc.usuarios.models.enetities.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> listar();
    Optional<Usuario> porId(Long id);
    Usuario guardar(Usuario usuario);
    void eliminar(Long id);

    Optional<Usuario> buscarPorEmail(String email);
    boolean existsByEmail(String email);

    List<Usuario> listarPorIds(Iterable<Long> ids);


}
