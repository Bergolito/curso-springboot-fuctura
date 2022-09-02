package br.com.fuctura.leonardo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fuctura.leonardo.model.Professor;

//Entidade e tipo da chave é passado aqui no JPA REPOSITORY
public interface ProfessoresRepository extends JpaRepository<Professor, Long> {

	Page<Professor> findByNome(String nomeProfessor, Pageable paginacao);

}
