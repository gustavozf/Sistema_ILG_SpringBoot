package com.silverdev.ilg.state;

import com.silverdev.ilg.repository.AlunoRepository;
import com.silverdev.ilg.repository.IngressanteRepository;
import com.silverdev.ilg.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/state")
public class MatriculaController {
    private final UsuarioRepository usuarioRepository;
    private final IngressanteRepository ingressanteRepository;
    private final AlunoRepository alunoRepository;
    protected Matricula matricula;

    @Autowired
    public MatriculaController(UsuarioRepository usuarioRepository,
                               IngressanteRepository ingressanteRepository,
                               AlunoRepository alunoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.ingressanteRepository = ingressanteRepository;
        this.alunoRepository = alunoRepository;
    }

    @GetMapping("/matricular/{id}")
    public String matricularSe(@PathVariable("id") Integer id){
        matricula = new AlunoState();

        matricula.viraAluno(id,
                            ingressanteRepository,
                            usuarioRepository,
                            alunoRepository
                            );

        return "redirect:/login";
    }

    @GetMapping("/desvincular/{id}")
    public String desvincularSe(@PathVariable("id") Integer id){
        matricula = new DesistenteState();

        matricula.desvincular(usuarioRepository, id);

        return "redirect:/";
    }
}
