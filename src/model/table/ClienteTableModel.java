package model.table;

import javax.swing.table.AbstractTableModel;
import model.cliente.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClienteTableModel extends AbstractTableModel {

    private List<Cliente> clientes;
    private String[] colunas = {"CPF", "RG", "Nome", "Sobrenome", "Endereço"};

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
        fireTableDataChanged();
    }

    public void addRow(Cliente cliente) {
        if (clientes == null) {
            clientes = new ArrayList<>();
        }

        clientes.add(cliente);
        fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
    }

    public void setValueAt(Cliente cliente, int rowIndex) {
        clientes.set(rowIndex, cliente);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public void removeRow(int rowIndex) {
        if (clientes != null && rowIndex >= 0 && rowIndex < clientes.size()) {
            clientes.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }

    public Cliente getClienteAt(int rowIndex) {
        return clientes.get(rowIndex);
    }

    @Override
    public int getRowCount() {
        return clientes != null ? clientes.size() : 0;
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
        Cliente cliente = clientes.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return cliente.getCpf();
            case 1:
                return cliente.getRg();
            case 2:
                return cliente.getNome();
            case 3:
                return cliente.getSobrenome();
            case 4:
                return cliente.getEndereco();
            default:
                return null;
        }
    }
}
