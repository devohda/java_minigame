package frame_panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import tool.ResizeImg;

public class Help extends JFrame {
    private JPanel helpPanel;
    private JButton game1, game2, game3, game4, game5, back;
    private ButtonListener buttonL;


    private JLabel bgLab, catchlab, bottleLab, hunminLab, singerLab;
    private ImageIcon bgimage, catchimg, bottleimg, hunminimg, singerimg, backimage, hoverimage;
    private ResizeImg bImg, rImg;
    private Image resizeimg, back1;
    private hoverListener hover;

    Help() {
        setPreferredSize(new Dimension(700, 400));
        setLayout(null);
        setResizable(false);
        setLocation(175, 200); // 가로 1050 mainPanel에서 중앙에 나타나게

        buttonL = new ButtonListener();

        helpPanel = new JPanel();
        helpPanel.setBounds(0, 0, 700, 400);
        helpPanel.setLayout(null);

        //event
        hover = new hoverListener();

        //back
        rImg = new ResizeImg("images/helpback.png", 50, 50);
        back1 = rImg.getResizeImage();
        backimage = new ImageIcon(back1);

		// back hovering img
        rImg = new ResizeImg("images/hoverback.png", 50, 50);
        back1 = rImg.getResizeImage();
        hoverimage = new ImageIcon(back1);



        // 각 게임 설명을 담은 image

        game1 = new JButton("결합");
        game1.setBounds(250, 55, 200, 50);
        game1.addActionListener(buttonL);
        helpPanel.add(game1);

        game2 = new JButton("병뚜껑");
        game2.setBounds(250, 115, 200, 50);
        game2.addActionListener(buttonL);
        helpPanel.add(game2);

        game3 = new JButton("CatchCatch");
        game3.setBounds(250, 175, 200, 50);
        game3.addActionListener(buttonL);
        helpPanel.add(game3);

        game4 = new JButton("훈민정음");
        game4.setBounds(250, 235, 200, 50);
        game4.addActionListener(buttonL);
        helpPanel.add(game4);

        game5 = new JButton("전국노래자랑");
        game5.setBounds(250, 295, 200, 50);
        game5.addActionListener(buttonL);
        helpPanel.add(game5);


        // 돌아가기 버튼
        back = new JButton("");
        back.setIcon(backimage);
        back.setBounds(590, 290, 50, 50);
        back.addActionListener(buttonL);
        back.setVisible(false);
        back.setBorderPainted(false);
        back.setContentAreaFilled(false);
        back.setFocusPainted(false);
        back.addMouseListener(hover);
        helpPanel.add(back);

		// 배경화면
        bImg = new ResizeImg("images/helpimg.jpg", 700, 400);
        resizeimg = bImg.getResizeImage();
        bgimage = new ImageIcon(resizeimg);
        bgLab = new JLabel("", bgimage, SwingConstants.CENTER);
        bgLab.setBounds(0, 0, 700, 400);
        helpPanel.add(bgLab);

        //catch
        catchimg = new ImageIcon("images/helpimg1.png");
        catchlab = new JLabel("", catchimg, SwingConstants.CENTER);
        catchlab.setBounds(0, 0, 700, 400);
        catchlab.setVisible(false);
        helpPanel.add(catchlab);

        //bottle
        bottleimg = new ImageIcon("images/helpimg2.png");
        bottleLab = new JLabel("", bottleimg, SwingConstants.CENTER);
        bottleLab.setBounds(0, 0, 700, 400);
        bottleLab.setVisible(false);
        helpPanel.add(bottleLab);

        //hunmin
        hunminimg = new ImageIcon("images/helpimg3.png");
        hunminLab = new JLabel("", hunminimg, SwingConstants.CENTER);
        hunminLab.setBounds(0, 0, 700, 400);
        hunminLab.setVisible(false);
        helpPanel.add(hunminLab);

        //game.singer
        singerimg = new ImageIcon("images/helpimg4.png");
        singerLab = new JLabel("", singerimg, SwingConstants.CENTER);
        singerLab.setBounds(0, 0, 700, 400);
        singerLab.setVisible(false);
        helpPanel.add(singerLab);


        getContentPane().add(helpPanel);
        pack();
        setVisible(true);
    }


    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            Object object = event.getSource();
            if (object == game1 || object == game2 || object == game3 || object == game4 || object == game5) {
                game1.setVisible(false);
                game2.setVisible(false);
                game3.setVisible(false);
                game4.setVisible(false);
                game5.setVisible(false);
                back.setVisible(true);
            }

            if (object == game1) {

            }
            else if (object == game2) {
                bgLab.setVisible(false);
                bottleLab.setVisible(true);
            }
            else if (object == game3) {
                bgLab.setVisible(false);
                catchlab.setVisible(true);
            }
            else if (object == game4) {
                bgLab.setVisible(false);
                hunminLab.setVisible(true);
            }
            else if (object == game5) {
                bgLab.setVisible(false);
                singerLab.setVisible(true);
            }
            else if (object == back) {
                game1.setVisible(true);
                game2.setVisible(true);
                game3.setVisible(true);
                game4.setVisible(true);
                game5.setVisible(true);
                back.setVisible(false);

                catchlab.setVisible(false);
                bottleLab.setVisible(false);
                hunminLab.setVisible(false);
                singerLab.setVisible(false);


                bgLab.setVisible(true);
            }
        }
    }


    private class hoverListener implements MouseListener {

        @Override
        public void mouseEntered(MouseEvent e) {
            Object obj = e.getSource();
            if (obj == back) {
                back.setIcon(hoverimage);
                back.setBorderPainted(false);
                back.setContentAreaFilled(false);
                back.setFocusPainted(false);
            }

        }

        @Override
        public void mouseExited(MouseEvent e) {
            Object obj = e.getSource();
            if (obj == back) {
                back.setIcon(backimage);
                back.setBorderPainted(false);
                back.setContentAreaFilled(false);
                back.setFocusPainted(false);

            }

        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

    }

}