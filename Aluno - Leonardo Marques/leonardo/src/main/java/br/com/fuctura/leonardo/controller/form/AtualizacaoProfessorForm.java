package br.com.fuctura.leonardo.controller.form;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import br.com.fuctura.leonardo.model.Professor;
import br.com.fuctura.leonardo.repository.ProfessoresRepository;

public class AtualizacaoProfessorForm {

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

	public Professor atualizar(Long id, ProfessoresRepository professorRepository) {
		Optional<Professor> professor = professorRepository.findById(id);

		if (professor.isPresent()) {
			professor.get().setNome(this.nome);
			professor.get().setEmail(this.email);
			professor.get().setValorHora(this.valorHora);
			professor.get().setCertificados(this.certificados);
			professor.get().setTipo(this.tipo);

			return professor.get();
		}

		return null;
	}
}
