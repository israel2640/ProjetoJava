package com.seudominio.repository;

import com.seudominio.model.Carro;

public interface CarroRepository {
    void salvar(Carro carro);

    Carro buscarPorId(int id);

    Carro[] listarTodos();

    void atualizar(int id, Carro novo);

    boolean remover(int id);

    void carregarDados();

    void salvarDados();

    boolean existePlaca(String placa);
}