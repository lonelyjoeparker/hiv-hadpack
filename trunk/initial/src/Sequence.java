
public class Sequence {
	public String name;
	public String rawSequence;
	public int seq_length;
	public float timepoint;
	public int num_gaps;
	public int num_subs;
	public int num_pos;
	public int num_CTL;
	public int num_PNLG;
	public int len_VL1;
	public int len_VL2;
	public Codon[] codons;
	
	public Sequence(){
		/*
		 * no-arg constructor, set default values
		 */
		name = "unnamed";
		rawSequence = null;
		seq_length = 0;
		timepoint = 0.0f;
		num_gaps = 0;
		num_subs = 0;
		num_pos = 0;
		num_CTL = 0;
		num_PNLG = 0;
		len_VL1 = 0;
		len_VL2 = 0;
		codons = null;
	}
	
	public Sequence(Codon[] codonSet){
		/*
		 * constructor with codon set
		 */
		name = "unnamed";
		rawSequence = null;
		seq_length = 0;
		timepoint = 0.0f;
		num_gaps = 0;
		num_subs = 0;
		num_pos = 0;
		num_CTL = 0;
		num_PNLG = 0;
		len_VL1 = 0;
		len_VL2 = 0;
		codons = codonSet;
	}
	
	public Sequence(String Name, String RawSequence, int Length, float Timepoint, int numGaps, int numSubs, int numPos, int numCTL, int numPNLG, int lenVL1, int lenVL2){
		/*
		 * Complete constructor
		 */
		name = Name;
		rawSequence = RawSequence;
		seq_length = Length;
		timepoint = Timepoint;
		num_gaps = numGaps;
		num_subs = numSubs;
		num_pos = numPos;
		num_CTL = numCTL;
		num_PNLG = numPNLG;
		len_VL1 = lenVL1;
		len_VL2 = lenVL2;
	}
	
	public Sequence(Codon[] codonSet, String Name, String RawSequnce, int Length, float Timepoint, int numGaps, int numSubs, int numPos, int numCTL, int numPNLG, int lenVL1, int lenVL2){
		/*
		 * Complete constructor including codon set
		 */
		name = Name;
		rawSequence = null;
		seq_length = Length;
		timepoint = Timepoint;
		num_gaps = numGaps;
		num_subs = numSubs;
		num_pos = numPos;
		num_CTL = numCTL;
		num_PNLG = numPNLG;
		len_VL1 = lenVL1;
		len_VL2 = lenVL2;
		codons = codonSet;
	}

	public Sequence(String Name, String RawSequence, float Timepoint){
		/*
		 * Constructor for raw sequence data.
		 */
		name = Name;
		rawSequence = RawSequence;
		timepoint = Timepoint;
		seq_length = 0;
		num_gaps = 0;
		num_subs = 0;
		num_pos = 0;
		num_CTL = 0;
		num_PNLG = 0;
		len_VL1 = 0;
		len_VL2 = 0;
		codons = null;
		
		char[] codonChars = rawSequence.toCharArray();
		seq_length = codonChars.length;
	}
	
	public Sequence(String Name, String RawSequence){
		/*
		 * Constructor for raw sequence data (no temporal information).
		 */
		name = Name;
		rawSequence = RawSequence;
		seq_length = 0;
		timepoint = 0.0f;
		num_gaps = 0;
		num_subs = 0;
		num_pos = 0;
		num_CTL = 0;
		num_PNLG = 0;
		len_VL1 = 0;
		len_VL2 = 0;
		char[] codonChars = rawSequence.toCharArray();
		seq_length = codonChars.length;
		codons = new Codon[seq_length];
		for(int i = 0; i<seq_length;i++){
			codons[i] = new Codon(codonChars[i]);
		}
	}
	
	public void determineSubstitutions(String ancestralSequence){
		char[] queryCodons = ancestralSequence.toCharArray();
		int position_index = 0;
		for(char query:queryCodons){
			if(codons[position_index].aa != query){
				codons[position_index].isSub = true;
				num_subs ++;
			}
			position_index ++;
		}
	}

