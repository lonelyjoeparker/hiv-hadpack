
public class Codon {
	public int pos_align;
	public int pos_HXB2;
	public char aa;
	public boolean isSub;
	public boolean isPosSub;
	public float pPosSub;
	public boolean isEpistatic;
	public float pEpistatic;
	public boolean isPNLG;
	public boolean isGap;
	public boolean isVL;
	public boolean isCTL;

	public Codon(){
		/*
		 * Constructor for no-arg calls. Sets default parameters.
		 */
		pos_align = 1;
		pos_HXB2 = 1;
		aa = 'a';
		isSub = false;
		isPosSub = false;
		pPosSub = 0.0f;
		isEpistatic = false;
		pEpistatic = 0.0f;
		isPNLG = false;
		isGap = false;
		isVL = false;
		isCTL = false;
	}

	public Codon(char a){
		/*
		 * Constructor for char calls. Sets default parameters.
		 */
		pos_align = 1;
		pos_HXB2 = 1;
		aa = a;
		isSub = false;
		isPosSub = false;
		pPosSub = 0.0f;
		isEpistatic = false;
		pEpistatic = 0.0f;
		isPNLG = false;
		isGap = false;
		isVL = false;
		isCTL = false;
	}

	public Codon(int pos,int HXB2pos, char AA, boolean Sub, boolean Pos, float pPos, boolean Epi, float pEpi, boolean PNLG, boolean Gap, boolean VL, boolean CTL){
		/*
		 * Constructor for all-arg call. Sets given parameters (in global declaration order.)
		 */
		pos_align = pos;
		pos_HXB2 = HXB2pos;
		aa = AA;
		isSub = Sub;
		isPosSub = Pos;
		pPosSub = pPos;
		isEpistatic = Epi;
		pEpistatic = pEpi;
		isPNLG = PNLG;
		isGap = Gap;
		isVL = VL;
		isCTL = CTL;
	}

	/**
	 * @return the pos_align
	 */
	public int getPos_align() {
		return pos_align;
	}
	/**
	 * @return the pos_HXB2
	 */
	public int getPos_HXB2() {
		return pos_HXB2;
	}
	/**
	 * @return the aa
	 */
	public char getAa() {
		return aa;
	}
	/**
	 * @return the isSub
	 */
	public boolean isSub() {
		return isSub;
	}
	/**
	 * @return the isPosSub
	 */
	public boolean isPosSub() {
		return isPosSub;
	}
	/**
	 * @return the pPosSub
	 */
	public float getPPosSub() {
		return pPosSub;
	}
	/**
	 * @return the isEpistatic
	 */
	public boolean isEpistatic() {
		return isEpistatic;
	}
	/**
	 * @return the pEpistatic
	 */
	public float getPEpistatic() {
		return pEpistatic;
	}
	/**
	 * @return the isPNLG
	 */
	public boolean isPNLG() {
		return isPNLG;
	}
	/**
	 * @return the isGap
	 */
	public boolean isGap() {
		return isGap;
	}
	/**
	 * @return the isVL
	 */
	public boolean isVL() {
		return isVL;
	}
	/**
	 * @return the isCTL
	 */
	public boolean isCTL() {
		return isCTL;
	}
	/**
	 * @param pos_align the pos_align to set
	 */
	public void setPos_align(int pos_align) {
		this.pos_align = pos_align;
	}
	/**
	 * @param pos_HXB2 the pos_HXB2 to set
	 */
	public void setPos_HXB2(int pos_HXB2) {
		this.pos_HXB2 = pos_HXB2;
	}
	/**
	 * @param aa the aa to set
	 */
	public void setAa(char aa) {
		this.aa = aa;
	}
	/**
	 * @param isSub the isSub to set
	 */
	public void setSub(boolean isSub) {
		this.isSub = isSub;
	}
	/**
	 * @param isPosSub the isPosSub to set
	 */
	public void setPosSub(boolean isPosSub) {
		this.isPosSub = isPosSub;
	}
	/**
	 * @param posSub the pPosSub to set
	 */
	public void setPPosSub(float posSub) {
		pPosSub = posSub;
	}
	/**
	 * @param isEpistatic the isEpistatic to set
	 */
	public void setEpistatic(boolean isEpistatic) {
		this.isEpistatic = isEpistatic;
	}
	/**
	 * @param epistatic the pEpistatic to set
	 */
	public void setPEpistatic(float epistatic) {
		pEpistatic = epistatic;
	}
	/**
	 * @param isPNLG the isPNLG to set
	 */
	public void setPNLG(boolean isPNLG) {
		this.isPNLG = isPNLG;
	}
	/**
	 * @param isGap the isGap to set
	 */
	public void setGap(boolean isGap) {
		this.isGap = isGap;
	}
	/**
	 * @param isVL the isVL to set
	 */
	public void setVL(boolean isVL) {
		this.isVL = isVL;
	}
	/**
	 * @param isCTL the isCTL to set
	 */
	public void setCTL(boolean isCTL) {
		this.isCTL = isCTL;
	}
}
