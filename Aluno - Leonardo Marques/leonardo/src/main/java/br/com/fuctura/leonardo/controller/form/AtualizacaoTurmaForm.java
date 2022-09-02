package br.com.fuctura.leonardo.controller.form;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import br.com.fuctura.leonardo.model.Curso;
import br.com.fuctura.leonardo.model.Professor;
import br.com.fuctura.leonardo.model.Turma;
import br.com.fuctura.leonardo.repository.TurmasRepository;

public class AtualizacaoTurmaForm {

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

	public String getNome() {
		return nome;
	}

	public Professor getProfessor() {
		return professor;
	}

	public Curso getCurso() {
		return curso;
	}

	public Integer getCargaHoraria() {
		return cargaHoraria;
	}

	public Turma atualizar(Long id, TurmasRepository turmaRepository) {
		Optional<Turma> turma = turmaRepository.findById(id);

		if (turma.isPresent()) {
			turma.get().setNome(this.nome);
			turma.get().setProfessor(this.professor);
			turma.get().setCurso(this.curso);
			turma.get().setCargaHoraria(this.cargaHoraria);

			return turma.get();
		}

		return null;
	}
}
