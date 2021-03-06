package banco;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class Conexao {
	public Statement st;
	public ResultSet rs;
	public Connection d;

	public static String status = "N�o conectou...";

	// Criar conexao
	public static Connection getConexao() {
		Connection connection = null;

		try // A captura de exce��es SQLException em Java � obrigat�ria para
		// usarmos JDBC.
		{
			// Este � um dos meios para registrar um driver
			Class.forName("com.mysql.jdbc.Driver");
			// Registrado o driver, vamos estabelecer uma conex�o

			String serverName = "localhost";
			String sid = "corrida";
			String url = "jdbc:mysql://" + serverName + "/" + sid;

			String password = "";
			connection = (Connection) DriverManager.getConnection(url, "root", password);

			if (connection != null) {
				status = ("STATUS--->Conectado com sucesso!");
			} else {
				status = ("STATUS--->N�o foi possivel realizar conex�o");
			}

		} catch (ClassNotFoundException e) {
			System.out.println("O driver expecificado nao foi encontrado.");
			return null;
		} catch (SQLException e) {
			System.out.println("Nao foi possivel conectar ao Banco de Dados.");
			return null;
		}
		return connection;

	}

	//Conexao usadas na hora de criar o Banco de dados caso ele nao exista
	public static Connection getConexaoCriarBanco() {
		Connection connection = null;

		try // A captura de exce��es SQLException em Java � obrigat�ria para
		// usarmos JDBC.
		{
			// Este � um dos meios para registrar um driver
			Class.forName("com.mysql.jdbc.Driver");
			// Registrado o driver, vamos estabelecer uma conex�o

			String serverName = "localhost";
			String url = "jdbc:mysql://" + serverName ;

			String password = "";
			connection = (Connection) DriverManager.getConnection(url, "root", password);

			if (connection != null) {
				status = ("STATUS--->Conectado com sucesso!");
			} else {
				status = ("STATUS--->N�o foi possivel realizar conex�o");
			}

		} catch (ClassNotFoundException e) {
			System.out.println("O driver expecificado nao foi encontrado.");
			return null;
		} catch (SQLException e) {
			System.out.println("Nao foi possivel conectar ao Banco de Dados.");
			return null;
		}
		return connection;

	}

	// Status da conexao
	public static String statusConection() {
		return status;
	}

	// Fechar conexao
	public static boolean FecharConexao() {
		try {
			Conexao.getConexao().close();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	// Reiniciar conexao
	public static Connection ReiniciarConexao() {
		FecharConexao();
		return Conexao.getConexao();
	}

	// Excluir tudo
	public void excluirDadosTabela() {
		try {
			Statement stmt = (Statement) getConexao().createStatement();
			String sql = "TRUNCATE my_table";
			sql = "DELETE FROM my_table";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
		}

	}

	public void excluirDadosEspecificos() {
		d = (Connection) getConexao();
		try { // Create a statement
			Statement stmt = (Statement) d.createStatement();
			// Prepare a statement to insert a record
			String sql = "DELETE FROM my_table WHERE col_string='a string'";
			// Execute the delete statement
			@SuppressWarnings("unused")
			int deleteCount = stmt.executeUpdate(sql);
			// deleteCount contai ns the number of deleted rows
			// Use a prepared statement to delete
			// Prepare a statement to delete a record
			sql = "DELETE FROM my_table WHERE col_string=?";
			PreparedStatement pstmt = (PreparedStatement) d.prepareStatement(sql);
			// Set the value
			pstmt.setString(1, "a string");
			deleteCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	// MODELO PARA INSERIR
	public void inserirValorMensal(double valor) {

		try {
			String f = "INSERT INTO `provapoo`.`atendente` (`valorFixoMensal`) VALUES (\'" + valor + "');";
			System.out.println(f);

			Connection c = (Connection) getConexao();
			Statement stmt = (Statement) c.createStatement();
			stmt.executeUpdate(f);

		} catch (SQLException e) {
			System.out.println("ERRO valor nao alterado");
		}
		FecharConexao();

	}

	public void mudarValor(double mudarDe, double para) {
		try {
			String f = "UPDATE `provapoo`.`atendente` SET `valorFixoMensal`='" + para
					+ "' WHERE `valorFixoMensal`='"+mudarDe+"'";
			System.out.println(f);

			Connection c = (Connection) getConexao();
			Statement stmt = (Statement) c.createStatement();
			stmt.executeUpdate(f);

		} catch (SQLException e) {
			System.out.println("ERRO valor nao alterado");
		}
		FecharConexao();
	}

	public void deletarValor(double valor) {
		try {
			String f = "DELETE FROM `provapoo`.`atendente` WHERE `valorFixoMensal`='" + valor + "' ";
			System.out.println(f);

			Connection c = (Connection) getConexao();
			Statement stmt = (Statement) c.createStatement();
			stmt.executeUpdate(f);

		} catch (SQLException e) {
			System.out.println("ERRO valor nao alterado");
		}
		FecharConexao();
	}
}
