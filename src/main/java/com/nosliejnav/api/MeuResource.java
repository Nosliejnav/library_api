package com.nosliejnav.api;

import org.springframework.web.bind.annotation.*;

@RestController
public class MeuResource {

//    @RequestMapping(value = "/api/clientes/{id}", method = RequestMethod.GET)

    @GetMapping("/api/clientes/{id}")
    public Cliente obterDadosCliente(@PathVariable Long id){
        System.out.println(String.format("Id recebido via url: %d", id));
        Cliente cliente = new Cliente("Fulano", "000.000.000-00");
        return cliente;

    }
}
