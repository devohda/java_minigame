package singer;

import tool.Customfont;
import tool.LabelThread;
import tool.ResizeImg;
import tool.Sound;
import tool.LabelThread;
import javax.swing.*;
import frame_panel.GameSelector;
import frame_panel.MainPanel;
import socket.ClientGame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Singer extends JPanel {
   private String singerList; // ���� ���
   private String[] pickSinger; // ���� ���� ������
   private JLabel word, boardimg, board; // ���� ���þ�, ��� �̹���, Ÿ�̸� �ֱ� ����
   private ImageIcon icon; // �̹��� ������
   private Image resizeimg; // �̹��� ������ �����뵵
   private int index;// �������� ","�� �������� ������� ����� �ε���
   private LabelThread time; // Ÿ�̸�
   private ResizeImg rImg; // �̹��� ������ �����뵵 
    private Image resizeImg;// �̹��� ������ �����뵵
   
   // ��Ʈ��ũ �������� ��ư
   private JButton btnReset, back; // ����, �ڷΰ��� ��ư
   
   // ä�ñ���
   private JTextArea chatArea;
   private JScrollPane scrollPane;
   private JTextField chat;
   private String nickName; 
       
   private String name; 
   private ClientGame cl;
   
   private GameSelector gameselector;
   private MainPanel main; 
   
   // �ܺ� ��Ʈ ����
   private Customfont makeFnt;
   private Font fnt;
   
   public Singer(ClientGame c, GameSelector gs, MainPanel m)  {
      
      gameselector = gs; // ���Ӽ����� ��ü
      main = m; // �����г� ��ü
      cl = c; // Ŭ���̾�Ʈ ��ü
      
      setBounds(50, 100, 950, 550);
      this.setLayout(null);
      
      singerList = "����ũ,�ҳ�ô�,  BTS,Ʈ���̽�,���座��, ������,  EXO,  ����, ������, ������,��θ���, ���Ͽ�,  ����,����ģ��, ������,��𷣵�, ���̵�,Ű��и�,�������ƽ�,  ����, �����, �鿹��,  �¿�,�����̰�, �ܳ���"; // ������ ����
      pickSinger = singerList.split(",");
      
      // �Ʒ� �� �� ��Ʈ��ũ �������� �ڵ� 
      index = cl.getSingerIndex(); // ����� ���� ������ �� �޼����� Ŭ���̾�Ʈ�� ���� ���� ���� �ʱ�ȭ ��ư���� refresh
      
      ResizeImg bImg = new ResizeImg("images/sing_backimg3.png",750,550); // ũ������ 950 -> 750
      resizeimg = bImg.getResizeImage(); // �缳���� �̹��� ��ȯ
      icon = new ImageIcon(resizeimg); // �̹��� �����ܿ��� ����� �� �ְ� ����
      boardimg = new JLabel("",icon,SwingConstants.CENTER); // j�󺧿� ������ ����
      boardimg.setBounds(0, 0, 750, 550); // ũ������ 950 -> 750
       
      board = new JLabel();
      board.setBounds(250, 100, 600, 100);
      board.setBackground(Color.white);
      board.setLayout(null);
            
      makeFnt = new Customfont();
      fnt = makeFnt.getCustomFont("font/Typo_HelloPOP_OutlineM.ttf", Font.PLAIN, 100);
      
      word = new JLabel(pickSinger[index]); //j�󺧿� ���� ���� ������ ����
      word.setBounds(120, 10, 520, 200); // ������ �� ��ġ ����
      word.setFont(fnt); // ���� ��Ÿ�� ����
      word.setForeground(new Color(0,0,255)); // ���ڻ� �Ķ������� ����
      
      btnReset = new JButton(""); // ����� ��ư
      btnReset.setBounds(600, 70, 100, 50); // ��ġ �� ������ ����
      add(btnReset); 
      
      back = new JButton(""); // �ڷΰ��� ��ư
      back.setBounds(40, 450, 100, 50); // ��ġ �� ������ ����
      add(back);
      
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
      back.addActionListener(new BackListener());
      
      // Ÿ�̸� ������ ����
      time = new LabelThread(10);
      time.start();
      
      rImg = new ResizeImg("images/back.png", 50, 50); // �ڷΰ��� ��ư ������ ������ �̹��� �߰� �� �׵θ� �����ֱ�
        resizeImg = rImg.getResizeImage();
        icon = new ImageIcon(resizeImg);
        back.setIcon(icon);
        back.setBorderPainted(false);
        back.setContentAreaFilled(false);
        back.setFocusPainted(false);
        
        rImg = new ResizeImg("images/rotate.png", 50, 50); // ����� ��ư ������ ������ �̹��� �߰� �� �׵θ� �����ֱ�
        resizeImg = rImg.getResizeImage();
        icon = new ImageIcon(resizeImg);
        btnReset.setIcon(icon);
        btnReset.setBorderPainted(false);
        btnReset.setContentAreaFilled(false);
        btnReset.setFocusPainted(false);
      
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
      time = new LabelThread(10);
      time.setBounds(300, 150, 500, 200);
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
   
   // ä��â�� �޼��� ���
   public void appendMsg(String msg) {
        chatArea.append(msg);
    }     
   
   // ������ �޼��� ����
   private class sendMessage implements ActionListener {
      public void actionPerformed(ActionEvent e) {
           String msg = name + " : " + chat.getText() + "\n";
           cl.sendMessage(msg);
           chat.setText("");
       }
   }
   
   // �ʱ�ȭ ������
   private class ResetListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         cl.sendMessage("[SINGERINIT]");
       }
   }
   
   // �ڷΰ��� ������
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