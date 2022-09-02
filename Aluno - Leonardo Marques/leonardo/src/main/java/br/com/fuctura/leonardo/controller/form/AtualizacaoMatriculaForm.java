package br.com.fuctura.leonardo.controller.form;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.lang.Nullable;

import br.com.fuctura.leonardo.model.Aluno;
import br.com.fuctura.leonardo.model.Matricula;
import br.com.fuctura.leonardo.model.Turma;
import br.com.fuctura.leonardo.repository.MatriculasRepository;

public class AtualizacaoMatriculaForm {

	@Nullable
	private Turma turma;

	@Nullable
	private Aluno aluno;

	@Nullable
	private LocalDateTime dataMatricula;

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

	public Matricula atualizar(Long id, MatriculasRepository matriculaRepository) {
		Optional<Matricula> matricula = matriculaRepository.findById(id);

		if (matricula.isPresent()) {
			matricula.get().setTurma(this.turma);
			matricula.get().setAluno(this.aluno);
			matricula.get().setDataMatricula(this.dataMatricula);

			return matricula.get();
		}

		return null;
	}
}
