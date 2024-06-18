package model.veiculo.automovel.modelo;

import model.veiculo.marca.Marca;

public enum ModeloAutomovel {
    // FIAT
    PANDA(Marca.FIAT, "Automovel", 1L),
    TIPO(Marca.FIAT, "Automovel", 2L),
    PUNTO(Marca.FIAT, "Automovel", 3L),
    DOBLO(Marca.FIAT, "Automovel", 4L),
    PALIO(Marca.FIAT, "Automovel", 5L),

    // VOLKSWAGEN
    GOLF(Marca.VOLKSWAGEM, "Automovel", 6L),
    PASSAT(Marca.VOLKSWAGEM, "Automovel", 7L),
    POLO(Marca.VOLKSWAGEM, "Automovel", 8L),
    TIGUAN(Marca.VOLKSWAGEM, "Automovel", 9L),
    JETTA(Marca.VOLKSWAGEM, "Automovel", 10L),

    // HONDA
    ACCORD(Marca.HONDA, "Automovel", 11L),
    CIVIC(Marca.HONDA, "Automovel", 12L),
    CRV(Marca.HONDA, "Automovel", 13L),
    PILOT(Marca.HONDA, "Automovel", 14L),
    FIT(Marca.HONDA, "Automovel", 15L),

    // HYUNDAI
    SONATA(Marca.HYUNDAI, "Automovel", 16L),
    ELANTRA(Marca.HYUNDAI, "Automovel", 17L),
    TUCSON(Marca.HYUNDAI, "Automovel", 18L),
    SANTAFE(Marca.HYUNDAI, "Automovel", 19L),
    KONA(Marca.HYUNDAI, "Automovel", 20L),

    // MERCEDES
    CCLASS(Marca.MERCEDES, "Automovel", 21L),
    ECLASS(Marca.MERCEDES, "Automovel", 22L),
    SCLASS(Marca.MERCEDES, "Automovel", 23L),
    GLE(Marca.MERCEDES, "Automovel", 24L);
	
	private Marca marca;
	private String tipo;
	private Long id;
	
	ModeloAutomovel(Marca marca, String tipo, Long id){
		this.marca = marca;
		this.tipo = tipo;
		this.id = id;
	}
	
	public Marca getMarca() {
		return marca;
	}

	public String getTipo() {
		return tipo;
	}

	public Long getId() {
		return id;
	}
}


