package model.table;

import model.locacao.Locacao;

import javax.swing.table.AbstractTableModel;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LocacaoTableModel extends AbstractTableModel {
    private List<Locacao> locacoes;
    private String[] colunas = {"Nome do Cliente", "Placa", "Marca", "Modelo", "Ano",
            "Data Locação", "Preço Diária", "Quantidade de dias locado", "Valor Locação"};

    public void setLocacoes(List<Locacao> locacoes) {
        this.locacoes = locacoes;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return locacoes != null ? locacoes.size() : 0;
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Locacao locacao = locacoes.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return locacao.getCliente().getNome();
            case 1:
                return locacao.getVeiculo().getPlaca();
            case 2:
                return locacao.getVeiculo().getMarca();
            case 3:
                //return locacao.getVeiculo().getModelo();
            case 4:
                return locacao.getVeiculo().getAno();
            case 5:
                return locacao.getData();
            case 6:
                return formatCurrency(locacao.getVeiculo().getValorDiariaLocacao());
            case 7:
                return locacao.getDias();
            case 8:
                return formatCurrency(locacao.getValor());
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    private String formatCurrency(double value) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return "R$ " + df.format(value);
    }
}

