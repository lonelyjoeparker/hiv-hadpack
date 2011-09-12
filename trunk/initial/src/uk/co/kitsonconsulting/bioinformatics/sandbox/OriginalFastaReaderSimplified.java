package uk.co.kitsonconsulting.bioinformatics.sandbox;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.*;

public class OriginalFastaReaderSimplified{

	final private String filename;
	private File inputFile;
	private FileReader fr;
	private BufferedReader br;
	private int numberOfTaxa = 0;
	private int numberOfPositions = 0;
	private boolean isName;
	private ArrayList<String> data;
	private ArrayList<String> taxaNames;
	
	
	public OriginalFastaReaderSimplified(String filename){
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
			
			String dataLine = new String();
			while((line = br.readLine()) != null){
				Matcher gtMatch = greaterThan.matcher(line);

				if(gtMatch.matches()){
					taxaNames.add(line);
					if (dataLine.length() > 0) {
						data.add(dataLine);
					}
					dataLine = new String();
				} else {
					dataLine += line;
				}
				i++;
			}
			data.add(dataLine);
			numberOfPositions = dataLine.length();
			numberOfTaxa  = taxaNames.size();
			
		} catch (Exception ex){
			ex.printStackTrace();
		}
	
	}
	
	public ArrayList<String> getData(){
		return data;
	}
	
	public int[] getSize(){
		System.out.println("dimensions= " + numberOfTaxa + " " + numberOfPositions);
		int size[] = new int[] {numberOfTaxa, numberOfPositions};
		return size;
	}
	
	
	public ArrayList<String> getNames(){
		return taxaNames;
	}
}