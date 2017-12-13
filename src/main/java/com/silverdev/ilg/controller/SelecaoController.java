package com.silverdev.ilg.controller;

import com.silverdev.ilg.model.*;
import com.silverdev.ilg.model.enums.PosicaoUEM;
import com.silverdev.ilg.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/selecao/")
public class SelecaoController {

    private final UsuarioRepository usuarioRepository;
    private final IngressanteRepository ingressanteRepository;
    private final DisputaRepository disputaRepository;
    private final InscricaoRepository inscricaoRepository;
    private final TurmaRepository turmaRepository;

    @Autowired
    public SelecaoController(UsuarioRepository usuarioRepository,
                             IngressanteRepository ingressanteRepository,
                             DisputaRepository disputaRepository,
                             InscricaoRepository inscricaoRepository,
                             TurmaRepository turmaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.ingressanteRepository = ingressanteRepository;
        this.disputaRepository = disputaRepository;
        this.inscricaoRepository = inscricaoRepository;
        this.turmaRepository = turmaRepository;
    }

    @GetMapping("/visualizacao/{id}")
    public String visualizaSelecao(@PathVariable("id") Integer inscricao_id, Model model){
        List<Disputa> alunosGerais = disputaRepository
                .findAllByInscricaoOrderByNomeIngressante(inscricao_id);
        List<Disputa> alunosFora = null;
        List<Disputa> naoAptos = disputaRepository.findByAptoOrderByNomeIngressante(false);

        model.addAttribute("disputas", alunosGerais);


        return "admin/selecao";
    }

    @GetMapping("{id}")
    public String realizaSelecao(@PathVariable("id") Integer inscricao_id, RedirectAttributes ra){
        List<Ingressante> ingressantes = ingressanteRepository.findAllByInscricao(inscricao_id);
        List<Disputa> disputas;
        Inscricao inscricao = inscricaoRepository.getOne(inscricao_id);
        Turma turma;
        Double aux;
        Integer  turmaId, vagasUem, vagasTotais, vagasFora;
        boolean erro = false;

        //acha os aptos de uma determinada inscricao
        buscaAptos(inscricao_id, ingressantes);


        try {
            disputas = disputaRepository.findAllByInscricaoAndAptoOrderByIdTurmaAscMediaDesc(inscricao_id, true);
            turmaId = disputas.get(0).getIdTurma(); // pega o primeiro elemento da lista
            turma = turmaRepository.findById(turmaId);
            vagasTotais = turma.getNum_vagas();
            aux = vagasTotais*0.8;
            vagasUem = aux.intValue();
            vagasFora = vagasTotais - vagasUem;
            //disputas = disputaRepository.findAllByInscricaoAndAptoOrderByIdTurmaAscMediaDesc(inscricao_id, true);
            ordenaMelhores(disputas, vagasFora, vagasUem, vagasTotais, turmaId);
        }catch (IndexOutOfBoundsException e){
            ra.addFlashAttribute("erro", "Erro ao realizar a seleção!");
            erro = true;
        } finally {
            if(!erro) {
                inscricao.setFeita(true);
                inscricaoRepository.saveAndFlush(inscricao);
                ra.addFlashAttribute("sucesso", "Seleção realizada com sucesso!");
            }


            return "redirect:/admin/habilitaInscricao";
        }
    }

    private void buscaAptos(Integer inscricao_id, List<Ingressante> ingressantes){
        Disputa disputa_aux = null;
        Usuario usuario_aux = null;

        for(Ingressante x: ingressantes){
            disputa_aux = new Disputa();
            usuario_aux =  usuarioRepository.getOneByCpf(x.getCpf());

            disputa_aux.setIdIngressante(x.getId());
            disputa_aux.setIdCurso(x.getCod_curso());
            disputa_aux.setIdTurma(x.getTurma());
            disputa_aux.setMedia(x.getMedia());
            disputa_aux.setPosicao(0);
            disputa_aux.setInscricao(inscricao_id);
            disputa_aux.setNomeCurso(x.getNome_curso());
            disputa_aux.setCpfIngressante(x.getCpf());
            disputa_aux.setNomeIngressante(usuario_aux.getNome() +" " + usuario_aux.getSobrenome());

            if (!x.isSit_entrega()){
                disputa_aux.setApto(false);
                disputa_aux.setMensagem("Documentos não entregues");
            }  else if (!x.isSit_pagamento()) {
                disputa_aux.setApto(false);
                disputa_aux.setMensagem("Boleto não pago");
            } else if(x.getMedia() < 7.0){
                disputa_aux.setApto(false);
                disputa_aux.setMensagem("Média abaixo de 7.0");
            } else {
                disputa_aux.setApto(true);
                disputa_aux.setMensagem("Lista de Espera");
            }
            disputaRepository.save(disputa_aux);
        }
    }

    private void ordenaMelhores(List<Disputa> disputas,
                                Integer vagasFora,
                                Integer vagasUem,
                                Integer vagasTotais,
                                Integer turmaId){
        Integer cont1, cont2;
        Turma turma;
        Ingressante ingre;
        Double aux;

        cont1 = 1; //contador da vaga dos vinculados a uem
        cont2 = 1; //contador da vaga dos nao vinculados a uem


        for (Disputa disputa: disputas) {
           if (!disputa.getIdTurma().equals(turmaId)){
                turma = turmaRepository.findById(disputa.getIdTurma());
                vagasTotais = turma.getNum_vagas();
                aux = vagasTotais*0.8;
                vagasUem = aux.intValue();
                vagasFora = vagasTotais - vagasUem;
                cont1 = 1;
                cont2 = 1;
           }
            ingre = ingressanteRepository.getOne(disputa.getIdIngressante());
            if (isMembroUem(ingre.getId())){
                if(cont1 <=  vagasUem){
                    disputa.setAprovado(true);
                    disputa.setMensagem("Aprovado(Com Vínculo)");
                }
                disputa.setPosicao(cont1);
                cont1 +=1;
            } else {
                if(cont2 <= vagasFora){
                    disputa.setAprovado(true);
                    disputa.setMensagem("Aprovado(Sem Vínculo)");
                }
                disputa.setPosicao(cont2);
                cont2 +=1;
            }
            disputaRepository.saveAndFlush(disputa);
        }


    }


    private boolean isMembroUem(Integer idIngressante){
        return (ingressanteRepository.findById(idIngressante).getPosUem() != PosicaoUEM.DESC_00);
    }

}