	public void determineGaps(){
		int position_index = 0;
		for(Codon aCodon:codons){
			if(aCodon.aa == '-'){
				codons[position_index].isGap = true;
				num_gaps ++;
			}
			position_index ++;
		}
	}
	
	public void determinePNLG(){
		/*
		 * Determine n-linked glycosylation sites
		 * 
		 * pick codon
		 * pick codon + 2
		 * if codon == N and codon+2 == S or T, tag codon as PNLG and increment numPNLG
		 */
		if(codons.length>3){
			for(int pos = 2; pos<codons.length;pos++){
				if(codons[pos-2].aa == 'N'){
					if(codons[pos].aa =='S'){
						codons[pos-2].isPNLG = true;
						this.num_PNLG++;
					}else{
						if(codons[pos].aa =='T'){
							codons[pos-2].isPNLG = true;
							this.num_PNLG++;
						}
					}
				}
			}
		}
	}

	/**
	 * @return the codons
	 */
	public Codon[] getCodons() {
		return codons;
	}
	/**
	 * @return the rawSequence
	 */
	public String getRawSequence() {
		return rawSequence;
	}

	/**
	 * @param rawSequence the rawSequence to set
	 */
	public void setRawSequence(String rawSequence) {
		this.rawSequence = rawSequence;
	}

	/**
	 * @param codons the codons to set
	 */
	public void setCodons(Codon[] codons) {
		this.codons = codons;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the length
	 */
	public int getLength() {
		return seq_length;
	}
	/**
	 * @return the timepoint
	 */
	public float getTimepoint() {
		return timepoint;
	}
	/**
	 * @return the num_gaps
	 */
	public int getNum_gaps() {
		return num_gaps;
	}
	/**
	 * @return the num_subs
	 */
	public int getNum_subs() {
		return num_subs;
	}
	/**
	 * @return the num_pos
	 */
	public int getNum_pos() {
		return num_pos;
	}
	/**
	 * @return the num_CTL
	 */
	public int getNum_CTL() {
		return num_CTL;
	}
	/**
	 * @return the num_PNLG
	 */
	public int getNum_PNLG() {
		return num_PNLG;
	}
	/**
	 * @return the len_VL1
	 */
	public int getLen_VL1() {
		return len_VL1;
	}
	/**
	 * @return the len_VL2
	 */
	public int getLen_VL2() {
		return len_VL2;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param length the length to set
	 */
	public void setLength(int length) {
		this.seq_length = length;
	}
	/**
	 * @param timepoint the timepoint to set
	 */
	public void setTimepoint(float timepoint) {
		this.timepoint = timepoint;
	}
	/**
	 * @param num_gaps the num_gaps to set
	 */
	public void setNum_gaps(int num_gaps) {
		this.num_gaps = num_gaps;
	}
	/**
	 * @param num_subs the num_subs to set
	 */
	public void setNum_subs(int num_subs) {
		this.num_subs = num_subs;
	}
	/**
	 * @param num_pos the num_pos to set
	 */
	public void setNum_pos(int num_pos) {
		this.num_pos = num_pos;
	}
	/**
	 * @param num_CTL the num_CTL to set
	 */
	public void setNum_CTL(int num_CTL) {
		this.num_CTL = num_CTL;
	}
	/**
	 * @param num_PNLG the num_PNLG to set
	 */
	public void setNum_PNLG(int num_PNLG) {
		this.num_PNLG = num_PNLG;
	}
	/**
	 * @param len_VL1 the len_VL1 to set
	 */
	public void setLen_VL1(int len_VL1) {
		this.len_VL1 = len_VL1;
	}
	/**
	 * @param len_VL2 the len_VL2 to set
	 */
	public void setLen_VL2(int len_VL2) {
		this.len_VL2 = len_VL2;
	}
}
