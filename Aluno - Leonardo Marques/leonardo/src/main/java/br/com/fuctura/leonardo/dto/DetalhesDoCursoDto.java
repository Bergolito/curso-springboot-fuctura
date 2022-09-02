package br.com.fuctura.leonardo.dto;

import br.com.fuctura.leonardo.model.Curso;

public class DetalhesDoCursoDto {

	private Long id;
	private String nome;
	private String requisitos;
	private Integer cargaHoraria;
	private Float preco;

	public DetalhesDoCursoDto(Curso curso) {
		this.id = curso.getId();
		this.nome = curso.getNome();
		this.requisitos = curso.getRequisitos();
		this.cargaHoraria = curso.getCargaHoraria();
		this.preco = curso.getPreco();
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getRequisitos() {
		return requisitos;
	}

	public Integer getCargaHoraria() {
		return cargaHoraria;
	}

	public Float getPreco() {
		return preco;
	}

}
