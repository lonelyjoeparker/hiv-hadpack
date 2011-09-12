package uk.co.kitsonconsulting.bioinformatics.sandbox;
import java.io.*;
import neobio.alignment.CharSequence;
import neobio.alignment.IncompatibleScoringSchemeException;
import neobio.alignment.InvalidSequenceException;
import neobio.alignment.NeedlemanWunsch;
import neobio.alignment.BasicScoringScheme;
import neobio.alignment.PairwiseAlignment;

public class SequenceHXB2ToolSandbox {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new SequenceHXB2ToolSandbox().go();
	}
	
	public void go(){
		String raw_sequence =      "MRVTGTRKNC-PQC--C---------IWGI---L---GFWIIM-TC--NG-------N-----GMWVTVYYGVPVWRE-AKTTLFCASDAKGYVKEVHNVWATHACVPTDPSPQELVL-P-NVTENFNMWKNDMVDQMHEDIVSLWDQSLKPCVELTPLCVTLNCTNAAAINT-------------------------------------HKNKTNEMK-N-CSFYIDTAV-R-DKKKKENALFYTLDI--VQIDE---------------------------NDNSS-YR-------------------PIPIHFCAPAGYAILK-CNN-KTFN-GTGPCNNVSTV-QCTHGIRPVVS-TQLLLNGS---LAE---ED---IIIRSENLTNNAK--TIIVHLNES--VEIVCTRPNNNTRKGVR---IGPGQTFYATGD--I-IG-NIRQAYCNISRDKWNRTL-HRVSEKLK--EHFP---------NRTIIF--NQS-AGGD-LEIT-THSFNCMGEFFYCNTSGLFNGTYNKTSSN-------TNSTNSNNTEDTIT-LQCRIKQIINMWQKVGRAIYAPPIAGNITCKSNITGILLTRDGG--------------TNTSTTNETFRPGGGEMR-DNWRSELYKYKVVEINPLGIAPTEAKRRVV------QREKR-AV-GIIG-AL-FLGFLGAAGSTMGAASITLTVQARQLLSGIVQQQNNLLRAIEAQQHMLQLTVWGIKQLQARVLAVERYLRDQQLLGIWGCSGKLICTTNVPWNSSW--------------------S-----NK------TKNEIW-D-N--MTWMQWDREISNYTDTIYKLLVDSQNQQERNEKDLLALDKWNDLWNWFDITKWLWYIKIFIMIVGGLIGLRIIFAVLSIVNRVRQGYSPLSLQTLLPNPRG--PDRHGGIEEEGGEQDRDRYI-RLVSGFLPL-VWDDLRNLCLFSYHRLRDFILIAVRAVELLGRSSLRGLQRGWETLKYLGSLVQYWGLELKKSAINLFDTIAIGVAEGTDRILELIQNLC-RGIRNVPRRIRQGF-EAALQ*";
		String raw_sequence3sub =  "AAATGTRKNC-PQC--C---------IWGI---L---GFWIIM-TC--NG-------N-----GMWVTVYYGVPVWRE-AKTTLFCASDAKGYVKEVHNVWATHACVPTDPSPQELVL-P-NVTENFNMWKNDMVDQMHEDIVSLWDQSLKPCVELTPLCVTLNCTNAAAINT-------------------------------------HKNKTNEMK-N-CSFYIDTAV-R-DKKKKENALFYTLDI--VQIDE---------------------------NDNSS-YR-------------------PIPIHFCAPAGYAILK-CNN-KTFN-GTGPCNNVSTV-QCTHGIRPVVS-TQLLLNGS---LAE---ED---IIIRSENLTNNAK--TIIVHLNES--VEIVCTRPNNNTRKGVR---IGPGQTFYATGD--I-IG-NIRQAYCNISRDKWNRTL-HRVSEKLK--EHFP---------NRTIIF--NQS-AGGD-LEIT-THSFNCMGEFFYCNTSGLFNGTYNKTSSN-------TNSTNSNNTEDTIT-LQCRIKQIINMWQKVGRAIYAPPIAGNITCKSNITGILLTRDGG--------------TNTSTTNETFRPGGGEMR-DNWRSELYKYKVVEINPLGIAPTEAKRRVV------QREKR-AV-GIIG-AL-FLGFLGAAGSTMGAASITLTVQARQLLSGIVQQQNNLLRAIEAQQHMLQLTVWGIKQLQARVLAVERYLRDQQLLGIWGCSGKLICTTNVPWNSSW--------------------S-----NK------TKNEIW-D-N--MTWMQWDREISNYTDTIYKLLVDSQNQQERNEKDLLALDKWNDLWNWFDITKWLWYIKIFIMIVGGLIGLRIIFAVLSIVNRVRQGYSPLSLQTLLPNPRG--PDRHGGIEEEGGEQDRDRYI-RLVSGFLPL-VWDDLRNLCLFSYHRLRDFILIAVRAVELLGRSSLRGLQRGWETLKYLGSLVQYWGLELKKSAINLFDTIAIGVAEGTDRILELIQNLC-RGIRNVPRRIRQGF-EAALQ*";
		String raw_sequence5sub =  "AAATGTRKNC-PQC--C---------IWGI---L---GFWIIM-TC--NG-------N-----GMWQQVYYGVPVWRE-AKTTLFCASDAKGYVKEVHNVWATHACVPTDPSPQELVL-P-NVTENFNMWKNDMVDQMHEDIVSLWDQSLKPCVELTPLCVTLNCTNAAAINT-------------------------------------HKNKTNEMK-N-CSFYIDTAV-R-DKKKKENALFYTLDI--VQIDE---------------------------NDNSS-YR-------------------PIPIHFCAPAGYAILK-CNN-KTFN-GTGPCNNVSTV-QCTHGIRPVVS-TQLLLNGS---LAE---ED---IIIRSENLTNNAK--TIIVHLNES--VEIVCTRPNNNTRKGVR---IGPGQTFYATGD--I-IG-NIRQAYCNISRDKWNRTL-HRVSEKLK--EHFP---------NRTIIF--NQS-AGGD-LEIT-THSFNCMGEFFYCNTSGLFNGTYNKTSSN-------TNSTNSNNTEDTIT-LQCRIKQIINMWQKVGRAIYAPPIAGNITCKSNITGILLTRDGG--------------TNTSTTNETFRPGGGEMR-DNWRSELYKYKVVEINPLGIAPTEAKRRVV------QREKR-AV-GIIG-AL-FLGFLGAAGSTMGAASITLTVQARQLLSGIVQQQNNLLRAIEAQQHMLQLTVWGIKQLQARVLAVERYLRDQQLLGIWGCSGKLICTTNVPWNSSW--------------------S-----NK------TKNEIW-D-N--MTWMQWDREISNYTDTIYKLLVDSQNQQERNEKDLLALDKWNDLWNWFDITKWLWYIKIFIMIVGGLIGLRIIFAVLSIVNRVRQGYSPLSLQTLLPNPRG--PDRHGGIEEEGGEQDRDRYI-RLVSGFLPL-VWDDLRNLCLFSYHRLRDFILIAVRAVELLGRSSLRGLQRGWETLKYLGSLVQYWGLELKKSAINLFDTIAIGVAEGTDRILELIQNLC-RGIRNVPRRIRQGF-EAALQ*";
		String raw_sequence10sub = "AAATGTRKNC-PQC--C---------IWGI---L---GFWIIM-TC--NG-------N----AGMWQQVYYGVPVWRE-AKTTLFCASDQQQQQKEVHNVWATHACVPTDPSPQELVL-P-NVTENFNMWKNDMVDQMHEDIVSLWDQSLKPCVELTPLCVTLNCTNAAAINT-------------------------------------HKNKTNEMK-N-CSFYIDTAV-R-DKKKKENALFYTLDI--VQIDE---------------------------NDNSS-YR-------------------PIPIHFCAPAGYAILK-CNN-KTFN-GTGPCNNVSTV-QCTHGIRPVVS-TQLLLNGS---LAE---ED---IIIRSENLTNNAK--TIIVHLNES--VEIVCTRPNNNTRKGVR---IGPGQTFYATGD--I-IG-NIRQAYCNISRDKWNRTL-HRVSEKLK--EHFP---------NRTIIF--NQS-AGGD-LEIT-THSFNCMGEFFYCNTSGLFNGTYNKTSSN-------TNSTNSNNTEDTIT-LQCRIKQIINMWQKVGRAIYAPPIAGNITCKSNITGILLTRDGG--------------TNTSTTNETFRPGGGEMR-DNWRSELYKYKVVEINPLGIAPTEAKRRVV------QREKR-AV-GIIG-AL-FLGFLGAAGSTMGAASITLTVQARQLLSGIVQQQNNLLRAIEAQQHMLQLTVWGIKQLQARVLAVERYLRDQQLLGIWGCSGKLICTTNVPWNSSW--------------------S-----NK------TKNEIW-D-N--MTWMQWDREISNYTDTIYKLLVDSQNQQERNEKDLLALDKWNDLWNWFDITKWLWYIKIFIMIVGGLIGLRIIFAVLSIVNRVRQGYSPLSLQTLLPNPRG--PDRHGGIEEEGGEQDRDRYI-RLVSGFLPL-VWDDLRNLCLFSYHRLRDFILIAVRAVELLGRSSLRGLQRGWETLKYLGSLVQYWGLELKKSAINLFDTIAIGVAEGTDRILELIQNLC-RGIRNVPRRIRQGF-EAALQ*";


//		FileReader fr;
//		BufferedReader br;
//		CharSequence HXB2;
//		CharSequence testSeq;
//		
//		try{
//			fr = new FileReader("/Users/gsjones/Desktop/test.fasta");
//			br = new BufferedReader(fr);
//			HXB2 = new CharSequence(br);
//		}catch (IOException fx){
//			fx.printStackTrace();
//		}catch (InvalidSequenceException ix){
//			ix.printStackTrace();
//		}
//		
//		try{
//			testSeq = new CharSequence(new BufferedReader(new FileReader("foo")));
//		}catch (IOException fx){
//			fx.printStackTrace();
//		}catch (InvalidSequenceException ix){
//			ix.printStackTrace();
//		}
		
		NeedlemanWunsch alignMe = new NeedlemanWunsch();
		PairwiseAlignment aligned;
		try{
			alignMe.loadSequences(new BufferedReader(new FileReader("/Users/gsjones/Desktop/test.fasta")), new BufferedReader(new FileReader("/Users/gsjones/Desktop/test2.fasta")));
		}catch (IOException fx){
			fx.printStackTrace();
		}catch (InvalidSequenceException ix){
			ix.printStackTrace();
		}
		alignMe.setScoringScheme(new BasicScoringScheme(100,0,1));
		try{
			aligned = alignMe.getPairwiseAlignment();
		}catch (IncompatibleScoringSchemeException ix){
			ix.printStackTrace();
			aligned = null;
		}
		
		System.out.println(aligned.getGappedSequence1());
		System.out.println(aligned.getGappedSequence2());

		CharArrayReader car = new CharArrayReader(raw_sequence.toCharArray());
		try{
			CharSequence testSeq = new CharSequence(car);
		}catch (IOException fx){
			fx.printStackTrace();
		}catch (InvalidSequenceException ix){
			ix.printStackTrace();
		}
		
	}

}
