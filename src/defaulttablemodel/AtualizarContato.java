package defaulttablemodel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import banco.DAOCorredor;
import circuitoPiauiense.Corredor;

/**
 *
 * @author Rosicl√©ia Frasson
 */
@SuppressWarnings("serial")
public class AtualizarContato extends JFrame {

    private DefaultTableModel modelo = new DefaultTableModel();
    private JPanel painelFundo;
    private JButton btSalvar;
    private JButton btLimpar;
    private JLabel lbNome;
    private JLabel lbTelefone;
    private JLabel lbEmail;
    private JLabel lbId;
    private JTextField txNome;
    private JTextField txNumero;
    private JTextField txSexo;
    private JTextField txIdade;
    Corredor contato;
    private int linhaSelecionada;
    private JTextField txTempoH;
    private JTextField txTempoM;
    private JTextField txTempoS;

    public AtualizarContato(DefaultTableModel md, int id, int linha) {
        super("Contatos");
        criaJanela();
        modelo = md;
        DAOCorredor dao = new DAOCorredor();
        contato = dao.pesquisar(id);
        txNumero.setText(Integer.toString(contato.getNumero()));
        txNome.setText(contato.getNome());
        txSexo.setText(contato.getSexo());
        txIdade.setText(Integer.toString(contato.getIdade()));
        
        JLabel lblTempoH = new JLabel("         Tempo H.:   ");
        lblTempoH.setBounds(0, 133, 197, 29);
        painelFundo.add(lblTempoH);
        
        txTempoH = new JTextField();
        txTempoH.setText(Integer.toString(contato.getTempoH()));
        txTempoH.setBounds(199, 133, 197, 29);
        painelFundo.add(txTempoH);
        
        JLabel lblTempoM = new JLabel("         Tempo M.:   ");
        lblTempoM.setBounds(0, 166, 197, 29);
        painelFundo.add(lblTempoM);
        
        txTempoM = new JTextField();
        txTempoM.setText(Integer.toString(contato.getTempoM()));
        txTempoM.setBounds(199, 166, 197, 29);
        painelFundo.add(txTempoM);
        
        JLabel lblTempoS = new JLabel("         Tempo S.:   ");
        lblTempoS.setBounds(0, 199, 197, 29);
        painelFundo.add(lblTempoS);
        
        txTempoS = new JTextField();
        txTempoS.setText(Integer.toString(contato.getTempoS()));
        txTempoS.setBounds(199, 199, 197, 29);
        painelFundo.add(txTempoS);
        linhaSelecionada = linha;  
    }

    public void criaJanela() {
        getContentPane().setLayout(null);
        btSalvar = new JButton("Salvar");
        btSalvar.setBounds(199, 234, 197, 29);
        btLimpar = new JButton("Limpar");
        btLimpar.setBounds(0, 234, 197, 29);
        lbNome = new JLabel("         Nome.:   ");
        lbNome.setBounds(0, 33, 197, 29);
        lbTelefone = new JLabel("         Sexo.:   ");
        lbTelefone.setBounds(0, 66, 197, 29);
        lbEmail = new JLabel("         Idade.:   ");
        lbEmail.setBounds(0, 99, 197, 29);
        lbId = new JLabel("         N\u00BA:   ");
        lbId.setBounds(0, 0, 197, 29);
        txNome = new JTextField();
        txNome.setBounds(199, 33, 197, 29);
        txSexo = new JTextField();
        txSexo.setBounds(199, 66, 197, 29);
        txIdade = new JTextField();
        txIdade.setBounds(199, 99, 197, 29);
        
        //N„o permitir alteracoes 
        txNumero = new JTextField();
        txNumero.setBounds(199, 0, 197, 29);
        txNumero.setEditable(false);

        painelFundo = new JPanel();
        painelFundo.setBounds(0, 0, 409, 272);
        painelFundo.setLayout(null);
        painelFundo.add(lbId);
        painelFundo.add(txNumero);
        painelFundo.add(lbNome);
        painelFundo.add(txNome);
        painelFundo.add(lbTelefone);
        painelFundo.add(txSexo);
        painelFundo.add(lbEmail);
        painelFundo.add(txIdade);
        painelFundo.add(btLimpar);
        painelFundo.add(btSalvar);

        getContentPane().add(painelFundo);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(418, 309);
        setVisible(true);

        btSalvar.addActionListener(new AtualizarContato.BtSalvarListener());
        btLimpar.addActionListener(new AtualizarContato.BtLimparListener());
    }

    private class BtSalvarListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            Corredor c = new Corredor();
            c.setNumero(Integer.parseInt(txNumero.getText()));
            c.setNome(txNome.getText());
            c.setSexo(txSexo.getText());
            c.setIdade(Integer.parseInt(txIdade.getText()));
            
            c.setTempoH(Integer.parseInt(txTempoH.getText()));
            c.setTempoM(Integer.parseInt(txTempoM.getText()));
            c.setTempoS(Integer.parseInt(txTempoS.getText()));
            
            DAOCorredor dao = new DAOCorredor();
            dao.atualizar(c);
            modelo.removeRow(linhaSelecionada);
            modelo.addRow(new Object[]{c.getNumero(), c.getNome(), c.getSexo(), c.getIdade(),c.getTempoH(),c.getTempoM(),c.getTempoS()});
            setVisible(false);
        }
    }

    private class BtLimparListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
        	txNome.setText("");
            txSexo.setText("");
            txIdade.setText("");
            txTempoH.setText("");
            txTempoM.setText("");
            txTempoS.setText("");
            }
    }
}
