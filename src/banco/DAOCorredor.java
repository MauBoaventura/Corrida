package banco;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import circuitoPiauiense.Corredor;

public class DAOCorredor {

	public void salvar(Corredor c) {
		Connection con = Conexao.getConexao();
		Statement st;
		try {
			st = con.createStatement();
			String sql = "INSERT INTO corredores (nome, km, numero, idade, sexo) VALUES (\'" + c.getNome() + "\',"
					+ c.getKm6() + "," + c.getNumero() + "," + c.getIdade() + ",\'" + c.getSexo() + "\'";
			sql += ")";
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
		} catch (SQLException e) {
			System.out.println("N�o foi poss�vel acessar a tabela corredores.");
			System.exit(1);
		}
	}

	public void setTempo(int numero, int tempoH, int tempoM, int tempoS, double tempoTotal, int voltas) {
		Connection con = Conexao.getConexao();
		Statement st;

		try {
			String f = "UPDATE corrida.corredores SET tempoH = " + tempoH + ", tempoM = " + tempoM + ", tempoS = "
					+ tempoS + ", tempoTotal = " + tempoTotal + ", voltas = " + voltas + " WHERE numero = " + numero
					+ "";
			System.out.println(f);
			st = con.createStatement();
			st.executeUpdate(f);

		} catch (SQLException e) {
			System.out.println("N�o foi possivel acessar a tabela corredores");
		}

	}

	public void atualizar(Corredor corredor) {
		Connection con = Conexao.getConexao();
		Statement st;

		try {
			String f = "UPDATE corrida.corredores SET tempoH = " + corredor.getTempoH() + ", tempoM = "
					+ corredor.getTempoM() + ", tempoS = " + corredor.getTempoS() + ", tempoTotal = "
					+ (corredor.getTempoH() * 3600 + corredor.getTempoM() * 60 + corredor.getTempoS()) + ", nome = \'"
					+ corredor.getNome() + "\', idade = " + corredor.getIdade() + ", sexo = \'" + corredor.getSexo()
					+ "\', voltas = " + 0 + " WHERE numero = " + corredor.getNumero() + "";
			System.out.println(f);
			st = con.createStatement();
			st.executeUpdate(f);

		} catch (SQLException e) {
			System.out.println("N�o foi possivel acessar a tabela corredores");
		}

	}

	public ArrayList<Corredor> recupera() {
		Connection con = Conexao.getConexao();
		Statement st;
		ResultSet rs;
		try {
			st = con.createStatement();
			String sql = "select * from corredores";
			System.out.println(sql);
			rs = st.executeQuery(sql);
			ArrayList<Corredor> c = new ArrayList<Corredor>();
			Corredor e = null;
			while (rs.next()) {
				String nome = rs.getString("nome");
				int km = rs.getInt("km");
				int idade = rs.getInt("idade");
				int numero = rs.getInt("numero");
				String sexo = rs.getString("sexo");
				int hora = rs.getInt("tempoH");
				int min = rs.getInt("tempoM");
				int seg = rs.getInt("tempoS");
				double tempo = rs.getDouble("tempoTotal");
				e = new Corredor(nome, numero, km, idade, sexo, hora, min, seg, tempo);
				c.add(e);
				// adicionaEstes();
			}
			return c;
		} catch (SQLException e) {
			System.out.println("Não foi possível acessar a tabela corredores.");

		}
		return null;

	}

