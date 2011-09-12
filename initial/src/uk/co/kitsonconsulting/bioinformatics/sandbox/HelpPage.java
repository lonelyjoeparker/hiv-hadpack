package uk.co.kitsonconsulting.bioinformatics.sandbox;


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

public class HelpPage {
	public HelpPage(){
		JFrame helpFrame = new JFrame("Help");
		JTextArea help = new JTextArea();
		help.setLineWrap(true);
		help.setWrapStyleWord(true);
		
		String helpContent = "Q: What is this tool for?\n" +
				"A: This is a tool to explore the sequence summary statistics including PNLG, substitutions, heterogeneity (divergence) within an alignment of nucleotide or amino acid data, using both position-specific measures and pairwise alignment-wide summary statistics.\n" +
				"\n" +
				"\nQ: How do I use it?\n" +
				"A: Select 'Add data' from the 'File' menu. The tool accepts only .fasta files at present.\n\n" +
				"Q: What are the measures and how do they differ?\n" +
				"A: Four measures are implemented:\n" +
				"\t- Shannon information entropy (by position)\n" +
				"\t- 1-(consensus frequency) (by position)\n" +
				"\t- 1-(heterozygosity index H) (by position)\n" +
				"\t- Hamming Distance (summed pairwise across alignment)\n" +
				"\n\t(Shannon, consensus and h index scores are also summed across the alignment (not pairwise))\n" +
				"\nQ: What is the output?\n" +
				"There are two views; the data viewer, which displays the output in text form which can be copied to a clipboard, and the graph viewer, which shows the value of the Shannon, consensus, and h index measures at each position in the alignment for all traces alignments loaded since the Shannon tool was started (or since the last 'clear all' command), as well as a graph summarising the relative values of each index in the last alignment that has been successfully loaded. These graphs copy straight to clipboard in some applicaions; others will need a third-party screen capture program (such as 'File>Grab>Selection...' in Preview on Mac OS\n" +
				"\n\nThere is no citation for this software - please contact me for citation, method, or user queries - joe@kitson-consulting.co.uk\n" +
				"I hope it's useful! :)\n" +
				"\n\nKnown bugs:\n" +
				"\t- It's a bit slow, especially with large alignments\n" +
				"\t- Alignments *must* have complete rows, that is, all sequences of the same length\n" +
				"\t- Uracil 'u' codes (e.g. viral RNA) are *not* currently supported.\n";
		
		help.setText(helpContent);
		
		JScrollPane scroller = new JScrollPane(help);
		helpFrame.setSize(600, 700);
		helpFrame.getContentPane().add(scroller);
		helpFrame.setLocation(100, 100);
		helpFrame.show();
	}
}
