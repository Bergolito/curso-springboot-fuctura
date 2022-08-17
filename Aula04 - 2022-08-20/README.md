[![N|Solid](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTCpE4j0_9z28bBm16L_pnFlq4ip65HWKlx9-Vg_lzQ&s)](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTCpE4j0_9z28bBm16L_pnFlq4ip65HWKlx9-Vg_lzQ&s)

# Fuctura - Curso de Spring Boot

## Aula 04 - 20/08/2022

# Em **AlunosController**, melhorar o método de listarAlunos adicionando parâmetro nomeAluno

	@GetMapping
	public List<AlunoDto> listaAlunos(@RequestParam(required = false) String nomeAluno){
		
	  if (nomeAluno == null) {
	    List<Aluno> Alunos = alunoRepository.findAll();
	    return AlunoDto.converter(Alunos);
	  } else {
	    List<Aluno> Alunos = alunoRepository.findByNome(nomeAluno);
	    return AlunoDto.converter(Alunos);
	  }
	}


- Na interface **AlunosRepository**, adicionar o método abstrato findByNome()

      public interface AlunoRepository extends JpaRepository<Aluno, Long> {

        // métodos
        List<Aluno> findByNome(String nomeAluno);
      }

- No repositório de Alunos, podemos ter mais métodos, por exemplo:
        
        List<Aluno> findByCpf(String cpf);


## Paginação nos serviços de consulta (GET)

- Na sua classe principal do projeto (no meu caso EscolaApiApplication), adicionar a anotação **@EnableSpringDataWebSupport**

