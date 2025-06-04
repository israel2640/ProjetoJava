package com.seudominio.service;

import com.seudominio.model.Cliente;
import com.seudominio.repository.ClienteRepository;
import com.seudominio.repository.ClienteRepositoryImpl;

public class ClienteServiceImpl implements ClienteService {
    private ClienteRepository repositorio;
    private int idCounter;

    public ClienteServiceImpl() {
        this.repositorio = new ClienteRepositoryImpl();
        Cliente[] existingClients = repositorio.listarTodos();
        if (existingClients.length > 0) {
            int maxId = 0;
            for (Cliente cliente : existingClients) {
                if (cliente.getId() > maxId) {
                    maxId = cliente.getId();
                }
            }
            this.idCounter = maxId + 1;
        } else {
            this.idCounter = 1;
        }
    }

    @Override
    public void cadastrarCliente(String nome, String cpf, String telefone, String endereco) {
        if (nome == null || nome.trim().isEmpty() || cpf == null || cpf.trim().isEmpty()) {
            System.out.println("Erro: Nome e CPF são campos obrigatórios para o cliente.");
            return;
        }
        if (repositorio.existeCpf(cpf)) {
            System.out.println("Erro: Já existe um cliente cadastrado com este CPF.");
            return;
        }
        repositorio.salvar(new Cliente(idCounter++, nome, cpf, telefone, endereco));
        System.out.println("Cliente cadastrado com sucesso!");
    }

    @Override
    public Cliente[] listarClientes() {
        return repositorio.listarTodos();
    }

    @Override
    public Cliente buscarClientePorId(int id) {
        return repositorio.buscarPorId(id);
    }

    @Override
    public Cliente buscarClientePorCpf(String cpf) {
        return repositorio.buscarPorCpf(cpf);
    }

    @Override
    public boolean excluirCliente(int id) {

        return repositorio.remover(id);
    }
}