package circuitoPiauiense;

import java.io.FileOutputStream;
import java.io.IOException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import banco.DAOCorredor;

//import java.io.ObjectInputStream.GetField;  
//import java.util.Comparator;  

//import java.io.ObjectInputStream.GetField;  
//import java.util.Comparator;  

public class Colocação {
	private static final DateFormat FORMATO = new SimpleDateFormat("MM/yyyy");
	private long inicio;
	FileSystemView dir = FileSystemView.getFileSystemView();
	String desktop = dir.getHomeDirectory().getPath();
	List<Corredor> atletas = new ArrayList<Corredor>();
	private String pasta = "Relatorios da Corrida";

	public Colocação() {
		super();
		if (criaBanco()) {
			System.out.println("Banco funcinando!");
		}else{
			System.out.println("Erro no Banco");
		}
		try {
			setAtletas();
		} catch (Exception e) {
			atletas = new ArrayList<Corredor>();
		}
		carregar();
	}

	public long getInicio() {
		return inicio;
	}

	public void setInicio(long inicio) {
		this.inicio = inicio;
	}

	@SuppressWarnings("resource")
	public boolean menuPrincipal() {
		// relatorioEspecial();
		System.out.println("1-Iniciar a corrida\n" + "2-Cadastrar um competidor\n" + "3-Lista de competidores\n"
				+ "4-Competidores por Categoria(ja chegaram)\n" + "5-Chegada do competidor(Horario de chegada)\n"
				+ "6-Relatorios\n7-Tempo decorrido\n8-Todos atletas por categoria\n9-Atletas restantes por categoria");

		Scanner ler;
		ler = new Scanner(System.in);
		String aw = ler.next();

		switch (aw) {

		case "1":
			iniciaCorrida();
			break;
		case "2":
			String nome;
			String numero;
			String km6;
			String idade;
			String sexo;

			// NOME
			System.out.println("Nome do atleta:");
			nome = ler.nextLine();
			// NUMERO

			int num = 0;
			int flag;

			do {
				flag = 0;
				System.out.println("Numero do atleta:");
				numero = ler.next();

				try {

					num = Integer.parseInt(numero);

				} catch (NumberFormatException e) {
					flag = 1;
				}

			} while (procuraNumero(num) != null || flag == 1);

			// QUILOMETRAGEM

			int quilometro = 0;
			do {
				flag = 0;
				System.out.println("Quilometragem do atleta:");
				km6 = ler.next();

				try {

					quilometro = Integer.parseInt(km6);

				} catch (NumberFormatException e) {
					flag = 1;
				}
				// error
			} while (flag == 1 || quilometro != 12 && quilometro != 6);

			// km6 = ler.nextInt();

			// IDADE

			int id = 0;
			do {
				flag = 0;

				System.out.println("Idade do atleta:");
				idade = ler.next();

				try {

					id = Integer.parseInt(idade);

				} catch (NumberFormatException e) {
					flag = 1;
				}

			} while (flag == 1);

			// SEXO

			sexo = ler.nextLine();
			do {
				System.out.println("Sexo do atleta:");
				sexo = ler.nextLine();

			} while (!sexo.equals("M") && !sexo.equals("F"));

			System.out.printf("Nome: %s\nNumero: %d\nKm: %d\nIdade: %d\nSexo: %s\n", nome, num, quilometro, id, sexo);

			adiciona(nome, numero, km6, idade, sexo);
			break;
		case "3":
			System.out.println("\t\t\tLISTA DE COMPETIDORES\n\n");
			for (int i = 0; i < atletas.size(); i++) {
				System.out.println("Nome: " + atletas.get(i).getNome() + "\nNumero: " + atletas.get(i).getNumero()
						+ "\nCategoria: " + atletas.get(i).getKm6() + "km" + "\nSexo: " + atletas.get(i).getSexo()
						+ "\n");
			}
			break;
		case "4":
			competidoresPorCategoria();
			break;
		case "5":
			// Ler numero e passar por parametro
			// chegada();

			break;
		case "6":
			relatorios();
			break;
		case "7":
			tempoDecorrido();
			break;
		case "8":

			Collections.sort(atletas, Corredor.getComparatorIdadeDescNomeCresc());

			System.out.println("\t\t\tGeral 12 KM\n");

			for (int i = 0, cont = 1; i < atletas.size(); i++) {
				if (atletas.get(i).getKm6() == 12 && (atletas.get(i).getTempoH() == 0 && atletas.get(i).getTempoM() == 0
						&& atletas.get(i).getTempoS() == 0)) {
					System.out.printf("%d- Nº:%02d   NOME:%s\t%02d:%02d:%02d\n", cont, atletas.get(i).getNumero(),
							atletas.get(i).getNome(), atletas.get(i).getTempoH(), atletas.get(i).getTempoM(),
							atletas.get(i).getTempoS());
					cont++;
				}
			}

			System.out.println("\t\t\tGeral 6 KM\n");

			for (int i = 0, cont = 1; i < atletas.size(); i++) {
				if (atletas.get(i).getKm6() == 6 && (atletas.get(i).getTempoH() == 0 && atletas.get(i).getTempoM() == 0
						&& atletas.get(i).getTempoS() == 0)) {
					System.out.printf("%d- Nº:%02d   NOME:%s\t%02d:%02d:%02d\n", cont, atletas.get(i).getNumero(),
							atletas.get(i).getNome(), atletas.get(i).getTempoH(), atletas.get(i).getTempoM(),
							atletas.get(i).getTempoS());
					cont++;
				}
			}
			System.out.println("\t\t\tGeral Feminina 12 KM\n");

			for (int i = 0, cont = 1; i < atletas.size(); i++) {
				if (atletas.get(i).getKm6() == 12 && atletas.get(i).getSexo().equals("F")
						&& (atletas.get(i).getTempoH() == 0 && atletas.get(i).getTempoM() == 0
								&& atletas.get(i).getTempoS() == 0)) {
					System.out.printf("%d- Nº:%02d   NOME:%s\t%02d:%02d:%02d\n", cont, atletas.get(i).getNumero(),
							atletas.get(i).getNome(), atletas.get(i).getTempoH(), atletas.get(i).getTempoM(),
							atletas.get(i).getTempoS());
					cont++;
				}
			}
			System.out.println("\t\t\tGeral Femininia 6 KM\n");

			for (int i = 0, cont = 1; i < atletas.size(); i++) {
				if (atletas.get(i).getKm6() == 6 && atletas.get(i).getSexo().equals("F")
						&& (atletas.get(i).getTempoH() == 0 && atletas.get(i).getTempoM() == 0
								&& atletas.get(i).getTempoS() == 0)) {
					System.out.printf("%d- Nº:%02d   NOME:%s\t%02d:%02d:%02d\n", cont, atletas.get(i).getNumero(),
							atletas.get(i).getNome(), atletas.get(i).getTempoH(), atletas.get(i).getTempoM(),
							atletas.get(i).getTempoS());
					cont++;
				}
			}
			System.out.println("\t\t\tGeral Masculina 12 KM\n");

			for (int i = 0, cont = 1; i < atletas.size(); i++) {
				if (atletas.get(i).getKm6() == 12 && atletas.get(i).getSexo().equals("M")
						&& (atletas.get(i).getTempoH() == 0 && atletas.get(i).getTempoM() == 0
								&& atletas.get(i).getTempoS() == 0)) {
					System.out.printf("%d- Nº:%02d   NOME:%s\t%02d:%02d:%02d\n", cont, atletas.get(i).getNumero(),
							atletas.get(i).getNome(), atletas.get(i).getTempoH(), atletas.get(i).getTempoM(),
							atletas.get(i).getTempoS());
					cont++;
				}
			}
			System.out.println("\t\t\tGeral Masculina 6 KM\n");

			for (int i = 0, cont = 1; i < atletas.size(); i++) {
				if (atletas.get(i).getKm6() == 6 && atletas.get(i).getSexo().equals("M")
						&& (atletas.get(i).getTempoH() == 0 && atletas.get(i).getTempoM() == 0
								&& atletas.get(i).getTempoS() == 0)) {
					System.out.printf("%d- Nº:%02d   NOME:%s\t%02d:%02d:%02d\n", cont, atletas.get(i).getNumero(),
							atletas.get(i).getNome(), atletas.get(i).getTempoH(), atletas.get(i).getTempoM(),
							atletas.get(i).getTempoS());
					cont++;
				}
			}
			System.out.println("\t\t\tMaster (+50 anos)KM\n");

			for (int i = 0, cont = 1; i < atletas.size(); i++) {
				if (atletas.get(i).getIdade() >= 50 && (atletas.get(i).getTempoH() == 0
						&& atletas.get(i).getTempoM() == 0 && atletas.get(i).getTempoS() == 0)) {
					System.out.printf("%d- Nº:%02d   NOME:%s\t%02d:%02d:%02d\n", cont, atletas.get(i).getNumero(),
							atletas.get(i).getNome(), atletas.get(i).getTempoH(), atletas.get(i).getTempoM(),
							atletas.get(i).getTempoS());
					cont++;
				}
			}

			break;
		case "9":
			restantes();
			break;
		case "00":
			salva();
			System.out.println("ACABOU");
			return false;
		default:
			salva();
			System.out.println("Aqui\n\n");

		}
		return true;

	}

