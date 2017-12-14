package com.silverdev.ilg.state;

import com.silverdev.ilg.model.Disputa;
import com.silverdev.ilg.model.Ingressante;
import com.silverdev.ilg.model.Usuario;
import com.silverdev.ilg.repository.AlunoRepository;
import com.silverdev.ilg.repository.DisputaRepository;
import com.silverdev.ilg.repository.IngressanteRepository;
import com.silverdev.ilg.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

public class DesistenteState implements Matricula {
    @Override
    public boolean desvincular(UsuarioRepository user,
                            IngressanteRepository ir,
                            AlunoRepository ar,
                            DisputaRepository dr,
                            Integer id){
        Ingressante ingressante = ir.getOne(id); //Acha um objeto ingressante com o ID
        String cpf = ingressante.getCpf(); // Pega o CPF do mesmo
        Integer tamListaIngre = ir.findByCpfAndInscricaoAndAtivo(cpf, ingressante.getInscricao(), true).size(); // Verifica a quantidade cursos que é candidato
        Integer tamListaAlun = ar.findByCpf(cpf).size(); // Verifica a quantidade de cursos que é inscrito
        Usuario usuario = user.findUsuarioByCpf(cpf); // Pega o objeto usuario do mesmo
        Disputa disputa = dr.getOne(ingressante.getId()); // pega a disputa pela vaga do ingressante
        boolean userDeleted = false;

        disputa.setMatriculado(true); // muda pra true para que nao aparece no menu do ingressante
        dr.saveAndFlush(disputa);
        ingressante.setAtivo(false); // Retira do curso X ao qual eh candidato
        ir.saveAndFlush(ingressante);
        // Se for o ultimo curso que eh candidato
        if((tamListaIngre == 1)){
            userDeleted = true;
            if ((tamListaAlun == 0)){ // e nao esta inscrito em nenhum curso
                usuario.setAtivo(false); // Retira o usario
                user.saveAndFlush(usuario);
            }
        }

        return userDeleted;
    }

    @Override
    public void viraAluno(Integer id, IngressanteRepository ir, UsuarioRepository usuarioRepository, AlunoRepository alunoRepository) {
        //System.out.println("Ola");
    }
}
