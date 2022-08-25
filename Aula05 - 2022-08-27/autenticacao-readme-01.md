[![N|Solid](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTCpE4j0_9z28bBm16L_pnFlq4ip65HWKlx9-Vg_lzQ&s)](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTCpE4j0_9z28bBm16L_pnFlq4ip65HWKlx9-Vg_lzQ&s)

# Fuctura - Curso de Spring Boot

## Aula 05 - 27/08/2022

# Protegendo a API com Spring Security

- No pom, adicionar as seguintes dependências referente ao Spring Security e ao JWT:

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>
    
		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt</artifactId>
			<version>0.9.1</version>
		</dependency>
    
- No pacote br.com.fuctura.escola.model, adicionar as seguintes classes: Perfil.java e Usuario.java

- Na classe Usuario.java, observar o detalhe:

	@Entity <br>
	public class Usuario implements UserDetails {

	}

	@Entity <br>
	public class Perfil implements GrantedAuthority {

	}
	
- No arquivo data.sql, adicionar os inserts referentes ao Usuário:

      /*
       * Cadastro de Usuários
       */ 
      INSERT INTO USUARIO(nome, email, senha) VALUES('Aluno', 'aluno@escola.com', '$2a$10$pzGb4RsG6syepEiry9xGxuSX2oOKL8K6G9PO21VJkBquKpEtMSpVS');
      INSERT INTO USUARIO(nome, email, senha) VALUES('Professor', 'prof@escola.com', '$2a$10$pzGb4RsG6syepEiry9xGxuSX2oOKL8K6G9PO21VJkBquKpEtMSpVS');


- No pacote br.com.fuctura.escola.repository, criar a classe referente ao Repositório de Usuário: UsuarioRepository.java

		public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

		    Optional<Usuario> findByEmail (String email);
		}

- Criar o pacote  br.com.fuctura.escola.config.security, dentro dele colar as seguintes classes: AutenticacaoService, AutenticacaoViaTokenFilter, SecurityConfigurations e TokenService

- Na classe SecurityConfigurations, observar as anotações @EnableWebSecurity e @Configuration, e observar o método configure(HttpSecurity http) referente às configurações de Autorização:

        // Configuracoes de autorizacao
        @Override
        protected void configure(HttpSecurity http) throws Exception {
          http.authorizeRequests()
          .antMatchers(HttpMethod.GET, "/alunos").permitAll()
          .antMatchers(HttpMethod.GET, "/alunos/*").permitAll()
          .antMatchers(HttpMethod.POST, "/auth").permitAll()
          .anyRequest().authenticated()
          .and().csrf().disable()
          .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          .and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);
        }

- No arquivo application.properties, adicionar 2 novas keys:

      # jwt
      escola.jwt.secret=A+X;fTJP&Pd,TD9dwVq(hsHX,ya^<wsD_UK7L+@=S;{'CydP]{v@}G'b>et;yz$*\yL5S8EJN:%P:X%H9>#nYLrX}@\s?CQcpspH,2emzBc!Q[V'AYa~uzF8WR~AUrMzxp/V$9([S9X#zj/CH('#]B_Hc+%fGhe27YB;^j4\Xk=Ju"Ap~_&<L;=!Z;!,2UP;!hF3P]j85#*`&T]/kB/W^6$v~u6qpejL>kY^f)sy4:qTq_Ec!-z!@aAp~sLKGU>$
      escola.jwt.expiration=86400000

- Agora, vamos testar quais os endpoints estão liberados. Tente acessar as seguintes urls referentes ao listarAlunos(), detalharAluno() e cadastrarAluno():

  http://localhost:8080/alunos <br>
  http://localhost:8080/alunos/1 <br>
  
 
- Qual foi o resultado ao tentar acessar a requisição POST? 
 
# Autenticação de Usuário

- No pacote br.com.fuctura.escola.controller.form, adicionar a classe LoginForm

- No pacote br.com.fuctura.escola.controller, adicionar a classe AutenticacaoController

		@RestController
		@RequestMapping("/auth")
		public class AutenticacaoController {

			@Autowired
			private AuthenticationManager authManager;

			@Autowired
			private TokenService tokenService;

			@PostMapping
			public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginForm form) {
				UsernamePasswordAuthenticationToken dadosLogin = form.converter();

				try {
					Authentication authentication = authManager.authenticate(dadosLogin);
					String token = tokenService.gerarToken(authentication);
					System.out.println("Token => "+token);
					return ResponseEntity.ok(new TokenDto(token, "Bearer"));
				} catch (AuthenticationException e) {
					return ResponseEntity.badRequest().build();
				}
			}

		}


- No pacote br.com.fuctura.escola.dto, adicionar a classe TokenDto

		public class TokenDto {

			private String token;
			private String tipo;

			public TokenDto(String token, String tipo) {
				this.token = token;
				this.tipo = tipo;
			}

			public String getToken() {
				return token;
			}

			public String getTipo() {
				return tipo;
			}

		}

- No Postman, vamos criar uma requisição POST para a url localhost:8080/auth, e passando no corpo da requisição as informações referentes ao usuário logado:


      {
          "email":"prof@escola.com",
          "senha": "123456" 
      }
    
    ou     
    
      {
          "email":"aluno@escola.com",
          "senha": "123456" 
      }


- Após a requisição POST, espera-se o seguinte resultado:

      {
          "token": "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJBUEkgZGUgQ29udHJvbGUgZGEgRXNjb2xhIiwic3ViIjoiMiIsImlhdCI6MTY2MTQ1MDQ4NCwiZXhwIjoxNjYxNTM2ODg0fQ.kzQh6mcGMaDpRBENgcFkafCMroIuggAl8sU-PsL0XHo",
          "tipo": "Bearer"
      }

- De posse do Token gerado pela autenticação, agora podemos fazer as requisições que estavam pendentes de autenticação.

- Para o POST, PUT e DELETE, faz-se necessário adicionar na aba Headers a chave Authorization => Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJBUEkgZGUgQ29udHJvbGUgZGEgRXNjb2xhIiwic3ViIjoiMiIsImlhdCI6MTY2MTQ1MDc2OSwiZXhwIjoxNjYxNTM3MTY5fQ.wV4sFKcSt6O8BMe_pwOBR7C6AGehxBKDOh0N1oRW7Q0 

- Após isto, será possível realizar as inserções, atualizações e deleções

