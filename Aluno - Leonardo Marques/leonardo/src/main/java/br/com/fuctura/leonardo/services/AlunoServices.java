package br.com.fuctura.leonardo.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.fuctura.leonardo.model.Aluno;
import br.com.fuctura.leonardo.repository.AlunosRepository;

@Service
@Transactional
public class AlunoServices {

	@Autowired
	private AlunosRepository alunoRepository;

	public List<Aluno> listAll() {
		return alunoRepository.findAll(Sort.by("nome").ascending());
	}

}
