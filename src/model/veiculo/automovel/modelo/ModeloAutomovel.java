package model.veiculo.automovel.modelo;

import model.veiculo.marca.Marca;

public enum ModeloAutomovel {
    // FIAT
    PANDA(Marca.FIAT, "Automovel"),
    TIPO(Marca.FIAT, "Automovel"),
    PUNTO(Marca.FIAT, "Automovel"),
    DOBLO(Marca.FIAT, "Automovel"),
    PALIO(Marca.FIAT, "Automovel"),

    // VOLKSWAGEN
    GOLF(Marca.VOLKSWAGEM, "Automovel"),
    PASSAT(Marca.VOLKSWAGEM, "Automovel"),
    POLO(Marca.VOLKSWAGEM, "Automovel"),
    TIGUAN(Marca.VOLKSWAGEM, "Automovel"),
    JETTA(Marca.VOLKSWAGEM, "Automovel"),

    // HONDA
    ACCORD(Marca.HONDA, "Automovel"),
    CIVIC(Marca.HONDA, "Automovel"),
    CRV(Marca.HONDA, "Automovel"),
    PILOT(Marca.HONDA, "Automovel"),
    FIT(Marca.HONDA, "Automovel"),

    // HYUNDAI
    SONATA(Marca.HYUNDAI, "Automovel"),
    ELANTRA(Marca.HYUNDAI, "Automovel"),
    TUCSON(Marca.HYUNDAI, "Automovel"),
    SANTAFE(Marca.HYUNDAI, "Automovel"),
    KONA(Marca.HYUNDAI, "Automovel"),

    // MERCEDES
    CCLASS(Marca.MERCEDES, "Automovel"),
    ECLASS(Marca.MERCEDES, "Automovel"),
    SCLASS(Marca.MERCEDES, "Automovel"),
    GLE(Marca.MERCEDES, "Automovel");
	
	private Marca marca;
	private String tipo;
	
	ModeloAutomovel(Marca marca, String tipo){
		this.marca = marca;
		this.tipo = tipo;
	}
	
	public Marca getMarca() {
		return marca;
	}

	public String getTipo() {
		return tipo;
	}
}


