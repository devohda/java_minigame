

import javax.swing.JFrame;

public class Simulation {
    public static void main(String[] args) {
        JFrame frame = new JFrame("���հ���");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //����Ǹ� ���α׷� ���� ����

        Game game = new Game(); //���� ���� �г� ��ü ����
        game.init();

        frame.getContentPane().add(game); //frame�� �߰��ϱ�
        frame.pack();
        frame.setVisible(true);
    }
}

