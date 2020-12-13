package frame_panel;

import java.awt.*;
import javax.swing.*;

import socket.ClientGame;

import java.util.Scanner;

public class Test {

	private static ClientGame client;
	private static String nickName;
	
	public Test() {

		client.setGui(this);
		client.setNickname(nickName);
		client.connet();
		
	} // Test()
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
        System.out.print("당신의 닉네임부터 설정하세요 : "); // 이름 입력 받기
        nickName = scanner.nextLine();
        scanner.close();
		client = new ClientGame();

		// frame
		JFrame frame = new JFrame("ONE SHOT");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false); // 크기 조절 못하게 설정

		// main panel
		MainPanel Main = new MainPanel(client);

		// add panel to frame
		frame.getContentPane().add(Main);
		frame.pack();// 포장
		frame.setVisible(true); // 프레임 보이게 설정

		new Test();


	}

}