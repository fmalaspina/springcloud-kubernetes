package com.fmalaspina.springcloud.msvc.cursos.services;

import com.fmalaspina.springcloud.msvc.cursos.models.Usuario;
import com.fmalaspina.springcloud.msvc.cursos.models.entities.Curso;

import java.util.List;
import java.util.Optional;


public interface CursoService {
    public List<Curso> listar();
    public Optional<Curso> porId(Long id);

    public Optional<Curso> porIdConUsuarios(Long id);
    public Curso guardar(Curso curso);
    public void eliminar(Long id);
    Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId);
    Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId);
    Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId);
    void eliminarCursoUsuarioPorUsuarioId(Long id);

}
