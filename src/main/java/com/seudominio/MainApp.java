package com.seudominio;

import com.seudominio.model.Carro;
import com.seudominio.model.Cliente;
import com.seudominio.service.CarroService;
import com.seudominio.service.CarroServiceImpl;
import com.seudominio.service.ClienteService;
import com.seudominio.service.ClienteServiceImpl;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CarroService carroService = new CarroServiceImpl();
        ClienteService clienteService = new ClienteServiceImpl();
        int opcao;

        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Gerenciar Carros");
            System.out.println("2. Gerenciar Clientes");
            System.out.println("3. Gerenciar Aluguéis");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    menuCarros(scanner, carroService, clienteService);
                    break;
                case 2:
                    menuClientes(scanner, clienteService);
                    break;
                case 3:
                    menuAlugueis(scanner, carroService, clienteService);
                    break;
                case 0:
                    System.out.println("Saindo do programa. Dados salvos.");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 0);
        scanner.close();
    }

    private static void menuCarros(Scanner scanner, CarroService carroService, ClienteService clienteService) {
        int opcaoCarro;
        do {
            System.out.println("\n=== GERENCIAR CARROS ===");
            System.out.println("1. Cadastrar carro");
            System.out.println("2. Listar carros");
            System.out.println("3. Excluir carro");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha: ");
            opcaoCarro = scanner.nextInt();

            switch (opcaoCarro) {
                case 1:
                    System.out.print("Modelo: ");
                    scanner.nextLine(); // Consumir newline
                    String modelo = scanner.nextLine();
                    System.out.print("Placa: ");
                    String placa = scanner.nextLine();
                    carroService.cadastrarCarro(modelo, placa);
                    break;
                case 2:
                    Carro[] carros = carroService.listarCarros();
                    if (carros.length == 0) {
                        System.out.println("Nenhum carro cadastrado.");
                    } else {
                        System.out.println("\n--- Lista de Carros ---");
                        for (Carro c : carros) {
                            String infoAdicional = "";
                            if (c.isAlugado()) {
                                Cliente cliente = clienteService.buscarClientePorId(c.getIdClienteAlugado());
                                if (cliente != null) {
                                    infoAdicional = " (Alugado por: " + cliente.getNome() + " | Aluguel: " + c.getDataAluguel() + " | Entrega: " + c.getDataEntrega() + ")";
                                } else {
                                    infoAdicional = " (Alugado por cliente ID desconhecido: " + c.getIdClienteAlugado() + " | Aluguel: " + c.getDataAluguel() + " | Entrega: " + c.getDataEntrega() + ")";
                                }
                            }
                            System.out.println("ID: " + c.getId() + " | Modelo: " + c.getModelo() + " | Placa: " + c.getPlaca() + " | Status: " + (c.isAlugado() ? "Alugado" : "Disponível") + infoAdicional);
                        }
                    }
                    break;
                case 3:
                    System.out.print("ID do carro para excluir: ");
                    int idExc = scanner.nextInt();
                    System.out.println(carroService.excluirCarro(idExc) ? "Carro excluído com sucesso!" : "Erro ao excluir carro. ID não encontrado ou carro alugado.");
                    break;
                case 0:
                    System.out.println("Voltando ao Menu Principal...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcaoCarro != 0);
    }

    private static void menuClientes(Scanner scanner, ClienteService clienteService) {
        int opcaoCliente;
        do {
            System.out.println("\n=== GERENCIAR CLIENTES ===");
            System.out.println("1. Cadastrar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Buscar cliente por ID");
            System.out.println("4. Excluir cliente");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha: ");
            opcaoCliente = scanner.nextInt();

            switch (opcaoCliente) {
                case 1:
                    System.out.print("Nome: ");
                    scanner.nextLine(); // Consumir newline
                    String nome = scanner.nextLine();
                    System.out.print("CPF: ");
                    String cpf = scanner.nextLine();
                    System.out.print("Telefone: ");
                    String telefone = scanner.nextLine();
                    System.out.print("Endereço: ");
                    String endereco = scanner.nextLine();
                    clienteService.cadastrarCliente(nome, cpf, telefone, endereco);
                    break;
                case 2:
                    Cliente[] clientes = clienteService.listarClientes();
                    if (clientes.length == 0) {
                        System.out.println("Nenhum cliente cadastrado.");
                    } else {
                        System.out.println("\n--- Lista de Clientes ---");
                        for (Cliente c : clientes) {
                            System.out.println(c);
                        }
                    }
                    break;
                case 3:
                    System.out.print("ID do cliente para buscar: ");
                    int idClienteBusca = scanner.nextInt();
                    Cliente clienteBuscado = clienteService.buscarClientePorId(idClienteBusca);
                    if (clienteBuscado != null) {
                        System.out.println("Cliente encontrado: " + clienteBuscado);
                    } else {
                        System.out.println("Cliente com ID " + idClienteBusca + " não encontrado.");
                    }
                    break;
                case 4:
                    System.out.print("ID do cliente para excluir: ");
                    int idClienteExc = scanner.nextInt();
                    System.out.println(clienteService.excluirCliente(idClienteExc) ? "Cliente excluído com sucesso!" : "Erro ao excluir cliente. Verifique o ID ou se ele possui carros alugados.");
                    break;
                case 0:
                    System.out.println("Voltando ao Menu Principal...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcaoCliente != 0);
    }

    private static void menuAlugueis(Scanner scanner, CarroService carroService, ClienteService clienteService) {
        int opcaoAluguel;
        do {
            System.out.println("\n=== GERENCIAR ALUGUÉIS ===");
            System.out.println("1. Alugar carro");
            System.out.println("2. Devolver carro");
            System.out.println("3. Relatório de Aluguéis Ativos"); // Nova opção de relatório
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha: ");
            opcaoAluguel = scanner.nextInt();

            switch (opcaoAluguel) {
                case 1:
                    System.out.print("ID do carro para alugar: ");
                    int idCarroAlugar = scanner.nextInt();
                    System.out.print("ID do cliente alugador: ");
                    int idClienteAlugar = scanner.nextInt();
                    scanner.nextLine(); // Consumir newline
                    System.out.print("Data do aluguel (DD/MM/AAAA): ");
                    String dataAluguel = scanner.nextLine();
                    System.out.print("Data prevista de entrega (DD/MM/AAAA): ");
                    String dataEntrega = scanner.nextLine();

                    if (carroService.alugarCarro(idCarroAlugar, idClienteAlugar, dataAluguel, dataEntrega)) {
                        System.out.println("Carro alugado com sucesso!");
                    } else {
                        System.out.println("Erro ao alugar carro. Verifique os IDs do carro e do cliente, se o carro já está alugado, ou as datas.");
                    }
                    break;
                case 2:
                    System.out.print("ID do carro para devolver: ");
                    int idDevolver = scanner.nextInt();
                    System.out.println(carroService.devolverCarro(idDevolver) ? "Carro devolvido com sucesso!" : "Erro ao devolver carro. Verifique o ID ou se não está alugado.");
                    break;
                case 3: // Relatório de Aluguéis Ativos
                    System.out.println("\n=== RELATÓRIO DE ALUGUÉIS ATIVOS ===");
                    Carro[] carrosAlugados = carroService.listarCarrosAlugados();
                    if (carrosAlugados.length == 0) {
                        System.out.println("Nenhum carro alugado no momento.");
                    } else {
                        for (Carro carro : carrosAlugados) {
                            Cliente cliente = clienteService.buscarClientePorId(carro.getIdClienteAlugado());
                            System.out.println("------------------------------------");
                            System.out.println("Carro:");
                            System.out.println("  Modelo: " + carro.getModelo());
                            System.out.println("  Placa: " + carro.getPlaca());
                            System.out.println("  Data do Aluguel: " + carro.getDataAluguel());
                            System.out.println("  Data Prevista de Entrega: " + carro.getDataEntrega());
                            System.out.println("Cliente:");
                            if (cliente != null) {
                                System.out.println("  Nome: " + cliente.getNome());
                                System.out.println("  CPF: " + cliente.getCpf());
                                System.out.println("  Telefone: " + cliente.getTelefone());
                                System.out.println("  Endereço: " + cliente.getEndereco());
                            } else {
                                System.out.println("  Cliente não encontrado (ID: " + carro.getIdClienteAlugado() + ")");
                            }
                            System.out.println("------------------------------------");
                        }
                    }
                    break;
                case 0:
                    System.out.println("Voltando ao Menu Principal...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcaoAluguel != 0);
    }
}