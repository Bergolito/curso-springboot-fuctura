[![N|Solid](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTCpE4j0_9z28bBm16L_pnFlq4ip65HWKlx9-Vg_lzQ&s)](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTCpE4j0_9z28bBm16L_pnFlq4ip65HWKlx9-Vg_lzQ&s)

# Fuctura - Curso de Spring Boot

## Aula 04 - 20/08/2022

## Melhorar o método de listarAlunos adicionando parâmetro nomeAluno

    @GetMapping
    public List<AlunoDto> listaAlunos(String nomeAluno) {
      if (nomeAluno == null) {
        List<Aluno> Alunos = alunoRepository.findAll();
        return AlunoDto.converter(Alunos);
      } else {
        List<Aluno> Alunos = alunoRepository.findByNome(nomeAluno);
        return AlunoDto.converter(Alunos);
      }
    }

- No AlunosRepository, adicionar o método abstrato findByNome()

      public interface AlunoRepository extends JpaRepository<Aluno, Long> {

        // métodos
        List<Aluno> findByNome(String nomeAluno);
      }


## Paginação nos serviços de consulta (GET)

- paginação

## Ordenação

- ordenação

## Cache

- cache

## Autenticação

- autenticação

## Projeto Lombok

- projeto Lombok

## Swagger

- Swagger

## Projeto da API de Escola

- projeto da API de Escola


