package br.com.anhembi.iHealth.dto;

import javax.validation.constraints.NotBlank;

import br.com.anhembi.iHealth.modelo.Posto;
import br.com.anhembi.iHealth.modelo.Regiao;
import br.com.anhembi.iHealth.repository.RegiaoRepository;

public class PostoForm {
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String endereco;
	@NotBlank
	private String urlMapa;
	
	@NotBlank
	private String nomeRegiao;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getUrlMapa() {
		return urlMapa;
	}

	public void setUrlMapa(String urlMapa) {
		this.urlMapa = urlMapa;
	}

	public String getNomeRegiao() {
		return nomeRegiao;
	}

	public void setNomeRegiao(String nomeRegiao) {
		this.nomeRegiao = nomeRegiao;
	}

	public Posto converter(RegiaoRepository regiaoRepository) {
		Posto posto = new Posto();
		posto.setEndereco(endereco);
		posto.setNome(nome);
		posto.setUrlMapa(urlMapa);
		Regiao regiao = regiaoRepository.findByNome(nomeRegiao);
		posto.setRegiao(regiao);
		return posto;
	}
}
