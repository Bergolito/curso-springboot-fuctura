package br.com.fuctura.leonardo.controller.form;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import br.com.fuctura.leonardo.model.Curso;
import br.com.fuctura.leonardo.repository.CursosRepository;

public class AtualizacaoCursoForm {

	@NotNull
	@NotEmpty
	@Length(min = 5)
	private String nome;

	@Nullable
	private String requisitos;

	@Nullable
	private Integer cargaHoraria;

	@Nullable
	private Float preco;

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

	public Curso atualizar(Long id, CursosRepository cursoRepository) {
		Optional<Curso> curso = cursoRepository.findById(id);

		if (curso.isPresent()) {
			curso.get().setNome(this.nome);
			curso.get().setRequisitos(this.requisitos);
			curso.get().setCargaHoraria(this.cargaHoraria);
			curso.get().setPreco(this.preco);

			return curso.get();
		}

		return null;
	}
}
