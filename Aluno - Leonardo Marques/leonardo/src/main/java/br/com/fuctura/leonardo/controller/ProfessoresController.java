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

import br.com.fuctura.leonardo.controller.form.AtualizacaoProfessorForm;
import br.com.fuctura.leonardo.controller.form.ProfessorForm;
import br.com.fuctura.leonardo.dto.DetalhesDoProfessorDto;
import br.com.fuctura.leonardo.dto.ProfessorDto;
import br.com.fuctura.leonardo.model.Professor;
import br.com.fuctura.leonardo.repository.ProfessoresRepository;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/professores")
public class ProfessoresController {

	@Autowired
	private ProfessoresRepository professorRepository;

	@GetMapping
	@Cacheable(value = "listaDeProfessores")
	@Operation(summary = "listarProfessores", description = "listar os professores da escola")
	public Page<ProfessorDto> listaProfessores(@RequestParam(required = false) String nomeProfessor,
			@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao) {

		if (nomeProfessor == null) {
			Page<Professor> Professores = professorRepository.findAll(paginacao);
			return ProfessorDto.converter(Professores);
		} else {
			Page<Professor> Professores = professorRepository.findByNome(nomeProfessor, paginacao);
			return ProfessorDto.converter(Professores);
		}
	}

	@PostMapping
	@Transactional
	@CacheEvict(value = "cadastrarProfessores", allEntries = true)
	@Operation(summary = "cadastrarProfessores", description = "cadastrar os professores da escola")
	public ResponseEntity<ProfessorDto> cadastrar(@RequestBody @Valid ProfessorForm form) {
		Professor professor = form.converterDTO();
		professorRepository.save(professor);

		return new ResponseEntity<ProfessorDto>(new ProfessorDto(professor), HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	@Operation(summary = "detalharProfessores", description = "detalha uma professor")
	public ResponseEntity<DetalhesDoProfessorDto> detalhar(@PathVariable Long id) {
		Optional<Professor> Professor = professorRepository.findById(id);
		if (Professor.isPresent()) {
			return ResponseEntity.ok(new DetalhesDoProfessorDto(Professor.get()));
		}

		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	@Transactional
	@Operation(summary = "atualizarProfessores", description = "atualizar os professores da escola")
	public ResponseEntity<ProfessorDto> atualizar(@PathVariable Long id,
			@RequestBody @Valid AtualizacaoProfessorForm form) {
		Optional<Professor> optional = professorRepository.findById(id);
		if (optional.isPresent()) {
			Professor Professor = form.atualizar(id, professorRepository);
			return ResponseEntity.ok(new ProfessorDto(Professor));
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	@Transactional
	@Operation(summary = "removerProfessores", description = "remover o professor pelo ID")
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Professor> optional = professorRepository.findById(id);
		if (optional.isPresent()) {
			professorRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

}
