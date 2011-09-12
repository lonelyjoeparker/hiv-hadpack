package uk.co.kitsonconsulting.bioinformatics.sandbox;

import java.io.*;
import java.util.ArrayList;
import javax.swing.JFileChooser;


public class EnvelopeAnalysis {
	private File inputFile;
	private JFileChooser newAlignmentChooser = new JFileChooser();
	

	public void go(){
		int retval = newAlignmentChooser.showOpenDialog(null);
		if(retval == JFileChooser.APPROVE_OPTION){
			inputFile = newAlignmentChooser.getSelectedFile();
			String newAlignmentFilename = inputFile.getAbsolutePath();
			newAlignmentChooser.setCurrentDirectory(inputFile.getParentFile());
			System.out.println("a new filename? " + newAlignmentFilename);
			OriginalFastaReaderSimplified fR = new OriginalFastaReaderSimplified(newAlignmentFilename);
			int [] dimensions = fR.getSize();
			System.out.println(dimensions[1]+" sites found.");
/*
 * 			Data fields:
 * 
 * 			0		length
 * 			1		gaps (alignedLength-length)
 * 			2		charge
 * 			3		PNLG
 * 			4:8		VL lengths 1->5
 * 			9:13	VL charges 1->5
 * 			14:18	VL PNLG 1->5
 */

			System.out.println("length\tgaps\tcharge\tPNLG\tVL1\tVL2\tVL3\tVL4\tVL5\tVC1\tVC2\tVC3\tVC4\tVC5\tVP1\tVP2\tVP3\tVP4\tVP5");
			int[][] fullDataset = new int[dimensions[0]][19];
			int lineCount = 0;
			for(String sequence:fR.getData()){
				int[]dataOut = new int[19];
				String ungappedSequence = this.ungap(sequence);
//				System.out.println(ungappedSequence);
				AlignableSequence thisSeq = new AlignableSequence("no_name",ungappedSequence);
				dataOut[0]	= thisSeq.length;
				dataOut[1]	= (thisSeq.alignedSequence.length()-thisSeq.length);
				dataOut[2]	= thisSeq.getCharge();
				dataOut[3]	= thisSeq.getPNLG();
				for(int vl = 0;vl<5;vl++){
					dataOut[4+vl] = thisSeq.variableLoopsLength[vl];
					dataOut[9+vl] = thisSeq.variableLoopsCharge[vl];
					dataOut[14+vl] = thisSeq.variableLoopsPNLG[vl];
				}
/*
 *				System.out.println("HXB2  "+thisSeq.alignedHXB2);
 *				System.out.println("query "+thisSeq.alignedSequence);
 *				System.out.println("length: "+thisSeq.length+" charge: "+thisSeq.getCharge()+" PNLG: "+thisSeq.getPNLG()+" VL1 "+thisSeq.variableLoopsLength[0]+" VC1 "+thisSeq.variableLoopsCharge[0]+" VP1 "+thisSeq.variableLoopsPNLG[0]);				
 */
				for(int dat:dataOut){
					System.out.print(dat+"\t");
				}
				System.out.println();
				fullDataset[lineCount] = dataOut;
				lineCount++;
			}
			StatsHolder stats = new StatsHolder(fullDataset,dimensions[0],19);
			// TODO getmean needs to return array of values, or even array of SH objects
			stats.getMean();	
		}
	}
	
	public String ungap(String gappedSequence){
		ArrayList<Character> holder = new ArrayList<Character>();
		for(char aChar:gappedSequence.toCharArray()){
			if(aChar !='-' && aChar !='*'){
				holder.add(aChar);
			}
		}
		char[] theUngaps = new char[holder.size()];
		for(int i=0;i<theUngaps.length;i++){
			theUngaps[i] = holder.get(i);
		}
		return new String(theUngaps);
	}
}
