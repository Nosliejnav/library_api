package com.nosliejnav;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith( MockitoJUnitRunner.class)
public class MockitoTests {

    @Mock
    List<String> lista;

    @Test
    public void primeiroTesteMockito(){
        Mockito.when(lista.size()).thenReturn(2);

        int size = 0;
        if(1 == 2){
            size = lista.size();
            size = lista.size();
        }

//        Assertions.assertThat(size).isEqualTo(2);
        Mockito.verify(lista, Mockito.never()).size();
    }
}
