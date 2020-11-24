
package hunmingame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.*;

import tool.LabelThread;
import clientgame.ClientGame;
import tool.ResizeImg;

public class HunMinGame extends JPanel {
	private String hangul, added;
	private int[] index;
	private char one, two;
	private JLabel word, board;
	private ImageIcon icon;
	private Image resizeimg;
	
	// Ÿ�̸�
	private LabelThread time;
	
	// ��Ʈ��ũ �������� ��ư
	private JButton btnReset;
	
	// ä�ñ���
	private JTextArea chatArea;
    private JScrollPane scrollPane;
    private JTextField chat;
    private String nickName; 
    
    private String name; 
    private ClientGame cl;
    
	public HunMinGame(ClientGame c) {
		
		cl = c;
		
		setBounds(50, 100, 950, 550);
		this.setLayout(null);
			
		ResizeImg bImg = new ResizeImg("images/board.jpg",750,550); // ũ������ 950 -> 750
		resizeimg = bImg.getResizeImage();
		icon = new ImageIcon(resizeimg);
		board = new JLabel("",icon,SwingConstants.CENTER);
		board.setBounds(0, 0, 750, 550); // ũ������ 950 -> 750
		
		
		hangul = "����������������������������";	
		
		index = cl.getHunminIndex();  // ����� ���� ������ �� �޼����� Ŭ���̾�Ʈ�� ���� ���� ���� �ʱ�ȭ ��ư���� refresh
		
		one = hangul.charAt(index[0]);	
		two = hangul.charAt(index[1]);
		added = String.valueOf(one);
		added = added + two;
		
		word = new JLabel(added);
		word.setBounds(125, 150, 500, 300); // ũ������ 250 -> 125
		word.setFont(new Font("MD��ü", Font.BOLD, 200));
		word.setForeground(Color.white);
		word.setHorizontalAlignment(SwingConstants.CENTER);
		word.setVerticalAlignment(SwingConstants.CENTER);
		board.add(word);
		
		// ��Ʈ��ũ �������� ��ư
		btnReset = new JButton("�ʱ�ȭ");
		btnReset.setBounds(50, 500, 100, 40);
		add(btnReset);
		
		// ä��â ����				
		chatArea = new JTextArea(); 
		scrollPane = new JScrollPane(chatArea);
		chatArea.setEditable(false); 
		scrollPane.setBounds(750, 0, 200, 525);
		add(scrollPane);		
		chat = new JTextField();
		chat.setBounds(750, 525, 200, 25);
	    
		// Ÿ�̸� ������ ����
		time = new LabelThread("10",10);
		time.start();
		
		chat.addActionListener(new sendMessage());
		btnReset.addActionListener(new ResetListener());
		
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
			time.getThread().interrupt(); 
		}
		board.remove(time);
		time = new LabelThread("10",10);
		time.setBounds(400, 50, 500, 200);
		time.setFont(new Font("MD��ü", Font.BOLD, 150));
		board.add(time);
		revalidate();
		repaint();
		time.start();
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
	

}
