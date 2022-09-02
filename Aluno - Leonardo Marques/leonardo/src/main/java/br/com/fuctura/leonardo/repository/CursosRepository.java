package br.com.fuctura.leonardo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fuctura.leonardo.model.Curso;

public interface CursosRepository extends JpaRepository<Curso, Long> {

	Page<Curso> findByNome(String nomeCurso, Pageable paginacao);

}
