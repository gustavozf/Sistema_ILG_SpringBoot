package com.silverdev.ilg.state;

import com.silverdev.ilg.model.Aluno;
import com.silverdev.ilg.model.Disputa;
import com.silverdev.ilg.model.Ingressante;
import com.silverdev.ilg.model.Usuario;
import com.silverdev.ilg.model.enums.Role;
import com.silverdev.ilg.repository.AlunoRepository;
import com.silverdev.ilg.repository.DisputaRepository;
import com.silverdev.ilg.repository.IngressanteRepository;
import com.silverdev.ilg.repository.UsuarioRepository;

public class AlunoState implements Matricula {
    @Override
    public boolean viraAluno(Integer id,
                          IngressanteRepository ingressanteRepository,
                          DisputaRepository disputaRepository,
                          UsuarioRepository usuarioRepository,
                          AlunoRepository alunoRepository) {

        Ingressante ingressante = ingressanteRepository.findById(id);
        Usuario usuario = usuarioRepository.findUsuarioByCpf(ingressante.getCpf());
        Disputa disputa = disputaRepository.findByIdIngressante(id);
        String cpf = ingressante.getCpf();
        boolean retorno = false;

        disputa.setMatriculado(true);
        ingressante.setAtivo(false);

        Aluno novoaluno = new Aluno();

        novoaluno.setCpf(ingressante.getCpf());
        novoaluno.setPosicao_uem(ingressante.getPosUem());
        novoaluno.setCod_curso(ingressante.getCod_curso());
        novoaluno.setNome_curso(ingressante.getNome_curso());
        novoaluno.setCod_turma(ingressante.getTurma());
        novoaluno.setProva1(0.0);
        novoaluno.setProva_aux1(0.0);
        novoaluno.setProva_aux2(0.0);
        novoaluno.setProva2(0.0);
        novoaluno.setAprovacao(false);
        novoaluno.setMedia(0.0);
        novoaluno.setFaltas(0);

        Integer tamListaIngre = ingressanteRepository.findByCpfAndInscricaoAndAtivo(cpf, ingressante.getInscricao(), true).size();
        if( tamListaIngre == 1){
            usuario.setAcesso(Role.ROLE_ALUNO);
            retorno = true;
        }

        usuarioRepository.saveAndFlush(usuario);
        alunoRepository.save(novoaluno);
        disputaRepository.saveAndFlush(disputa);
        ingressanteRepository.saveAndFlush(ingressante);

        return retorno;

    }

    @Override
    public boolean desvincular(UsuarioRepository user,
                               IngressanteRepository ir,
                               AlunoRepository ar,
                               DisputaRepository dr,
                               Integer id){
        return true;
    }
}
