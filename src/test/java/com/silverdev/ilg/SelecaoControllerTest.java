package com.silverdev.ilg;

import com.silverdev.ilg.controller.SelecaoController;
import com.silverdev.ilg.model.Disputa;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SelecaoControllerTest {
    @Autowired
    private SelecaoController controller;

    @Test
    public void caminho1() throws Exception{
        controller.realizaSelecao(1,new RedirectAttributesModelMap());
    }

    @Test
    public void caminho2() throws Exception{
        // deve passar pois trata-se a excessao
        controller.realizaSelecao(16,new RedirectAttributesModelMap());
    }

    @Test
    public void caminho3() throws Exception{
        controller.visualizaSelecao(1,new RedirectAttributesModelMap());
    }


}
