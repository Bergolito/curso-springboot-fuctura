package br.com.fuctura.leonardo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fuctura.leonardo.model.Turma;

//Entidade e tipo da chave é passado aqui no JPA REPOSITORY
public interface TurmasRepository extends JpaRepository<Turma, Long> {

	Page<Turma> findByNome(String nomeTurma, Pageable paginacao);
}
