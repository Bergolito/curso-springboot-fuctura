[![N|Solid](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTCpE4j0_9z28bBm16L_pnFlq4ip65HWKlx9-Vg_lzQ&s)](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTCpE4j0_9z28bBm16L_pnFlq4ip65HWKlx9-Vg_lzQ&s)

# Fuctura - Curso de Spring Boot

## Aula 03 - 13/08/2022

## Nosso primeiro EndPoint

        @RestController
        @RequestMapping("/primeiro")
        public class PrimeiroController {

		@GetMapping("/listar1")
		public List<Aluno> listarAlunos1(){
			Aluno aluno1 = new Aluno("11111111111", "Aluno 1", "aluno1@escola.com", "81 1234-5678", TipoAluno.CONVENCIONAL.toString());
			Aluno aluno2 = new Aluno("22222222222", "Aluno 2", "aluno2@escola.com", "81 1234-5678", TipoAluno.CONVENCIONAL.toString());
			Aluno aluno3 = new Aluno("33333333333", "Aluno 3", "aluno3@escola.com", "81 1234-5678", TipoAluno.MONITOR.toString());

			return Arrays.asList(aluno1, aluno2, aluno3);
		}

        }
	
- Testar no seu navegador ou no cliente Postman através da url: localhost:8080/primeiro/listar1
	
## Melhorando nosso primeiro controlador
	
- Criação de um outro endpoint para listar os alunos criados, mas desta vez retornando objetos AlunoDto

- Adicionar no seu pom a nova dependência do projeto referente ao Bean Validation

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation</artifactId>
		</dependency>


### No pacote br.com.fuctura.escola.dto, criar a classe DTO - AlunoDTO

        public class AlunoDto {

		@NotNull @NotEmpty @Length(min = 11, max = 11)
		private String cpf;

		@NotNull @NotEmpty @Length(min = 5)
		private String nome;

		@Nullable
		private String email;

		@Nullable
		private String fone;

		@Nullable 
		private String tipo;

		// Construtor
		public AlunoDto(Aluno aluno) {
			this.cpf = aluno.getCpf();
			this.nome = aluno.getNome();
			this.email = aluno.getEmail();
			this.fone = aluno.getFone();
			this.tipo = aluno.getTipo();
		}

		// converte Aluno em AlunoDto
		public static List<AlunoDto> converter(List<Aluno> alunos) {
			return alunos.stream().map(AlunoDto::new).collect(Collectors.toList());
		}

	        // getters aqui

        }


### Na classe PrimeiroController, adicionar novo método de listagem de alunos retornando uma lista de objetos AlunoDto

	@GetMapping("/listar2")
	public List<AlunoDto> listarAlunos2(){
		Aluno aluno1 = new Aluno("11111111111", "Aluno 1", "aluno1@escola.com", "81 1234-5678", TipoAluno.CONVENCIONAL.toString());
		Aluno aluno2 = new Aluno("22222222222", "Aluno 2", "aluno2@escola.com", "81 1234-5678", TipoAluno.CONVENCIONAL.toString());
		Aluno aluno3 = new Aluno("33333333333", "Aluno 3", "aluno3@escola.com", "81 1234-5678", TipoAluno.MONITOR.toString());
		
		List<Aluno> listaAlunos = Arrays.asList(aluno1, aluno2, aluno3);
		
		List<AlunoDto> listaAlunosDTO = listaAlunos.stream().map(AlunoDto::new).collect(Collectors.toList());		

		return listaAlunosDTO;
	}

- Testar no seu navegador ou no cliente Postman através da url: localhost:8080/primeiro/listar2

# Spring Data JPA (Java Persistence API)

## Arquivo application.properties


    # datasource
    spring.datasource.driverClassName=org.h2.Driver
    spring.datasource.url=jdbc:h2:mem:escola-controle-api
    spring.datasource.username=sa
    spring.datasource.password=
    
    # jpa
    spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
    spring.jpa.hibernate.ddl-auto=update
    
    # ATENCAO
    spring.jpa.defer-datasource-initialization=true
    
    # Console do banco H2
    spring.h2.console.enabled=true
    spring.h2.console.path=/h2-console
    
    
## Na classe Aluno, adicionar as anotações referentes ao JPA

        @Entity
        @Table
        public class Aluno {

            @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
            private Long id;

            @Column(nullable = false, name = "CPF")
            private String cpf;

            @Column(nullable = false, name = "NOME")
            private String nome;

            @Column(nullable = true, name = "EMAIL")
            private String email;

            @Column(nullable = false, name = "FONE")
            private String fone;

            @Column(nullable = false, name = "TIPO")
            private String tipo = TipoAluno.CONVENCIONAL.toString();

        }    


## No arquivo data.sql, adicionar o comando INSERT de três registros de Aluno

        INSERT INTO ALUNO (cpf, nome, email, fone, tipo) VALUES  
        ('11111111111', 'Huguinho', 'aluno111@escola.com', '81 1234-5555', 'CONVENCIONAL');

        INSERT INTO ALUNO (cpf, nome, email, fone, tipo) VALUES  
        ('22222222222', 'Zezinho', 'aluno222@escola.com', '81 1234-5555', 'CONVENCIONAL');

        INSERT INTO ALUNO (cpf, nome, email, fone, tipo) VALUES  
        ('33333333333', 'Luizinho', 'aluno333@escola.com', '81 1234-5555', 'MONITOR');

