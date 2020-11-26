package frame_panel;

import java.awt.*;
import javax.swing.*;

public class test {

	public static void main(String[] args) {
		
		// frame
		JFrame frame = new JFrame("ONE SHOT"); // 프레임 이름
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 끄면 정상 종료
		frame.setPreferredSize(new Dimension(1050,800)); //크기 850*600
		frame.setResizable(false); // 크기 조절 못하게 설정
		
		// main panel
		MainPanel Main = new MainPanel(); // 메인 패널 클래스 생성
		
		// add panel to frame
		frame.getContentPane().add(Main); // 메인 패널 추가
		frame.pack();// 포장
		frame.setVisible(true); // 프레임 보이게 설정 
	}

}