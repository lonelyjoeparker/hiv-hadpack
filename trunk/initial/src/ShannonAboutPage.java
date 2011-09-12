

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

public class ShannonAboutPage {
	public ShannonAboutPage(){
		JFrame aboutFrame = new JFrame("about");
		JTextArea about = new JTextArea();
		about.setLineWrap(true);
		about.setText("WIMM tool v0.0.1\nAlignment statistic summary tool\n\nBy Joe Parker, 2011\nWIMM, Oxford\n\nPlease email me if you need help:\njoe@kitson-consulting.co.uk\n\n(or see 'help')");
		
		JScrollPane scroller = new JScrollPane(about);
		aboutFrame.getContentPane().add(scroller);
		aboutFrame.setSize(300,300);
		aboutFrame.setLocation(50,50);
		aboutFrame.show();
	}
}
