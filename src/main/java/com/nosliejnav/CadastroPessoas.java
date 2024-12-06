package com.nosliejnav;

import java.util.ArrayList;
import java.util.List;

public class CadastroPessoas {

    /*
    deveCriarOCadastroDePessoas
     */
    private List<Pessoa> pessoas;

    public CadastroPessoas() {
        this.pessoas = new ArrayList<>();
    }

    public List<Pessoa> getPessoas() {
        return this.pessoas;
    }
/*
deveAdiciconarUmaPessoa
 */
    public void adicionar(Pessoa pessoa) {
        this.pessoas.add(pessoa);
    }
}
