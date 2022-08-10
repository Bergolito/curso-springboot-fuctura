[![N|Solid](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTCpE4j0_9z28bBm16L_pnFlq4ip65HWKlx9-Vg_lzQ&s)](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTCpE4j0_9z28bBm16L_pnFlq4ip65HWKlx9-Vg_lzQ&s)

# Fuctura - Curso de Spring Boot

## Aula 03 - 13/08/2022

## Nosso primeiro EndPoint

        @RestController
        @RequestMapping("/primeiro")
        public class PrimeiroController {

            @GetMapping("/listar1")
            public List<Aluno> listar1(){
                Aluno aluno1 = new Aluno("11111111111", "Aluno 1", "aluno1@escola.com");
                Aluno aluno2 = new Aluno("22222222222", "Aluno 2", "aluno2@escola.com");
                Aluno aluno3 = new Aluno("33333333333", "Aluno 3", "aluno3@escola.com");

                return Arrays.asList(aluno1, aluno2, aluno3);
            }	

        }

##  Criar a classe DTO - AlunoDTO


        public class AlunoDto {

            private String cpf;
            private String nome;
            private String email;

            public AlunoDto(Aluno aluno) {
                this.cpf = aluno.getCpf();
                this.nome = aluno.getNome();
                this.email = aluno.getEmail();
            }

            // getters aqui

        }

## Melhorando nosso primeiro controlador

	@GetMapping("/listar2")
	public List<AlunoDto> listar2(){
		Aluno aluno1 = new Aluno("11111111111", "Aluno 1", "aluno1@escola.com");
		Aluno aluno2 = new Aluno("22222222222", "Aluno 2", "aluno2@escola.com");
		Aluno aluno3 = new Aluno("33333333333", "Aluno 3", "aluno3@escola.com");
		
		List<Aluno> listaAlunos = Arrays.asList(aluno1, aluno2, aluno3);
		
		List<AlunoDto> listaAlunosDTO = listaAlunos.stream().map(AlunoDto::new).collect(Collectors.toList());		

		return listaAlunosDTO;
	}
        
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
    
    
## Classe Aluno com JPA

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


## Arquivo data.sql

        INSERT INTO ALUNO (cpf, nome, email, fone, tipo) VALUES  
        ('11111111111', 'Huguinho', 'aluno111@escola.com', '81 1234-5555', 'CONVENCIONAL');

        INSERT INTO ALUNO (cpf, nome, email, fone, tipo) VALUES  
        ('22222222222', 'Zezinho', 'aluno222@escola.com', '81 1234-5555', 'CONVENCIONAL');

        INSERT INTO ALUNO (cpf, nome, email, fone, tipo) VALUES  
        ('33333333333', 'Luizinho', 'aluno333@escola.com', '81 1234-5555', 'MONITOR');

## No pacote br.com.fuctura.escola.repository, cria o repositório de Aluno

	public interface AlunoRepository extends JpaRepository<Aluno, Long> {

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
	
## Em AlunosControlador, cria o serviço GET para listar alunos

	@GetMapping
	public List<AlunoDto> listaAlunos() {
		List<Aluno> Alunos = alunoRepository.findAll();
		return AlunoDto.converter(Alunos);
	}

## Criar o pacote br.com.fuctura.escola.controller.form

	package br.com.fuctura.escola.controller.form;

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
		private String dataNasc;

		@Nullable
		private String tipo;

		// ... getters/setters

		public Aluno converterDTO(AlunoRepository alunoRepository) {
			Aluno aluno = new Aluno(cpf, nome, email, fone, tipo); 
			return aluno;
		}
	}

## Em AlunosControlador, cria o serviço POST para cadastrar novo aluno

	@PostMapping
	@Transactional
	public ResponseEntity<AlunoDto> cadastrar(@RequestBody @Valid AlunoForm form) {
		Aluno aluno = form.converterDTO(alunoRepository);
		alunoRepository.save(aluno);
		
		return new ResponseEntity<AlunoDto>(new AlunoDto(aluno), HttpStatus.CREATED);
	}

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

	}

## Em AlunosControlador, cria o serviço GET para detalhar um aluno já existente

	@GetMapping("/{id}")
	public ResponseEntity<DetalhesDoAlunoDto> detalhar(@PathVariable Long id) {
		Optional<Aluno> Aluno = alunoRepository.findById(id);
		if (Aluno.isPresent()) {
			return ResponseEntity.ok(new DetalhesDoAlunoDto(Aluno.get()));
		}
		
		return ResponseEntity.notFound().build();
	}

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

## Em AlunosControlador, cria o serviço PUT para atualizar os dados de um aluno já existente

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


## Em AlunosControlador, cria o serviço DELETE para remover os dados de um aluno já existente

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


