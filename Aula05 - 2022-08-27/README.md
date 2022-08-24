[![N|Solid](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTCpE4j0_9z28bBm16L_pnFlq4ip65HWKlx9-Vg_lzQ&s)](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTCpE4j0_9z28bBm16L_pnFlq4ip65HWKlx9-Vg_lzQ&s)

# Fuctura - Curso de Spring Boot

## Aula 05 - 27/08/2022

- Para a aula de hoje, precisaremos copiar do repositório do GitHub, as seguintes classes já prontas:

Usuario, Perfil, SecurityConfigurations, UsuariosRepository


## Spring Security

-Adicionar a dependência do Spring Security

      <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-security</artifactId>
      </dependency>

- Criar um novo pacote para as configurações gerais do projeto: br.com.fuctura.escola.config

- Dentro do pacote br.com.fuctura.escola.config, criar um novo pacote relacionado à segurança: br.com.fuctura.escola.config.security

- Dentro do pacote br.com.fuctura.escola.config.security, criar uma nova classe: SecurityConfigurations, herdando da classe WebSecurityConfigurerAdapter, e adicionando 2 novas anotações: @EneableWebSecurity e @Configuration.

      @EneableWebSecurity
      @Configuration
      public class SecurityConfigurations extends WebSecurityConfigurerAdapter{}
    
    
- Após isto, acessar o link localhost:8080/alunos no Postman. Qual foi o códido de retorno (200 - OK, ou 401 - Unauthorized)??

- Adicionar os métodos de configuração abaixo:

        // configuracoes de autenticacao
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        }

        // configuracoes de autorizacao
        @Override
        protected void configure(HttpSecurity http) throws Exception {

        }

        // configuracoes de recursos estaticos
        @Override
        public void configure(WebSecurity web) throws Exception {

        }
	
- No método configure() que configura a autorização, adiciona os endpoins de listar alunos e detalhar que ficarão públicos. Os demais endpoints só seram liberados depois de autenticar na api.


        http.authorizeRequests()
        .antMatchers(HttpMethod.GET, "/alunos").permitAll() // libera a consulta de alunos
        .antMatchers(HttpMethod.GET, "/alunos/*").permitAll() // libera o detalhamento de um aluno
        .anyRequest().authenticated()
        .and().formLogin();



- Após isto, acesse as urls de listagem de alunos localhost:8080/alunos e a url de detalhamento de aluno localhost:8080/alunos/{id}. Deu tudo certo?

- Depois de testar os métodos GET, tente acessar os outros endpoints (POST, PUT e DELETE) e verifique o resultado.

# Autenticação dos Usuários da API

- No pacote model, precisaremos de mais duas classes: Usuario e Perfil



- A classe Usuario deve implementar a interface UserDetails 

      @Entity
      public class Usuario implements UserDetails {

        private static final long serialVersionUID = 1L;

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String nome;
        private String email;
        private String senha;
        @ManyToMany(fetch = FetchType.EAGER)
        private List<Perfil> perfis = new ArrayList<>(); 

        @Override
        public int hashCode() {
          final int prime = 31;
          int result = 1;
          result = prime * result + ((id == null) ? 0 : id.hashCode());
          return result;
        }

        @Override
        public boolean equals(Object obj) {
          if (this == obj)
            return true;
          if (obj == null)
            return false;
          if (getClass() != obj.getClass())
            return false;
          Usuario other = (Usuario) obj;
          if (id == null) {
            if (other.id != null)
              return false;
          } else if (!id.equals(other.id))
            return false;
          return true;
        }

        public Long getId() {
          return id;
        }

        public void setId(Long id) {
          this.id = id;
        }

        public String getNome() {
          return nome;
        }

        public void setNome(String nome) {
          this.nome = nome;
        }

        public String getEmail() {
          return email;
        }

        public void setEmail(String email) {
          this.email = email;
        }

        public String getSenha() {
          return senha;
        }

        public void setSenha(String senha) {
          this.senha = senha;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
          return this.perfis;
        }

        @Override
        public String getPassword() {
          return this.senha;
        }

        @Override
        public String getUsername() {
          return this.email;
        }

        @Override
        public boolean isAccountNonExpired() {
          return true;
        }

        @Override
        public boolean isAccountNonLocked() {
          return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
          return true;
        }

        @Override
        public boolean isEnabled() {
          return true;
        }

      }

      @Entity
      public class Perfil implements GrantedAuthority {

        private static final long serialVersionUID = 1L;

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String nome;

        public Long getId() {
          return id;
        }

        public void setId(Long id) {
          this.id = id;
        }

        public String getNome() {
          return nome;
        }

        public void setNome(String nome) {
          this.nome = nome;
        }

        @Override
        public String getAuthority() {
          return this.nome;
        }

      }



- No data.sql, adicionar os usuários a serem inseridos no banco:

      /*
       * Cadastro de Usuários 
       */ 
      INSERT INTO USUARIO(nome, email, senha) VALUES('Aluno', 'aluno@escola.com', '123456');
      INSERT INTO USUARIO(nome, email, senha) VALUES('Professor', 'prof@escola.com', '123456');

- A lógica da autenticação será implementada no método configure(AuthenticationManagerBuilder auth) da classe SecurityConfigurations:

      // configuracoes de autenticacao
      @Override
      protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		    auth.userDetailsService(autenticacaoService);
      }

- No pacote br.com.fuctura.escola.config.security, precisaremos criar a classe AutenticacaoService

      @Service
      public class AutenticacaoService implements UserDetailsService {

          @Override
          public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
              return null;
          }

      }

- Criar o repositório de Usuário

      public interface UsuariosRepository extends JpaRepository<Usuario, Long> {

          Optional<Usuario> findByEmail (String email);

      }