	public long tempoDecorrido() {
		long end = System.currentTimeMillis();

		long controle = end - inicio;

		// Mais 3 horas
		return controle + 3600000 * 3;

		/*
		 * int h = (int) controle / 3600000; controle = controle % 3600000;
		 * 
		 * int m = (int) controle / 60000;
		 * 
		 * int s = (int) controle % 60000 ;
		 * 
		 * System.out.printf("Tempo decorrido: %02d:%02d:%02d\n", h, m, s);
		 */
	}

	public void iniciaCorrida() {
		inicio = System.currentTimeMillis();
		System.out.println("\nINICIO DA CORRIDA\n");

	}

	private Corredor procuraNumero(int numero) {
		for (int i = 0; i < atletas.size(); i++) {
			if (atletas.get(i).getNumero() == numero) {
				return atletas.get(i);
			}
		}
		return null;
	}

	public void chegada(int num) {

		// Scanner ler = new Scanner(System.in);
		//
		// String n;
		// int flag = 0;
		// int num = 0;

		// do {
		// flag = 0;
		// Força o usuario a digitar um numero
		// System.out.println("Numero: ");
		// n = ler.next();
		//
		// try {
		// num = Integer.parseInt(n);
		//
		// } catch (NumberFormatException e) {
		// flag = 1;
		// }
		//
		// } while (flag == 1);
		//
		Corredor corre;
		corre = procuraNumero(num);
		//
		// // CASO NENHUM TENHA SIDO CADASTRADO
		// if (num == 0) {
		// return;
		// }

		if (corre == null)

		{
			System.out.println("Numero invalido");
			JOptionPane.showMessageDialog(null, "Numero invalido", "TEMPO", JOptionPane.INFORMATION_MESSAGE);

			return;
		}
		long fim = System.currentTimeMillis();

		corre.setTotalDeSegundos((fim - inicio) / 1000);

		int tempoH, tempoM, tempoS;

		double controle = (fim - inicio) / 1000;
		double tempoTotal = controle;
		tempoH = (int) (controle / 3600);
		corre.setTempoH((int) controle / 3600);
		controle = controle % 3600;

		tempoM = (int) (controle / 60);
		corre.setTempoM((int) controle / 60);

		tempoS = (int) controle % 60;
		corre.setTempoS((int) controle % 60);

		System.out.printf("Nº:%d chegou com o tempo de %02d:%02d:%02d\n", corre.getNumero(), corre.getTempoH(),
				corre.getTempoM(), corre.getTempoS());

		DAOCorredor daoC = new DAOCorredor();

		daoC.setTempo(num, tempoH, tempoM, tempoS, tempoTotal, daoC.voltas(num));

	}

