package br.com.fuctura.leonardo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fuctura.leonardo.model.Aluno;

//Entidade e tipo da chave é passado aqui no JPA REPOSITORY
public interface AlunosRepository extends JpaRepository<Aluno, Long> {

//	List<Aluno> findByNome(String nomeAluno);

	Page<Aluno> findByNome(String nomeAluno, Pageable paginacao);

	List<Aluno> findByCpf(String cpf);

}
