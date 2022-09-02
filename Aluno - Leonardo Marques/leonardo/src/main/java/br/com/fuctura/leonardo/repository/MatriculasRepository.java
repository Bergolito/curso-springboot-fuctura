package br.com.fuctura.leonardo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fuctura.leonardo.model.Matricula;

//Entidade e tipo da chave é passado aqui no JPA REPOSITORY
public interface MatriculasRepository extends JpaRepository<Matricula, Long> {

//	Page<Matricula> findByMatricula(String nomeMatricula, Pageable paginacao);

}
