package com.silverdev.ilg.state;

import com.silverdev.ilg.model.Ingressante;
import com.silverdev.ilg.model.Usuario;
import com.silverdev.ilg.repository.AlunoRepository;
import com.silverdev.ilg.repository.IngressanteRepository;
import com.silverdev.ilg.repository.UsuarioRepository;

import java.util.Optional;

public class DesistenteState implements Matricula {
    @Override
    public void desvincular(UsuarioRepository user,
                            IngressanteRepository ingressanteRepository,
                            Integer id){
        Ingressante ingressante = ingressanteRepository.getOne(id);
        Usuario usuario = user.findUsuarioByCpf(ingressante.getCpf());

        usuario.setAtivo(false);
        user.saveAndFlush(usuario);
    }

    @Override
    public void viraAluno(Integer id, IngressanteRepository ingressanteRepository, UsuarioRepository usuarioRepository, AlunoRepository alunoRepository) {
        System.out.println("Ola");
    }
}
