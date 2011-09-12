package uk.co.kitsonconsulting.sequenceTools;

import java.io.*;
import java.util.ArrayList;

import neobio.alignment.CharSequence;
import neobio.alignment.IncompatibleScoringSchemeException;
import neobio.alignment.InvalidSequenceException;
import neobio.alignment.NeedlemanWunsch;
import neobio.alignment.BasicScoringScheme;
import neobio.alignment.PairwiseAlignment;

public class AlignableSequence {
	final String name;
	final String HXB2string = "MRVKEKYQHLWRWGWRWGTMLLGMLMICSATEKLWVTVYYGVPVWKEATTTLFCASDAKAYDTEVHNVWATHACVPTDPNPQEVVLVNVTENFNMWKNDMVEQMHEDIISLWDQSLKPCVKLTPLCVSLKCTDLKNDTNTNSSSGRMIMEKGEIKNCSFNISTSIRGKVQKEYAFFYKLDIIPIDNDTTSYKLTSCNTSVITQACPKVSFEPIPIHYCAPAGFAILKCNNKTFNGTGPCTNVSTVQCTHGIRPVVSTQLLLNGSLAEEEVVIRSVNFTDNAKTIIVQLNTSVEINCTRPNNNTRKRIRIQRGPGRAFVTIGKIGNMRQAHCNISRAKWNNTLKQIASKLREQFGNNKTIIFKQSSGGDPEIVTHSFNCGGEFFYCNSTQLFNSTWFNSTWSTEGSNNTEGSDTITLPCRIKQIINMWQKVGKAMYAPPISGQIRCSSNITGLLLTRDGGNSNNESEIFRPGGGDMRDNWRSELYKYKVVKIEPLGVAPTKAKRRVVQREKRAVGIGALFLGFLGAAGSTMGAASMTLTVQARQLLSGIVQQQNNLLRAIEAQQHLLQLTVWGIKQLQARILAVERYLKDQQLLGIWGCSGKLICTTAVPWNASWSNKSLEQIWNHTTWMEWDREINNYTSLIHSLIEESQNQQEKNEQELLELDKWASLWNWFNITNWLWYIKLFIMIVGGLVGLRIVFAVLSIVNRVRQGYSPLSFQTHLPTPRGPDRPEGIEEEGGERDRDRSIRLVNGSLALIWDDLRSLCLFSYHRLRDLLLIVTRIVELLGRRGWEALKYWWNLLQYWSQELKNSAVSLLNATAIAVAEGTDRVIEVVQGACRAIRHIPRRIRQGLERILL"; 
	final int[] HXB2vlStart = {131,157,296,385,461};	//These are indexed to 1, NOT zero. correct later...
	final int[] HXB2vlEnd = {157,196,331,418,471};		//Thes were obtained by comparing the ungapped HXB2 above with LANL 2005 compendium
	final String rawSequence;
	final String alignedHXB2;
	final String alignedSequence;
	final char[] rawCodons;
	final char[] alignedCodons;
	final char[] alignedHXB2codons;
	CharSequence HXB2;
	CharSequence thisSequence;
	PairwiseAlignment pairwiseHXB2alignment;
	NeedlemanWunsch alignMe = new NeedlemanWunsch();
	BasicScoringScheme bss = new BasicScoringScheme(100,0,1);
	final int[] HXB2positions;
	int[] positivelySelectedPositions;
	int[] epistaticPositions;
	int[] CTLpositions;
	String[] CTLepitopes;

	int length = -1;
	int charge = -10000;
	int PNLG = -1;
	int substitutions;
	int selectedSubstitutions;
	int epistaticSubstitutions;
	int CTLpositionsMatches;

	SimpleSequence[] variableLoops = new SimpleSequence[5];
	int[] variableLoopsLength = {-1,-1,-1,-1,-1};
	int[] variableLoopsCharge = {-10000,-10000,-10000,-10000,-10000};
	int[] variableLoopsPNLG = {-1,-1,-1,-1,-1};
	
