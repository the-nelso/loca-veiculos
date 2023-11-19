package model.table;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.veiculo.Veiculo;

public class VeiculoTableModel extends AbstractTableModel {
    private List<Veiculo> veiculos;
    private String[] colunas = {"Placa", "Marca", "Modelo", "Ano", "Preço da Diária"};

    public void setVeiculos(List<Veiculo> veiculos) {
        this.veiculos = veiculos;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return veiculos != null ? veiculos.size() : 0;
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return colunas[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Veiculo veiculo = veiculos.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return veiculo.getPlaca();
            case 1:
                return veiculo.getMarca();
            case 2:
            	return veiculo.getModelo();
            case 3:
            	return veiculo.getAno();
            case 4:
            	return formatarValorDiaria(veiculo.getValorDiariaLocacao());
            default:
                return null;
        }
    }

    private String formatarValorDiaria(double valor) {
        return String.format("R$%.2f", valor);
    }
    
    public Veiculo getVeiculo(int rowIndex) {
        return veiculos.get(rowIndex);
    }
}