package com.nosliejnav;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class PrimeiroTeste {

    @Test
    public void deveSomar2Numeros(){
        // cenário
        Calculadora calculadora = new Calculadora();
        int numero1 = 10, numero2 = 5;

        // execução
        int resultado = calculadora.somar(numero1, numero2);

        //verificações
        Assertions.assertThat(resultado).isEqualTo(15);
    }

    @Test(expected = RuntimeException.class)
    public void naoDeveSomarNumerosNegativos(){
        //cenário
        Calculadora calculadora = new Calculadora();
        int num1 = -10, num2 =5;

        //execução
        calculadora.somar(num1, num2);
    }
}

class Calculadora {
    int somar (int num, int num2){
        if(num < 0 || num2 < 0){
            throw new RuntimeException("Não é permitido somar numeros negativos.");
        }
        return num + num2;
    }
}