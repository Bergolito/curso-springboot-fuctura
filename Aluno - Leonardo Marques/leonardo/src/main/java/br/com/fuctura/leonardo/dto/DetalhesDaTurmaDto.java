package br.com.fuctura.leonardo.dto;

import br.com.fuctura.leonardo.model.Curso;
import br.com.fuctura.leonardo.model.Professor;
import br.com.fuctura.leonardo.model.Turma;

public class DetalhesDaTurmaDto {

	private Long id;
	private String nome;
	private Professor professor;
	private Curso curso;
	private Integer cargaHoraria;

	public DetalhesDaTurmaDto(Turma turma) {
		this.id = turma.getId();
		this.nome = turma.getNome();
		this.professor = turma.getProfessor();
		this.curso = turma.getCurso();
		this.cargaHoraria = turma.getCargaHoraria();
	}

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

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Integer getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(Integer cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

}
