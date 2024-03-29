package com.silverdev.ilg.state;

import com.silverdev.ilg.repository.*;

import com.silverdev.ilg.model.Usuario;
import com.silverdev.ilg.repository.UsuarioRepository;

public interface Matricula {
    public boolean desvincular(UsuarioRepository user,
                            IngressanteRepository ingressanteRepository,
                            AlunoRepository alunoRepository,
                            DisputaRepository disputaRepository,
                            Integer id);
    public boolean viraAluno(Integer id,
                          IngressanteRepository ingressanteRepository,
                          DisputaRepository disputaRepository,
                          UsuarioRepository usuarioRepository,
                          AlunoRepository alunoRepository);
}
