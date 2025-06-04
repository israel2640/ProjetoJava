package com.seudominio.repository;

import com.seudominio.model.Carro;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class CarroRepositoryImpl implements CarroRepository {
    private static final String FILE_NAME = "carros.txt";
    private List<Carro> carros;
    private int nextId = 1;

    public CarroRepositoryImpl() {
        carros = new ArrayList<>();
        carregarDados();
        if (!carros.isEmpty()) {
            nextId = carros.stream()
                    .mapToInt(Carro::getId)
                    .max()
                    .orElse(0) + 1;
        }
    }

    @Override
    public void salvar(Carro carro) {
        carros.add(carro);
        salvarDados();
    }

    @Override
    public Carro buscarPorId(int id) {
        return carros.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Carro[] listarTodos() {
        return carros.toArray(new Carro[0]);
    }

    @Override
    public void atualizar(int id, Carro novo) {
        for (int i = 0; i < carros.size(); i++) {
            if (carros.get(i).getId() == id) {
                carros.set(i, novo);
                salvarDados();
                return;
            }
        }
    }

    @Override
    public boolean remover(int id) {
        boolean removed = carros.removeIf(c -> c.getId() == id);
        if (removed) {
            salvarDados();
        }
        return removed;
    }

    @Override
    public void carregarDados() {
        carros.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                Carro carro = Carro.fromFileString(line);
                if (carro != null) {
                    carros.add(carro);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de dados de carros não encontrado. Será criado um novo.");
        } catch (IOException e) {
            System.err.println("Erro ao ler dados de carros do arquivo: " + e.getMessage());
        }
    }

    @Override
    public void salvarDados() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Carro carro : carros) {
                bw.write(carro.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados de carros no arquivo: " + e.getMessage());
        }
    }

    @Override
    public boolean existePlaca(String placa) {
        return carros.stream()
                .anyMatch(c -> c.getPlaca().equalsIgnoreCase(placa));
    }
}