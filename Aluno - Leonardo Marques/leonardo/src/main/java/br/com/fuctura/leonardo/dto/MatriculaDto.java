package br.com.fuctura.leonardo.dto;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;

import br.com.fuctura.leonardo.model.Aluno;
import br.com.fuctura.leonardo.model.Matricula;
import br.com.fuctura.leonardo.model.Turma;

public class MatriculaDto {

	@Nullable
	private Turma turma;
	@Nullable
	private Aluno aluno;
	@Nullable
	private LocalDateTime dataMatricula;

	public MatriculaDto(Matricula matricula) {
		this.turma = matricula.getTurma();
		this.aluno = matricula.getAluno();
		this.dataMatricula = matricula.getDataMatricula();
	}

	public static Page<MatriculaDto> converter(Page<Matricula> matriculas) {
		return matriculas.map(MatriculaDto::new);
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
