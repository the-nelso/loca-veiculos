package model.veiculo.motocicleta.modelo;

import model.veiculo.marca.Marca;

public enum ModeloMotocicleta {
    // HONDA
    CBR(Marca.HONDA, "Motocicleta"),
    CRF(Marca.HONDA, "Motocicleta"),
    CB(Marca.HONDA, "Motocicleta"),

    // YAMAHA
    YZF_R1(Marca.YAMAHA, "Motocicleta"),
    YZF_R6(Marca.YAMAHA, "Motocicleta"),
    MT_07(Marca.YAMAHA, "Motocicleta"),
    MT_09(Marca.YAMAHA, "Motocicleta"),
    XT_1200ZE(Marca.YAMAHA, "Motocicleta");
    
    private Marca marca;
    private String tipo;

    ModeloMotocicleta(Marca marca, String tipo) {
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

