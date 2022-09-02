package br.com.fuctura.leonardo.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;

import br.com.fuctura.leonardo.model.Professor;

public class ProfessorDto {

	@NotNull
	@NotEmpty
	@Length(min = 11, max = 11)
	private String cpf;

	@NotNull
	@NotEmpty
	@Length(min = 5)
	private String nome;

	@Nullable
	private String email;

	@Nullable
	private String tipo;

	@Nullable
	private Float valorHora;

	@Nullable
	private String certificados;

	public ProfessorDto(Professor professor) {
		this.cpf = professor.getCpf();
		this.nome = professor.getNome();
		this.email = professor.getEmail();
		this.tipo = professor.getTipo();
		this.valorHora = professor.getValorHora();
		this.certificados = professor.getCertificados();
	}

	public static Page<ProfessorDto> converter(Page<Professor> professores) {
		return professores.map(ProfessorDto::new);
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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

}
