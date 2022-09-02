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

import br.com.fuctura.leonardo.controller.form.AtualizacaoCursoForm;
import br.com.fuctura.leonardo.controller.form.CursoForm;
import br.com.fuctura.leonardo.dto.CursoDto;
import br.com.fuctura.leonardo.dto.DetalhesDoCursoDto;
import br.com.fuctura.leonardo.model.Curso;
import br.com.fuctura.leonardo.repository.CursosRepository;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/cursos")
public class CursosController {

	@Autowired
	private CursosRepository cursoRepository;

	@GetMapping
	@Cacheable(value = "listaDeCursos")
	@Operation(summary = "listarCursos", description = "listar os cursos da escola")
	public Page<CursoDto> listaCursos(@RequestParam(required = false) String nomeCurso,
			@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao) {

		if (nomeCurso == null) {
			Page<Curso> Cursos = cursoRepository.findAll(paginacao);
			return CursoDto.converter(Cursos);
		} else {
			Page<Curso> Cursos = cursoRepository.findByNome(nomeCurso, paginacao);
			return CursoDto.converter(Cursos);
		}
	}

	@PostMapping
	@Transactional
	@CacheEvict(value = "cadastrarCursos", allEntries = true)
	@Operation(summary = "cadastrarCursos", description = "cadastrar os cursos da escola")
	public ResponseEntity<CursoDto> cadastrar(@RequestBody @Valid CursoForm form) {
		Curso curso = form.converterDTO();
		cursoRepository.save(curso);

		return new ResponseEntity<CursoDto>(new CursoDto(curso), HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	@Operation(summary = "detalharCursos", description = "detalha um curso")
	public ResponseEntity<DetalhesDoCursoDto> detalhar(@PathVariable Long id) {
		Optional<Curso> Curso = cursoRepository.findById(id);
		if (Curso.isPresent()) {
			return ResponseEntity.ok(new DetalhesDoCursoDto(Curso.get()));
		}

		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	@Transactional
	@Operation(summary = "atualizarCursos", description = "atualizar os cursos da escola")
	public ResponseEntity<CursoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoCursoForm form) {
		Optional<Curso> optional = cursoRepository.findById(id);
		if (optional.isPresent()) {
			Curso Curso = form.atualizar(id, cursoRepository);
			return ResponseEntity.ok(new CursoDto(Curso));
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	@Transactional
	@Operation(summary = "removerCursos", description = "remover o curso pelo ID")
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Curso> optional = cursoRepository.findById(id);
		if (optional.isPresent()) {
			cursoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

}
