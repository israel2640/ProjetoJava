package com.seudominio.model;

public class Carro {
    private int id;
    private String modelo;
    private String placa;
    private boolean alugado;
    private int idClienteAlugado;
    private String dataAluguel;
    private String dataEntrega;


    public Carro(int id, String modelo, String placa) {
        this.id = id;
        this.modelo = modelo;
        this.placa = placa;
        this.alugado = false;
        this.idClienteAlugado = 0;
        this.dataAluguel = null;
        this.dataEntrega = null;
    }


    public Carro(int id, String modelo, String placa, boolean alugado, int idClienteAlugado, String dataAluguel, String dataEntrega) {
        this.id = id;
        this.modelo = modelo;
        this.placa = placa;
        this.alugado = alugado;
        this.idClienteAlugado = idClienteAlugado;
        this.dataAluguel = dataAluguel;
        this.dataEntrega = dataEntrega;
    }

    public int getId() {
        return id;
    }

    public String getModelo() {
        return modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public boolean isAlugado() {
        return alugado;
    }

    public int getIdClienteAlugado() {
        return idClienteAlugado;
    }

    public String getDataAluguel() {
        return dataAluguel;
    }

    public String getDataEntrega() {
        return dataEntrega;
    }

    public void setAlugado(boolean alugado) {
        this.alugado = alugado;
    }

    public void setIdClienteAlugado(int idClienteAlugado) {
        this.idClienteAlugado = idClienteAlugado;
    }

    public void setDataAluguel(String dataAluguel) {
        this.dataAluguel = dataAluguel;
    }

    public void setDataEntrega(String dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    @Override
    public String toString() {
        String status = alugado ? "Alugado (Cliente ID: " + idClienteAlugado + ")" : "Disponível";
        return "ID: " + id + " | Modelo: " + modelo + " | Placa: " + placa + " | Status: " + status;
    }

    public String toFileString() {
        String dataAluguelStr = (dataAluguel != null) ? dataAluguel : "null";
        String dataEntregaStr = (dataEntrega != null) ? dataEntrega : "null";
        return id + ";" + modelo + ";" + placa + ";" + alugado + ";" + idClienteAlugado + ";" + dataAluguelStr + ";" + dataEntregaStr;
    }

    public static Carro fromFileString(String line) {
        String[] parts = line.split(";");
        if (parts.length == 7) {
            int id = Integer.parseInt(parts[0]);
            String modelo = parts[1];
            String placa = parts[2];
            boolean alugado = Boolean.parseBoolean(parts[3]);
            int idClienteAlugado = Integer.parseInt(parts[4]);
            String dataAluguel = parts[5].equals("null") ? null : parts[5];
            String dataEntrega = parts[6].equals("null") ? null : parts[6];
            return new Carro(id, modelo, placa, alugado, idClienteAlugado, dataAluguel, dataEntrega);
        }
        return null;
    }
}