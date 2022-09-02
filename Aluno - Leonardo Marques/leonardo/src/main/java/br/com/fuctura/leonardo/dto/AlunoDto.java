package br.com.fuctura.leonardo.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;

import br.com.fuctura.leonardo.model.Aluno;

public class AlunoDto {

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
	private String fone;

	@Nullable
	private String tipo;

	// Construtor
	public AlunoDto(Aluno aluno) {
		this.cpf = aluno.getCpf();
		this.nome = aluno.getNome();
		this.email = aluno.getEmail();
		this.fone = aluno.getFone();
		this.tipo = aluno.getTipo();
	}

	// converte Aluno em AlunoDto
//	public static List<AlunoDto> converter(List<Aluno> alunos) {
//		return alunos.stream().map(AlunoDto::new).collect(Collectors.toList());
//	}

	public static Page<AlunoDto> converter(Page<Aluno> alunos) {
		return alunos.map(AlunoDto::new);
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

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
