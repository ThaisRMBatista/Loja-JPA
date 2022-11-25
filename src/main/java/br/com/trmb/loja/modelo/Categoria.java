package br.com.trmb.loja.modelo;

import javax.persistence.*;

@Entity
@Table(name = "categorias")
public class Categoria {

    //Chave composta
    @EmbeddedId
    private CategoriaId id;

    public Categoria() {
    }

    public Categoria(String nome) {
        this.id = new CategoriaId(nome, "XPTO");
    }

    public String getNome() {
        return this.id.getNome();
    }

}