	public void relatorios() {

		Collections.sort(atletas, Corredor.getComparatorIdadeDescNomeCresc());

		Document document = new Document();

		// Adrenalina 18-28 M 12

		// Retorna o endereco da area de trabalho

		System.out.println("\t\t\tTodos corredores\n");
		try {
			PdfWriter.getInstance(document,
					new FileOutputStream(desktop + File.separator + pasta + File.separator + "RELATORIOS.pdf"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		document.open();
		try {

			int cont = 0;
			document.add(
					new Paragraph("                                                     	      Todos Corredores - "
							+ FORMATO.format(new Date())));
			for (Corredor corredor : atletas) {
				document.add(new Paragraph(corredor.montada(cont + 1, atletas, cont)));
				cont++;
			}
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		}

		System.out.println("\t\t\tAdrenalina 18-28 M 12\n");
		document.newPage();

		Corredor corredor = new Corredor();
		try {
			document.add(new Paragraph(
					"                                                      	        Adrenalina 18-28 M 12 - "
							+ FORMATO.format(new Date())));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 18 && atletas.get(i).getIdade() <= 28 && atletas.get(i).getKm6() == 12
					&& atletas.get(i).getSexo().equals("M") && !(atletas.get(i).getTempoH() == 0
							&& atletas.get(i).getTempoM() == 0 && atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		// Adrenalina 18-28 M 6

		System.out.println("\t\t\tAdrenalina 18-28 M 6\n");

		document.newPage();
		try {
			document.add(new Paragraph(
					"                                                      	        Adrenalina 18-28 M 6 - "
							+ FORMATO.format(new Date())));
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 18 && atletas.get(i).getIdade() <= 28 && atletas.get(i).getKm6() == 6
					&& atletas.get(i).getSexo().equals("M") && !(atletas.get(i).getTempoH() == 0
							&& atletas.get(i).getTempoM() == 0 && atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		// Adrenalina 18-28 F 12

		System.out.println("\t\t\tAdrenalina 18-28 F 12\n");

		document.newPage();
		try {
			document.add(new Paragraph(
					"                                                      	        Adrenalina 18-28 F 12 - "
							+ FORMATO.format(new Date())));
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 18 && atletas.get(i).getIdade() <= 28 && atletas.get(i).getKm6() == 12
					&& atletas.get(i).getSexo().equals("F") && !(atletas.get(i).getTempoH() == 0
							&& atletas.get(i).getTempoM() == 0 && atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		// Adrenalina 18-28 F 6

		document.newPage();
		try {
			document.add(new Paragraph(
					"                                                      	        Adrenalina 18-28 F 6 - "
							+ FORMATO.format(new Date())));
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 18 && atletas.get(i).getIdade() <= 28 && atletas.get(i).getKm6() == 6
					&& atletas.get(i).getSexo().equals("F") && !(atletas.get(i).getTempoH() == 0
							&& atletas.get(i).getTempoM() == 0 && atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		// ****************************************************************************************************************************************************

		// Race 29-39 M 12

		System.out.println("\t\t\tRace 29-39 M 12\n");

		document.newPage();
		corredor = new Corredor();
		try {
			document.add(
					new Paragraph("                                                      	        Race 29-39 M 12 - "
							+ FORMATO.format(new Date())));
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 29 && atletas.get(i).getIdade() <= 39 && atletas.get(i).getKm6() == 12
					&& atletas.get(i).getSexo().equals("M") && !(atletas.get(i).getTempoH() == 0
							&& atletas.get(i).getTempoM() == 0 && atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		// Race 29-39 M 6

		System.out.println("\t\t\tRace 29-39 M 6\n");

		document.newPage();
		try {
			document.add(
					new Paragraph("                                                      	        Race 29-39 M 6 - "
							+ FORMATO.format(new Date())));
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 29 && atletas.get(i).getIdade() <= 39 && atletas.get(i).getKm6() == 6
					&& atletas.get(i).getSexo().equals("M") && !(atletas.get(i).getTempoH() == 0
							&& atletas.get(i).getTempoM() == 0 && atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		// Race 29-39 F 12

		System.out.println("\t\t\tRace 29-39 F 12\n");

		document.newPage();
		try {
			document.add(
					new Paragraph("                                                      	        Race 29-39 F 12 - "
							+ FORMATO.format(new Date())));
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 29 && atletas.get(i).getIdade() <= 39 && atletas.get(i).getKm6() == 12
					&& atletas.get(i).getSexo().equals("F") && !(atletas.get(i).getTempoH() == 0
							&& atletas.get(i).getTempoM() == 0 && atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		// Race 29-39 F 6

		System.out.println("\t\t\tRace 29-39 F 6\n");

		document.newPage();
		try {
			document.add(
					new Paragraph("                                                      	        Race 29-39 F 6 - "
							+ FORMATO.format(new Date())));
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 29 && atletas.get(i).getIdade() <= 39 && atletas.get(i).getKm6() == 6
					&& atletas.get(i).getSexo().equals("F") && !(atletas.get(i).getTempoH() == 0
							&& atletas.get(i).getTempoM() == 0 && atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		// ****************************************************************************************************************************************************

		// Extreme 40-49 M 12

		System.out.println("\t\t\tExtreme 40-49 M 12\n");

		document.newPage();
		corredor = new Corredor();
		try {
			document.add(new Paragraph(
					"                                                      	        Extreme 40-49 M 12 - "
							+ FORMATO.format(new Date())));
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 40 && atletas.get(i).getIdade() <= 49 && atletas.get(i).getKm6() == 12
					&& atletas.get(i).getSexo().equals("M") && !(atletas.get(i).getTempoH() == 0
							&& atletas.get(i).getTempoM() == 0 && atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		// Extreme 40-49 M 6

		System.out.println("\t\t\tExtreme 40-49 M 6\n");

		document.newPage();
		try {
			document.add(new Paragraph(
					"                                                      	        Extreme 40-49 M 6 - "
							+ FORMATO.format(new Date())));
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 40 && atletas.get(i).getIdade() <= 49 && atletas.get(i).getKm6() == 6
					&& atletas.get(i).getSexo().equals("M") && !(atletas.get(i).getTempoH() == 0
							&& atletas.get(i).getTempoM() == 0 && atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		// Extreme 40-49 F 12

		System.out.println("\t\t\tExtreme 40-49 F 12\n");

		document.newPage();
		try {
			document.add(new Paragraph(
					"                                                      	        Extreme 40-49 F 12 - "
							+ FORMATO.format(new Date())));
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 40 && atletas.get(i).getIdade() <= 49 && atletas.get(i).getKm6() == 12
					&& atletas.get(i).getSexo().equals("F") && !(atletas.get(i).getTempoH() == 0
							&& atletas.get(i).getTempoM() == 0 && atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		// Extreme 40-49 F 6

		System.out.println("\t\t\tExtreme 40-49 F 6\n");

		document.newPage();
		try {
			document.add(new Paragraph(
					"                                                      	        Extreme 40-49 F 6 - "
							+ FORMATO.format(new Date())));
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 40 && atletas.get(i).getIdade() <= 49 && atletas.get(i).getKm6() == 6
					&& atletas.get(i).getSexo().equals("F") && !(atletas.get(i).getTempoH() == 0
							&& atletas.get(i).getTempoM() == 0 && atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		// ****************************************************************************************************************************************************

		// Force 50-59 M 12

		System.out.println("\t\t\tForce 50-59 M 12\n");

		document.newPage();
		corredor = new Corredor();
		try {
			document.add(
					new Paragraph("                                                      	        Force 50-59 M 12 - "
							+ FORMATO.format(new Date())));
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 50 && atletas.get(i).getIdade() <= 59 && atletas.get(i).getKm6() == 12
					&& atletas.get(i).getSexo().equals("M") && !(atletas.get(i).getTempoH() == 0
							&& atletas.get(i).getTempoM() == 0 && atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		// Force 50-59 M 6

		System.out.println("\t\t\tForce 50-59 M 6\n");

		document.newPage();
		try {
			document.add(
					new Paragraph("                                                      	        Force 50-59 M 6 - "
							+ FORMATO.format(new Date())));
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 50 && atletas.get(i).getIdade() <= 59 && atletas.get(i).getKm6() == 6
					&& atletas.get(i).getSexo().equals("M") && !(atletas.get(i).getTempoH() == 0
							&& atletas.get(i).getTempoM() == 0 && atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		// Force 50-59 F 12

		System.out.println("\t\t\tForce 50-59 F 12\n");

		document.newPage();
		try {
			document.add(
					new Paragraph("                                                      	        Force 50-59 F 12 - "
							+ FORMATO.format(new Date())));
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 50 && atletas.get(i).getIdade() <= 59 && atletas.get(i).getKm6() == 12
					&& atletas.get(i).getSexo().equals("F") && !(atletas.get(i).getTempoH() == 0
							&& atletas.get(i).getTempoM() == 0 && atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		// Force 50-59 F 6

		System.out.println("\t\t\tForce 50-59 F 6\n");

		document.newPage();
		try {
			document.add(
					new Paragraph("                                                      	        Force 50-59 F 6 - "
							+ FORMATO.format(new Date())));
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 50 && atletas.get(i).getIdade() <= 59 && atletas.get(i).getKm6() == 6
					&& atletas.get(i).getSexo().equals("F") && !(atletas.get(i).getTempoH() == 0
							&& atletas.get(i).getTempoM() == 0 && atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		// ****************************************************************************************************************************************************

		// Iron +60 M 12

		System.out.println("\t\t\tIron +60 M 12\n");

		document.newPage();
		corredor = new Corredor();
		try {
			document.add(
					new Paragraph("                                                      	        Iron +60 M 12 - "
							+ FORMATO.format(new Date())));
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 60 && atletas.get(i).getKm6() == 12 && atletas.get(i).getSexo().equals("M")
					&& !(atletas.get(i).getTempoH() == 0 && atletas.get(i).getTempoM() == 0
							&& atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		// Iron +60 M 6

		System.out.println("\t\t\tIron +60 M 6\n");

		document.newPage();
		try {
			document.add(new Paragraph("                                                      	        Iron +60 M 6 - "
					+ FORMATO.format(new Date())));
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 60 && atletas.get(i).getKm6() == 6 && atletas.get(i).getSexo().equals("M")
					&& !(atletas.get(i).getTempoH() == 0 && atletas.get(i).getTempoM() == 0
							&& atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		// Iron +60 F 12

		System.out.println("\t\t\tIron +60 F 12\n");

		document.newPage();
		try {
			document.add(
					new Paragraph("                                                      	        Iron +60 F 12 - "
							+ FORMATO.format(new Date())));
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 60 && atletas.get(i).getKm6() == 12 && atletas.get(i).getSexo().equals("F")
					&& !(atletas.get(i).getTempoH() == 0 && atletas.get(i).getTempoM() == 0
							&& atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		// Iron +60 F 6

		System.out.println("\t\t\tIron +60 F 6\n");

		document.newPage();
		try {
			document.add(new Paragraph("                                                      	        Iron +60 F 6 - "
					+ FORMATO.format(new Date())));
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 60 && atletas.get(i).getKm6() == 6 && atletas.get(i).getSexo().equals("F")
					&& !(atletas.get(i).getTempoH() == 0 && atletas.get(i).getTempoM() == 0
							&& atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}
		/*
		 * document.newPage(); try { document.add(new Paragraph(
		 * "                                                      	        Menos de 17 anos "
		 * + FORMATO.format(new Date()))); } catch (DocumentException e) {
		 * 
		 * e.printStackTrace(); } for (int i = 0, cont = 1; i < atletas.size();
		 * i++) { if (atletas.get(i).getIdade() <18) { try { document.add(new
		 * Paragraph(corredor.montada(cont, atletas, i))); cont++;
		 * 
		 * } catch (DocumentException de) { System.err.println(de.getMessage());
		 * } } }
		 */
		document.close();
	}

	public void restantes() {

		Collections.sort(atletas, Corredor.getComparatorIdadeDescNomeCresc());

		Document document = new Document();

		// Adrenalina 18-28 M 12

		System.out.println("\t\t\tTodos corredores\n");
		try {
			PdfWriter.getInstance(document, new FileOutputStream(
					desktop + File.separator + pasta + File.separator + "RELATORIOS RESTANTES.pdf"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		document.open();

		System.out.println("\t\t\tAdrenalina 18-28 M 12\n");

		Corredor corredor = new Corredor();
		try {
			document.add(new Paragraph(
					"                                                      	        Adrenalina 18-28 M 12 - "
							+ FORMATO.format(new Date())));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 18 && atletas.get(i).getIdade() <= 28 && atletas.get(i).getKm6() == 12
					&& atletas.get(i).getSexo().equals("M") && (atletas.get(i).getTempoH() == 0
							&& atletas.get(i).getTempoM() == 0 && atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		// Adrenalina 18-28 M 6

		System.out.println("\t\t\tAdrenalina 18-28 M 6\n");

		document.newPage();
		try {
			document.add(new Paragraph(
					"                                                      	        Adrenalina 18-28 M 6 - "
							+ FORMATO.format(new Date())));
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 18 && atletas.get(i).getIdade() <= 28 && atletas.get(i).getKm6() == 6
					&& atletas.get(i).getSexo().equals("M") && (atletas.get(i).getTempoH() == 0
							&& atletas.get(i).getTempoM() == 0 && atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		// Adrenalina 18-28 F 12

		System.out.println("\t\t\tAdrenalina 18-28 F 12\n");

		document.newPage();
		try {
			document.add(new Paragraph(
					"                                                      	        Adrenalina 18-28 F 12 - "
							+ FORMATO.format(new Date())));
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 18 && atletas.get(i).getIdade() <= 28 && atletas.get(i).getKm6() == 12
					&& atletas.get(i).getSexo().equals("F") && (atletas.get(i).getTempoH() == 0
							&& atletas.get(i).getTempoM() == 0 && atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		// Adrenalina 18-28 F 6

		document.newPage();
		try {
			document.add(new Paragraph(
					"                                                      	        Adrenalina 18-28 F 6 - "
							+ FORMATO.format(new Date())));
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 18 && atletas.get(i).getIdade() <= 28 && atletas.get(i).getKm6() == 6
					&& atletas.get(i).getSexo().equals("F") && (atletas.get(i).getTempoH() == 0
							&& atletas.get(i).getTempoM() == 0 && atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		// ****************************************************************************************************************************************************

		// Race 29-39 M 12

		System.out.println("\t\t\tRace 29-39 M 12\n");

		document.newPage();
		corredor = new Corredor();
		try {
			document.add(
					new Paragraph("                                                      	        Race 29-39 M 12 - "
							+ FORMATO.format(new Date())));
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 29 && atletas.get(i).getIdade() <= 39 && atletas.get(i).getKm6() == 12
					&& atletas.get(i).getSexo().equals("M") && (atletas.get(i).getTempoH() == 0
							&& atletas.get(i).getTempoM() == 0 && atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		// Race 29-39 M 6

		System.out.println("\t\t\tRace 29-39 M 6\n");

		document.newPage();
		try {
			document.add(
					new Paragraph("                                                      	        Race 29-39 M 6 - "
							+ FORMATO.format(new Date())));
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 29 && atletas.get(i).getIdade() <= 39 && atletas.get(i).getKm6() == 6
					&& atletas.get(i).getSexo().equals("M") && (atletas.get(i).getTempoH() == 0
							&& atletas.get(i).getTempoM() == 0 && atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		// Race 29-39 F 12

		System.out.println("\t\t\tRace 29-39 F 12\n");

		document.newPage();
		try {
			document.add(
					new Paragraph("                                                      	        Race 29-39 F 12 - "
							+ FORMATO.format(new Date())));
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 29 && atletas.get(i).getIdade() <= 39 && atletas.get(i).getKm6() == 12
					&& atletas.get(i).getSexo().equals("F") && (atletas.get(i).getTempoH() == 0
							&& atletas.get(i).getTempoM() == 0 && atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		// Race 29-39 F 6

		System.out.println("\t\t\tRace 29-39 F 6\n");

		document.newPage();
		try {
			document.add(
					new Paragraph("                                                      	        Race 29-39 F 6 - "
							+ FORMATO.format(new Date())));
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 29 && atletas.get(i).getIdade() <= 39 && atletas.get(i).getKm6() == 6
					&& atletas.get(i).getSexo().equals("F") && (atletas.get(i).getTempoH() == 0
							&& atletas.get(i).getTempoM() == 0 && atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		// ****************************************************************************************************************************************************

		// Extreme 40-49 M 12

		System.out.println("\t\t\tExtreme 40-49 M 12\n");

		document.newPage();
		corredor = new Corredor();
		try {
			document.add(new Paragraph(
					"                                                      	        Extreme 40-49 M 12 - "
							+ FORMATO.format(new Date())));
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 40 && atletas.get(i).getIdade() <= 49 && atletas.get(i).getKm6() == 12
					&& atletas.get(i).getSexo().equals("M") && (atletas.get(i).getTempoH() == 0
							&& atletas.get(i).getTempoM() == 0 && atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		// Extreme 40-49 M 6

		System.out.println("\t\t\tExtreme 40-49 M 6\n");

		document.newPage();
		try {
			document.add(new Paragraph(
					"                                                      	        Extreme 40-49 M 6 - "
							+ FORMATO.format(new Date())));
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 40 && atletas.get(i).getIdade() <= 49 && atletas.get(i).getKm6() == 6
					&& atletas.get(i).getSexo().equals("M") && (atletas.get(i).getTempoH() == 0
							&& atletas.get(i).getTempoM() == 0 && atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		// Extreme 40-49 F 12

		System.out.println("\t\t\tExtreme 40-49 F 12\n");

		document.newPage();
		try {
			document.add(new Paragraph(
					"                                                      	        Extreme 40-49 F 12 - "
							+ FORMATO.format(new Date())));
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 40 && atletas.get(i).getIdade() <= 49 && atletas.get(i).getKm6() == 12
					&& atletas.get(i).getSexo().equals("F") && (atletas.get(i).getTempoH() == 0
							&& atletas.get(i).getTempoM() == 0 && atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		// Extreme 40-49 F 6

		System.out.println("\t\t\tExtreme 40-49 F 6\n");

		document.newPage();
		try {
			document.add(new Paragraph(
					"                                                      	        Extreme 40-49 F 6 - "
							+ FORMATO.format(new Date())));
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 40 && atletas.get(i).getIdade() <= 49 && atletas.get(i).getKm6() == 6
					&& atletas.get(i).getSexo().equals("F") && (atletas.get(i).getTempoH() == 0
							&& atletas.get(i).getTempoM() == 0 && atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		// ****************************************************************************************************************************************************

		// Force 50-59 M 12

		System.out.println("\t\t\tForce 50-59 M 12\n");

		document.newPage();
		corredor = new Corredor();
		try {
			document.add(
					new Paragraph("                                                      	        Force 50-59 M 12 - "
							+ FORMATO.format(new Date())));
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 50 && atletas.get(i).getIdade() <= 59 && atletas.get(i).getKm6() == 12
					&& atletas.get(i).getSexo().equals("M") && (atletas.get(i).getTempoH() == 0
							&& atletas.get(i).getTempoM() == 0 && atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		// Force 50-59 M 6

		System.out.println("\t\t\tForce 50-59 M 6\n");

		document.newPage();
		try {
			document.add(
					new Paragraph("                                                      	        Force 50-59 M 6 - "
							+ FORMATO.format(new Date())));
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 50 && atletas.get(i).getIdade() <= 59 && atletas.get(i).getKm6() == 6
					&& atletas.get(i).getSexo().equals("M") && (atletas.get(i).getTempoH() == 0
							&& atletas.get(i).getTempoM() == 0 && atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		// Force 50-59 F 12

		System.out.println("\t\t\tForce 50-59 F 12\n");

		document.newPage();
		try {
			document.add(
					new Paragraph("                                                      	        Force 50-59 F 12 - "
							+ FORMATO.format(new Date())));
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 50 && atletas.get(i).getIdade() <= 59 && atletas.get(i).getKm6() == 12
					&& atletas.get(i).getSexo().equals("F") && (atletas.get(i).getTempoH() == 0
							&& atletas.get(i).getTempoM() == 0 && atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		// Force 50-59 F 6

		System.out.println("\t\t\tForce 50-59 F 6\n");

		document.newPage();
		try {
			document.add(
					new Paragraph("                                                      	        Force 50-59 F 6 - "
							+ FORMATO.format(new Date())));
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 50 && atletas.get(i).getIdade() <= 59 && atletas.get(i).getKm6() == 6
					&& atletas.get(i).getSexo().equals("F") && (atletas.get(i).getTempoH() == 0
							&& atletas.get(i).getTempoM() == 0 && atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		// ****************************************************************************************************************************************************

		// Iron +60 M 12

		System.out.println("\t\t\tIron +60 M 12\n");

		document.newPage();
		corredor = new Corredor();
		try {
			document.add(
					new Paragraph("                                                      	        Iron +60 M 12 - "
							+ FORMATO.format(new Date())));
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 60 && atletas.get(i).getKm6() == 12 && atletas.get(i).getSexo().equals("M")
					&& (atletas.get(i).getTempoH() == 0 && atletas.get(i).getTempoM() == 0
							&& atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		// Iron +60 M 6

		System.out.println("\t\t\tIron +60 M 6\n");

		document.newPage();
		try {
			document.add(new Paragraph("                                                      	        Iron +60 M 6 - "
					+ FORMATO.format(new Date())));
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 60 && atletas.get(i).getKm6() == 6 && atletas.get(i).getSexo().equals("M")
					&& (atletas.get(i).getTempoH() == 0 && atletas.get(i).getTempoM() == 0
							&& atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		// Iron +60 F 12

		System.out.println("\t\t\tIron +60 F 12\n");

		document.newPage();
		try {
			document.add(
					new Paragraph("                                                      	        Iron +60 F 12 - "
							+ FORMATO.format(new Date())));
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 60 && atletas.get(i).getKm6() == 12 && atletas.get(i).getSexo().equals("F")
					&& (atletas.get(i).getTempoH() == 0 && atletas.get(i).getTempoM() == 0
							&& atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		// Iron +60 F 6

		System.out.println("\t\t\tIron +60 F 6\n");

		document.newPage();
		try {
			document.add(new Paragraph("                                                      	        Iron +60 F 6 - "
					+ FORMATO.format(new Date())));
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 60 && atletas.get(i).getKm6() == 6 && atletas.get(i).getSexo().equals("F")
					&& (atletas.get(i).getTempoH() == 0 && atletas.get(i).getTempoM() == 0
							&& atletas.get(i).getTempoS() == 0)) {
				try {
					document.add(new Paragraph(corredor.montada(cont, atletas, i)));
					cont++;

				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				}
			}
		}

		document.close();
	}

	public void relatorioEspecial() {
		Collections.sort(atletas, Corredor.getComparatorIdadeDescNomeCresc());

		Document document = new Document();

		try {
			PdfWriter.getInstance(document,
					new FileOutputStream(desktop + File.separator + pasta + File.separator + "6KM e 12KM.pdf"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		document.open();
		try {
			int cont = 0;
			document.add(
					new Paragraph("                                                      	        Corredores 6KM - "
							+ FORMATO.format(new Date())));
			Corredor corredor = new Corredor();
			for (int i = 0; i < atletas.size(); i++) {
				if (atletas.get(i).getKm6() == 6) {
					try {
						document.add(new Paragraph(corredor.montada(cont, atletas, i)));
						cont++;

					} catch (DocumentException de) {
						System.err.println(de.getMessage());
					}
				}
			}

		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		}
		document.newPage();

		try {
			int cont = 0;
			document.add(
					new Paragraph("                                                      	        Corredores 12KM - "
							+ FORMATO.format(new Date())));
			Corredor corredor = new Corredor();
			for (int i = 0; i < atletas.size(); i++) {
				if (atletas.get(i).getKm6() == 12) {
					try {
						document.add(new Paragraph(corredor.montada(cont, atletas, i)));
						cont++;

					} catch (DocumentException de) {
						System.err.println(de.getMessage());
					}
				}
			}

		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		}
		document.close();

	}

	private void competidoresPorCategoria() {

		Collections.sort(atletas, Corredor.getComparatorIdadeDescNomeCresc());

		System.out.println("\t\t\tGeral 12 KM\n");

		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getKm6() == 12 && !(atletas.get(i).getTempoH() == 0 && atletas.get(i).getTempoM() == 0
					&& atletas.get(i).getTempoS() == 0)) {
				System.out.printf(atletas.get(i).montada(cont, atletas, i) + "\n");
				cont++;
			}
		}

		System.out.println("\t\t\tGeral 6 KM\n");

		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getKm6() == 6 && !(atletas.get(i).getTempoH() == 0 && atletas.get(i).getTempoM() == 0
					&& atletas.get(i).getTempoS() == 0)) {
				System.out.printf("%d- Nº:%02d   NOME:%s\t%02d:%02d:%02d\n", cont, atletas.get(i).getNumero(),
						atletas.get(i).getNome(), atletas.get(i).getTempoH(), atletas.get(i).getTempoM(),
						atletas.get(i).getTempoS());
				cont++;
			}
		}
		System.out.println("\t\t\tGeral Feminina 12 KM\n");

		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getKm6() == 12 && atletas.get(i).getSexo().equals("F")
					&& !(atletas.get(i).getTempoH() == 0 && atletas.get(i).getTempoM() == 0
							&& atletas.get(i).getTempoS() == 0)) {
				System.out.printf("%d- Nº:%02d   NOME:%s\t%02d:%02d:%02d\n", cont, atletas.get(i).getNumero(),
						atletas.get(i).getNome(), atletas.get(i).getTempoH(), atletas.get(i).getTempoM(),
						atletas.get(i).getTempoS());
				cont++;
			}
		}
		System.out.println("\t\t\tGeral Femininia 6 KM\n");

		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getKm6() == 6 && atletas.get(i).getSexo().equals("F")
					&& !(atletas.get(i).getTempoH() == 0 && atletas.get(i).getTempoM() == 0
							&& atletas.get(i).getTempoS() == 0)) {
				System.out.printf("%d- Nº:%02d   NOME:%s\t%02d:%02d:%02d\n", cont, atletas.get(i).getNumero(),
						atletas.get(i).getNome(), atletas.get(i).getTempoH(), atletas.get(i).getTempoM(),
						atletas.get(i).getTempoS());
				cont++;
			}
		}
		System.out.println("\t\t\tGeral Masculina 12 KM\n");

		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getKm6() == 12 && atletas.get(i).getSexo().equals("M")
					&& !(atletas.get(i).getTempoH() == 0 && atletas.get(i).getTempoM() == 0
							&& atletas.get(i).getTempoS() == 0)) {
				System.out.printf("%d- Nº:%02d   NOME:%s\t%02d:%02d:%02d\n", cont, atletas.get(i).getNumero(),
						atletas.get(i).getNome(), atletas.get(i).getTempoH(), atletas.get(i).getTempoM(),
						atletas.get(i).getTempoS());
				cont++;
			}
		}
		System.out.println("\t\t\tGeral Masculina 6 KM\n");

		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getKm6() == 6 && atletas.get(i).getSexo().equals("M")
					&& !(atletas.get(i).getTempoH() == 0 && atletas.get(i).getTempoM() == 0
							&& atletas.get(i).getTempoS() == 0)) {
				System.out.printf("%d- Nº:%02d   NOME:%s\t%02d:%02d:%02d\n", cont, atletas.get(i).getNumero(),
						atletas.get(i).getNome(), atletas.get(i).getTempoH(), atletas.get(i).getTempoM(),
						atletas.get(i).getTempoS());
				cont++;
			}
		}
		System.out.println("\t\t\tMaster (+50 anos)KM\n");

		for (int i = 0, cont = 1; i < atletas.size(); i++) {
			if (atletas.get(i).getIdade() >= 50 && !(atletas.get(i).getTempoH() == 0 && atletas.get(i).getTempoM() == 0
					&& atletas.get(i).getTempoS() == 0)) {
				System.out.printf("%d- Nº:%02d   NOME:%s\t%02d:%02d:%02d\n", cont, atletas.get(i).getNumero(),
						atletas.get(i).getNome(), atletas.get(i).getTempoH(), atletas.get(i).getTempoM(),
						atletas.get(i).getTempoS());
				cont++;
			}
		}

	}

	public int adiciona(String nome, String numero, String quilo, String identi, String sexo) {

		if (procuraNumero(Integer.parseInt(numero)) != null) {
			return 1;
		}

		DAOCorredor daoC = new DAOCorredor();

		Corredor c = new Corredor(nome, Integer.parseInt(numero), Integer.parseInt(quilo), Integer.parseInt(identi),
				sexo);

		atletas.add(c);

		if (daoC.pesquisar(c.getNumero()) == null) {
			daoC.salvar(c);
		}
		return 0;
	}

	public void setAtletas() {
		DAOCorredor daoC = new DAOCorredor();
		atletas = daoC.recupera();
	}

	public boolean criaBanco() {
		DAOCorredor daoC = new DAOCorredor();
		return daoC.criaBanco();
	}

	public void carregar() {

		BufferedReader lerArq;
		String a = desktop + File.separator + pasta + File.separator + "Relatorios da Corrida";
		System.out.println(a);

		File diretorio = new File(desktop + File.separator + pasta);

		if (diretorio.mkdir()) {
			System.out.println("diretório criado com sucesso");
		} else {
			System.out.println("não foi possível criar o diretorio");
		}
		try {
			FileReader arq = new FileReader(desktop + File.separator + pasta + File.separator + "INPUT.txt");
			if (arq.ready()) {

				lerArq = new BufferedReader(arq);
				String linha = lerArq.readLine();
				inicio = Long.parseLong(linha);
			}
			arq.close();
		} catch (IOException e) {
			// System.out.printf("Erro na abertura do arquivo: %s.\n",
			// e.getMessage());
			salva();
		}

	}

	public void salva() {
		FileWriter arq = null;
		try {
			setAtletas();
		} catch (Exception e) {
			atletas = new ArrayList<Corredor>();
		}

		try {
			arq = new FileWriter(desktop + File.separator + pasta + File.separator + "INPUT.txt");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Arquivo Criado", "Relatorios", JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
		}
		PrintWriter gravarArq = new PrintWriter(arq);
		gravarArq.print(inicio);
		try {
			arq.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro Salvar arquivo", "Relatorios", JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
		}

	}

}
