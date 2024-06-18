package model.veiculo;

import java.time.LocalDate;

import model.cliente.Cliente;
import model.locacao.Locacao;
import model.veiculo.automovel.modelo.ModeloAutomovel;
import model.veiculo.categoria.Categoria;
import model.veiculo.estado.Estado;
import model.veiculo.marca.Marca;
import model.veiculo.motocicleta.modelo.ModeloMotocicleta;
import model.veiculo.van.modelo.ModeloVan;

public interface VeiculoI {
	 public void locar(Long id, int dias, LocalDate data, Cliente cliente);
	 public void vender();
	 public void devolver();
	 public Estado getEstado();
	 public Marca getMarca();
	 public Categoria getCategoria();
	 public Locacao getLocacao();
	 public String getPlaca();
	 public int getAno();
	 public double getValorParaVenda();
	 public double getValorDiariaLocacao();
	 public Long getId();
	 public void setId(Long id);
	 public ModeloAutomovel getModeloAutomovel();
	 public ModeloVan getModeloVan();
	 public ModeloMotocicleta getModeloMotocicleta();
	}
