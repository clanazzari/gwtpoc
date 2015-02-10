package br.tur.reservafacil.gwtpoc.shared.vo;

import java.io.Serializable;

public class ReservaVO implements Serializable {

    private static final long serialVersionUID = -1439812011272268890L;

    private Integer id;
    private String  nome;
    private String  tipo;

    public ReservaVO(final Integer id, final String nome, final String tipo) {
	this.id = id;
	this.nome = nome;
	this.tipo = tipo;
    }

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public String getNome() {
	return nome;
    }

    public void setNome(String nome) {
	this.nome = nome;
    }

    public String getTipo() {
	return tipo;
    }

    public void setTipo(String tipo) {
	this.tipo = tipo;
    }
}
