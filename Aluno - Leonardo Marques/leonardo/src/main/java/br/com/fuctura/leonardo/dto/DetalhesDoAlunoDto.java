package br.com.fuctura.leonardo.dto;

import br.com.fuctura.leonardo.model.Aluno;

public class DetalhesDoAlunoDto {

	private Long id;
	private String cpf;
	private String nome;
	private String email;
	private String fone;
	private String tipo;

	public DetalhesDoAlunoDto(Aluno aluno) {
		this.id = aluno.getId();
		this.cpf = aluno.getCpf();
		this.nome = aluno.getNome();
		this.email = aluno.getEmail();
		this.fone = aluno.getFone();
		this.tipo = aluno.getTipo().toString();
	}

	public Long getId() {
		return id;
	}

	public String getCpf() {
		return cpf;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getFone() {
		return fone;
	}

	public String getTipo() {
		return tipo;
	}

}
