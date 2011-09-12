package uk.co.kitsonconsulting.bioinformatics.sandbox;

import java.io.*;
import java.util.ArrayList;

import neobio.alignment.CharSequence;
import neobio.alignment.IncompatibleScoringSchemeException;
import neobio.alignment.InvalidSequenceException;
import neobio.alignment.NeedlemanWunsch;
import neobio.alignment.BasicScoringScheme;
import neobio.alignment.PairwiseAlignment;

public class SimpleSequence {
	final String HXB2string = "MRVKEKYQHLWRWGWRWGTMLLGMLMICSATEKLWVTVYYGVPVWKEATTTLFCASDAKAYDTEVHNVWATHACVPTDPNPQEVVLVNVTENFNMWKNDMVEQMHEDIISLWDQSLKPCVKLTPLCVSLKCTDLKNDTNTNSSSGRMIMEKGEIKNCSFNISTSIRGKVQKEYAFFYKLDIIPIDNDTTSYKLTSCNTSVITQACPKVSFEPIPIHYCAPAGFAILKCNNKTFNGTGPCTNVSTVQCTHGIRPVVSTQLLLNGSLAEEEVVIRSVNFTDNAKTIIVQLNTSVEINCTRPNNNTRKRIRIQRGPGRAFVTIGKIGNMRQAHCNISRAKWNNTLKQIASKLREQFGNNKTIIFKQSSGGDPEIVTHSFNCGGEFFYCNSTQLFNSTWFNSTWSTEGSNNTEGSDTITLPCRIKQIINMWQKVGKAMYAPPISGQIRCSSNITGLLLTRDGGNSNNESEIFRPGGGDMRDNWRSELYKYKVVKIEPLGVAPTKAKRRVVQREKRAVGIGALFLGFLGAAGSTMGAASMTLTVQARQLLSGIVQQQNNLLRAIEAQQHLLQLTVWGIKQLQARILAVERYLKDQQLLGIWGCSGKLICTTAVPWNASWSNKSLEQIWNHTTWMEWDREINNYTSLIHSLIEESQNQQEKNEQELLELDKWASLWNWFNITNWLWYIKLFIMIVGGLVGLRIVFAVLSIVNRVRQGYSPLSFQTHLPTPRGPDRPEGIEEEGGERDRDRSIRLVNGSLALIWDDLRSLCLFSYHRLRDLLLIVTRIVELLGRRGWEALKYWWNLLQYWSQELKNSAVSLLNATAIAVAEGTDRVIEVVQGACRAIRHIPRRIRQGLERILL"; 
	final String rawSequence;
	final char[] rawCodons;
	CharSequence HXB2;
	CharSequence thisSequence;
	PairwiseAlignment pairwiseHXB2alignment;
	NeedlemanWunsch alignMe = new NeedlemanWunsch();
	BasicScoringScheme bss = new BasicScoringScheme(100,0,1);
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

	
	public SimpleSequence(String input){
		rawSequence = input;
		rawCodons = rawSequence.toCharArray();
		length = this.ungap(rawSequence).length();
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
