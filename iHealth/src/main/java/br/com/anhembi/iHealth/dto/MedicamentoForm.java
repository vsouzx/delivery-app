package br.com.anhembi.iHealth.dto;

import javax.validation.constraints.NotBlank;

import br.com.anhembi.iHealth.modelo.Remedio;

public class MedicamentoForm {

	@NotBlank
	private String nome;
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Remedio converter() {
		Remedio remedio = new Remedio();
		remedio.setNome(nome);
		return remedio;
	}
	
	
}
