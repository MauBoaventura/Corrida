package interfaceGrafica;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import circuitoPiauiense.Colocação;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class CadastroTela extends JPanel {
	private JTextField txtNome;
	private JTextField textIdade;
	private JTextField textNumero;

	/**
	 * Create the panel.
	 * 
	 * @param mudar
	 */
	public CadastroTela(Colocação mudar) {
		setLayout(null);

		txtNome = new JTextField();
		txtNome.setBounds(138, 36, 230, 20);
		add(txtNome);
		txtNome.setColumns(10);


		JLabel lblNomeDoCorredor = new JLabel("Nome");
		lblNomeDoCorredor.setBounds(40, 39, 88, 14);
		add(lblNomeDoCorredor);

		JLabel lblCategoria = new JLabel("Categoria");
		lblCategoria.setBounds(40, 67, 76, 14);
		add(lblCategoria);

		JRadioButton rdbtnKm = new JRadioButton("6 KM");
		rdbtnKm.setBounds(138, 67, 109, 14);
		add(rdbtnKm);

		JRadioButton rdbtnKm_1 = new JRadioButton("12 KM");
		rdbtnKm_1.setBounds(249, 65, 109, 18);
		add(rdbtnKm_1);

		JLabel lblSexo = new JLabel("Sexo");
		lblSexo.setBounds(40, 92, 46, 14);
		add(lblSexo);

		JRadioButton rdbtnMasculino = new JRadioButton("Masculino");
		rdbtnMasculino.setBounds(138, 88, 109, 23);
		add(rdbtnMasculino);

		JRadioButton rdbtnFemenino = new JRadioButton("Femenino");
		rdbtnFemenino.setBounds(249, 88, 109, 23);
		add(rdbtnFemenino);

		JLabel lblIdade = new JLabel("Idade");
		lblIdade.setBounds(40, 117, 46, 14);
		add(lblIdade);

		textIdade = new JTextField();
		textIdade.setBounds(138, 114, 54, 20);
		add(textIdade);
		textIdade.setColumns(10);

		JLabel lblNumero = new JLabel("Numero");
		lblNumero.setBounds(40, 148, 46, 14);
		add(lblNumero);

		textNumero = new JTextField();
		textNumero.setBounds(138, 145, 56, 20);
		add(textNumero);
		textNumero.setColumns(10);

		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String km;
				String sexo = null;

				if (rdbtnKm.isSelected()) {
					km = "6";
				} else
					km = "12";

				if (rdbtnMasculino.isSelected()) {
					sexo = "M";
				} else
					sexo = "F";

				int a = mudar.adiciona(txtNome.getText(), textNumero.getText(), km, textIdade.getText(), sexo);
				if (a == 1) {
					JOptionPane.showMessageDialog(null, "Corredor já cadastrado", "Sucesso", JOptionPane.OK_OPTION);
				} else {
					JOptionPane.showMessageDialog(null, "Cadastro bem sucessido", "Sucesso",
							JOptionPane.INFORMATION_MESSAGE);
				}
				
				txtNome.setText("");
				textIdade.setText("");;
				textNumero.setText("");
				rdbtnFemenino.setSelected(false);
				rdbtnMasculino.setSelected(false);
				rdbtnKm.setSelected(false);
				rdbtnKm_1.setSelected(false);
			}
		});
		btnCadastrar.setBounds(165, 176, 99, 23);
		add(btnCadastrar);

	}

	public static void main(String[] args, Colocação mudar) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame frame = new JFrame();
				frame.setContentPane(new CadastroTela(mudar));
				frame.pack();
				frame.setVisible(true);
				frame.setBounds(100, 100, 432, 255);

			}
		});
	}
}