- Na classe **AlunosController**, altera o método listaAlunos() para receber o parâmetro de paginação na própria requisição

		@GetMapping
		public Page<AlunoDto> listaAlunos(
				@RequestParam(required = false) String nomeAluno,
				@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao) {

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

- Em AlunoRepository, o método findByNome() agora recebe o Pageable como parâmetro, e retorna não mais uma lista de Aluno mas uma coleção Page<Aluno>

	    Page<Aluno> findByNome(String nomeAluno, Pageable paginacao);

- Antes de testar a paginação o ideal seria triplicar o arquivo data.sql e alterar alguns valores, ficando assim:


		INSERT INTO ALUNO (cpf, nome, email, fone, tipo) VALUES  
		('11111111111', 'Alberto', 'aluno111@escola.com', '81 1234-5555', 'CONVENCIONAL');

		INSERT INTO ALUNO (cpf, nome, email, fone, tipo) VALUES  
		('22222222222', 'Bruno', 'aluno222@escola.com', '81 1234-5555', 'CONVENCIONAL');

		INSERT INTO ALUNO (cpf, nome, email, fone, tipo) VALUES  
		('33333333333', 'Carlos', 'aluno333@escola.com', '81 1234-5555', 'MONITOR');

		INSERT INTO ALUNO (cpf, nome, email, fone, tipo) VALUES  
		('44444444444', 'Daniel', 'aluno111@escola.com', '81 1234-5555', 'CONVENCIONAL');

		INSERT INTO ALUNO (cpf, nome, email, fone, tipo) VALUES  
		('55555555555', 'Emerson', 'aluno222@escola.com', '81 1234-5555', 'CONVENCIONAL');

		INSERT INTO ALUNO (cpf, nome, email, fone, tipo) VALUES  
		('66666666666', 'Fernando', 'aluno333@escola.com', '81 1234-5555', 'MONITOR');

		INSERT INTO ALUNO (cpf, nome, email, fone, tipo) VALUES  
		('77777777777', 'Guilherme', 'aluno111@escola.com', '81 1234-5555', 'CONVENCIONAL');

		INSERT INTO ALUNO (cpf, nome, email, fone, tipo) VALUES  
		('88888888888', 'Heitor', 'aluno222@escola.com', '81 1234-5555', 'CONVENCIONAL');

		INSERT INTO ALUNO (cpf, nome, email, fone, tipo) VALUES  
		('99999999999', 'José', 'aluno333@escola.com', '81 1234-5555', 'MONITOR');

		INSERT INTO ALUNO (cpf, nome, email, fone, tipo) VALUES  
		('99999999999', 'Paulo', 'aluno333@escola.com', '81 1234-5555', 'MONITOR');

		INSERT INTO ALUNO (cpf, nome, email, fone, tipo) VALUES  
		('99999999999', 'Roberto', 'aluno333@escola.com', '81 1234-5555', 'MONITOR');


- Para testar, é só acessar a url no Postman, e na aba **Params** passar a informação do número da página (&page) e a quantidade de registros por página (size). <br>

  Exemplos:  <br>
  localhost:8080/alunos <br>
  localhost:8080/alunos?page=0&size=5 <br>
  localhost:8080/alunos?page=1&size=5 <br>
  localhost:8080/alunos?page=2&size=5 <br>


## Ordenação

- Agora que já temos o suporte à Paginação, ficou mais fácil para ordenar os registros. É só passar na própria requisição as informações referentes à ordenação.

    Exemplos: <br>
	localhost:8080/alunos?size=15&sort=nome,asc <br>
	localhost:8080/alunos?size=15&sort=nome,desc <br>
	localhost:8080/alunos?size=15&sort=cpf,asc  <br>
	localhost:8080/alunos?size=15&sort=nome,asc&sort=cpf,desc  <br>



## Cache de Consultas

- No pom.xml adicionar a seguinte dependência:
	
		<dependency>
		    <groupId>org.springframework.boot</groupId>
		    <artifactId>spring-boot-starter-cache</artifactId>
		</dependency>

- Na classe principal do projeto (no meu caso EscolaApiApplication), adicionar mais uma anotação **@EnableCaching**
	
- Na classe AlunosController, adicionar a anotação @Cacheable(), nomeando-a com algum valor (no meu caso, value = "listaDeAlunos")
	
		@GetMapping
		@Cacheable(value = "listaDeAlunos")
		public Page<AlunoDto> listaAlunos(
				@RequestParam(required = false) String nomeAluno,
				@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao) {

			if (nomeAluno == null) {
				Page<Aluno> Alunos = alunoRepository.findAll(paginacao);
				return AlunoDto.converter(Alunos);
			} else {
				Page<Aluno> Alunos = alunoRepository.findByNome(nomeAluno, paginacao);
				return AlunoDto.converter(Alunos);
			}
		}
	
- No arquivo application.properties, adicionar a seguintes configurações que nos ajudarão a verificar se o Spring está cacheando ou não as consultas
	
		spring.jpa.properties.hibernate.show_sql=true
		spring.jpa.properties.hibernate.format_sql=true
	
- Para testar é só acessar o endpoint de listagem de alunos, verificando se o console da sua IDE (no meu caso STS) emite algum log de saída.
	
	localhost:8080/alunos <br>
	localhost:8080/alunos?nomeAluno=Huguinho 1 <br>

	- Saída do console do STS:
	
		Hibernate: <br> 
		    select <br>
			aluno0_.id as id1_0_, <br>
			aluno0_.cpf as cpf2_0_, <br>
			aluno0_.email as email3_0_, <br>
			aluno0_.fone as fone4_0_, <br>
			aluno0_.nome as nome5_0_, <br>
			aluno0_.tipo as tipo6_0_  <br>
		    from <br>
			aluno aluno0_ limit ?	 <br>
	
- Se usar cache nas consultas, lembrar de invalidar o cache nos métodos	de cadastro (POST)
	
		@PostMapping
		@Transactional
		@CacheEvict(value = "listaDeAlunos", allEntries = true)
		public ResponseEntity<AlunoDto> cadastrar(@RequestBody @Valid AlunoForm form) {
			Aluno aluno = form.converterDTO();
			alunoRepository.save(aluno);

			return new ResponseEntity<AlunoDto>(new AlunoDto(aluno), HttpStatus.CREATED);
		}		
