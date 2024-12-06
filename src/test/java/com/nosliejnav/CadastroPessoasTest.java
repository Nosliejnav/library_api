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
        pessoa.setNome("Wilson");

        //execução
        cadastroPessoas.adicionar(pessoa);

        //verificação
        Assertions.assertThat(cadastroPessoas.getPessoas())
                .isNotEmpty()
                .hasSize(1)
                .contains(pessoa);
    }

    /*
    naoDeveAdicionarPessoasComNomeVazio
     */
    @Test(expected = PessoasSemNotException.class)
        public void naoDeveAdicionarPessoasComNomeVazio(){
        //cenário
        CadastroPessoas cadastroPessoas = new CadastroPessoas();
        Pessoa pessoa = new Pessoa();

        //execução
        cadastroPessoas.adicionar(pessoa);
    }

    /*
    deveRemoverUmaPessoa
     */
    @Test
    public void deveRemoverUmaPessoa(){
        //cenário
        CadastroPessoas cadastroPessoas = new CadastroPessoas();
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Wilson");
        cadastroPessoas.adicionar(pessoa);

        //execução
        cadastroPessoas.remover(pessoa);

        //verificação
        Assertions.assertThat(cadastroPessoas.getPessoas()).isEmpty();
    }

    /*
    deveLancarErroAoTentarRemoverPessoaInexistente
     */
    @Test(expected = CadastroVazioException.class)
    public void deveLancarErroAoTentarRemoverPessoaInexistente(){
        //cenário
        CadastroPessoas cadastroPessoas = new CadastroPessoas();
        Pessoa pessoa = new Pessoa();

        //execução
        cadastroPessoas.remover(pessoa);

        //verificação
    }
}
