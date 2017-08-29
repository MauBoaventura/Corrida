package circuitoPiauiense;
import java.util.Comparator;
import java.util.List;

public class Corredor {
	private String nome;
	private int numero;
	private int km6;
	private int idade;
	private String sexo;
	
	private int tempoH;
	private int tempoM;
	private int tempoS;

	private double totalDeSegundos;

	public Corredor(String nomeA, int n, int km, int idad, String sexoM, int tempoHora, int tempoMin, int tempoSeg,
			double totalDeMilis) {
		setNome(nomeA);
		numero = n;
		km6 = km;
		idade = idad;
		sexo = sexoM;
		tempoH = tempoHora;
		tempoM = tempoMin;
		tempoS = tempoSeg;
		totalDeSegundos = totalDeMilis;
	}

	public Corredor(String nomeA, int n, int km, int idad, String sexoM) {
		setNome(nomeA);
		numero = n;
		km6 = km;
		idade = idad;
		sexo = sexoM;
	}

	public Corredor() {
	}

	public int compara(Corredor o1, Corredor o2) {
		if (o1.totalDeSegundos == o2.totalDeSegundos)
			return 0;
		if (o1.totalDeSegundos < o2.totalDeSegundos)
			return 1;
		if (o1.totalDeSegundos > o2.totalDeSegundos)
			return -1;
		return 0;

	}

	public static Comparator<Corredor> getComparatorIdadeDescNomeCresc() {
		return new Comparator<Corredor>() {
			@Override
			public int compare(Corredor o1, Corredor o2) {
				int valor = o1.compara(o1, o2) * -1; 
				return valor;
			}

		};
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getKm6() {
		return km6;
	}

	public void setKm6(int km6) {
		this.km6 = km6;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public int getTempoH() {
		return tempoH;
	}

	public void setTempoH(int tempoH) {
		this.tempoH = tempoH;
	}

	public int getTempoM() {
		return tempoM;
	}

	public void setTempoM(int tempoM) {
		this.tempoM = tempoM;
	}

	public int getTempoS() {
		return tempoS;
	}

	public void setTempoS(int tempoS) {
		this.tempoS = tempoS;
	}

	public double getTotalDeSegundos() {
		return totalDeSegundos;
	}

	public void setTotalDeSegundos(double totalDeSegundos) {
		this.totalDeSegundos = totalDeSegundos;
	}

	public String montada(int colocacaoCorredor, List<Corredor> atletas, int indiceCorredor) {

		StringBuilder numeros = new StringBuilder();

		String tempo = String.format("%02d", colocacaoCorredor);
		numeros.append(tempo);
		numeros.append(" - Nº:");
		numeros.append(atletas.get(indiceCorredor).getNumero());
		numeros.append("   NOME:");
		numeros.append(atletas.get(indiceCorredor).getNome());
		numeros.append("      ");

		tempo = String.format("%02d", atletas.get(indiceCorredor).getTempoH());
		numeros.append(tempo);
		numeros.append(":");
		tempo = String.format("%02d", atletas.get(indiceCorredor).getTempoM());
		numeros.append(tempo);
		numeros.append(":");
		tempo = String.format("%02d", atletas.get(indiceCorredor).getTempoS());
		numeros.append(tempo);

		System.out.println(numeros.toString());

		return numeros.toString();
	}


}
