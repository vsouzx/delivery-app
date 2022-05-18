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
import javax.persistence.UniqueConstraint;


@Entity
public class Remedio implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true, length = 40)
	private String nome;
	
	private byte[] imagem;

	
	@ManyToMany
	@JoinTable(
			name="postos_remedios",
			uniqueConstraints = @UniqueConstraint(columnNames = { "remedio_id", "posto_id" }),
			joinColumns = @JoinColumn(name = "remedio_id"),
			inverseJoinColumns = @JoinColumn(name = "posto_id")
			)	
	private List<Posto> postos;
	
	@ManyToMany
	@JoinTable(
			name="pedidos_remedios",
			uniqueConstraints = @UniqueConstraint(columnNames = { "remedio_id", "pedido_id" }),
			joinColumns = @JoinColumn(name = "remedio_id"),
			inverseJoinColumns = @JoinColumn(name = "pedido_id")
			)	
	private List<Pedido> pedidos;
	
	
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


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public byte[] getImagem() {
		return imagem;
	}

	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}

	public List<Posto> getPostos() {
		return postos;
	}

	public void setPostos(List<Posto> postos) {
		this.postos = postos;
	}


	
	
	
}
