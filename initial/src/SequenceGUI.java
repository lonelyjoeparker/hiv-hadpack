import java.io.File;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

//import uk.co.kitsonconsulting.bioinformatics.sandbox.FastaReader;

public class SequenceGUI {

	/**
	 * @param args
	 */
	private String filename = "dummy";
	private File newAlignmentFile;
	private JFrame frame;
	private JTextArea reporter = new JTextArea("Click 'add alignment' from the File menu to analyse data...");
	private JFileChooser newAlignmentChooser = new JFileChooser();
	private JMenuBar menu = new JMenuBar();
	private Sequence[] alignment = {};
	private MySequencePanel msp;
	private JSlider slider;
	private int drawSeqLimit = 0;
	private JTextArea rowLabel = new JTextArea();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("i'm running...");
		SequenceGUI sg = new SequenceGUI();
		sg.goGUI();
	}
	public void goGUI(){

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
	}

	class sliderChangeListener implements ChangeListener{
		public void stateChanged (ChangeEvent event){
			int slider_val = slider.getValue();
			System.out.println("slider changed, value "+ slider_val);
			drawSeqLimit = slider_val;
			try{
				rowLabel.setText("Row: "+slider_val+", "+alignment[slider_val].getName());
			}catch (NullPointerException ex){
				ex.printStackTrace();
				rowLabel.setText("Row 0");
			}
			msp.repaint();
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
				System.out.println("a new filename? " + newAlignmentFilename);
				reportUpdate += "Processing file: " + newAlignmentFilename + "\n";
				AlternativeFastaReader fR = new AlternativeFastaReader(newAlignmentFilename);
				System.out.println("FR init has completed");
				System.out.println("FR init has "+fR.getNumberOfTaxa()+" taxa");
				alignment = new Sequence[fR.getNumberOfTaxa()];
				slider.setMaximum(fR.getNumberOfTaxa());
				slider.setMajorTickSpacing(Math.round(fR.getNumberOfTaxa()/4));
				slider.setPaintTicks(true);
				slider.setPaintLabels(true);
				slider.addChangeListener(new sliderChangeListener());
				slider.setValue(0);

				for(int i=0;i<fR.getNumberOfTaxa();i++){
//					System.out.println(fR.getTaxaNames().get(i)+"\t\t"+fR.getData().get(i));
					Sequence newSequence = new Sequence(fR.getTaxaNames().get(i),fR.getData().get(i));
					newSequence.determineGaps();
					newSequence.determinePNLG();
					if(i>0){
						newSequence.determineSubstitutions(alignment[0].getRawSequence());
					}
					System.out.println("name "+newSequence.getName()+"\tgaps: "+newSequence.getNum_gaps()+"\tPNLG "+newSequence.getNum_PNLG()+"\tsubs "+newSequence.getNum_subs());
					reportUpdate += "name\t"+newSequence.getName()+"\tgaps: "+newSequence.getNum_gaps()+"\tPNLG "+newSequence.getNum_PNLG()+"\tsubs "+newSequence.getNum_subs()+"\n";
					alignment[i] = newSequence;

				}

				reportUpdate += "Site-specific heterogeneity measures:\nAlignment position\tShannon entropies\tConsensus frequency\tClustal (1-h) score\n";
				reportUpdate += "\nAlignment summary* statistics:\n";
				reportUpdate += "\n*Caveat: summed site-heterogeneity measures are only comparable between alignments of the same dimensions (sites _and_ sequences)\n\n";

				reporter.setText(reportUpdate);
				msp.repaint();
			}
		}
	}

	class addDummyTraceListener implements ActionListener{
		public void actionPerformed (ActionEvent event){
			int points = 10;
			for(int i=0; i < points; i++){
			}
		}
	}

	class clearTracesListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			System.out.println("clear all traces");
		}
	}

	class aboutListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			System.out.println("about");
			ShannonAboutPage about = new ShannonAboutPage();
		}
	}

	class quitListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			System.out.println("quit");
			System.exit(0);
		}
	}

	class helpListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			System.out.println("help");
			ShannonHelpPage help = new ShannonHelpPage();
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

			if(alignment.length>0){
				if(drawSeqLimit > 0){
					for(int limit=0;limit<drawSeqLimit;limit++){
						Sequence thisSeq = alignment[limit];

						/*
						 * draw the sequence backbone
						 */
						g.setColor(Color.black);
						g.drawLine(base_x, base_y, base_x+thisSeq.getLength(), base_y);
						g.setFont(new Font("Arial", Font.PLAIN, 10));
						g.drawString(thisSeq.getName(),base_x-70,base_y);
						g.drawString(Float.toString(thisSeq.getTimepoint()),base_x-70, base_y+10);

						/*
						 * draw in the codons
						 */

						int xpos = 0;
						for(Codon aCodon:thisSeq.getCodons()){
							if(aCodon.isGap){
								// a gap
								g.setColor(Color.gray);
								g.fillOval(base_x+xpos,base_y, 5, 5);
							}
							if(aCodon.isPNLG){
								// PNLG
								g.setColor(Color.blue);
								g.fillOval(base_x+xpos, base_y+5, 5, 5);
							}	
							if(aCodon.isSub){
								// any sub
								g.setColor(Color.red);
								g.fillOval(base_x+xpos,base_y-5,5,5);
							}
							xpos++;
						}
						try{
							Thread.sleep(50);
						}catch(Exception ex){}
						base_y += 30;

					}
				}else{
					for(Sequence thisSeq:alignment){

						/*
						 * draw the sequence backbone
						 */
						g.setColor(Color.black);
						g.drawLine(base_x, base_y, base_x+thisSeq.getLength(), base_y);
						g.setFont(new Font("Arial", Font.PLAIN, 10));
						g.drawString(thisSeq.getName(),base_x-70,base_y);
						g.drawString(Float.toString(thisSeq.getTimepoint()),base_x-70, base_y+10);

						/*
						 * draw in the codons
						 */

						int xpos = 0;
						for(Codon aCodon:thisSeq.getCodons()){
							if(aCodon.isGap){
								// a gap
								g.setColor(Color.gray);
								g.fillOval(base_x+xpos,base_y, 5, 5);
							}
							if(aCodon.isPNLG){
								// PNLG
								g.setColor(Color.blue);
								g.fillOval(base_x+xpos, base_y+5, 5, 5);
							}	
							if(aCodon.isSub){
								// any sub
								g.setColor(Color.red);
								g.fillOval(base_x+xpos,base_y-5,5,5);
							}
							xpos++;
						}
						try{
							Thread.sleep(50);
						}catch(Exception ex){}
						base_y += 30;
					}
				}
			}
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
				if(row <= alignment.length){
					return alignment[row].getName();
				}else{
					return "sequenceName";
				}
			}else{
				if(col == 1){
					if(row <= alignment.length){
						return alignment[row].getLength();
					}else{
						return "sequence_len";
					}
				}else{
					if(col == 2){
						if(row <= alignment.length){
						//	alignment[row].determinePNLG();
							return alignment[row].getNum_PNLG();
						}else{
							return "PNLG";
						}
					}else{
						if(col == 3){
							if(row <= alignment.length){
							//	alignment[row].determinePNLG();
								return alignment[row].getNum_subs();
							}else{
								return "Subs";
							}
						}else{
							if(col == 4){
								if(row <= alignment.length){
								//	alignment[row].determinePNLG();
									return alignment[row].getNum_gaps();
								}else{
									return "gaps";
								}
							}else{
								return 0;
							}
						}
					}
				}
			}
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
}