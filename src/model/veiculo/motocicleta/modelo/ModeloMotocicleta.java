package model.veiculo.motocicleta.modelo;

import model.veiculo.marca.Marca;

public enum ModeloMotocicleta {
    // HONDA
    CBR(Marca.HONDA, "Motocicleta", 1L),
    CRF(Marca.HONDA, "Motocicleta", 2L),
    CB(Marca.HONDA, "Motocicleta", 3L),

    // YAMAHA
    YZF_R1(Marca.YAMAHA, "Motocicleta", 4L),
    YZF_R6(Marca.YAMAHA, "Motocicleta", 5L),
    MT_07(Marca.YAMAHA, "Motocicleta", 6L),
    MT_09(Marca.YAMAHA, "Motocicleta", 7L),
    XT_1200ZE(Marca.YAMAHA, "Motocicleta", 8L);
    
    private Marca marca;
    private String tipo;
    private Long id;

    ModeloMotocicleta(Marca marca, String tipo, Long id) {
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

