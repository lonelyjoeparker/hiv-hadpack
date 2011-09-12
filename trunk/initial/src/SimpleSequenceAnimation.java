import javax.swing.*;
import java.awt.*;

public class SimpleSequenceAnimation {

	/**
	 * @param args
	 */

	int x = 70;
	int y = 70;
	Sequence thisSeq;
	public Sequence[] alignment = new Sequence[4];
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimpleSequenceAnimation gui = new SimpleSequenceAnimation();
		gui.go();
	}

	public void go(){
		String raw_sequence = "MRVTGTRKNC-PQC--C---------IWGI---L---GFWIIM-TC--NG-------N-----GMWVTVYYGVPVWRE-AKTTLFCASDAKGYVKEVHNVWATHACVPTDPSPQELVL-P-NVTENFNMWKNDMVDQMHEDIVSLWDQSLKPCVELTPLCVTLNCTNAAAINT-------------------------------------HKNKTNEMK-N-CSFYIDTAV-R-DKKKKENALFYTLDI--VQIDE---------------------------NDNSS-YR-------------------PIPIHFCAPAGYAILK-CNN-KTFN-GTGPCNNVSTV-QCTHGIRPVVS-TQLLLNGS---LAE---ED---IIIRSENLTNNAK--TIIVHLNES--VEIVCTRPNNNTRKGVR---IGPGQTFYATGD--I-IG-NIRQAYCNISRDKWNRTL-HRVSEKLK--EHFP---------NRTIIF--NQS-AGGD-LEIT-THSFNCMGEFFYCNTSGLFNGTYNKTSSN-------TNSTNSNNTEDTIT-LQCRIKQIINMWQKVGRAIYAPPIAGNITCKSNITGILLTRDGG--------------TNTSTTNETFRPGGGEMR-DNWRSELYKYKVVEINPLGIAPTEAKRRVV------QREKR-AV-GIIG-AL-FLGFLGAAGSTMGAASITLTVQARQLLSGIVQQQNNLLRAIEAQQHMLQLTVWGIKQLQARVLAVERYLRDQQLLGIWGCSGKLICTTNVPWNSSW--------------------S-----NK------TKNEIW-D-N--MTWMQWDREISNYTDTIYKLLVDSQNQQERNEKDLLALDKWNDLWNWFDITKWLWYIKIFIMIVGGLIGLRIIFAVLSIVNRVRQGYSPLSLQTLLPNPRG--PDRHGGIEEEGGEQDRDRYI-RLVSGFLPL-VWDDLRNLCLFSYHRLRDFILIAVRAVELLGRSSLRGLQRGWETLKYLGSLVQYWGLELKKSAINLFDTIAIGVAEGTDRILELIQNLC-RGIRNVPRRIRQGF-EAALQ*";
		Codon c1 = new Codon(20,20,'R',true,true,0.01f,false,0.5f,true,false,false,false);
		Codon c2 = new Codon(40,40,'K',true,true,0.01f,false,0.5f,true,false,false,false);
		Codon c3 = new Codon(50,50,'P',true,true,0.02f,false,0.5f,true,false,false,false);
		Codon c4 = new Codon(80,80,'N',true,false,0.4f,false,0.5f,true,false,false,false);
		Codon c5 = new Codon(90,99,'L',true,true,0.04f,false,0.5f,true,false,false,false);
		Codon c6 = new Codon(99,99,'E',false,false,0.8f,false,0.5f,true,false,false,false);
		Codon[] codons1 = {c1,c2,c3,c4};
		Codon[] codons2 = {c1,c2,c5,c6};
		Codon[] codons3 = {c1,c3,c5,c6};
		Codon[] codons4 = {c1,c2,c3,c4,c5,c6};
		Sequence s1 = new Sequence(codons1,"seq1",raw_sequence,100,0.0f,30,20,10,0,0,10,10);
		Sequence s2 = new Sequence(codons2,"seq2",raw_sequence,130,2.0f,32,22,12,0,0,11,10);
		Sequence s3 = new Sequence(codons3,"seq3",raw_sequence,150,3.1f,32,22,12,0,0,11,10);
		Sequence s4 = new Sequence(codons4,"seq4",raw_sequence,170,5.0f,32,22,12,0,0,11,10);

		alignment[0] = s1;
		alignment[1] = s2;
		alignment[2] = s3;
		alignment[3] = s4;

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MySequencePanel drawPanel = new MySequencePanel();
		
		frame.getContentPane().add(drawPanel);
		frame.setSize(300, 300);
		frame.setVisible(true);		
		
		for(Sequence aSeq:alignment){
			thisSeq = aSeq;
			System.out.println(thisSeq.getName());
			x++;
			y++;
			
			drawPanel.repaint();
			
			try{
				Thread.sleep(2000);
			}catch(Exception ex){}
		}
	}
	
	class MySequencePanel extends JPanel {
		public void paintComponent(Graphics g){
			int base_x = 50;
			int base_y = 100;
			
			/*
			 * clear the canvas
			 */
			g.setColor(Color.white);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			
			/*
			 * draw the sequence backbone
			 */
			g.setColor(Color.black);
			g.drawLine(base_x, base_y, base_x+thisSeq.getLength(), base_y);
			g.setFont(new Font("Arial", Font.PLAIN, 10));
			g.drawString(thisSeq.getName(),base_x,base_y+50);
			g.drawString(Float.toString(thisSeq.getTimepoint()),base_x, base_y+70);
			
			/*
			 * draw in the codons
			 */
			
			for(Codon aCodon:thisSeq.getCodons()){
				if(aCodon.isSub){
					if(aCodon.isPosSub){
						// a positively selected substitution
						int significance = Math.round((1-aCodon.getPPosSub())*10);
						System.out.println("a pos sub, p="+Integer.toString(significance));
						g.setColor(Color.red);
						g.fillOval(base_x+aCodon.getPos_align(), base_y-5, significance, significance);
					}else{
						// a neutrally-evolving site
						g.setColor(Color.cyan);
						g.fillOval(base_x+aCodon.getPos_align(),base_y-5, 10, 10);
					}
				}
			}
			/*
			 * drawing a palette
			 */
			int tl = 250;
			int tr = 0;
			int br = 10;
			int bl = 10;
			
			g.setColor(Color.black);
			g.fillRect(tl,tr,br,bl);
			g.setColor(Color.blue);
			tr += 10;
			g.fillRect(tl,tr,br,bl);
			g.setColor(Color.cyan);
			tr += 10;
			g.fillRect(tl,tr,br,bl);
			g.setColor(Color.darkGray);
			tr += 10;
			g.fillRect(tl,tr,br,bl);
			g.setColor(Color.gray);
			tr += 10;
			g.fillRect(tl,tr,br,bl);
			g.setColor(Color.green);
			tr += 10;
			g.fillRect(tl,tr,br,bl);
			g.setColor(Color.lightGray);
			tr += 10;
			g.fillRect(tl,tr,br,bl);
			g.setColor(Color.magenta);
			tr += 10;
			g.fillRect(tl,tr,br,bl);
			g.setColor(Color.orange);
			tr += 10;
			g.fillRect(tl,tr,br,bl);
			g.setColor(Color.pink);
			tr += 10;
			g.fillRect(tl,tr,br,bl);
			g.setColor(Color.red);
			tr += 10;
			g.fillRect(tl,tr,br,bl);
			g.setColor(Color.white);
			tr += 10;
			g.fillRect(tl,tr,br,bl);
			g.setColor(Color.yellow);
			tr += 10;
			g.fillRect(tl,tr,br,bl);
		}
	}
}
