

import javax.swing.*;
import java.awt.*;

public class CardPanel extends JPanel {

    private Card card;

    public CardPanel(Card c){
        setPreferredSize(new Dimension(100,100));
        card = c;
    }//constructor
    
    //카드 패널 GUI 만들기
    public void paintComponent(Graphics paint){

        super.paintComponent(paint); //패널 내 잔상 지우기

        // 도형 색깔 결정하기
        switch (card.getCardColor()){
            case RED : {
                paint.setColor(Color.RED);
            }
            case YELLOW : {
                paint.setColor(Color.YELLOW);
            }
            case GREEN : {
                paint.setColor(Color.GREEN);
            }
        }

        //도형 그리기
        switch (card.getShape()){
            case CIRCLE : { //원 그리기
                paint.fillOval(15,15,70,70);
            }
            case SQUARE : { // 사각형 그리기
                paint.fillRect(15,15,70,70);
            }
            case TRIANGLE : { //삼각형 그리기
                int x[] = { 15, 50, 85 };
                int y[] = { 85, 15, 85 };
                paint.fillPolygon( x, y, 3 );
            }
        }

        //카드 배경 칠하기
        switch (card.getBackgroundColor()){
            case WHITE : {
                super.setBackground(Color.WHITE);
            }
            case GRAY : {
                super.setBackground(Color.GRAY);
            }
            case BLACK : {
                super.setBackground(Color.BLACK);
            }
        }

    }//paintComponent()
}