	/*
	 * public void adicionaEstes() {
	 * 
	 * String[] nomes = { "Adriana mara Ribeiro Baptista",
	 * "James Alves do nascimento", "Juliana Alves Bezerra",
	 * "Ernildo da Silva Gomes", "Maria Erisvalda de Sousa araujo",
	 * "Monica Angela Machado Araujo", "Rita de cassia Sousa",
	 * "Fabr�cio Bacelar Salles", "Elizangela Gomes Coelho",
	 * "Amarildo Gon�alves de Ara�jo", "Igor Felipe Pinto de Ara�jo",
	 * "Jeferson de SOUZA Santos", "Dem�stenes Lu�s Campelo Galv�o",
	 * "Lilian Cunha Marinho", "Luis Carlos Dourado dos Santos",
	 * "Mabenys Augusto Castelo Branco de Sousa", "GEOVAN Fernandes da silva",
	 * "COSMO Alves de LIMA", "Jos� Osmar de Moura", "Jos� Anazildo de Ara�jo",
	 * "Jos� Sotero da Silva J�nior", "M�rcio Rodrigo de Ara�jo Souza",
	 * "Fabiola Alves de Meneses", "Carlos magno furtado Sousa",
	 * "Ant�nio Luiz Gomes de Sales", "S�nia Maria de Ara�jo Cris�stomo",
	 * "Tony Cesar Magalh�es Silva", "Joao Fabiano Fernandes",
	 * "Ant�nio sampaio evangelista", "Dsordi Sousa Dantas",
	 * "francisco marcos ferreira de lima", "Ricardo Teixeira de Carvalho",
	 * "Joana de Sousa Machado", "Luciana Eug�niaMendesdosSantos",
	 * "Ana Lucia Cavalcanti", "Andr� da Silva freitas", "Carla Carvalho Couto",
	 * "MATIAS FRANCISCO GOMES SALES", "Wandson sousa nascimento" };
	 * 
	 * int[] idades = { 46, 36, 37, 51, 42, 38, 34, 36, 37, 54, 28, 41, 50, 30,
	 * 34, 43, 42, 38, 43, 50, 50, 34, 28, 40, 58, 48, 46, 21, 64, 44, 44, 56,
	 * 34, 35, 56, 18, 39, 58, 31 };
	 * 
	 * int[] kms = { 12, 6, 12, 6, 6, 12, 12, 12, 6, 12, 12, 12, 6, 6, 12, 6,
	 * 12, 6, 6, 6, 6, 12, 12, 12, 6, 6, 6, 12, 12, 12, 12, 6, 6, 6, 12, 12, 6,
	 * 6, 12 }; Corredor c = null; for (int i = 0; i < nomes.length; i++) { c =
	 * new Corredor(nomes[i], i, kms[i], idades[i], "M"); // salvar(c);
	 * 
	 * } }
	 */
	public Corredor pesquisar(int numero) {
		Connection con = Conexao.getConexao();
		Statement st;
		ResultSet rs;
		try {
			st = con.createStatement();
			String sql = "select * from corredores where numero = " + numero + ";";
			System.out.println(sql);
			rs = st.executeQuery(sql);
			Corredor c = null;

			while (rs.next()) {
				String nome = rs.getString("nome");
				int km = rs.getInt("km");
				int idade = rs.getInt("idade");
				String sexo = rs.getString("sexo");
				int hora = rs.getInt("tempoH");
				int min = rs.getInt("tempoM");
				int seg = rs.getInt("tempoS");
				double tempo = rs.getDouble("tempoTotal");
				c = new Corredor(nome, numero, km, idade, sexo, hora, min, seg, tempo);
				return c;
			}
		} catch (SQLException e) {
			System.out.println("Não foi possível acessar a tabela corredores.");
			System.exit(1);
		}
		return null;

	}

	public int voltas(int numero) {
		Connection con = Conexao.getConexao();
		Statement st;
		ResultSet rs;
		try {
			st = con.createStatement();
			String sql = "select * from corredores where numero = " + numero + ";";
			System.out.println(sql);
			rs = st.executeQuery(sql);
			while (rs.next()) {
				int voltas = rs.getInt("voltas");
				return voltas + 1;
			}
		} catch (SQLException e) {
			System.out.println("Não foi possível acessar a tabela corredores.");
			System.exit(1);
		}
		return 0;

	}

	public static void main(String[] args) {
		// DAOCorredor d = new DAOCorredor();
		// d.adicionaEstes();
	}

	public boolean criaBanco() {
		Connection con = Conexao.getConexaoCriarBanco();
		if (con==null) {
			System.out.println("Conexao nula");
		}
		Statement st;
		try {
			st = con.createStatement();
			String sql = "CREATE DATABASE IF NOT EXISTS `corrida` DEFAULT CHARACTER SET utf8 ;";
			String sql1 = "USE `corrida`;";
			String sql2 = "CREATE TABLE IF NOT EXISTS `corrida`.`corredores` (  `idcorredores` INT(11) NOT NULL AUTO_INCREMENT,  `nome` VARCHAR(45) NOT NULL,  `km` INT(11) NOT NULL,  `numero` INT(11) NOT NULL,  `idade` INT(11) NOT NULL,  `sexo` VARCHAR(45) NOT NULL,  `tempoH` INT(11) NULL DEFAULT '0',  `tempoM` INT(11) NULL DEFAULT '0',  `tempoS` INT(11) NULL DEFAULT '0',  `tempoTotal` DOUBLE NULL DEFAULT '0',  `voltas` INT(11) NULL DEFAULT '0',  PRIMARY KEY (`idcorredores`))ENGINE = InnoDB,AUTO_INCREMENT = 43,DEFAULT CHARACTER SET = utf8,COMMENT = '	';";

			System.out.println(sql);
			System.out.println(sql1);
			System.out.println(sql2);

			st.executeUpdate(sql);
			st.executeUpdate(sql1);
			st.executeUpdate(sql2);
			st.close();
		} catch (SQLException e) {
			System.out.println("Nao foi poss�vel criar o banco.");
			return false;
		}
		return true;
	}

}
