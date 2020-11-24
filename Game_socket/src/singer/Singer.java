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
	
	// 네트워크 구현위한 버튼
	private JButton btnReset;
	
	// 채팅구현
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
		
		singerList = "블랙핑크,소녀시대,BTS";
		pickSinger = singerList.split(",");
		
		// 아래 두 행 네트워크 구현위한 코드 
		index = cl.getSingerIndex(); // 여기는 아직 서버로 간 메세지가 클라이언트에 닿지 못한 상태 초기화 버튼으로 refresh
		
		ResizeImg bImg = new ResizeImg("images/sing_backimg3.png",750,550); // 크기조절 950 -> 750
		resizeimg = bImg.getResizeImage();
		icon = new ImageIcon(resizeimg);
		boardimg = new JLabel("",icon,SwingConstants.CENTER);
		boardimg.setBounds(0, 0, 750, 550); // 크기조절 950 -> 750
		 
		board = new JLabel();
		board.setBounds(250, 100, 600, 100);
		board.setBackground(Color.white);
		board.setLayout(null);
				
		word = new JLabel(pickSinger[index]);
		word.setBounds(150, 10, 500, 200);
		word.setFont(new Font("타이포_헬로피오피 테두리M", Font.BOLD, 100));
		word.setForeground(new Color(0,0,255));
		
		// 네트워크 구현위한 버튼
		btnReset = new JButton("초기화");
		btnReset.setBounds(50, 500, 100, 40);
		add(btnReset);
		
		// 채팅창 구현				
		chatArea = new JTextArea(); 
		scrollPane = new JScrollPane(chatArea);
		chatArea.setEditable(false); 
		scrollPane.setBounds(750, 0, 200, 525);
		add(scrollPane);		
		chat = new JTextField();
		chat.setBounds(750, 525, 200, 25);
	    
		chat.addActionListener(new sendMessage());
		btnReset.addActionListener(new ResetListener());
		
		// 타이머 쓰레드 생성
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
		
		//타이머 쓰레드 적용
		if(time.getThread().isAlive()){
			time.getThread().interrupt(); 
		}
		remove(time);
		time = new LabelThread("10",10);
		time.setBounds(400, 150, 500, 200);
		time.setFont(new Font("MD솔체", Font.BOLD, 150));
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
