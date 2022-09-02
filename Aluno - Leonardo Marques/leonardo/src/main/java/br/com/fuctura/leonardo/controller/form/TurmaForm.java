package br.com.fuctura.leonardo.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import br.com.fuctura.leonardo.model.Curso;
import br.com.fuctura.leonardo.model.Professor;
import br.com.fuctura.leonardo.model.Turma;

public class TurmaForm {

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

	public Turma converterDTO() {
		Turma turma = new Turma(nome, professor, curso, cargaHoraria);
		return turma;
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
