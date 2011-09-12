package uk.co.kitsonconsulting.bioinformatics.sandbox;

public class StatsHolder {
	int[][] intData;
	int rows;
	int columns;
	
	public StatsHolder(int[][]input, int inputRows, int inputColumns){
		intData = input;
		rows = inputRows;
		columns = inputColumns;
	}
	
	public void transposeAndAnalyse(){
		// transpose the matrix and analyse it's contents
	}
	
	public float getMean(){
		return 0.0f;
	}
	
	public float getMedian(){
		return 0.0f;
	}
	
	public float[] getCIs(){
		float[] defaultCI = {0.0f,0.0f};
		return defaultCI;
	}
}
