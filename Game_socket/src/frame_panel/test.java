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
		frame.setResizable(false); // ũ�� ���� ���ϰ� ����
				
		// main panel
		MainPanel Main = new MainPanel(client);
				
		// add panel to frame
		frame.getContentPane().add(Main);
		frame.pack();// ����
		frame.setVisible(true); // ������ ���̰� ���� 
		
		//client = new ClientGame();
		client.setGui(this);
		client.setNickname(nickName);
		client.connet();
	}
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
        System.out.print("����� �г��Ӻ��� �����ϼ��� : ");
        nickName = scanner.nextLine();
        scanner.close();
		      
		new test();	
		
	}

}