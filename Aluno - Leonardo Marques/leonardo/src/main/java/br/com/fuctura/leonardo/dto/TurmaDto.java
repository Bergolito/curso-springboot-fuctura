package br.com.fuctura.leonardo.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;

import br.com.fuctura.leonardo.model.Curso;
import br.com.fuctura.leonardo.model.Professor;
import br.com.fuctura.leonardo.model.Turma;

public class TurmaDto {

	@NotNull
	@NotEmpty
	@Length(min = 5)
	private String nome;

	@Nullable
	private Professor professor;

	@Nullable
	private Curso curso;

	@Nullable
	private Integer cargaHoraria;

	// Construtor
	public TurmaDto(Turma turma) {
		this.nome = turma.getNome();
		this.professor = turma.getProfessor();
		this.curso = turma.getCurso();
		this.cargaHoraria = turma.getCargaHoraria();
	}

	// converte Turma em TurmaDto
	public static Page<TurmaDto> converter(Page<Turma> turmas) {
		return turmas.map(TurmaDto::new);
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
