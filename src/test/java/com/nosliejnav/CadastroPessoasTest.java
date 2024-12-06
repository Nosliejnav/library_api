package com.nosliejnav;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class CadastroPessoasTest {

    /*
    deveCriarOCadastroDePessoas
     */
    @Test
    public void deveCriarOCadastroDePessoas(){
        //cenário e execução
        CadastroPessoas cadastro = new CadastroPessoas();

        //verificação
        Assertions.assertThat(cadastro.getPessoas()).isEmpty();
    }
/*
deveAdiciconarUmaPessoa
 */
    @Test
    public void deveAdiciconarUmaPessoa(){
        //Cenário
        CadastroPessoas cadastroPessoas = new CadastroPessoas();
        Pessoa pessoa = new Pessoa();

        //execução
        cadastroPessoas.adicionar(pessoa);

        //verificação
        Assertions.assertThat(cadastroPessoas.getPessoas())
                .isNotEmpty()
                .hasSize(1)
                .contains(pessoa);
    }
    /*

     */
}
