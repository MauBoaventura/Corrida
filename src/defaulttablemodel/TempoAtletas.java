package defaulttablemodel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import banco.DAOCorredor;
import circuitoPiauiense.Corredor;

/**
 * @author Rosicléia Frasson
 */
@SuppressWarnings("serial")
public class TempoAtletas extends JFrame {

	private JPanel painelFundo;
	private JPanel painelBotoes;
	private JTable tabela;
	private JScrollPane barraRolagem;
	private JButton btEditar;
	private DefaultTableModel modelo = new DefaultTableModel();

	public TempoAtletas() {
		super("Contatos");
		criaJTable();
		criaJanela();
	}

	public void criaJanela() {
		btEditar = new JButton("Editar");
		painelBotoes = new JPanel();
		barraRolagem = new JScrollPane(tabela);
		painelFundo = new JPanel();
		painelFundo.setLayout(new BorderLayout());
		painelFundo.add(BorderLayout.CENTER, barraRolagem);
		//painelBotoes.add(btInserir);
		painelBotoes.add(btEditar);
		//painelBotoes.add(btExcluir);
		painelFundo.add(BorderLayout.SOUTH, painelBotoes);
		getContentPane().add(painelFundo);
		// setSize(500, 320);
		setExtendedState(MAXIMIZED_BOTH);
		setVisible(true);
		btEditar.addActionListener(new BtEditarListener());
	}

	private void criaJTable() {
		tabela = new JTable(modelo);

		modelo.addColumn("Numero");
		modelo.addColumn("Nome");
		modelo.addColumn("Sexo");
		modelo.addColumn("Idade");
		modelo.addColumn("Tempo H");
		modelo.addColumn("Tempo M");
		modelo.addColumn("Tempo S");

		tabela.getColumnModel().getColumn(0).setPreferredWidth(10);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(80);
		tabela.getColumnModel().getColumn(2).setPreferredWidth(120);
		tabela.getColumnModel().getColumn(3).setPreferredWidth(120);
		tabela.getColumnModel().getColumn(4).setPreferredWidth(120);
		tabela.getColumnModel().getColumn(4).setPreferredWidth(120);
		tabela.getColumnModel().getColumn(4).setPreferredWidth(120);
		
		pesquisar(modelo);
	}

	public static void pesquisar(DefaultTableModel modelo) {
		modelo.setNumRows(0);
		DAOCorredor dao = new DAOCorredor();

		for (Corredor c : dao.recupera()) {
			modelo.addRow(new Object[] { c.getNumero(), c.getNome(), c.getSexo(),c.getIdade(),c.getTempoH(),c.getTempoM(),c.getTempoS() });
		}
	}

	

	private class BtEditarListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			int linhaSelecionada = -1;
			linhaSelecionada = tabela.getSelectedRow();
			if (linhaSelecionada >= 0) {
				int numero = (int) tabela.getValueAt(linhaSelecionada, 0);
				AtualizarContato ic = new AtualizarContato(modelo, numero, linhaSelecionada);
				ic.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null, "É necesário selecionar uma linha.");
			}
		}
	}

	@SuppressWarnings("unused")
	private class BtExcluirListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			int linhaSelecionada = -1;
			linhaSelecionada = tabela.getSelectedRow();
			if (linhaSelecionada >= 0) {
				int idContato = (int) tabela.getValueAt(linhaSelecionada, 0);
				ContatoDao dao = new ContatoDao();
				dao.remover(idContato);
				modelo.removeRow(linhaSelecionada);
			} else {
				JOptionPane.showMessageDialog(null, "É necesário selecionar uma linha.");
			}
		}
	}

	public static void main(String[] args) {
		TempoAtletas lc = new TempoAtletas();
		lc.setVisible(true);
	}
}
