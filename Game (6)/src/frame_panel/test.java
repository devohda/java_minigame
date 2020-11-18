package frame_panel;

import java.awt.*;
import javax.swing.*;

public class test {

	public static void main(String[] args) {
		
		// frame
		JFrame frame = new JFrame("ONE SHOT");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(1050,800)); //크기 850*600
		frame.setResizable(false); // 크기 조절 못하게 설정
		
		// main panel
		MainPanel Main = new MainPanel();
		
		// add panel to frame
		frame.getContentPane().add(Main);
		frame.pack();// 포장
		frame.setVisible(true); // 프레임 보이게 설정 
	}

}