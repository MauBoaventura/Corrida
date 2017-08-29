package banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {
	public Statement st;
	public ResultSet rs;
	public Connection d;

	public static String status = "Não conectou...";

	// Criar conexao
	public static java.sql.Connection getConexao() {
		Connection connection = null;

		try // A captura de exceções SQLException em Java é obrigatória para
		// usarmos JDBC.
		{
			// Este é um dos meios para registrar um driver
			Class.forName("com.mysql.jdbc.Driver");
			// Registrado o driver, vamos estabelecer uma conexão

			String serverName = "localhost";
			String sid = "corrida";
			String url = "jdbc:mysql://" + serverName + "/" + sid;

			String password = "";
			connection = DriverManager.getConnection(url, "root", password);

			if (connection != null) {
				status = ("STATUS--->Conectado com sucesso!");
			} else {
				status = ("STATUS--->Não foi possivel realizar conexão");
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
	public static java.sql.Connection getConexaoCriarBanco() {
		Connection connection = null;

		try // A captura de exceções SQLException em Java é obrigatória para
		// usarmos JDBC.
		{
			// Este é um dos meios para registrar um driver
			Class.forName("com.mysql.jdbc.Driver");
			// Registrado o driver, vamos estabelecer uma conexão

			String serverName = "localhost";
			String url = "jdbc:mysql://" + serverName ;

			String password = "";
			connection = DriverManager.getConnection(url, "root", password);

			if (connection != null) {
				status = ("STATUS--->Conectado com sucesso!");
			} else {
				status = ("STATUS--->Não foi possivel realizar conexão");
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
	public static java.sql.Connection ReiniciarConexao() {
		FecharConexao();
		return Conexao.getConexao();
	}

	// Excluir tudo
	public void excluirDadosTabela() {
		try {
			Statement stmt = getConexao().createStatement();
			String sql = "TRUNCATE my_table";
			sql = "DELETE FROM my_table";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
		}

	}

	public void excluirDadosEspecificos() {
		d = getConexao();
		try { // Create a statement
			Statement stmt = d.createStatement();
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

			Connection c = getConexao();
			Statement stmt = c.createStatement();
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

			Connection c = getConexao();
			Statement stmt = c.createStatement();
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

			Connection c = getConexao();
			Statement stmt = c.createStatement();
			stmt.executeUpdate(f);

		} catch (SQLException e) {
			System.out.println("ERRO valor nao alterado");
		}
		FecharConexao();
	}
}
