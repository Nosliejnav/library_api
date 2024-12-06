package com.nosliejnav;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class CadastroPessoasTest {

    @Test
    public void deveCriarOCadastroDePessoas(){
        //cenário e execução
        CadastroPessoas cadastro = new CadastroPessoas();

        //verificação
        Assertions.assertThat(cadastro.getPessoas()).isEmpty();
    }
}
