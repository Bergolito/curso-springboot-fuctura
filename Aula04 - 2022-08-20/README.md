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

- No repositório podemos ter mais métodos, por exemplo:
        
        List<Aluno> findByCpf(String cpf);


## Paginação nos serviços de consulta (GET)

- Na sua classe principal do projeto (no meu caso EscolaApiApplication), adicionar a anotação **@EnableSpringDataWebSupport**

- Na classe **AlunosController**, altera o método listaAlunos() para receber o parâmetro de paginação na própria requisição

        @GetMapping
        public Page<AlunoDto> listaAlunos(@RequestParam(required = false) String nomeAluno, Pageable paginacao) {
            if (nomeAluno == null) {
                Page<Aluno> Alunos = alunoRepository.findAll(paginacao);
                return AlunoDto.converter(Alunos);
            } else {
                Page<Aluno> Alunos = alunoRepository.findByNome(nomeAluno, paginacao);
                return AlunoDto.converter(Alunos);
            }
        }
    
- Em AlunoDto, altera o método converter() para retornar uma lista de Page<AlunoDto> 

        public static Page<AlunoDto> converter(Page<Aluno> alunos) {
            return alunos.map(AlunoDto::new);
        }

- Em AlunoRepository, o método findByNome() agora recebe o Pageable como parâmetro

	    Page<Aluno> findByNome(String nomeAluno, Pageable paginacao);

- Antes de testar a paginação o ideal seria triplicar o arquivo data.sql e alterar alguns valores, ficando assim:

        INSERT INTO ALUNO (cpf, nome, email, fone, tipo) VALUES  
        ('11111111111', 'Huguinho', 'aluno111@escola.com', '81 1234-5555', 'CONVENCIONAL');

        INSERT INTO ALUNO (cpf, nome, email, fone, tipo) VALUES  
        ('22222222222', 'Zezinho', 'aluno222@escola.com', '81 1234-5555', 'CONVENCIONAL');

        INSERT INTO ALUNO (cpf, nome, email, fone, tipo) VALUES  
        ('33333333333', 'Luizinho', 'aluno333@escola.com', '81 1234-5555', 'MONITOR');

        INSERT INTO ALUNO (cpf, nome, email, fone, tipo) VALUES  
        ('44444444444', 'Huguinho 2', 'aluno111@escola.com', '81 1234-5555', 'CONVENCIONAL');

        INSERT INTO ALUNO (cpf, nome, email, fone, tipo) VALUES  
        ('55555555555', 'Zezinho 2', 'aluno222@escola.com', '81 1234-5555', 'CONVENCIONAL');

        INSERT INTO ALUNO (cpf, nome, email, fone, tipo) VALUES  
        ('66666666666', 'Luizinho 2', 'aluno333@escola.com', '81 1234-5555', 'MONITOR');

        INSERT INTO ALUNO (cpf, nome, email, fone, tipo) VALUES  
        ('77777777777', 'Huguinho 3', 'aluno111@escola.com', '81 1234-5555', 'CONVENCIONAL');

        INSERT INTO ALUNO (cpf, nome, email, fone, tipo) VALUES  
        ('88888888888', 'Zezinho 3', 'aluno222@escola.com', '81 1234-5555', 'CONVENCIONAL');

        INSERT INTO ALUNO (cpf, nome, email, fone, tipo) VALUES  
        ('99999999999', 'Luizinho 3', 'aluno333@escola.com', '81 1234-5555', 'MONITOR');



    - Para testar, é só acessar a url no Postman, passando a informação do número da página (&page) e a quantidade de registros por página (size).
        Exemplo: localhost:8080/alunos?page=0&size=3


## Ordenação

- Agora que já temos o suporte à Paginação, ficou mais fácil para ordenar os registros. É só passar na própria requisição as informações referentes à ordenação.

    Exemplos: <br>
    localhost:8080/alunos?**page**=0&**size**=3&**sort**=nome,asc <br>
    localhost:8080/alunos?**page**=0&**size**=9&**sort**=nome,asc&**sort**=email,desc <br>
    

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


