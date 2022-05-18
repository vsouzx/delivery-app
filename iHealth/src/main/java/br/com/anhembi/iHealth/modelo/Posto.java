package br.com.anhembi.iHealth.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.UniqueConstraint;

@Entity
public class Posto implements Serializable{

	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 50)
	private String nome;
	
	@Column(nullable = false, unique = true, length = 200)
	private String endereco;

	@ManyToOne(optional = false)
	private Regiao regiao;

	@ManyToMany
	@JoinTable(
			name="postos_remedios",
			uniqueConstraints = @UniqueConstraint(columnNames = { "remedio_id", "posto_id" }),
			joinColumns = @JoinColumn(name = "posto_id"),
			inverseJoinColumns = @JoinColumn(name = "remedio_id")
			)	
	private List<Remedio> remedios;
	
	
	private byte[] urlImagemPosto;
	
	@Column(nullable = true, unique = true, length = 2000)
	private String urlMapa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Regiao getRegiao() {
		return regiao;
	}

	public void setRegiao(Regiao regiao) {
		this.regiao = regiao;
	}

	
	public List<Remedio> getRemedios() {
		return remedios;
	}
	
	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public byte[] getUrlImagemPosto() {
		return urlImagemPosto;
	}

	public void setUrlImagemPosto(byte[] urlImagemPosto) {
		this.urlImagemPosto = urlImagemPosto;
	}

	public void setRemedios(List<Remedio> remedios) {
		this.remedios = remedios;
	}
	

	public String getUrlMapa() {
		return urlMapa;
	}

	public void setUrlMapa(String urlMapa) {
		this.urlMapa = urlMapa;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