- Após executar o comando de SQL para inserir alunos, acessar no navegador a url http://localhost:8080/h2-console



## No pacote br.com.fuctura.escola.repository, cria a interface AlunoRepository referente ao repositório de Aluno

- Extende a interface JpaRepository<T, ID>, substituindo o **T** pela classe de entidade (Aluno) e o **ID** pelo tipo da chave (Long) 

		public interface AlunoRepository extends JpaRepository<Aluno, Long> {
			//
		}


## No pacote br.com.fuctura.escola.controller, cria o controlador de Aluno

	@RestController
	@RequestMapping("/alunos")
	public class AlunosController {
		
		//
	}

## Injeta o repositório de Aluno no AlunosControlador

	@Autowired
	private AlunoRepository alunoRepository;
	
## Em AlunosController, cria o serviço GET para listar alunos

	@GetMapping
	public List<AlunoDto> listaAlunos() {
		List<Aluno> Alunos = alunoRepository.findAll();
		return AlunoDto.converter(Alunos);
	}
	
- Testar o serviço GET criando no seu navegador ou no cliente Postman através da url: localhost:8080/alunos

## Criar o pacote br.com.fuctura.escola.controller.form

	package br.com.fuctura.escola.controller.form;

## Spring Validation

- Anotações específicas para validação dos dados do DTO: 
  <p>@NotNull 
  <p>@NotEmpty 
  <p>@Length(min = 11, max = 11)
  <p>@Size
  <p>@Nullable

## No pacote br.com.fuctura.escola.controller.form, criar a classe AlunoForm


	public class AlunoForm {

		@NotNull @NotEmpty @Length(min = 11, max = 11)
		private String cpf;

		@NotNull @NotEmpty  @Length(min = 5)
		private String nome;

		@Nullable
		private String email;

		@Nullable
		private String fone;

		@Nullable
		private String tipo;

		// ... getters/setters
		public Aluno converterDTO() {
			Aluno aluno = new Aluno(cpf, nome, email, fone, tipo); 
			return aluno;
		}

	}

## Em AlunosController, cria o serviço POST para cadastrar novo aluno

	@PostMapping
	@Transactional
	public ResponseEntity<AlunoDto> cadastrar(@RequestBody @Valid AlunoForm form) {
		Aluno aluno = form.converterDTO();
		alunoRepository.save(aluno);
		
		return new ResponseEntity<AlunoDto>(new AlunoDto(aluno), HttpStatus.CREATED);
	}

- Para testar o método POST cadastrar, faz-se necessário duas coisas:
	  
	1) Na aba Headers (cabeçalho) do Postman, adicionar a propriedade **Content-Type** com o valor **application/json** 	  
	2) Na aba Body (corpo da requisição), adicionar os dados para a classe **AlunoForm**
	  
	{ <br>
		"cpf": "44444444444", <br>
		"nome": "Pato Donald", <br>
		"email": "superduck@escola.com", <br> 
		"fone": "81 4444-4444",	<br> 
		"tipo": "CONVENCIONAL" <br>
	} <br>

- Após cadastrar novo Aluno, executar o serviço **GET** de listarAlunos através da url localhost:8080/alunos para verificar se existe um novo aluno (4 alunos no total)
	  
## No pacote br.com.fuctura.escola.dto, criar a classe DetalhesDoAlunoDto

	public class DetalhesDoAlunoDto {

		private Long id;
		private String cpf;
		private String nome;
		private String email;
		private String fone;
		private String tipo;

		public DetalhesDoAlunoDto(Aluno aluno) {
			this.id = aluno.getId();
			this.cpf = aluno.getCpf();
			this.nome = aluno.getNome();
			this.email = aluno.getEmail();
			this.fone = aluno.getFone();
			this.tipo = aluno.getTipo().toString();
		}

	  	// cria apenas os métodos getters
	}

