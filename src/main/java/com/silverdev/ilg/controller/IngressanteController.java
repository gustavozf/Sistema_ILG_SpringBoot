package com.silverdev.ilg.controller;

import com.silverdev.ilg.general.IngressoBoleto;
import com.silverdev.ilg.model.Aluno;
import com.silverdev.ilg.model.Ingressante;
import com.silverdev.ilg.model.Usuario;
import com.silverdev.ilg.repository.AlunoRepository;
import com.silverdev.ilg.repository.IngressanteRepository;
import com.silverdev.ilg.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by narcizo on 23/10/17.
 */
@Controller
@RequestMapping("/ingressante/{id}")
public class IngressanteController {

    private final IngressanteRepository ingressanteRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public IngressanteController(IngressanteRepository ingressanteRepository,
                                 UsuarioRepository usuarioRepository){
        this.ingressanteRepository = ingressanteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String abreTelaIngressante(@PathVariable("id") Integer id, Model model){

        findIngressanteByUsuarioID(id);

        //Ingressante ingressante = ingressanteLista.get(0);

        model.addAttribute("ingressante", usuarioRepository.getOne(id));

        return "/ingressante/ingressante";
    }

    @GetMapping("/boleto/{id}")
    public String visualizaBoleto(@PathVariable("id") Integer id, Model model){
        Usuario usuario = usuarioRepository.getOne(id);
        IngressoBoleto ingressoBoleto = null;

        List<Ingressante> ingressantes = findIngressanteByUsuarioID(id);

        for(Ingressante ing: ingressantes){
            ingressoBoleto = new IngressoBoleto(ing, usuario);
            ingressoBoleto.geraBoleto();
        }

        model.addAttribute("ingressante", ingressantes);
        model.addAttribute("id", id);

        return  "/ingressante/boleto";
    }

    private List<Ingressante> findIngressanteByUsuarioID(Integer id){
        Usuario usuario =  usuarioRepository.getOne(id);
        List<Ingressante> ingressanteLista = ingressanteRepository.findByCpf(usuario.getCpf());

        return ingressanteLista;
    }


}