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

    // Inicio Desafio calculadora

    @Test
    public void deveSubtrair2Numeros(){
        // cenário
        Calculadora calculadora = new Calculadora();
        int numero1 = 10, numero2 = 5;

        // execução
        int resultado = calculadora.subtrair(numero1, numero2);

        //verificações
        Assertions.assertThat(resultado).isEqualTo(5);
    }

    @Test
    public void deveDividir2Numeros(){
        // cenário
        Calculadora calculadora = new Calculadora();
        int numero1 = 10, numero2 = 5;

        // execução
        int resultado = calculadora.dividir(numero1, numero2);

        //verificações
        Assertions.assertThat(resultado).isEqualTo(2);
    }

    @Test
    public void deveMultiplicar2Numeros(){
        // cenário
        Calculadora calculadora = new Calculadora();
        int numero1 = 10, numero2 = 5;

        // execução
        int resultado = calculadora.multiplicar(numero1, numero2);

        //verificações
        Assertions.assertThat(resultado).isEqualTo(50);
    }

    //Desafio calculadora Fim
}

class Calculadora {
    int somar (int num, int num2){
        if(num < 0 || num2 < 0){
            throw new RuntimeException("Não é permitido somar numeros negativos.");
        }
        return num + num2;
    }

    int subtrair(int num, int num2){
        return num - num2;
    }

    int dividir(int num, int num2){
        if(num < 0 || num2 < 0){
            throw new RuntimeException("Não é permitido dividir numeros negativos.");
        }
        return num / num2;
    }

    int multiplicar(int num, int num2){
        return num * num2;
    }
}
