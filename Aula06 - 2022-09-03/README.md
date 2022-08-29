[![N|Solid](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTCpE4j0_9z28bBm16L_pnFlq4ip65HWKlx9-Vg_lzQ&s)](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTCpE4j0_9z28bBm16L_pnFlq4ip65HWKlx9-Vg_lzQ&s)

# Fuctura - Curso de Spring Boot

## Aula 06 - 03/09/2022

# Thymeleaf

- Adicionar a dependência


        <!-- thymeleaf -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>

- No pacote br.com.fuctura.escola.controller, criar a classe WelcomeController, e colocar o conteúdo abaixo

        @Controller
        public class WelcomeController {

            private List<String> assuntos = Arrays.asList("Rest", "MVC", "API", "JSON", "Java", "Controller", "JPA");

            @RequestMapping(value="/", method = RequestMethod.GET)
            public String main(Model model) {
                model.addAttribute("message", "Olá Aluno, seja bem vindo ao curso de Spring Boot da Fuctura");
                model.addAttribute("assuntos", assuntos);

                return "welcome"; //view
            }

            // hello?name=Bergson
            @GetMapping("/hello")
            public String mainWithParam(
                    @RequestParam(name = "name", required = false, defaultValue = "") 
              String name, Model model) {

                model.addAttribute("message", "Olá "+name+", seja bem vindo ao curso de Spring Boot da Fuctura");
                model.addAttribute("assuntos", assuntos);

                return "welcome"; //view
            }

        }

- No diretório src/main/resources/templates/, criar o arquivo HTML com o nome <strong>welcome.html</strong>

		<!DOCTYPE HTML>
		<html lang="en" xmlns:th="http://www.thymeleaf.org">
		<head>
		    <meta charset="utf-8">
		    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		    <title>Spring Boot Thymeleaf Fuctura</title>
		    <link rel="stylesheet" th:href="@{webjars/bootstrap/4.2.1/css/bootstrap.min.css}"/>
		    <link rel="stylesheet" th:href="@{/css/main.css}"/>
		</head>

		<body>

		<main role="main" class="container">

		    <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTCpE4j0_9z28bBm16L_pnFlq4ip65HWKlx9-Vg_lzQ&s">
		    <div class="starter-template">
			<h1>Curso de Spring Boot da Fuctura</h1>
			<h2>
			    <span th:text="'Hello, ' + ${message}"></span>
			</h2>
		    </div>

		    <ol>
			<li th:each="assunto : ${assuntos}" th:text="${assunto}"></li>
		    </ol>

		</main>

		<script type="text/javascript" th:src="@{webjars/bootstrap/4.2.1/js/bootstrap.min.js}"></script>
		</body>
		</html>        
	

- No application.properties, adicionar a seguinte propriedade

    spring.thymeleaf.cache=false

- Se tudo deu certo, vamos ver a seguinte apresentação

	![Apresentação com Thymeleaf!](https://github.com/Bergolito/curso-springboot-fuctura/blob/main/Aula06%20-%202022-09-03/tela-boas-vindas.png "Apresentação com Thymeleaf")
        

# Repository Query Methods

- [Query Methods](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods)

- Palavras chaves para criação de Queries: Distinct, And, Or, Between, Is, Equal, etc  


# Construindo Queries com @Query

                public interface AlunoRepository extends JpaRepository<Aluno, Long> {

                        Page<Aluno> findByNome(String nomeAluno, Pageable paginacao);

                        List<Aluno> findByCpf(String cpf);

                        List<Aluno> findByEmailAndFone(String email, String fone);

                        List<Aluno> findByEmailOrFone(String email, String fone);

                        @Query("select a from Aluno a where a.email = ?1")
                        List<Aluno> findByEmail(String email);

                        @Query("select a from Aluno a where a.fone = ?1")
                        List<Aluno> findByFone(String fone);
                }


# Named Queries

                @NamedQuery(name = "Aluno.findByEmail", query = "select a from Aluno a where a.email = ?1")
                @NamedQuery(name = "Aluno.findByFone",  query = "select a from Aluno a where a.fone = ?1")
                @NamedQuery(name = "Aluno.findByEmailAndFone", 
                        query = "select a from Aluno a where a.email = ?1 and a.fone = ?1")
                @NamedQuery(name = "Aluno.findByEmailOrFone", 
                        query = "select a from Aluno a where a.email = ?1 or a.fone = ?1")
                @NamedQuery(name = "Aluno.findByTipo",  query = "select a from Aluno a where a.tipo = ?1")
                public class Aluno implements Serializable {
                        //
                }


## Projeto da API de Escola

- projeto da API de Escola


