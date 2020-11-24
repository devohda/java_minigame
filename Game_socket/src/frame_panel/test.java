package frame_panel;

import java.awt.*;
import javax.swing.*;
import java.util.Scanner;
import clientgame.ClientGame;

public class test {

	private static ClientGame client;
	private static String nickName;
	
	public test() {
		
		client = new ClientGame();
		
		// frame
		JFrame frame = new JFrame("ONE SHOT");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(1050,800));
		frame.setResizable(false); // 크기 조절 못하게 설정
				
		// main panel
		MainPanel Main = new MainPanel(client);
				
		// add panel to frame
		frame.getContentPane().add(Main);
		frame.pack();// 포장
		frame.setVisible(true); // 프레임 보이게 설정 
		
		//client = new ClientGame();
		client.setGui(this);
		client.setNickname(nickName);
		client.connet();
	}
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
        System.out.print("당신의 닉네임부터 설정하세요 : ");
        nickName = scanner.nextLine();
        scanner.close();
		      
		new test();	
		
	}

}