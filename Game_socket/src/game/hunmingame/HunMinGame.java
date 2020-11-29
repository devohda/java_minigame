
package game.hunmingame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.*;

import tool.Customfont;
import tool.LabelThread;
import frame_panel.GameSelector;
import frame_panel.MainPanel;
import socket.ClientGame;
import tool.ResizeImg;

public class HunMinGame extends JPanel {
	private String hangul, added; // ��~�� ���� ����, 2���� ���� ����
	private int[] index; // �迭 �ε���
	private char one, two; // �������� ���� ����
	private JLabel word, board; // �ʼ�, Ÿ�̸� ���� ��
	private ImageIcon icon; // ������
    private ResizeImg rImg;// ���������̹��� Ŭ���� 
	private Image resizeimg; // ������ �� ���� �̹���
	
	// Ÿ�̸�
	private LabelThread time; // Ÿ�̸�
	
	// ��Ʈ��ũ �������� ��ư
	private JButton btnReset, back; // �����, �ڷΰ��� ��ư
	
	// ä�ñ���
	private JTextArea chatArea;
    private JScrollPane scrollPane;
    private JTextField chat;
    private String nickName; 
    
    private String name; 
    private ClientGame cl;
    
    private GameSelector gameselector;
    private MainPanel main; 

    private Customfont makeFnt;
    private Font fnt;

	public HunMinGame(ClientGame c, GameSelector gs, MainPanel m) {
			
		gameselector = gs;
		main = m;
		cl = c;
		
		setBounds(50, 100, 950, 550);
		this.setLayout(null);
			
		ResizeImg bImg = new ResizeImg("images/board.jpg",750,550); // ũ������ 950 -> 750
		resizeimg = bImg.getResizeImage(); // �������� �̹��� ��ȯ
		icon = new ImageIcon(resizeimg); // �̹��� ������ȭ
		board = new JLabel("",icon,SwingConstants.CENTER); // �������� ������ �󺧷� ���ȭ�� ����
		board.setBounds(0, 0, 750, 550); // ũ������ 950 -> 750

		makeFnt = new Customfont();
		fnt = makeFnt.getCustomFont("font/���ƺ���ũ���Ľ�A.ttf", Font.PLAIN, 250);

		hangul = "����������������������������"; // ���� ����
		
		index = cl.getHunminIndex();  // ���� �ʱ�ȭ
		
		one = hangul.charAt(index[0]); // �� ~ ������ �ε��� ������ �̱�
		two = hangul.charAt(index[1]); // �� ~ ������ �ε��� ������ �̱�
		added = String.valueOf(one); // ���� ���� �ֱ�
		added = added + two; // ���� �ΰ� ���ϱ�
		
		word = new JLabel(added); // ������ �ʼ� �󺧿� �ֱ�
		word.setBounds(110, 70, 500, 300); // ũ������ 250 -> 125
		word.setFont(fnt); // ��Ʈ ����
		word.setForeground(Color.white); // ���� �� ����
		word.setHorizontalAlignment(SwingConstants.CENTER); // ��ġ ����
		word.setVerticalAlignment(SwingConstants.CENTER); // ��ġ ����
		board.add(word); // �� �����ֱ�
		
		// �ʱ�ȭ ��ư
		btnReset = new JButton("");
		btnReset.setBounds(600, 70, 100, 50); // ��ġ �� ������ ����
		add(btnReset); // ��ư ȭ�鿡 ���ϱ�
		
		back = new JButton(""); // �ڷΰ��� ��ư
		back.setBounds(40, 450, 100, 50); // ��ġ �� ������ ����
		add(back); // ��ư ȭ�鿡 ���ϱ�
		
		rImg = new ResizeImg("images/back.png", 50, 50); // ��ư �̹��� ������ �������ֱ�
        resizeimg = rImg.getResizeImage();
        icon = new ImageIcon(resizeimg);
        back.setIcon(icon);// ��ư�� ������ �������� �̹��� ����
        back.setBorderPainted(false);
        back.setContentAreaFilled(false);
        back.setFocusPainted(false);
		
        rImg = new ResizeImg("images/rotate.png", 50, 50);  // ��ư �̹��� ������ �������ֱ�
        resizeimg = rImg.getResizeImage();
        icon = new ImageIcon(resizeimg);
        btnReset.setIcon(icon);
        btnReset.setBorderPainted(false);// ��ư�� ������ �������� �̹��� ����
        btnReset.setContentAreaFilled(false);
        btnReset.setFocusPainted(false);
		
		// ä��â ����				
		chatArea = new JTextArea(); 
		scrollPane = new JScrollPane(chatArea);
		chatArea.setEditable(false); 
		scrollPane.setBounds(750, 0, 200, 525);
		add(scrollPane);		
		chat = new JTextField();
		chat.setBounds(750, 525, 200, 25);
	    
		// Ÿ�̸� ������ ����
		time = new LabelThread(10);
		time.start();
		
		chat.addActionListener(new sendMessage());
		btnReset.addActionListener(new ResetListener());
		back.addActionListener(new BackListener());
		
		add(board);
		add(chat);	
		
		cl.setGuiHunmin(this);
		name = cl.getNickname();
	} 	
	
	public void init() {
		index = cl.getHunminIndex();
		one = hangul.charAt(index[0]);	
		two = hangul.charAt(index[1]);
		added = String.valueOf(one);
		added = added + two;
		word.setText(added);
		
		// Ÿ�̸� ������ ����
		if(time.getThread().isAlive()){
			time.getThread().interrupt(); // Ÿ�̸� �۵� ���̸� ������
		}
		board.remove(time); //Ÿ�̸� ����
		time = new LabelThread(10); // Ÿ�̸� �� ����
		time.setBounds(340, 30, 500, 200); // ��ġ �� ������ ����
		fnt = makeFnt.getCustomFont("font/���ƺ���ũ���Ľ�A.ttf", Font.BOLD, 90);
		time.setFont(fnt); // ��Ʈ ����
		board.add(time); // Ÿ�̸� �����ֱ�
		revalidate();
		repaint();
		time.start(); // Ÿ�̸� ����
	}
	
	public void appendMsg(String msg) {
        chatArea.append(msg);
    }     
	
	private class sendMessage implements ActionListener {
		public void actionPerformed(ActionEvent e) {
	        String msg = name + " : " + chat.getText() + "\n";
	        cl.sendMessage(msg);
	        chat.setText("");
	    }
	}
	
	private class ResetListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			cl.sendMessage("[HUNMININIT]");
	    }
	}
	
	private class BackListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			main.createGameSelector();  // ���Ӽ����ͷ� ���ư�
        	main.addMainPanel(); // ���� �г��� �������
        	main.offMainIntro(); // ��Ʈ�� ���ֱ�
        	gameselector.offIntro(); // ��Ʈ�� ���ֱ�
        	gameselector.setgameNumZero(); // ��Ʈ�� ���� ��ȣ 0���� ����
	    }
	}
}
