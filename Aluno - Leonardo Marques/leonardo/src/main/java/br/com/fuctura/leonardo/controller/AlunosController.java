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

import br.com.fuctura.leonardo.controller.form.AlunoForm;
import br.com.fuctura.leonardo.controller.form.AtualizacaoAlunoForm;
import br.com.fuctura.leonardo.dto.AlunoDto;
import br.com.fuctura.leonardo.dto.DetalhesDoAlunoDto;
import br.com.fuctura.leonardo.model.Aluno;
import br.com.fuctura.leonardo.repository.AlunosRepository;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/alunos")
public class AlunosController {

	// ira fazer a estanciacao manutencao desse repositorio9
	@Autowired
	private AlunosRepository alunoRepository;

	@GetMapping
	@Cacheable(value = "listaDeAlunos")
	@Operation(summary = "listarAlunos", description = "listar os alunos da escola")
	public Page<AlunoDto> listaAlunos(@RequestParam(required = false) String nomeAluno,
			@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao) {

		if (nomeAluno == null) {
			Page<Aluno> Alunos = alunoRepository.findAll(paginacao);
			return AlunoDto.converter(Alunos);
		} else {
			Page<Aluno> Alunos = alunoRepository.findByNome(nomeAluno, paginacao);
			return AlunoDto.converter(Alunos);
		}
	}

	// Transactional irá fazer o procedimento de abertura , persistencia e
	// fechamento do banco
	@PostMapping
	@Transactional
	@CacheEvict(value = "cadastrarAlunos", allEntries = true)
	@Operation(summary = "cadastrarAlunos", description = "cadastrar os alunos da escola")
	public ResponseEntity<AlunoDto> cadastrar(@RequestBody @Valid AlunoForm form) {
		Aluno aluno = form.converterDTO();
		alunoRepository.save(aluno);

		return new ResponseEntity<AlunoDto>(new AlunoDto(aluno), HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	@Operation(summary = "detalharAlunos", description = "detalha um aluno")
	public ResponseEntity<DetalhesDoAlunoDto> detalhar(@PathVariable Long id) {
		Optional<Aluno> Aluno = alunoRepository.findById(id);
		if (Aluno.isPresent()) {
			return ResponseEntity.ok(new DetalhesDoAlunoDto(Aluno.get()));
		}

		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	@Transactional
	@Operation(summary = "atualizarAlunos", description = "atualizar os alunos da escola")
	public ResponseEntity<AlunoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoAlunoForm form) {
		Optional<Aluno> optional = alunoRepository.findById(id);
		if (optional.isPresent()) {
			Aluno Aluno = form.atualizar(id, alunoRepository);
			return ResponseEntity.ok(new AlunoDto(Aluno));
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	@Transactional
	@Operation(summary = "removerAlunos", description = "remover o aluno pelo ID")
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Aluno> optional = alunoRepository.findById(id);
		if (optional.isPresent()) {
			alunoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

}
