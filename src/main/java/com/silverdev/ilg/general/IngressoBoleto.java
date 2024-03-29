package com.silverdev.ilg.general;

import com.silverdev.ilg.model.Ingressante;
import com.silverdev.ilg.model.Usuario;
import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.view.BoletoViewer;
import org.jrimum.domkee.comum.pessoa.endereco.CEP;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.comum.pessoa.endereco.UnidadeFederativa;
import org.jrimum.domkee.financeiro.banco.febraban.*;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo.Aceite;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * Exemplo de código para geração de um boleto simples.
 * </p>
 * <p>
 * Utiliza o Banco Bradesco como exemplo, já que possui um implementação simples.
 * </p>
 *
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a>
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 *
 * @since 0.2
 *
 * @version 0.2
 */

public class IngressoBoleto {
    private final Ingressante ingressante;
    private final Usuario usuario;

    public IngressoBoleto(Ingressante ingressante, Usuario usuario){
        this.ingressante = ingressante;
        this.usuario = usuario;
    }

    public String geraBoleto() {
                /*
                 * INFORMANDO DADOS SOBRE O CEDENTE.
                 */
        Cedente cedente = new Cedente("Universidade Estadual de Maringá", "00.000.208/0001-00");

                /*
                 * INFORMANDO DADOS SOBRE O SACADO.
                 */
        Sacado sacado = new Sacado(usuario.getNome(), usuario.getCpf());

        // Informando o endereço do sacado.
        Endereco enderecoSac = new Endereco();
        enderecoSac.setUF(UnidadeFederativa.PR);
        enderecoSac.setLocalidade(usuario.getCidade());
        enderecoSac.setCep(new CEP(usuario.getCEP()));
        enderecoSac.setBairro(usuario.getBairro());
        enderecoSac.setLogradouro(usuario.getRua());
        enderecoSac.setNumero(usuario.getNumero());
        sacado.addEndereco(enderecoSac);

                /*
                 * INFORMANDO DADOS SOBRE O SACADOR AVALISTA.
                 */
        SacadorAvalista sacadorAvalista = new SacadorAvalista("Universidade Estadual de Maringá", "00.000.000/0001-91");

        // Informando o endereço do sacador avalista.
        Endereco enderecoSacAval = new Endereco();
        enderecoSacAval.setUF(UnidadeFederativa.PR);
        enderecoSacAval.setLocalidade("Maringá");
        enderecoSacAval.setCep(new CEP("87020-900"));
        enderecoSacAval.setBairro("Zona 7");
        enderecoSacAval.setLogradouro("Av Colombo");
        enderecoSacAval.setNumero("5790");
        sacadorAvalista.addEndereco(enderecoSacAval);

                /*
                 * INFORMANDO OS DADOS SOBRE O TÍTULO.
                 */

        // Informando dados sobre a conta bancária do título.
        ContaBancaria contaBancaria = new ContaBancaria(BancosSuportados.BANCO_BRADESCO.create());
        contaBancaria.setNumeroDaConta(new NumeroDaConta(3178, "0"));
        contaBancaria.setCarteira(new Carteira(30));
        contaBancaria.setAgencia(new Agencia(3178, "0"));

        Titulo titulo = new Titulo(contaBancaria, sacado, cedente, sacadorAvalista);
        titulo.setNumeroDoDocumento("123456");
        titulo.setNossoNumero("99345678912");
        titulo.setDigitoDoNossoNumero("5");
        titulo.setValor(BigDecimal.valueOf(12.90));
        titulo.setDataDoDocumento(new Date());
        titulo.setDataDoVencimento(new Date());
        titulo.setTipoDeDocumento(TipoDeTitulo.DM_DUPLICATA_MERCANTIL);
        titulo.setAceite(Aceite.A);
        titulo.setDesconto(new BigDecimal(0.05));
        titulo.setDeducao(BigDecimal.ZERO);
        titulo.setMora(BigDecimal.ZERO);
        titulo.setAcrecimo(BigDecimal.ZERO);
        titulo.setValorCobrado(BigDecimal.ZERO);

                /*
                 * INFORMANDO OS DADOS SOBRE O BOLETO.
                 */
        Boleto boleto = new Boleto(titulo);

        boleto.setLocalPagamento("Pagável preferencialmente no banco do Bradesco ou em" +
                "qualquer casa lotérica até o vencimento.");
       // boleto.setInstrucaoAoSacado("Senhor sacado, sabemos sim que o valor " +
         //       "cobrado não é o esperado, aproveite o DESCONTÃO!");
        boleto.setInstrucao1("APÓS o Vencimento, Pagável Somente no Banco Bradesco.");

                /*
                 * GERANDO O BOLETO BANCÁRIO.
                 */
        // Instanciando um objeto "BoletoViewer", classe responsável pela
        // geração do boleto bancário.
        BoletoViewer boletoViewer = new BoletoViewer(boleto);

        // Gerando o arquivo. No caso o arquivo mencionado será salvo na mesma
        // pasta do projeto. Outros exemplos:
        // WINDOWS: boletoViewer.getAsPDF("C:/Temp/MeuBoleto.pdf");
        // LINUX: boletoViewer.getAsPDF("/home/temp/MeuBoleto.pdf");
        File arquivoPdf = boletoViewer.getPdfAsFile("src/main/resources/static/PDFs/Ingressante/"+ingressante.getId() +".pdf");
        boletoViewer.getPdfAsFile("target/classes/static/PDFs/Ingressante/"+ingressante.getId() +".pdf");

        // Mostrando o boleto gerado na tela.
        //mostreBoletoNaTela(arquivoPdf);

        return "redirect:/";
    }

    private static void mostreBoletoNaTela(File arquivoBoleto) {

        java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

        try {
            desktop.open(arquivoBoleto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}