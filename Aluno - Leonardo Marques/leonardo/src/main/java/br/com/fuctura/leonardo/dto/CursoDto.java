package br.com.fuctura.leonardo.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;

import br.com.fuctura.leonardo.model.Curso;

public class CursoDto {

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

	public CursoDto(Curso curso) {
		this.nome = curso.getNome();
		this.requisitos = curso.getRequisitos();
		this.cargaHoraria = curso.getCargaHoraria();
		this.preco = curso.getPreco();
	}

	public static Page<CursoDto> converter(Page<Curso> cursos) {
		return cursos.map(CursoDto::new);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRequisitos() {
		return requisitos;
	}

	public void setRequisitos(String requisitos) {
		this.requisitos = requisitos;
	}

	public Integer getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(Integer cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public Float getPreco() {
		return preco;
	}

	public void setPreco(Float preco) {
		this.preco = preco;
	}

}
