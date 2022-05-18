package br.com.anhembi.iHealth.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.UniqueConstraint;

@Entity
public class Pedido implements Serializable{


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToMany
	@JoinTable(
			name="pedidos_remedios",
			uniqueConstraints = @UniqueConstraint(columnNames = { "remedio_id", "pedido_id" }),
			joinColumns = @JoinColumn(name = "pedido_id"),
			inverseJoinColumns = @JoinColumn(name = "remedio_id")
			)	
	private List<Remedio> remedios;
	
	private byte[] receitaMedica;
	
	@Enumerated(EnumType.STRING)
	private Status status = Status.ANALISE;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Remedio> getRemedios() {
		return remedios;
	}

	public void setRemedios(List<Remedio> remedios) {
		this.remedios = remedios;
	}

	public byte[] getReceitaMedica() {
		return receitaMedica;
	}

	public void setReceitaMedica(byte[] receitaMedica) {
		this.receitaMedica = receitaMedica;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
