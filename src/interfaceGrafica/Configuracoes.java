package interfaceGrafica;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import circuitoPiauiense.Colocação;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class Configuracoes extends JPanel {
	private JTextField textField;
	private JTextField textField_1;

	public static void main(String[] args, Colocação mudar) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame();
				frame.setContentPane(new Configuracoes(mudar));
				frame.pack();
				frame.setVisible(true);
				frame.setBounds(100, 100, 200, 270);
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @param mudar
	 */
	public Configuracoes(Colocação mudar) {
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setBounds(100, 100, 200, 270);

		JLabel lblConfiguracaoDaCorrida = new JLabel("Configuracao da Corrida");
		lblConfiguracaoDaCorrida.setBounds(32, 11, 143, 14);
		add(lblConfiguracaoDaCorrida);

		JLabel lblQuilometragemMenor = new JLabel("Quilometragem menor");
		lblQuilometragemMenor.setBounds(35, 56, 180, 14);
		add(lblQuilometragemMenor);

		JLabel lblQuilometragemMaior = new JLabel("Quilometragem maior");
		lblQuilometragemMaior.setBounds(35, 112, 158, 14);
		add(lblQuilometragemMaior);

		textField = new JTextField();
		textField.setBounds(45, 81, 86, 20);
		add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBounds(45, 137, 86, 20);
		add(textField_1);
		textField_1.setColumns(10);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) {
				mudar.setKmMenor(textField.getText());
				mudar.setKmMaior(textField_1.getText());
			}
		});
		btnSalvar.setBounds(42, 198, 89, 23);
		add(btnSalvar);

		JLabel label = new JLabel("__________________________________________");
		label.setBounds(-11, 23, 243, 14);
		add(label);
	}
}
