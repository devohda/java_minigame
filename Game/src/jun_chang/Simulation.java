package jun_chang;

import javax.swing.JFrame;

import combination.Game;

public class Simulation {
    public static void main(String[] args) {
        JFrame frame = new JFrame("결합게임");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //종료되면 프로그램 같이 종료

        Game game = new Game(); //게임 실행 패널 객체 생성
        game.init();

        frame.getContentPane().add(game); //frame에 추가하기
        frame.pack();
        frame.setVisible(true);
    }
}

