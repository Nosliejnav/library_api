package com.nosliejnav;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CadastroPessoasTest {

    @Test
    @DisplayName("Deve criar o Cadastro de pessoas.")
    public void deveCriarOCadastroDePessoas(){
        //cenário e execução
        CadastroPessoas cadastro = new CadastroPessoas();

        //verificação
        Assertions.assertThat(cadastro.getPessoas()).isEmpty();
    }

    @Test
    @DisplayName("Deve Adicionar uma pessoa.")
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

    @Test
    @DisplayName("Não Deve Adicionar pessoa com nome vazio.")
    public void naoDeveAdicionarPessoasComNomeVazio(){
        //cenário
        CadastroPessoas cadastroPessoas = new CadastroPessoas();
        Pessoa pessoa = new Pessoa();

        //execução
        org.junit.jupiter.api.Assertions
                .assertThrows( PessoasSemNotException.class, () -> cadastroPessoas.adicionar(pessoa));
    }

    @Test
    @DisplayName("Deve Remover uma pessoa.")
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

    @Test
    @DisplayName("Deve lançar erro ao tentar remover pessoa inexistente.")
    public void deveLancarErroAoTentarRemoverPessoaInexistente(){
        //cenário
        CadastroPessoas cadastroPessoas = new CadastroPessoas();
        Pessoa pessoa = new Pessoa();

        //execução
        org.junit.jupiter.api.Assertions
                .assertThrows(CadastroVazioException.class, () -> cadastroPessoas.remover(pessoa));

        //verificação
    }
}
