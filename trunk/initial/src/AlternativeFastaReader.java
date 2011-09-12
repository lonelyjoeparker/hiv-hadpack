import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.*;

public class AlternativeFastaReader{

	final private String filename;
	private File inputFile;
	private FileReader fr;
	private BufferedReader br;
	private int numberOfTaxa = 0;
	private int numberOfPositions = 0;
	private boolean isName;
	private ArrayList<String> data;
	private ArrayList<String> taxaNames;


	public AlternativeFastaReader(String filename){
		this.data = new ArrayList<String>();
		this.taxaNames = new ArrayList<String>();
		this.filename = filename;

		try {
			this.inputFile = new File(filename);
			this.fr = new FileReader(inputFile);
			this.br = new BufferedReader(fr);
			String line = null;
			Pattern greaterThan = Pattern.compile(">+.*");
			int i = 0;

			while((line = br.readLine()) != null){
				Matcher gtMatch = greaterThan.matcher(line);

				if(gtMatch.matches()){
					taxaNames.add(line);
					System.out.println(line);
				} else {
					if (line != null) {
						data.add(line);
						System.out.println("\t"+line);
					}
				}
				i++;
			}
			numberOfPositions = data.get(0).length();
			numberOfTaxa  = taxaNames.size();
			System.out.println("read in "+numberOfPositions+" bases and "+numberOfTaxa+" taxa");

		} catch (Exception ex){
			ex.printStackTrace();
		}

	}


	/**
	 * @return the inputFile
	 */
	public File getInputFile() {
		return inputFile;
	}


	/**
	 * @param inputFile the inputFile to set
	 */
	public void setInputFile(File inputFile) {
		this.inputFile = inputFile;
	}


	/**
	 * @return the numberOfTaxa
	 */
	public int getNumberOfTaxa() {
		return numberOfTaxa;
	}


	/**
	 * @param numberOfTaxa the numberOfTaxa to set
	 */
	public void setNumberOfTaxa(int numberOfTaxa) {
		this.numberOfTaxa = numberOfTaxa;
	}


	/**
	 * @return the numberOfPositions
	 */
	public int getNumberOfPositions() {
		return numberOfPositions;
	}


	/**
	 * @param numberOfPositions the numberOfPositions to set
	 */
	public void setNumberOfPositions(int numberOfPositions) {
		this.numberOfPositions = numberOfPositions;
	}


	/**
	 * @return the isName
	 */
	public boolean isName() {
		return isName;
	}


	/**
	 * @param isName the isName to set
	 */
	public void setName(boolean isName) {
		this.isName = isName;
	}


	/**
	 * @return the data
	 */
	public ArrayList<String> getData() {
		return data;
	}


	/**
	 * @param data the data to set
	 */
	public void setData(ArrayList<String> data) {
		this.data = data;
	}


	/**
	 * @return the taxaNames
	 */
	public ArrayList<String> getTaxaNames() {
		return taxaNames;
	}


	/**
	 * @param taxaNames the taxaNames to set
	 */
	public void setTaxaNames(ArrayList<String> taxaNames) {
		this.taxaNames = taxaNames;
	}
}