	public AlignableSequence(String aName, String input){
		name = aName;
		rawSequence = input;
		rawCodons = rawSequence.toCharArray();
		length = rawCodons.length;
		try{
			alignMe.loadSequences(new CharArrayReader(HXB2string.toCharArray()), new CharArrayReader(ungap(rawSequence).toCharArray()));
		}catch (IOException fx){
			fx.printStackTrace();
		}catch (InvalidSequenceException ix){
			ix.printStackTrace();
		}
		alignMe.setScoringScheme(bss);
		try{
			pairwiseHXB2alignment = alignMe.getPairwiseAlignment();
		}catch (IncompatibleScoringSchemeException ix){
			ix.printStackTrace();
			pairwiseHXB2alignment = null;
		}
		alignedHXB2 = pairwiseHXB2alignment.getGappedSequence1();
		alignedSequence = pairwiseHXB2alignment.getGappedSequence2();
		alignedHXB2codons = alignedHXB2.toCharArray();
		alignedCodons = alignedSequence.toCharArray();

		// read HXB2 sequence for HXB2 positions
		
		if(pairwiseHXB2alignment != null){
			HXB2positions = new int[alignedHXB2.toCharArray().length];
			int HXB2pos = 1;
			int HXB2positionsIndex = 0;
			for(char c:alignedHXB2.toCharArray()){
				if(c != '-'){
					HXB2positions[HXB2positionsIndex] = HXB2pos;
					HXB2pos++;
				}else{
					HXB2positions[HXB2positionsIndex] = -1;			// IMPORTANT: gaps have HXB2pos = -1 	
				}
				HXB2positionsIndex++;
			}
		}else{
			HXB2positions = null;
		}
		
		this.scoreVL();
	}
	
	public int getPNLG(){
		if(PNLG == -1){
			/*
			 * Determine n-linked glycosylation sites
			 * 
			 * pick codon
			 * pick codon + 2
			 * if codon == N and codon+2 == S or T, tag codon as PNLG and increment numPNLG
			 */
			if(rawCodons.length>3){
				for(int pos = 2; pos<rawCodons.length;pos++){
					if(rawCodons[pos-2] == 'N'){
						if(rawCodons[pos] =='S'){
							this.PNLG++;
						}else{
							if(rawCodons[pos] =='T'){
								this.PNLG++;
							}
						}
					}
				}
			}
		}
		return PNLG;
	}
	
	public int getCharge(){
		if(charge == -10000){
			charge = 0;
			for(char aChar:rawCodons){
				if(aChar == 'D' || aChar == 'E'){
					charge --;
				}else{
					if(aChar == 'R' || aChar == 'K' || aChar == 'H'){
						charge ++;
					}
				}
			}
		}
		return charge;
	}
	
	private String ungap(String gappedSequence){
		ArrayList<Character> holder = new ArrayList<Character>();
		for(char aChar:gappedSequence.toCharArray()){
			if(aChar !='-' && aChar !='*' && aChar != '?'){
				holder.add(aChar);
			}
		}
		char[] theUngaps = new char[holder.size()];
		for(int i=0;i<theUngaps.length;i++){
			theUngaps[i] = holder.get(i);
		}
		return new String(theUngaps);
	}
	
	private void scoreVL(){
		for(int VL = 0; VL<5; VL++){
			int startTarget = HXB2vlStart[VL];
			int endTarget = HXB2vlEnd[VL];
			String thisVL = "";
			ArrayList<Integer> theseHXpos = new ArrayList<Integer>();
			boolean inVL = false;
			for(int index=0;index<HXB2positions.length;index++){
				if(HXB2positions[index]>=startTarget && HXB2positions[index] != -1){
					inVL = true;
				}
				if(HXB2positions[index]>endTarget && HXB2positions[index] != -1){
					inVL = false;
				}
				if(inVL){
					// we are in a VL
//					System.out.println("in vl "+index+" "+alignedCodons[index]);
					thisVL = thisVL+alignedCodons[index];
					theseHXpos.add(HXB2positions[index]);
				}
			}
//			System.out.println(thisVL);
			String ungapVL = this.ungap(thisVL);
			SimpleSequence ss = new SimpleSequence(ungapVL);
			variableLoops[VL] = ss;
			variableLoopsLength[VL] = variableLoops[VL].length;
			variableLoopsCharge[VL] = variableLoops[VL].getCharge();
			variableLoopsPNLG[VL] = variableLoops[VL].getPNLG();
		}
	}
}
