package combination;


import javax.swing.*;

import java.awt.*;

public class GameBoardPanel extends JPanel {
    public GameBoardPanel(Card boardInfo[]){
    	
    	//setBounds(525, 150, 500, 500);
        setBackground(Color.pink);
        setPreferredSize(new Dimension(500,500));
        setLayout(null); //레이아웃 배치 관리자 끄기

        int cnt = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                CardPanel cardPanel = new CardPanel(boardInfo[cnt++]);
                cardPanel.setBounds(150*(j) + 50, 150*(i) + 50, 100, 100);
                add(cardPanel);
            }
        }

    }//constructor
}
