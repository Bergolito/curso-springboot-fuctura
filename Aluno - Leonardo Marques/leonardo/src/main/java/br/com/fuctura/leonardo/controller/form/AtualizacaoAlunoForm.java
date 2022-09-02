package br.com.fuctura.leonardo.controller.form;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import br.com.fuctura.leonardo.model.Aluno;
import br.com.fuctura.leonardo.repository.AlunosRepository;

public class AtualizacaoAlunoForm {

	@NotNull
	@NotEmpty
	@Length(min = 5)
	private String nome;

	@Nullable
	private String email;

	@Nullable
	private String fone;

	@Nullable
	private String tipo;

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getFone() {
		return fone;
	}

	public String getTipo() {
		return tipo;
	}

	public Aluno atualizar(Long id, AlunosRepository alunoRepository) {
		Optional<Aluno> aluno = alunoRepository.findById(id);

		if (aluno.isPresent()) {
			aluno.get().setNome(this.nome);
			aluno.get().setEmail(this.email);
			aluno.get().setFone(this.fone);
			aluno.get().setTipo(this.tipo);

			return aluno.get();
		}

		return null;
	}
}
