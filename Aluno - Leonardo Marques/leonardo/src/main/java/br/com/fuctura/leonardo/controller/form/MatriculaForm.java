package br.com.fuctura.leonardo.controller.form;

import java.time.LocalDateTime;

import org.springframework.lang.Nullable;

import br.com.fuctura.leonardo.model.Aluno;
import br.com.fuctura.leonardo.model.Matricula;
import br.com.fuctura.leonardo.model.Turma;

public class MatriculaForm {

	@Nullable
	private Turma turma;

	@Nullable
	private Aluno aluno;

	@Nullable
	private LocalDateTime dataMatricula;

	public Matricula converterDTO() {
		Matricula matricula = new Matricula(turma, aluno, dataMatricula);
		return matricula;
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
