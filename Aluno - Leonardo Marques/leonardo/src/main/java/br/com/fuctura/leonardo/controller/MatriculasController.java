package br.com.fuctura.leonardo.controller;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fuctura.leonardo.controller.form.AtualizacaoMatriculaForm;
import br.com.fuctura.leonardo.controller.form.MatriculaForm;
import br.com.fuctura.leonardo.dto.DetalhesDaMatriculaDto;
import br.com.fuctura.leonardo.dto.MatriculaDto;
import br.com.fuctura.leonardo.model.Matricula;
import br.com.fuctura.leonardo.repository.MatriculasRepository;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/matriculas")
public class MatriculasController {

	// ira fazer a estanciacao manutencao desse repositorio9
	@Autowired
	private MatriculasRepository matriculaRepository;

	@GetMapping
	@Cacheable(value = "listaDeMatriculas")
	@Operation(summary = "listarMatriculas", description = "listar as matriculas da escola")
	public Page<MatriculaDto> listaMatriculas(@RequestParam(required = false) String nomeMatricula,
			@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao) {

		if (nomeMatricula == null) {
			Page<Matricula> Matriculas = matriculaRepository.findAll(paginacao);
			return MatriculaDto.converter(Matriculas);
		} else {
			Page<Matricula> Matriculas = null;
//			  matriculaRepository.findByMatricula(nomeMatricula, paginacao);
			return MatriculaDto.converter(Matriculas);
		}
	}

	// Transactional irá fazer o procedimento de abertura , persistencia e
	// fechamento do banco
	@PostMapping
	@Transactional
	@CacheEvict(value = "cadastrarMatriculas", allEntries = true)
	@Operation(summary = "cadastrarMatriculas", description = "cadastrar as matriculas da escola")
	public ResponseEntity<MatriculaDto> cadastrar(@RequestBody @Valid MatriculaForm form) {
		Matricula matricula = form.converterDTO();
		matriculaRepository.save(matricula);

		return new ResponseEntity<MatriculaDto>(new MatriculaDto(matricula), HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	@Operation(summary = "detalharMatriculas", description = "detalha uma matricula")
	public ResponseEntity<DetalhesDaMatriculaDto> detalhar(@PathVariable Long id) {
		Optional<Matricula> Matricula = matriculaRepository.findById(id);
		if (Matricula.isPresent()) {
			return ResponseEntity.ok(new DetalhesDaMatriculaDto(Matricula.get()));
		}

		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	@Transactional
	@Operation(summary = "atualizarMatriculas", description = "atualizar as matriculas da escola")
	public ResponseEntity<MatriculaDto> atualizar(@PathVariable Long id,
			@RequestBody @Valid AtualizacaoMatriculaForm form) {
		Optional<Matricula> optional = matriculaRepository.findById(id);
		if (optional.isPresent()) {
			Matricula Matricula = form.atualizar(id, matriculaRepository);
			return ResponseEntity.ok(new MatriculaDto(Matricula));
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	@Transactional
	@Operation(summary = "removerMatriculas", description = "remover a matricula pelo ID")
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Matricula> optional = matriculaRepository.findById(id);
		if (optional.isPresent()) {
			matriculaRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

}
