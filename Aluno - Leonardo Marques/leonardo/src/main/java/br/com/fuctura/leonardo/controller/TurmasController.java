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

import br.com.fuctura.leonardo.controller.form.AtualizacaoTurmaForm;
import br.com.fuctura.leonardo.controller.form.TurmaForm;
import br.com.fuctura.leonardo.dto.DetalhesDaTurmaDto;
import br.com.fuctura.leonardo.dto.TurmaDto;
import br.com.fuctura.leonardo.model.Turma;
import br.com.fuctura.leonardo.repository.TurmasRepository;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/turmas")
public class TurmasController {

	// ira fazer a estanciacao manutencao desse repositorio9
	@Autowired
	private TurmasRepository turmaRepository;

	@GetMapping
	@Cacheable(value = "listaDeTurmas")
	@Operation(summary = "listarTurmas", description = "listar as turmas da escola")
	public Page<TurmaDto> listaTurmas(@RequestParam(required = false) String nomeTurma,
			@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao) {

		if (nomeTurma == null) {
			Page<Turma> Turmas = turmaRepository.findAll(paginacao);
			return TurmaDto.converter(Turmas);
		} else {
			Page<Turma> Turmas = turmaRepository.findByNome(nomeTurma, paginacao);
			return TurmaDto.converter(Turmas);
		}
	}

	@PostMapping
	@Transactional
	@CacheEvict(value = "cadastrarTurmas", allEntries = true)
	@Operation(summary = "cadastrarTurmas", description = "cadastrar a turma da escola")
	public ResponseEntity<TurmaDto> cadastrar(@RequestBody @Valid TurmaForm form) {
		Turma turma = form.converterDTO();
		turmaRepository.save(turma);

		return new ResponseEntity<TurmaDto>(new TurmaDto(turma), HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	@Operation(summary = "detalharTurmas", description = "detalha uma turma")
	public ResponseEntity<DetalhesDaTurmaDto> detalhar(@PathVariable Long id) {
		Optional<Turma> Turma = turmaRepository.findById(id);
		if (Turma.isPresent()) {
			return ResponseEntity.ok(new DetalhesDaTurmaDto(Turma.get()));
		}

		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	@Transactional
	@Operation(summary = "atualizarTurmas", description = "atualizar a turma da escola")
	public ResponseEntity<TurmaDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTurmaForm form) {
		Optional<Turma> optional = turmaRepository.findById(id);
		if (optional.isPresent()) {
			Turma Turma = form.atualizar(id, turmaRepository);
			return ResponseEntity.ok(new TurmaDto(Turma));
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	@Transactional
	@Operation(summary = "removerTurmas", description = "remover a turma pelo ID")
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Turma> optional = turmaRepository.findById(id);
		if (optional.isPresent()) {
			turmaRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

}
