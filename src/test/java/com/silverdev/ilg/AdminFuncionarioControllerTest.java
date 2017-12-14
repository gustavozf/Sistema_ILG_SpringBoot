package com.silverdev.ilg;


import com.silverdev.ilg.controller.AdminFuncionarioController;
import com.silverdev.ilg.model.Usuario;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.validation.Valid;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminFuncionarioControllerTest {
    @Autowired
    private AdminFuncionarioController controller;

    @Test
    public void caminho1(){
        Usuario usuario = new Usuario();
        usuario.setCpf("111.222.333-44");
        usuario.setPassword("123");

        controller.registraFuncionario(usuario, new RedirectAttributesModelMap());
    }

    @Test
    public void caminho2(){
        Usuario usuario = new Usuario();
        usuario.setCpf("013.104.679-94");
        usuario.setPassword("123");

        controller.registraFuncionario(usuario, new RedirectAttributesModelMap());
    }
}
