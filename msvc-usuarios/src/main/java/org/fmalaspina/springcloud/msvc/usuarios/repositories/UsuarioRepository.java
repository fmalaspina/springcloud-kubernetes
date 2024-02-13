package org.fmalaspina.springcloud.msvc.usuarios.repositories;

import org.fmalaspina.springcloud.msvc.usuarios.models.enetities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    public Optional<Usuario> findByEmail(String email);

    @Query("select u from Usuario u where u.email=?1")
    public Optional<Usuario> porEmail(String email);

    boolean existsByEmail(String email);
}