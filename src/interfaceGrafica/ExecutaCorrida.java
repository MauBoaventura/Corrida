package interfaceGrafica;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import circuitoPiauiense.Colocação;
import defaulttablemodel.TempoAtletas;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class ExecutaCorrida extends JFrame {
	Colocação eu = new Colocação();
	// Local onde atualizaremos a hora
	private JLabel lblHora;
	// Formatador da hora
	private static final DateFormat FORMATO = new SimpleDateFormat("HH:mm:ss");

	public void MyFrame() {
		// Construímos nosso frame
		getContentPane().setLayout(new FlowLayout());
		getContentPane().add(getLblHora());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 75);
		setVisible(true);
		// Iniciamos um timer para atualizar o relógio
		Timer t = new Timer(1000, new ClockAction());
		t.setInitialDelay(0);
		t.setRepeats(true);
		t.start();
	}

	private JLabel getLblHora() {
		if (lblHora == null) {
			lblHora = new JLabel();
			lblHora.setHorizontalAlignment(SwingConstants.CENTER);
			lblHora.setFont(new Font("Tahoma", Font.BOLD, 18));
		}
		return lblHora;
	}

	/**
	 * Método para atualizar a hora no formulário. Não é thread-safe, portanto,
	 * se o utilizado fora da thread da AWT, deve-se utilizar um dos comandos da
	 * EventQueue (invokeLater ou invokeAndWait).
	 */
	public void setHora(Date date) {
		// System.out.println(date.getHours());
		getLblHora().setText("Tempo: " + FORMATO.format(date));

	}

	/**
	 * Action que contém o código que atuará na nossa thread. Basicamente, ele
	 * chama o método setHora de segundo em segundo, passando a data atual. Roda
	 * diretamente na thread da AWT.
	 */
	Date data = null;

	private class ClockAction implements ActionListener {

		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent e) {
			// Só podemos chamar setHora diretamente dessa
			// forma, pois esse Runnable é uma InnerClass não
			// estática.

			if (data == null) {
				data = new Date(0, 0, 0, 0, 0, 0);
				setHora(data);
			} else {
				data.setTime(eu.tempoDecorrido());
				// data.setTime(data.getTime() + 10000*6);
				setHora(data);

			}
		}
	}

	private JPanel contentPane;
	private int flag;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExecutaCorrida frame = new ExecutaCorrida();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ExecutaCorrida() {
		flag = 0;

		// Encontra uma maneira desse timer ficar dentro do programa principal
		// (quando chamar o iniciar corrida)
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(350, 200, 600, 358);
		contentPane = new JPanel();
		contentPane.setForeground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Circuito Piauiense de Corrida na Natureza");

		JButton btnIniciaCorrida = new JButton("Inicia Corrida");
		btnIniciaCorrida.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				// AQUI EU INICIO A CORRIDA
				eu.iniciaCorrida();
				data = new Date(0, 0, 0, 0, 0, 0);
				setHora(data);

				if (flag == 0) {

					Timer t = new Timer(1000, new ClockAction());
					t.setInitialDelay(0);
					t.setRepeats(true);
					t.start();
				}
				flag = 1;

			}
		});

		btnIniciaCorrida.setBounds(10, 76, 159, 47);
		contentPane.add(btnIniciaCorrida);

		JButton btnCadastroCorredor = new JButton("Cadastro Corredor");
		btnCadastroCorredor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String[] args = null;
				CadastroTela.main(args, eu);
			}
		});
		btnCadastroCorredor.setBounds(207, 76, 159, 47);
		contentPane.add(btnCadastroCorredor);

		JButton btnRelatorios = new JButton("Relatorios");
		btnRelatorios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = 0;
				try {
					eu.relatorioEspecial();
					i++;
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Erro 6 e 12 KM", "Relatorios",
							JOptionPane.INFORMATION_MESSAGE);
				}
				try {
					eu.relatorios();
					i++;
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Erro relatorios OFICIAL", "Relatorios",
							JOptionPane.INFORMATION_MESSAGE);
				}
				try {
					eu.restantes();
					i++;
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Erro restantes KM", "Relatorios",
							JOptionPane.INFORMATION_MESSAGE);
				}
				try {
					eu.salva();
					i++;
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Erro salvar tempo", "Relatorios",
							JOptionPane.INFORMATION_MESSAGE);
				}
				if (i == 4) {
					JOptionPane.showMessageDialog(null, "Relatorios Gerados com Sucesso", "Relatorios",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnRelatorios.setBounds(391, 76, 159, 47);
		contentPane.add(btnRelatorios);

		// TEMPO RELOGIO ANTES DE INICIAR A CORRIDA
		getLblHora();
		lblHora.setBounds(53, 25, 497, 40);
		if (eu.getInicio() == 0) {
			lblHora.setText("Corrida não inicializada");
		} else {

			Timer t = new Timer(1000, new ClockAction());
			t.setInitialDelay(0);
			t.setRepeats(true);
			t.start();
		}

		contentPane.add(lblHora);

		textField = new JTextField();
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int numero = 0;
				try {
					numero = Integer.parseInt(textField.getText());
					eu.chegada(numero);
					// Depois de digitar o enter a caixa de texto fica vazia de
					// novo

					textField.setText("");
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Digite um valor válido no campo", "Erro de validação",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setForeground(Color.RED);
		textField.setFont(new Font("Tahoma", Font.PLAIN, 25));
		textField.setBounds(295, 200, 179, 47);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblNumeroDoCorredor = new JLabel("Numero do Corredor:");
		lblNumeroDoCorredor.setFont(new Font("Tahoma", Font.ITALIC, 18));
		lblNumeroDoCorredor.setBounds(118, 203, 180, 47);
		contentPane.add(lblNumeroDoCorredor);

		JLabel label = new JLabel("________________________________________________________________________________");
		label.setBounds(10, 134, 564, 30);
		contentPane.add(label);

		JLabel lblChegadaCorredores = new JLabel("Chegada Corredores");
		lblChegadaCorredores.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		lblChegadaCorredores.setHorizontalAlignment(SwingConstants.CENTER);
		lblChegadaCorredores.setBounds(10, 159, 564, 30);
		contentPane.add(lblChegadaCorredores);

		JButton btnTempos = new JButton("Tempos");
		btnTempos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TempoAtletas.main(null);
			}
		});
		btnTempos.setBounds(485, 286, 89, 23);
		contentPane.add(btnTempos);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				eu.salva();
			}
		});
		btnSalvar.setBounds(10, 286, 89, 23);
		contentPane.add(btnSalvar);
		
		JButton btnAjustes = new JButton("Ajustes");
		btnAjustes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Configuracoes.main(null,eu);
			}
		});
		btnAjustes.setBounds(385, 286, 89, 23);
		contentPane.add(btnAjustes);
	}
}
