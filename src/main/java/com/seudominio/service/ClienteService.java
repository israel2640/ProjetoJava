package com.seudominio.service;

import com.seudominio.model.Cliente;

public interface ClienteService {
    void cadastrarCliente(String nome, String cpf, String telefone, String endereco);

    Cliente[] listarClientes();

    Cliente buscarClientePorId(int id);

    Cliente buscarClientePorCpf(String cpf);

    boolean excluirCliente(int id);
}