package model.veiculo.van.modelo;

import model.veiculo.marca.Marca;

public enum ModeloVan {
    // FIAT
    DUCATO(Marca.FIAT, "Van"),
    FIORINO(Marca.FIAT, "Van"),

    // VOLKSWAGEN
    TRANSPORTER(Marca.VOLKSWAGEM, "Van"),
    CRAFTER(Marca.VOLKSWAGEM, "Van"),

    // VOLVO
    V60(Marca.VOLVO, "Van"),
    XC60(Marca.VOLVO, "Van"),

    // MERCEDES
    SPRINTER(Marca.MERCEDES, "Van"),
    METRIS(Marca.MERCEDES, "Van");
	
	private Marca marca;
	private String tipo;
	
    ModeloVan(Marca marca, String tipo) {
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


