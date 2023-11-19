package model.cliente;

public class Cliente {
	private String cpf;
	private String rg;
	private String nome;
	private String sobrenome;
	private String endereco;
	
	public Cliente(String cpf, String rg, String nome, String sobrenome, String endereco) {
		this.cpf = cpf;
		this.rg = rg;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.endereco = endereco;
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
	
	
}
