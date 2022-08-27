[![N|Solid](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTCpE4j0_9z28bBm16L_pnFlq4ip65HWKlx9-Vg_lzQ&s)](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTCpE4j0_9z28bBm16L_pnFlq4ip65HWKlx9-Vg_lzQ&s)

# Fuctura - Curso de Spring Boot

## Aula 05 - 27/08/2022

## Monitorando sua aplicação com Spring Actuator

- Adicionar a seguinte dependẽncia referente à Segurança

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>
		
- Criar o pacote br.com.fuctura.escola.config.security e dentro dele, crie a classe SecurityConfigurations.java:

		@EnableWebSecurity
		@Configuration
		public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

			// Configuracoes de autenticacao
			@Override
			protected void configure(AuthenticationManagerBuilder auth) throws Exception {
				//
			}

			@Override
			protected void configure(HttpSecurity http) throws Exception {
				http.authorizeRequests().anyRequest().permitAll()  
				    .and().csrf().disable();
			}
		    
		    	// Configuracoes de recursos estaticos(js, css, imagens, etc.)
			@Override
			public void configure(WebSecurity web) throws Exception {
				//
			}
		}


- Adicionar a dependência no pom

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>

- Após configurar a dependência, já teremos disponível na nossa aplicação, mais um endpoint, acessível através da url http://localhost:8080/actuator

		{
		    "_links": {
			"self": {
			    "href": "http://localhost:8080/actuator",
			    "templated": false
			},
			"health": {
			    "href": "http://localhost:8080/actuator/health",
			    "templated": false
			},
			"health-path": {
			    "href": "http://localhost:8080/actuator/health/{*path}",
			    "templated": true
			}
		    }
		}
		
- Tentar acessar a url localhost:8080/actuator/health e ver se o sistema está no AR

		{
		    "status": "UP",
		    "components": {
			"db": {
			    "status": "UP",
			    "details": {
				"database": "H2",
				"validationQuery": "isValid()"
			    }
			},
			"diskSpace": {
			    "status": "UP",
			    "details": {
				"total": 944057839616,
				"free": 452381790208,
				"threshold": 10485760,
				"exists": true
			    }
			},
			"ping": {
			    "status": "UP"
			}
		    }
		}

- Adicionar mais informações no application.properties

		# actuator
		management.endpoint.health.show-details=always
		management.endpoints.web.exposure.include=*
		info.app.name=@project.name@
		info.app.version=@project.version@

- Para monitorar nossa aplicação, usaremos uma ferramenta chamada Spring Boot Admin: https://github.com/codecentric/spring-boot-admin

- Necessário criar uma nossa aplicação no Spring Initializr, com os valores de group=br.com.fuctura, artifact=spring-boot-admin e adicionar as seguintes dependências:

		<dependency>
		    <groupId>de.codecentric</groupId>
		    <artifactId>spring-boot-admin-starter-server</artifactId>
		    <version>2.6.6</version>
		</dependency>
		<dependency>
		    <groupId>org.springframework.boot</groupId>
		    <artifactId>spring-boot-starter-web</artifactId>
		</dependency>


- Na classe principal do projeto, adicione as remova a anotação que existe e adicione as 3 novas anotações:

		@Configuration
		@EnableAutoConfiguration
		@EnableAdminServer
		public class SpringBootAdminApplication {
		    public static void main(String[] args) {
			SpringApplication.run(SpringBootAdminApplication.class, args);
		    }
		}

- No arquivo application.properties, adicione a nova porta do admin para não conflitar com a nossa aplicação da API Escola que roda na porta 8080

	server.port=8081

- Após configurado tudo certo, inicie o projeto do spring-boot-admin, acesse a url http://localhost:8081/ e veja o resultado


- Agora precisamos configurar a nossa API de Escola para ser monitorada pelo Spring Boot Admin

- Adicione as duas novas dependências no pom da API Escola

		<dependency>
		    <groupId>de.codecentric</groupId>
		    <artifactId>spring-boot-admin-starter-client</artifactId>
		    <version>2.5.1</version>
		</dependency>
		<dependency>
		    <groupId>org.springframework.boot</groupId>
		    <artifactId>spring-boot-starter-security</artifactId>
		</dependency>

- No application.properties da aplicacação cliente (API Escola), definir qual será o endereço do Spring Boot Admin

	spring.boot.admin.client.url=http://localhost:8081

