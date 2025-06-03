package com.seudominio.model;

public class Cliente {
    private int id;
    private String nome;
    private String cpf;
    private String telefone;
    private String endereco;

    public Cliente(int id, String nome, String cpf, String telefone, String endereco) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.endereco = endereco;
    }


    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEndereco() {
        return endereco;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Nome: " + nome + " | CPF: " + cpf + " | Tel: " + telefone + " | End: " + endereco;
    }

    public String toFileString() {
        return id + ";" + nome + ";" + cpf + ";" + telefone + ";" + endereco;
    }

    public static Cliente fromFileString(String line) {
        String[] parts = line.split(";");
        if (parts.length == 5) {
            int id = Integer.parseInt(parts[0]);
            String nome = parts[1];
            String cpf = parts[2];
            String telefone = parts[3];
            String endereco = parts[4];
            return new Cliente(id, nome, cpf, telefone, endereco);
        }
        return null;
    }
}
