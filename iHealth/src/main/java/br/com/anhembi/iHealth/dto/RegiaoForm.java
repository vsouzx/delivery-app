package br.com.anhembi.iHealth.dto;

import javax.validation.constraints.NotBlank;

import br.com.anhembi.iHealth.modelo.Regiao;

public class RegiaoForm {
	
	@NotBlank
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Regiao converter() {
		Regiao regiao = new Regiao();
		regiao.setNome(nome);
		return regiao;
	}
}
