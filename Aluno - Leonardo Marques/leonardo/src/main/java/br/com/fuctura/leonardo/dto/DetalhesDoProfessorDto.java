package br.com.fuctura.leonardo.dto;

import br.com.fuctura.leonardo.model.Professor;

public class DetalhesDoProfessorDto {

	private Long id;
	private String cpf;
	private String nome;
	private String email;
	private Float valorHora;
	private String certificados;
	private String tipo;

	public DetalhesDoProfessorDto(Professor professor) {
		this.id = professor.getId();
		this.cpf = professor.getCpf();
		this.nome = professor.getNome();
		this.valorHora = professor.getValorHora();
		this.certificados = professor.getCertificados();
		this.tipo = professor.getTipo().toString();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Float getValorHora() {
		return valorHora;
	}

	public void setValorHora(Float valorHora) {
		this.valorHora = valorHora;
	}

	public String getCertificados() {
		return certificados;
	}

	public void setCertificados(String certificados) {
		this.certificados = certificados;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
