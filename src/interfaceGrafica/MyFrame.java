package interfaceGrafica;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class MyFrame extends JFrame {
	// Local onde atualizaremos a hora
	private JLabel lblHora;
	// Formatador da hora
	private static final DateFormat FORMATO = new SimpleDateFormat("HH:mm:ss");

	public MyFrame() {
		// Constru�mos nosso frame
		super("Exemplo");
		setLayout(new FlowLayout());
		getContentPane().add(getLblHora());
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(200, 75);
		setVisible(true);
		// Iniciamos um timer para atualizar o rel�gio
		Timer t = new Timer(1000, new ClockAction());
		t.setInitialDelay(0);
		t.setRepeats(true);
		t.start();
	}

	private JLabel getLblHora() {
		if (lblHora == null) {
			lblHora = new JLabel();
		}
		return lblHora;
	}

	/**
	 * M�todo para atualizar a hora no formul�rio. N�o � thread-safe, portanto,
	 * se o utilizado fora da thread da AWT, deve-se utilizar um dos comandos da
	 * EventQueue (invokeLater ou invokeAndWait).
	 */
	public void setHora(Date date) {
		getLblHora().setText(FORMATO.format(date));
	}

	/**
	 * Action que cont�m o c�digo que atuar� na nossa thread. Basicamente, ele
	 * chama o m�todo setHora de segundo em segundo, passando a data atual. Roda
	 * diretamente na thread da AWT.
	 */
	Date data = null;

	private class ClockAction implements ActionListener {

		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent e) {
			// S� podemos chamar setHora diretamente dessa
			// forma, pois esse Runnable � uma InnerClass n�o
			// est�tica.

			if (data == null) {
				data = new Date(0, 0, 0, 0, 0, 0);
				setHora(data);
			} else {
				data.setTime(data.getTime() + 1000);
				setHora(data);
			}
		}
	}

	public static void main(String args[]) {
		new MyFrame();
	}
}