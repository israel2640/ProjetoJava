package com.seudominio.repository;

import com.seudominio.model.Cliente;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepositoryImpl implements ClienteRepository {
    private static final String FILE_NAME = "clientes.txt";
    private List<Cliente> clientes;
    private int nextId = 1;

    public ClienteRepositoryImpl() {
        clientes = new ArrayList<>();
        carregarDados();
        if (!clientes.isEmpty()) {
            nextId = clientes.stream()
                    .mapToInt(Cliente::getId)
                    .max()
                    .orElse(0) + 1;
        }
    }

    @Override
    public void salvar(Cliente cliente) {
        clientes.add(cliente);
        salvarDados();
    }

    @Override
    public Cliente buscarPorId(int id) {
        return clientes.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Cliente buscarPorCpf(String cpf) {
        return clientes.stream()
                .filter(c -> c.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Cliente[] listarTodos() {
        return clientes.toArray(new Cliente[0]);
    }

    @Override
    public void atualizar(int id, Cliente novo) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId() == id) {
                clientes.set(i, novo);
                salvarDados();
                return;
            }
        }
    }

    @Override
    public boolean remover(int id) {
        boolean removed = clientes.removeIf(c -> c.getId() == id);
        if (removed) {
            salvarDados();
        }
        return removed;
    }

    @Override
    public void carregarDados() {
        clientes.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                Cliente cliente = Cliente.fromFileString(line);
                if (cliente != null) {
                    clientes.add(cliente);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de dados de clientes não encontrado. Será criado um novo.");
        } catch (IOException e) {
            System.err.println("Erro ao ler dados de clientes do arquivo: " + e.getMessage());
        }
    }

    @Override
    public void salvarDados() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Cliente cliente : clientes) {
                bw.write(cliente.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados de clientes no arquivo: " + e.getMessage());
        }
    }

    @Override
    public boolean existeCpf(String cpf) {
        return clientes.stream()
                .anyMatch(c -> c.getCpf().equals(cpf));
    }
}