- Agora com tudo configurado, inicie a aplicação da API Escola e veja o resultado do monitoramento do Spring Boot Admin

- Qual foi o resultado na sua máquina? Deu tudo certo?

## Documentando sua API com Spring Doc Open Api

- Documentação Oficial do Spring Doc: https://springdoc.org/#Introduction


- Adicionar uma nova dependência no pom

		<dependency>
			<groupId>org.springdoc</groupId>
			<artifactId>springdoc-openapi-ui</artifactId>
			<version>1.6.11</version>
		</dependency>

- No application.properties, adicionar uma nova configuração

	# swagger-ui custom path
	springdoc.swagger-ui.path=/swagger-ui.html



- Criar um novo pacote br.com.fuctura.escola.config, e dentro dele, criar a classe SwaggerConfigurations, adicionando uma nova anotação @Configuration

	@Configuration
	public class SwaggerConfigurations {

		@Bean
		public GroupedOpenApi publicApi() {
			return GroupedOpenApi.builder().group("br.com.fuctura").pathsToMatch("/**").build();
		}

	}

- Na classe SecurityConfigurations, do pacote br.com.fuctura.escola.config.security, atualize o método configure(WebSecurity web)


		//Configuracoes de recursos estaticos(js, css, imagens, etc.)
		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");
		}
	
- Após isto, adicione as tags de documentação no controlador de Alunos


	- Em listaAlunos(): @Operation(summary = "listarAlunos", description = "listar os alunos da escola")
	- Em detalhar(): 	@Operation(summary = "detalhar", description = "detalha um aluno de acordo com o Id")




## Melhorando seu código com Projeto Lombok

- Site oficial do Lombok: (https://projectlombok.org/)

- Para que serve o Lombok?

- Onde baixar o instalador? (https://projectlombok.org/download)

- Como instalar na sua IDE? (https://projectlombok.org/setup/eclipse)

	- Baixar o arquivo .jar para qualquer pasta do seu computador.
	- Executar o seguinte comando: java -jar lombok.jar
	- Após abrir a tela do instalador, clicar no botão "Specify location..." e indicar qual é o diretório onde está instalado a sua IDE
	- Ao selecionar o diretório da instalação, clique no arquivo SpringToolSuite4.ini à direita e clique no botão "Select"
	- Após isto, clique no botão "Install/Update"
	- Se tudo deu certo, aparecerá uma tela com a mensagem de instalação com sucesso "Install Successful"
	
	![Lombok instalação OK!](https://github.com/Bergolito/curso-springboot-fuctura/blob/main/Aula05%20-%202022-08-27/lombok-sucesso.png "Lombok instalação OK")
	
	- Feche a tela de instalação no botão "Quit Install"
	- Feche o Eclipse/STS e o abra novamente
	- Após Eclipse/STS abrir, vá na opção Help e depois About Spring Tool Suite 4. Verifique se apareceu a opção referente ao Lombok. 
	
	<strong>Exemplo: Lombok v1.18.24 "Envious Ferret" is installed. https://projectlombok.org/</strong>
	
	![Lombok instalação OK!](https://github.com/Bergolito/curso-springboot-fuctura/blob/main/Aula05%20-%202022-08-27/lombok-sts-OK.png "Lombok rodando OK no STS")
	
	
	
	
	
	
	
	

- Para habilitar o Lombok no seu projeto, é necessário adicionar a seguinte dependência:

		<dependency>
		    <groupId>org.projectlombok</groupId>
		    <artifactId>lombok</artifactId>
		    <version>1.18.24</version>
		    <scope>provided</scope>
		</dependency>

- Agora que o Lombok já está instalado na sua IDE e também,  a dependência foi adicionada no projeto, podemos ajustar todas as classes de Entidade (Aluno, Professor, Curso, Turma, Matricula) e também as classes de DTO (AlunoDTO, AlunoForm, AtualizacaoAlunoForm) etc. 


		@Entity
		@Table
		@NoArgsConstructor @AllArgsConstructor
		@Data
		@EqualsAndHashCode @ToString
		public class Aluno implements Serializable {
		     //
		}

- Para Aluno, podemos ajustar as classes de Entidade (Aluno) e as classes de DTO (AlunoDTO, AlunoForm, AtualizacaoAlunoForm), adicionar o construtor sem argumentos, com todos os campos e removendo os getters e setters depois de adicionar a anotação @Data.



