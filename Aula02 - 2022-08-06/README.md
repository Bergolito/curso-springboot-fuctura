[![N|Solid](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTCpE4j0_9z28bBm16L_pnFlq4ip65HWKlx9-Vg_lzQ&s)](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTCpE4j0_9z28bBm16L_pnFlq4ip65HWKlx9-Vg_lzQ&s)

# Fuctura - Curso de Spring Boot

## Aula 02 - 06/08/2022

## Criar os seguintes pacotes:
- br.com.fuctura.escola.model
- br.com.fuctura.escola.dto
- br.com.fuctura.escola.repository
- br.com.fuctura.escola.controller

## Classe Aluno


        package br.com.fuctura.escola.model;

        public class Aluno {

                private Long id;
                private String cpf;
                private String nome;	
                private String email;	
                private String fone;
                // Tipo pode ser CONVENCIONAL ou MONITOR
                private String tipo = TipoAluno.CONVENCIONAL.toString();

        }


## Classe Enum TipoAluno 


        package br.com.fuctura.escola.model;

        public enum TipoAluno {

            CONVENCIONAL,
            MONITOR;

        }

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

## Classe AlunoDTO


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

