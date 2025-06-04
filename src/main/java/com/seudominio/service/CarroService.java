package com.seudominio.service;

import com.seudominio.model.Carro;

public interface CarroService {
    void cadastrarCarro(String modelo, String placa);

    Carro[] listarCarros();

    boolean alugarCarro(int idCarro, int idCliente, String dataAluguel, String dataEntrega);

    boolean devolverCarro(int idCarro);


    boolean excluirCarro(int id);


    Carro[] listarCarrosAlugados();
}