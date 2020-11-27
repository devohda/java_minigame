package tool;

import java.awt.*;

import javax.swing.JLabel;

public class LabelThread extends JLabel implements Runnable { 
	private int _start, _finish;
	private Thread myThread;
	private int _delayTime;
	
	public LabelThread(){ // 클래수 기본 생성자
		_start = _finish = 0; // 
		myThread = null;//thread 미생성
		_delayTime = 1000;// 딜레이 타임 1초
	}
	
	public LabelThread(int start){ //타이머 시작시간 설정하며 나머지 변수 기본 값 설정
		_start = start;
		_finish = 0; // 타이머 마지막 숫자는 0
		myThread = null; //thread 미생성
		_delayTime = 1000; // 딜레이 타임 1초
	}
	

	public int getStart() { return _start;} // 타이머 시작시간 얻기
	public int getFinish() { return _finish;} // 타이머 마지막시간 얻기
	public int getDelayTime() { return _delayTime;} // 딜레이시간 얻기
	public Thread getThread() { return myThread;} // Thread 얻기
	
	public void setStart(int start) {_start = start;} //타이머 시작시간 설정
	public void setFinish(int finish) { _finish = finish;}// 타이머 마지막시간 설정
	public void setNumber(int start, int finish) { _start = start; _finish = finish;}// 타이머 시작 끝 시간 설정
	public void setDelayTime(int time) {_delayTime = time; } // 딜레이 타임 설정
	
	public void start() { // Thread 실행 함수
		if (myThread == null) { myThread = new Thread(this);} //Thread가 존재하지 않을 때 생성
		myThread.start(); /// Thread 작동
	}
	
	@Override
	public void run() {
		for( int i=_start; i>=_finish;i--) { // 타이머 시작 시간에서 끝 시간 까지 반복
			setText(Integer.toString(i)); // 라벨에 넣을 정수 값을 string으로 변환
			try {
				myThread.sleep(_delayTime); // Thread 지연시간 설정
			}catch(Exception e) {} // exception handle
		}
		
		setForeground(Color.red); // 타이머가 끝나고 난 후 글자색 빨간색으로
		
		for( int i=0; i<10;i++) { // 깜빡거림 10회
			setVisible(false); // 보이지 않게
			try {
				myThread.sleep(_delayTime-300);//Thread 지연
			}catch(Exception e) {}
			setVisible(true); // 보이게
			try {
				myThread.sleep(_delayTime-300);//Thread 지연
			}catch(Exception e) {}
		}
	}
}
