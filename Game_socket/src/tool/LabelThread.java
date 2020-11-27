package tool;

import java.awt.*;

import javax.swing.JLabel;

public class LabelThread extends JLabel implements Runnable { 
	private int _start, _finish;
	private Thread myThread;
	private int _delayTime;
	
	public LabelThread(){ // Ŭ���� �⺻ ������
		_start = _finish = 0; // 
		myThread = null;//thread �̻���
		_delayTime = 1000;// ������ Ÿ�� 1��
	}
	
	public LabelThread(int start){ //Ÿ�̸� ���۽ð� �����ϸ� ������ ���� �⺻ �� ����
		_start = start;
		_finish = 0; // Ÿ�̸� ������ ���ڴ� 0
		myThread = null; //thread �̻���
		_delayTime = 1000; // ������ Ÿ�� 1��
	}
	

	public int getStart() { return _start;} // Ÿ�̸� ���۽ð� ���
	public int getFinish() { return _finish;} // Ÿ�̸� �������ð� ���
	public int getDelayTime() { return _delayTime;} // �����̽ð� ���
	public Thread getThread() { return myThread;} // Thread ���
	
	public void setStart(int start) {_start = start;} //Ÿ�̸� ���۽ð� ����
	public void setFinish(int finish) { _finish = finish;}// Ÿ�̸� �������ð� ����
	public void setNumber(int start, int finish) { _start = start; _finish = finish;}// Ÿ�̸� ���� �� �ð� ����
	public void setDelayTime(int time) {_delayTime = time; } // ������ Ÿ�� ����
	
	public void start() { // Thread ���� �Լ�
		if (myThread == null) { myThread = new Thread(this);} //Thread�� �������� ���� �� ����
		myThread.start(); /// Thread �۵�
	}
	
	@Override
	public void run() {
		for( int i=_start; i>=_finish;i--) { // Ÿ�̸� ���� �ð����� �� �ð� ���� �ݺ�
			setText(Integer.toString(i)); // �󺧿� ���� ���� ���� string���� ��ȯ
			try {
				myThread.sleep(_delayTime); // Thread �����ð� ����
			}catch(Exception e) {} // exception handle
		}
		
		setForeground(Color.red); // Ÿ�̸Ӱ� ������ �� �� ���ڻ� ����������
		
		for( int i=0; i<10;i++) { // �����Ÿ� 10ȸ
			setVisible(false); // ������ �ʰ�
			try {
				myThread.sleep(_delayTime-300);//Thread ����
			}catch(Exception e) {}
			setVisible(true); // ���̰�
			try {
				myThread.sleep(_delayTime-300);//Thread ����
			}catch(Exception e) {}
		}
	}
}
