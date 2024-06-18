package model.cliente;

import java.util.Objects;

public class Cliente {
	private Long id;
	private String cpf;
	private String rg;
	private String nome;
	private String sobrenome;
	private String endereco;
	
	public Cliente(String cpf, String rg, String nome, String sobrenome, String endereco, Long id) {
		this.cpf = cpf;
		this.rg = rg;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.endereco = endereco;
		this.id = id;
	}
	
	public Cliente() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public String getCpf() {
		return cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNome() {
		return nome;
	}

	public String getEndereco() {
		return endereco;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf, endereco, id, nome, rg, sobrenome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(cpf, other.cpf) && Objects.equals(endereco, other.endereco)
				&& Objects.equals(id, other.id) && Objects.equals(nome, other.nome) && Objects.equals(rg, other.rg)
				&& Objects.equals(sobrenome, other.sobrenome);
	}
}
