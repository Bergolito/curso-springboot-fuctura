package br.com.fuctura.leonardo.dto;

import java.time.LocalDateTime;

import br.com.fuctura.leonardo.model.Aluno;
import br.com.fuctura.leonardo.model.Matricula;
import br.com.fuctura.leonardo.model.Turma;

public class DetalhesDaMatriculaDto {

	private Long id;
	private Turma turma;
	private Aluno aluno;
	private LocalDateTime dataMatricula;

	public DetalhesDaMatriculaDto(Matricula matricula) {
		this.id = matricula.getId();
		this.turma = matricula.getTurma();
		this.aluno = matricula.getAluno();
		this.dataMatricula = matricula.getDataMatricula();

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public LocalDateTime getDataMatricula() {
		return dataMatricula;
	}

	public void setDataMatricula(LocalDateTime dataMatricula) {
		this.dataMatricula = dataMatricula;
	}

}
