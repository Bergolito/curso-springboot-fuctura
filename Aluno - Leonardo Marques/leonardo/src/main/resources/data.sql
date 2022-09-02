
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
  ('44422233344', 'Paulo', 'aluno333@escola.com', '81 1234-5555', 'MONITOR');
  
INSERT INTO ALUNO (cpf, nome, email, fone, tipo) VALUES  
  ('55599944411', 'Roberto', 'aluno333@escola.com', '81 1234-5555', 'MONITOR');
  
  
/**
 * Professor
 */
INSERT INTO PROFESSOR (cpf, nome, email, valor_hora, certificados, tipo) VALUES  
  ('11156356399', 'Professor Pardal', 'professor111@escola.com', 50.00, 'DP-900, LGPDF, PCEP-02', 'TITULAR');
  
INSERT INTO PROFESSOR (cpf, nome, email, valor_hora, certificados, tipo) VALUES  
  ('22256356399', 'Doutor Brown', 'professor222@escola.com', 60.00, 'ABC, DEF, GHI', 'TITULAR');
  
INSERT INTO PROFESSOR (cpf, nome, email, valor_hora, certificados, tipo) VALUES  
  ('33356356399', 'Professor Rodrigo', 'professor333@escola.com', 70.00, 'ABC, DEF, GHI', 'TITULAR');
  
/**
 * Curso
 */
INSERT INTO CURSO (nome, requisitos, carga_horaria, preco) VALUES  
  ('Spring Boot', 'Java, Orientação a Objetos, REST, JPA', 24, 1000.0); 
INSERT INTO CURSO (nome, requisitos, carga_horaria, preco) VALUES  
  ('Java Básico', 'Java, Orientação a Objetos, REST, JPA', 40, 1000.0); 
INSERT INTO CURSO (nome, requisitos, carga_horaria, preco) VALUES  
  ('Banco de Dados', 'JDBC, Java, JPA', 30, 1000.0);
 

/**
 * Turma
 */
INSERT INTO TURMA(nome, carga_horaria, professor_id, curso_id) VALUES 
	('TURMA-Spring Boot-2022-1Sem', 40, 1, 1);
INSERT INTO TURMA(nome, carga_horaria, professor_id, curso_id) VALUES 
	('TURMA-Spring Boot-2022-2Sem', 40, 2, 1);

/**
 * Matricula
 */
INSERT INTO MATRICULA(turma_id, aluno_id, data_matricula) VALUES 
	(1, 1, '2022-06-01');

  
  
