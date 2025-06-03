package com.seudominio.model;

public class Carro {
    private int id;
    private String modelo;
    private String placa;
    private boolean alugado;
    private int idClienteAlugado;

    public Carro(int id, String modelo, String placa) {
        this.id = id;
        this.modelo = modelo;
        this.placa = placa;
        this.alugado = false;
        this.idClienteAlugado = 0;
    }

    public Carro(int id, String modelo, String placa, boolean alugado, int idClienteAlugado) {
        this.id = id;
        this.modelo = modelo;
        this.placa = placa;
        this.alugado = alugado;
        this.idClienteAlugado = idClienteAlugado;
    }

    public int getId() { return id; }
    public String getModelo() { return modelo; }
    public String getPlaca() { return placa; }
    public boolean isAlugado() { return alugado; }
    public int getIdClienteAlugado() { return idClienteAlugado; } // Novo getter

    public void setAlugado(boolean alugado) { this.alugado = alugado; }
    public void setIdClienteAlugado(int idClienteAlugado) { this.idClienteAlugado = idClienteAlugado; } // Novo setter

    @Override
    public String toString() {

        return "ID: " + id + " | Modelo: " + modelo + " | Placa: " + placa + " | Status: " + (alugado ? "Alugado (Cliente ID: " + idClienteAlugado + ")" : "Disponível");
    }


    public String toFileString() {
        return id + ";" + modelo + ";" + placa + ";" + alugado + ";" + idClienteAlugado; // Inclui idClienteAlugado
    }


    public static Carro fromFileString(String line) {
        String[] parts = line.split(";");
        if (parts.length == 5) { // Agora esperamos 5 partes
            int id = Integer.parseInt(parts[0]);
            String modelo = parts[1];
            String placa = parts[2];
            boolean alugado = Boolean.parseBoolean(parts[3]);
            int idClienteAlugado = Integer.parseInt(parts[4]); // Pega o ID do cliente
            return new Carro(id, modelo, placa, alugado, idClienteAlugado);
        }
        return null;
    }
}