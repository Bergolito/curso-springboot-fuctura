package br.com.fuctura.leonardo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Professor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, name = "CPF")
	private String cpf;

	@Column(nullable = false, name = "NOME")
	private String nome;

	@Column(nullable = true, name = "EMAIL")
	private String email;

	@Column(nullable = true, name = "VALOR_HORA")
	private Float valorHora;

	@Column(nullable = true, name = "CERTIFICADOS")
	private String certificados;

	// TITULAR ou SUBSTITUTO
	@Column(nullable = true, name = "TIPO")
	private String tipo = TipoProfessor.TITULAR.toString();

	public Professor() {

	}

	public Professor(Long id, String cpf, String nome, String email, Float valorHora, String certificados,
			String tipo) {
		super();
		this.id = id;
		this.cpf = cpf;
		this.nome = nome;
		this.email = email;
		this.valorHora = valorHora;
		this.certificados = certificados;
		this.tipo = tipo;
	}

	public Professor(String cpf, String nome, String email, Float valorHora, String certificados, String tipo) {
		super();
		this.cpf = cpf;
		this.nome = nome;
		this.email = email;
		this.valorHora = valorHora;
		this.certificados = certificados;
		this.tipo = tipo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
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

	public Float getValorHora() {
		return valorHora;
	}

	public void setValorHora(Float valorHora) {
		this.valorHora = valorHora;
	}

	public String getCertificados() {
		return certificados;
	}

	public void setCertificados(String certificados) {
		this.certificados = certificados;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((certificados == null) ? 0 : certificados.hashCode());
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((valorHora == null) ? 0 : valorHora.hashCode());
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
		Professor other = (Professor) obj;
		if (certificados == null) {
			if (other.certificados != null)
				return false;
		} else if (!certificados.equals(other.certificados))
			return false;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		if (valorHora == null) {
			if (other.valorHora != null)
				return false;
		} else if (!valorHora.equals(other.valorHora))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Professor [id=" + id + ", cpf=" + cpf + ", nome=" + nome + ", email=" + email + ", valorHora="
				+ valorHora + ", certificados=" + certificados + ", tipo=" + tipo + "]";
	}

}
