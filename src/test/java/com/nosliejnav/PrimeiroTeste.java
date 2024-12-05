package com.nosliejnav;

import org.assertj.core.api.Assertions;


import org.junit.Test;

public class PrimeiroTeste {

    @Test
    public void deveSomar2Numeros(){
        // cenário
        int numero1 = 10, numero2 = 5;

        // execução
        int resultado = numero1 + numero2;

        //verificações
        Assertions.assertThat(resultado).isEqualTo(15);


    }
}

