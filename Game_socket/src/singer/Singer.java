package singer;

import tool.LabelThread;
import tool.ResizeImg;
import clientgame.ClientGame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;




public class Singer extends JPanel {
	private String singerList;
	private String[] pickSinger;
	private JLabel word, backGround, boardimg;
	private ImageIcon icon;
	private Image resizeimg;
	private JLabel board;
	private int index;
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
	
	public Singer(ClientGame c)  {
		
		cl = c;
		
		setBounds(50, 100, 950, 550);
		this.setLayout(null);
		
		singerList = "����ũ,�ҳ�ô�,BTS";
		pickSinger = singerList.split(",");
		
		// �Ʒ� �� �� ��Ʈ��ũ �������� �ڵ� 
		index = cl.getSingerIndex(); // ����� ���� ������ �� �޼����� Ŭ���̾�Ʈ�� ���� ���� ���� �ʱ�ȭ ��ư���� refresh
		
		ResizeImg bImg = new ResizeImg("images/sing_backimg3.png",750,550); // ũ������ 950 -> 750
		resizeimg = bImg.getResizeImage();
		icon = new ImageIcon(resizeimg);
		boardimg = new JLabel("",icon,SwingConstants.CENTER);
		boardimg.setBounds(0, 0, 750, 550); // ũ������ 950 -> 750
		 
		board = new JLabel();
		board.setBounds(250, 100, 600, 100);
		board.setBackground(Color.white);
		board.setLayout(null);
				
		word = new JLabel(pickSinger[index]);
		word.setBounds(150, 10, 500, 200);
		word.setFont(new Font("Ÿ����_����ǿ��� �׵θ�M", Font.BOLD, 100));
		word.setForeground(new Color(0,0,255));
		
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
	    
		chat.addActionListener(new sendMessage());
		btnReset.addActionListener(new ResetListener());
		
		// Ÿ�̸� ������ ����
		time = new LabelThread("10",10);
		time.start();
		
		this.add(word);
		this.add(board);
		this.add(boardimg);
		add(chat);	
		
		cl.setGuiSing(this);
		name = cl.getNickname();
	}
	
	public void init() {
		index = cl.getSingerIndex();
		word.setText(pickSinger[index]);
		
		//Ÿ�̸� ������ ����
		if(time.getThread().isAlive()){
			time.getThread().interrupt(); 
		}
		remove(time);
		time = new LabelThread("10",10);
		time.setBounds(400, 150, 500, 200);
		time.setFont(new Font("MD��ü", Font.BOLD, 150));
		add(time);
		add(board);
		add(boardimg);	
		revalidate();
		repaint();
		time.start();
	}
	
	public String getList() {
		return singerList;
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
			cl.sendMessage("[SINGERINIT]");
	    }
	}
	
	
}
