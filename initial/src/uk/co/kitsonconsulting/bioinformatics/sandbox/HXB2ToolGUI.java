package uk.co.kitsonconsulting.bioinformatics.sandbox;

import java.io.*;

import java.util.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;




public class HXB2ToolGUI {

	class aboutListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			System.out.println("about");
			AboutPage about = new AboutPage();
		}
	}


	class addDummyTraceListener implements ActionListener{
		public void actionPerformed (ActionEvent event){
			int points = 10;
			for(int i=0; i < points; i++){
			}
		}
	}


	class addTraceListener implements ActionListener{
	
			public void actionPerformed (ActionEvent event){
	
				int retval = newAlignmentChooser.showOpenDialog(null);
				if(retval == JFileChooser.APPROVE_OPTION){
					String reportUpdate = new String();
					newAlignmentFile = newAlignmentChooser.getSelectedFile();
					String newAlignmentFilename = newAlignmentFile.getAbsolutePath();
					newAlignmentChooser.setCurrentDirectory(newAlignmentFile.getParentFile());
					reportUpdate += "Processing file: " + newAlignmentFilename + "\n";
//					AlternativeFastaReader fR = new AlternativeFastaReader(newAlignmentFilename);
//					System.out.println("FR init has completed");
//					System.out.println("FR init has "+fR.getNumberOfTaxa()+" taxa");
//					alignment = new Sequence[fR.getNumberOfTaxa()];
					slider.setMaximum(10);
					slider.setMajorTickSpacing(Math.round(101/4));
					slider.setPaintTicks(true);
					slider.setPaintLabels(true);
					slider.addChangeListener(new sliderChangeListener());
					slider.setValue(0);
	
//					for(int i=0;i<fR.getNumberOfTaxa();i++){
//	//					System.out.println(fR.getTaxaNames().get(i)+"\t\t"+fR.getData().get(i));
//						Sequence newSequence = new Sequence(fR.getTaxaNames().get(i),fR.getData().get(i));
//						newSequence.determineGaps();
//						newSequence.determinePNLG();
//						if(i>0){
//							newSequence.determineSubstitutions(alignment[0].getRawSequence());
//						}
//						System.out.println("name "+newSequence.getName()+"\tgaps: "+newSequence.getNum_gaps()+"\tPNLG "+newSequence.getNum_PNLG()+"\tsubs "+newSequence.getNum_subs());
//						reportUpdate += "name\t"+newSequence.getName()+"\tgaps: "+newSequence.getNum_gaps()+"\tPNLG "+newSequence.getNum_PNLG()+"\tsubs "+newSequence.getNum_subs()+"\n";
//						alignment[i] = newSequence;
//	
//					}
	
					System.out.println("a new filename? " + newAlignmentFilename);
					reportUpdate += ("a new filename? " + newAlignmentFilename+"\n");
					OriginalFastaReaderSimplified fR = new OriginalFastaReaderSimplified(newAlignmentFilename);
					int [] dimensions = fR.getSize();
					System.out.println(dimensions[1]+" sites found.");
					reportUpdate += (dimensions[1]+" sites found.\n");
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
					reportUpdate += ("length\tgaps\tcharge\tPNLG\tVL1\tVL2\tVL3\tVL4\tVL5\tVC1\tVC2\tVC3\tVC4\tVC5\tVP1\tVP2\tVP3\tVP4\tVP5\n");
					int[][] fullDataset = new int[dimensions[0]][19];
					int lineCount = 0;
					for(String sequence:fR.getData()){
						int[]dataOut = new int[19];
						String ungappedSequence = ungap(sequence);
//						System.out.println(ungappedSequence);
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
							reportUpdate += (dat+"\t");
						}
						System.out.println();
						reportUpdate += "\n";
						fullDataset[lineCount] = dataOut;
						lineCount++;
					}
					StatsHolder stats = new StatsHolder(fullDataset,dimensions[0],19);
					// TODO getmean needs to return array of values, or even array of SH objects
					stats.getMean();	

//					reportUpdate += "Site-specific heterogeneity measures:\nAlignment position\tShannon entropies\tConsensus frequency\tClustal (1-h) score\n";
//					reportUpdate += "\nAlignment summary* statistics:\n";
//					reportUpdate += "\n*Caveat: summed site-heterogeneity measures are only comparable between alignments of the same dimensions (sites _and_ sequences)\n\n";
	
					reporter.setText(reportUpdate);
					msp.repaint();
				}
			}
		}


	class clearTracesListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			System.out.println("clear all traces");
		}
	}


	class helpListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			System.out.println("help");
	//		ShannonHelpPage help = new ShannonHelpPage();
		}
	}


	class MySequencePalette extends JPanel {
		public void paintComponent(Graphics g){
			int base_x = 50;
			int base_y = 100;
	
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


	class MySequencePanel extends JPanel {
		public void paintComponent(Graphics g){
			int base_x = 70;
			int base_y = 100;
			/*
			 * clear the canvas
			 */
			g.setColor(Color.white);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
	
	//		if(alignment.length>0){
	//			if(drawSeqLimit > 0){
	//				for(int limit=0;limit<drawSeqLimit;limit++){
	//					Sequence thisSeq = alignment[limit];
	//
	//					/*
	//					 * draw the sequence backbone
	//					 */
	//					g.setColor(Color.black);
	//					g.drawLine(base_x, base_y, base_x+thisSeq.getLength(), base_y);
	//					g.setFont(new Font("Arial", Font.PLAIN, 10));
	//					g.drawString(thisSeq.getName(),base_x-70,base_y);
	//					g.drawString(Float.toString(thisSeq.getTimepoint()),base_x-70, base_y+10);
	//
	//					/*
	//					 * draw in the codons
	//					 */
	//
	//					int xpos = 0;
	//					for(Codon aCodon:thisSeq.getCodons()){
	//						if(aCodon.isGap){
	//							// a gap
	//							g.setColor(Color.gray);
	//							g.fillOval(base_x+xpos,base_y, 5, 5);
	//						}
	//						if(aCodon.isPNLG){
	//							// PNLG
	//							g.setColor(Color.blue);
	//							g.fillOval(base_x+xpos, base_y+5, 5, 5);
	//						}	
	//						if(aCodon.isSub){
	//							// any sub
	//							g.setColor(Color.red);
	//							g.fillOval(base_x+xpos,base_y-5,5,5);
	//						}
	//						xpos++;
	//					}
	//					try{
	//						Thread.sleep(50);
	//					}catch(Exception ex){}
	//					base_y += 30;
	//
	//				}
	//			}else{
	//				for(Sequence thisSeq:alignment){
	//
	//					/*
	//					 * draw the sequence backbone
	//					 */
	//					g.setColor(Color.black);
	//					g.drawLine(base_x, base_y, base_x+thisSeq.getLength(), base_y);
	//					g.setFont(new Font("Arial", Font.PLAIN, 10));
	//					g.drawString(thisSeq.getName(),base_x-70,base_y);
	//					g.drawString(Float.toString(thisSeq.getTimepoint()),base_x-70, base_y+10);
	//
	//					/*
	//					 * draw in the codons
	//					 */
	//
	//					int xpos = 0;
	//					for(Codon aCodon:thisSeq.getCodons()){
	//						if(aCodon.isGap){
	//							// a gap
	//							g.setColor(Color.gray);
	//							g.fillOval(base_x+xpos,base_y, 5, 5);
	//						}
	//						if(aCodon.isPNLG){
	//							// PNLG
	//							g.setColor(Color.blue);
	//							g.fillOval(base_x+xpos, base_y+5, 5, 5);
	//						}	
	//						if(aCodon.isSub){
	//							// any sub
	//							g.setColor(Color.red);
	//							g.fillOval(base_x+xpos,base_y-5,5,5);
	//						}
	//						xpos++;
	//					}
	//					try{
	//						Thread.sleep(50);
	//					}catch(Exception ex){}
	//					base_y += 30;
	//				}
	//			}
	//		}
		}
	}


	class quitListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			System.out.println("quit");
			System.exit(0);
		}
	}


	class sliderChangeListener implements ChangeListener{
		public void stateChanged (ChangeEvent event){
			int slider_val = slider.getValue();
			System.out.println("slider changed, value "+ slider_val);
			drawSeqLimit = slider_val;
			try{
//				rowLabel.setText("Row: "+slider_val+", "+alignment[slider_val].getName());
			}catch (NullPointerException ex){
				ex.printStackTrace();
				rowLabel.setText("Row 0");
			}
			msp.repaint();
		}
	}


	class MyTableModel extends AbstractTableModel{
		
		 final String[] columnNames = {"Sequence",
				"Timepoint",
				"Length",
				"# gaps",
		"#PNLG","# subs"};
	
	
		 Object[][] data = {
				{"one", 0,
					0, 0, new Integer(5), new Boolean(false)},
					{"two", 0,0,0,new Integer(3), new Boolean(true)},
						{"three", 0,1,0,new Integer(2), new Boolean(false)},
							{"four", 0,1,1,new Integer(20), new Boolean(true)},
								{"five", 1,2,3,new Integer(10), new Boolean(false)}
		};
		
		
		/* (non-Javadoc)
	
		 * @see javax.swing.table.TableModel#getColumnCount()
		 */
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return 5;
		}
	
		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getRowCount()
		 */
		public int getRowCount() {
			// TODO Auto-generated method stub
			return 10;
		}
	
		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		public Object getValueAt(int row, int col) {
			// TODO Auto-generated method stub
			if(col == 0){
//				if(row <= alignment.length){
//					return alignment[row].getName();
//				}else{
//					return "sequenceName";
//				}
			}else{
				if(col == 1){
//					if(row <= alignment.length){
//						return alignment[row].getLength();
//					}else{
//						return "sequence_len";
//					}
				}else{
					if(col == 2){
//						if(row <= alignment.length){
//						//	alignment[row].determinePNLG();
//							return alignment[row].getNum_PNLG();
//						}else{
//							return "PNLG";
//						}
					}else{
						if(col == 3){
//							if(row <= alignment.length){
//							//	alignment[row].determinePNLG();
//								return alignment[row].getNum_subs();
//							}else{
//								return "Subs";
//							}
						}else{
							if(col == 4){
//								if(row <= alignment.length){
//								//	alignment[row].determinePNLG();
//									return alignment[row].getNum_gaps();
//								}else{
//									return "gaps";
//								}
							}else{
								return 0;
							}
						}
					}
				}
			}
			return "null retval";
		}
		
		public void setValueAt(Object value, int row, int col) {
		        data[row][col] = value;
		        fireTableCellUpdated(row, col);
		}
		
		public String getColumnName(int index){
			if(index == 0){
				return "name";
			}else{
				if(index == 1){
					return "length";
				}else{
					if(index == 2){
						return "PNLG";
					}else{
						if(index == 3){
							return "subs";
						}else{
							if(index == 4){
								return "gaps";
							}else{
								return "a name";
							}
						}
					}
				}
			}
		}
	}


	private JFrame frame;
	private JTextArea reporter = new JTextArea("Click 'add alignment' from the File menu to analyse data...");
	private JFileChooser newAlignmentChooser = new JFileChooser();
	private File newAlignmentFile;
	private JMenuBar menu = new JMenuBar();
	private JSlider slider;
	private int drawSeqLimit = 0;
	private JTextArea rowLabel = new JTextArea();
	private MySequencePanel msp;

	
	public void go(){
		JScrollPane reporterScroll = new JScrollPane(reporter);
		JMenu fileMenu = new JMenu("File");
		JMenuItem about = new JMenuItem("About");
		about.addActionListener(new aboutListener());
		JMenuItem aAdd = new JMenuItem("Add an alignment");
		aAdd.addActionListener(new addTraceListener());
		JMenuItem aClear = new JMenuItem("Clear all");
		aClear.addActionListener(new clearTracesListener());
		JMenuItem aDummy = new JMenuItem("Add dummy data (testing)");
		aDummy.addActionListener(new addDummyTraceListener());
		JMenuItem quit = new JMenuItem("Quit");
		quit.addActionListener(new quitListener());
		fileMenu.add(about);
		fileMenu.add(aAdd);
		fileMenu.add(aClear);
		fileMenu.add(aDummy);
		fileMenu.add(quit);

		JMenu helpMenu = new JMenu("Help");
		JMenuItem help = new JMenuItem("Help");
		help.addActionListener(new helpListener());
		helpMenu.add(help);

		menu.add(fileMenu);
		menu.add(helpMenu);

		frame = new JFrame("WIMM sequence summary tool v0.0.1");
		JTabbedPane viewPane = new JTabbedPane();
		viewPane.addTab("Data View", reporterScroll);
		JTabbedPane chartPane = new JTabbedPane();
		msp = new MySequencePanel();
		slider = new JSlider();
		slider.setBorder(BorderFactory.createTitledBorder("Timepoint"));
		slider.setMajorTickSpacing(20);
		slider.setPaintTicks(true);
		MySequencePalette palette = new MySequencePalette();
		MyTableModel mtl = new MyTableModel();
		JTable t2 = new JTable(mtl);
		JPanel wekapane = new JPanel(new BorderLayout());
		wekapane.add(t2.getTableHeader(), BorderLayout.NORTH);
		wekapane.add(t2, BorderLayout.CENTER);
		wekapane.add(new JButton("Send data to Weka"),BorderLayout.SOUTH);

		JScrollPane jsp = new JScrollPane(msp);
//		jsp.setAutoscrolls(true);
		jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//		jsp.setVisible(true);

		JPanel graphical = new JPanel(new BorderLayout());
		graphical.add(slider,BorderLayout.NORTH);
		graphical.add(rowLabel = new JTextArea("label"),BorderLayout.SOUTH);
		graphical.add(jsp,BorderLayout.CENTER);
		chartPane.addTab("Weka Data", wekapane);
		chartPane.addTab("Graphical View", graphical);
		chartPane.addTab("Palette", palette);
		viewPane.addTab("Sequence representation", chartPane);
		viewPane.addTab("Data", reporterScroll);
		JButton addTraceButton = new JButton("add trace");
		JButton addDummyTraceButton = new JButton("add dummy trace");
		JButton clearTracesButton = new JButton("clear traces");
		addTraceButton.addActionListener(new addTraceListener());
		addDummyTraceButton.addActionListener(new addDummyTraceListener());
		clearTracesButton.addActionListener(new clearTracesListener());
		frame.setJMenuBar(menu);
		frame.setSize(1200,600);
		frame.getContentPane().add(viewPane);
		frame.addWindowListener(
				new WindowAdapter(){
					public void windowClosing(WindowEvent e){
						System.exit(0);
					}
				}
		);
		frame.setVisible(true);

		////////////////
		//@TODO I need to add the listeners and, well, basically do the work here! x
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


