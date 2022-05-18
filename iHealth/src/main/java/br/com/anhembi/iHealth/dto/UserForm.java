package br.com.anhembi.iHealth.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.anhembi.iHealth.modelo.Regiao;
import br.com.anhembi.iHealth.modelo.User;
import br.com.anhembi.iHealth.repository.RegiaoRepository;

public class UserForm {

	@NotBlank
	@Length(min = 2, max=30)
	private String nome;
	
	@NotBlank
	@Length(min = 2, max=30)
	private String sobrenome;
	
	@NotBlank
	@Length(min = 15, max=15)
	private String codSus;
	
	@NotBlank
	@Length(min = 8, max=50)
	private String email;
	
	@NotBlank
	@Length(min = 11, max=11)
	private String cpf;
	
	@NotBlank
	@Length(min = 3, max=10)
	private String password;
	
	@NotBlank
	@Length(min = 10, max=100)
	private String endereco;
	
	@NotBlank @NotNull
	private String regiao;
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getCodSus() {
		return codSus;
	}

	public void setCodSus(String codSus) {
		this.codSus = codSus;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getRegiao() {
		return regiao;
	}

	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}

	public User converter(RegiaoRepository regiaoRepository) {
		User user = new User();
		user.setNome(nome);
		user.setSobrenome(sobrenome);
		user.setCodSus(codSus);
		user.setCpf(cpf);
		user.setAtivo(true);
		user.setEmail(email);
		user.setEndereco(endereco);
		user.setPassword(password);
		Regiao userRegiao = regiaoRepository.findByNome(regiao);
		user.setRegiao(userRegiao);
		return user;
	}

}
