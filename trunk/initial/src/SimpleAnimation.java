import javax.swing.*;
import java.awt.*;

public class SimpleAnimation {

	/**
	 * @param args
	 */

	int x = 70;
	int y = 70;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimpleAnimation gui = new SimpleAnimation();
		gui.go();
	}

	public void go(){
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MyDrawPanel drawPanel = new MyDrawPanel();
		
		frame.getContentPane().add(drawPanel);
		frame.setSize(300, 300);
		frame.setVisible(true);
		
		for(int i = 0; i<130; i++){
			x++;
			y++;
			
			drawPanel.repaint();
			
			try{
				Thread.sleep(50);
			}catch(Exception ex){}
		}
	}
	
	class MyDrawPanel extends JPanel {
		public void paintComponent(Graphics g){
			g.setColor(Color.white);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			g.setColor(Color.green);
			g.fillOval(x, y, 40, 40);
			g.setColor(Color.MAGENTA);
			g.drawLine(10, 10, x, y);
			
			/*
			 * drawing a palette
			 */
			int tl = 200;
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