## Em AlunosController, cria o serviço **GET** para detalhar um aluno já existente

	@GetMapping("/{id}")
	public ResponseEntity<DetalhesDoAlunoDto> detalhar(@PathVariable Long id) {
		Optional<Aluno> Aluno = alunoRepository.findById(id);
		if (Aluno.isPresent()) {
			return ResponseEntity.ok(new DetalhesDoAlunoDto(Aluno.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	  
- Para testar o serviço **GET** de detalhar aluno, acessar através da url localhost:8080/alunos/{id} 
	  
	- Exemplo: localhost:8080/alunos/1 (passa o id do aluno como parâmetro)	  

## No pacote br.com.fuctura.escola.controller.form, criar a classe AtualizacaoAlunoForm

	public class AtualizacaoAlunoForm {

		@NotNull @NotEmpty  @Length(min = 5)
		private String nome;

		@Nullable
		private String email;

		@Nullable
		private String fone;

		@Nullable
		private String tipo;

		public String getNome() {
			return nome;
		}

		public String getEmail() {
			return email;
		}

		public String getFone() {
			return fone;
		}

		public String getTipo() {
			return tipo;
		}

		public Aluno atualizar(Long id, AlunoRepository alunoRepository) {
			Optional<Aluno> aluno = alunoRepository.findById(id);

			if(aluno.isPresent()) {
				aluno.get().setNome(this.nome);
				aluno.get().setEmail(this.email);
				aluno.get().setFone(this.fone);
				aluno.get().setTipo(this.tipo);

				return aluno.get();
			}

			return null;
		}

	}

## Em AlunosController, cria o serviço **PUT** para atualizar os dados de um aluno já existente

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<AlunoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoAlunoForm form) {
		Optional<Aluno> optional = alunoRepository.findById(id);
		if (optional.isPresent()) {
			Aluno Aluno = form.atualizar(id, alunoRepository);
			return ResponseEntity.ok(new AlunoDto(Aluno));
		}
		
		return ResponseEntity.notFound().build();
	}

- Para testar o método PUT atualizar, faz-se necessário duas coisas:
	  
	1) Na aba Headers (cabeçalho) do Postman, adicionar a propriedade **Content-Type** com o valor **application/json** 	  
	2) Na aba Body (corpo da requisição), adicionar os dados para a classe **AtualizacaoAlunoForm**
	  
		{ <br>
		    "cpf": "11111111111", <br>
		    "nome": "Huguinho Novo aluno 2022", <br>
		    "email": "aluno111-huguinho@escola.com", <br>
		    "fone": "81 1234-5555", <br>
		    "tipo": "CONVENCIONAL" <br>
		} <br>
	  
	3) Acessar o serviço **PUT** através da URL localhost:8080/alunos/1, passando o json de atualização no corpo da requisição
	4) Após a atualização, chamar o serviço **GET** de listarAlunos (localhost:8080/alunos) e verificar se o registro de id 1 atualizou 

	  
## Em AlunosController, cria o serviço **DELETE** para remover os dados de um aluno já existente

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Aluno> optional = alunoRepository.findById(id);
		if (optional.isPresent()) {
			alunoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}

- Para testar o serviço **DELETE** de excluir aluno, acessar através da url localhost:8080/alunos/{id}

	- Exemplo: localhost:8080/alunos/4 (passa o id do aluno como parâmetro)	  

	- Após a exclusão de aluno, chamar o serviço GET de listarAlunos (localhost:8080/alunos) e verificar se ainda existe o registro de id 4


## Classe AlunosController completa

	package br.com.fuctura.escola.controller;

	import java.util.List;
	import java.util.Optional;

	import javax.transaction.Transactional;
	import javax.validation.Valid;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.HttpStatus;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.DeleteMapping;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PathVariable;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.PutMapping;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RestController;

	import br.com.fuctura.escola.controller.form.AlunoForm;
	import br.com.fuctura.escola.controller.form.AtualizacaoAlunoForm;
	import br.com.fuctura.escola.dto.AlunoDto;
	import br.com.fuctura.escola.dto.DetalhesDoAlunoDto;
	import br.com.fuctura.escola.model.Aluno;
	import br.com.fuctura.escola.repository.AlunoRepository;

	@RestController
	@RequestMapping("/alunos")
	public class AlunosController {

		@Autowired
		private AlunoRepository alunoRepository;

		@GetMapping
		public List<AlunoDto> listaAlunos() {
			List<Aluno> Alunos = alunoRepository.findAll();
			return AlunoDto.converter(Alunos);
		}

		@GetMapping("/listaPorNome")
		public List<AlunoDto> listaAlunoPorNome(String nomeAluno) {
			if (nomeAluno == null) {
				List<Aluno> Alunos = alunoRepository.findAll();
				return AlunoDto.converter(Alunos);
			} else {
				List<Aluno> Alunos = alunoRepository.findByNome(nomeAluno);
				return AlunoDto.converter(Alunos);
			}
		}

		@PostMapping
		@Transactional
		public ResponseEntity<AlunoDto> cadastrar(@RequestBody @Valid AlunoForm form) {
			Aluno aluno = form.converterDTO();
			alunoRepository.save(aluno);

			return new ResponseEntity<AlunoDto>(new AlunoDto(aluno), HttpStatus.CREATED);
		}

		@GetMapping("/{id}")
		public ResponseEntity<DetalhesDoAlunoDto> detalhar(@PathVariable Long id) {
			Optional<Aluno> Aluno = alunoRepository.findById(id);
			if (Aluno.isPresent()) {
				return ResponseEntity.ok(new DetalhesDoAlunoDto(Aluno.get()));
			}

			return ResponseEntity.notFound().build();
		}

		@PutMapping("/{id}")
		@Transactional
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
		public ResponseEntity<?> remover(@PathVariable Long id) {
			Optional<Aluno> optional = alunoRepository.findById(id);
			if (optional.isPresent()) {
				alunoRepository.deleteById(id);
				return ResponseEntity.ok().build();
			}

			return ResponseEntity.notFound().build();
		}

	}
