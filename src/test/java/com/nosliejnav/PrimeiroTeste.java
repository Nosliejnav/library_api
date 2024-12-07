package com.nosliejnav;

import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.exceptions.base.MockitoException;


//@RunWith(MockitoJUnitRunner.class) trocou para a de baixo
//@ExtendWith(MockitoException.class)
public class PrimeiroTeste {

//    @Mock usando com a a novo modelo
//    Calculadora calculadora;

    Calculadora calculadora;

    int numero1 = 10, numero2 = 5;

    @BeforeEach
    public void setUp(){
        calculadora = new Calculadora();
    }

    @Test
    public void deveSomar2Numeros(){
        // cenário

        // execução
        int resultado = calculadora.somar(numero1, numero2);

        //verificações
        Assertions.assertThat(resultado).isEqualTo(15);
    }

    @Test
    public void naoDeveSomarNumerosNegativos(){
        //cenário
        int num1 = -10, num2 =5;

        //execução
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> calculadora.somar(num1, num2));
    }

//     INICIO Desafio calculadora

    @Test
    public void deveSubtrair2Numeros(){
        // cenário

        // execução
        int resultado = calculadora.subtrair(numero1, numero2);

        //verificações
        Assertions.assertThat(resultado).isEqualTo(5);
    }

    @Test
    public void deveMultiplicar2Numeros(){
        // cenário

        // execução
        int resultado = calculadora.multiplicar(numero1, numero2);

        //verificações
        Assertions.assertThat(resultado).isEqualTo(50);
    }

    @Test
    public void deveDividir2Numeros() {
        // cenário

        // execução
        float resultado = calculadora.dividir(numero1, numero2);

        //verificações
        Assertions.assertThat(resultado).isEqualTo(2);
    }

    @Test
    public void naoDeveDividirPorZero(){
        //cenario
        int numero1 = 10, numero2 = 0;

        //execução
        org.junit.jupiter.api.Assertions.assertThrows(ArithmeticException.class, () -> calculadora.dividir(numero1, numero2));
        }
    }

    //Desafio calculadora FIM

class Calculadora {
    int somar (int num, int num2){
        if(num < 0 || num2 < 0){
            throw new RuntimeException("Não é permitido somar numeros negativos.");
        }
        return num + num2;
    }
//     INICIO Desafio calculadora
    int subtrair(int num, int num2){
        return num - num2;
    }

    int multiplicar(int num, int num2){
        return num * num2;
    }

    float dividir(int num, int num2){
        return num / num2;
    }
//    Desafio calculadora FIM


}
