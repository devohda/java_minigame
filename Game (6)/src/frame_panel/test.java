package frame_panel;

import java.awt.*;
import javax.swing.*;

public class test {

	public static void main(String[] args) {
		
		// frame
		JFrame frame = new JFrame("ONE SHOT");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(1050,800)); //ũ�� 850*600
		frame.setResizable(false); // ũ�� ���� ���ϰ� ����
		
		// main panel
		MainPanel Main = new MainPanel();
		
		// add panel to frame
		frame.getContentPane().add(Main);
		frame.pack();// ����
		frame.setVisible(true); // ������ ���̰� ���� 
	}

}