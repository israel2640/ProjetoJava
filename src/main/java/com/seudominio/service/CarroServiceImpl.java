package com.seudominio.service;

import com.seudominio.model.Carro;
import com.seudominio.model.Cliente;
import com.seudominio.repository.CarroRepository;
import com.seudominio.repository.CarroRepositoryImpl;
import com.seudominio.repository.ClienteRepository;
import com.seudominio.repository.ClienteRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class CarroServiceImpl implements CarroService {
    private CarroRepository carroRepositorio;
    private ClienteRepository clienteRepositorio;
    private int idCounter;

    public CarroServiceImpl() {
        this.carroRepositorio = new CarroRepositoryImpl();
        this.clienteRepositorio = new ClienteRepositoryImpl();

        Carro[] existingCars = carroRepositorio.listarTodos();
        if (existingCars.length > 0) {
            int maxId = 0;
            for (Carro carro : existingCars) {
                if (carro.getId() > maxId) {
                    maxId = carro.getId();
                }
            }
            this.idCounter = maxId + 1;
        } else {
            this.idCounter = 1;
        }
    }

    @Override
    public void cadastrarCarro(String modelo, String placa) {
        if (carroRepositorio.existePlaca(placa)) {
            System.out.println("Erro: Já existe um carro com esta placa. Por favor, use uma placa única.");
            return;
        }

        carroRepositorio.salvar(new Carro(idCounter++, modelo, placa));
        System.out.println("Carro cadastrado com sucesso!");
    }

    @Override
    public Carro[] listarCarros() {
        return carroRepositorio.listarTodos();
    }

    @Override
    public boolean alugarCarro(int idCarro, int idCliente, String dataAluguel, String dataEntrega) {
        Carro carro = carroRepositorio.buscarPorId(idCarro);
        if (carro == null) {
            System.out.println("Erro: Carro com ID " + idCarro + " não encontrado.");
            return false;
        }
        if (carro.isAlugado()) {
            System.out.println("Erro: Carro com ID " + idCarro + " já está alugado.");
            return false;
        }

        Cliente cliente = clienteRepositorio.buscarPorId(idCliente);
        if (cliente == null) {
            System.out.println("Erro: Cliente com ID " + idCliente + " não encontrado. Cadastre o cliente primeiro.");
            return false;
        }


        if (dataAluguel == null || dataAluguel.trim().isEmpty() || dataEntrega == null || dataEntrega.trim().isEmpty()) {
            System.out.println("Erro: Data de Aluguel e Data de Entrega são obrigatórias.");
            return false;
        }

        carro.setAlugado(true);
        carro.setIdClienteAlugado(idCliente);
        carro.setDataAluguel(dataAluguel);
        carro.setDataEntrega(dataEntrega);
        carroRepositorio.salvarDados();
        return true;
    }

    @Override
    public boolean devolverCarro(int idCarro) {
        Carro carro = carroRepositorio.buscarPorId(idCarro);
        if (carro != null && carro.isAlugado()) {
            carro.setAlugado(false);
            carro.setIdClienteAlugado(0);
            carro.setDataAluguel(null);
            carro.setDataEntrega(null);
            carroRepositorio.salvarDados();
            return true;
        }
        return false;
    }

    @Override
    public boolean excluirCarro(int id) {
        Carro carro = carroRepositorio.buscarPorId(id);
        if (carro != null && carro.isAlugado()) {
            System.out.println("Erro: Não é possível excluir um carro que está alugado. Devolva-o primeiro.");
            return false;
        }
        return carroRepositorio.remover(id);
    }

    @Override
    public Carro[] listarCarrosAlugados() {
        List<Carro> alugados = new ArrayList<>();
        for (Carro carro : carroRepositorio.listarTodos()) {
            if (carro.isAlugado()) {
                alugados.add(carro);
            }
        }
        return alugados.toArray(new Carro[0]);
    }
}