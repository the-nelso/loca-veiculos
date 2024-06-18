package model.veiculo.van.modelo;

import model.veiculo.marca.Marca;

public enum ModeloVan {
    // FIAT
    DUCATO(Marca.FIAT, "Van", 1L),
    FIORINO(Marca.FIAT, "Van", 2L),

    // VOLKSWAGEN
    TRANSPORTER(Marca.VOLKSWAGEM, "Van", 3L),
    CRAFTER(Marca.VOLKSWAGEM, "Van", 4L),

    // VOLVO
    V60(Marca.VOLVO, "Van", 5L),
    XC60(Marca.VOLVO, "Van", 6L),

    // MERCEDES
    SPRINTER(Marca.MERCEDES, "Van", 7L),
    METRIS(Marca.MERCEDES, "Van", 8L);
	
	private Marca marca;
	private String tipo;
	private Long id;
	
    ModeloVan(Marca marca, String tipo, Long id) {
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

	public void setId(Long id) {
		this.id = id;
	}
}


