package com.seudominio.repository;

import com.seudominio.model.Cliente;

public interface ClienteRepository {
    void salvar(Cliente cliente);

    Cliente buscarPorId(int id);

    Cliente buscarPorCpf(String cpf);

    Cliente[] listarTodos();

    void atualizar(int id, Cliente novo);

    boolean remover(int id);

    void carregarDados();

    void salvarDados();

    boolean existeCpf(String cpf);
}