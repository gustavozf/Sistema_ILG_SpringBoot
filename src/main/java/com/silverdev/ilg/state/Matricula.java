package com.silverdev.ilg.state;

import com.silverdev.ilg.repository.AlunoRepository;
import com.silverdev.ilg.repository.IngressanteRepository;
import com.silverdev.ilg.repository.UsuarioRepository;

public interface Matricula {
    public void viraAluno(Integer id, IngressanteRepository ingressanteRepository, UsuarioRepository usuarioRepository, AlunoRepository alunoRepository);
}
