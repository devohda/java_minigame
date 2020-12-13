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
        System.out.print("����� �г��Ӻ��� �����ϼ��� : "); // �̸� �Է� �ޱ�
        nickName = scanner.nextLine();
        scanner.close();
		client = new ClientGame();

		// frame
		JFrame frame = new JFrame("ONE SHOT");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false); // ũ�� ���� ���ϰ� ����

		// main panel
		MainPanel Main = new MainPanel(client);

		// add panel to frame
		frame.getContentPane().add(Main);
		frame.pack();// ����
		frame.setVisible(true); // ������ ���̰� ����

		new Test();


	}

}