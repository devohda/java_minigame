package tool;

import java.io.*;
import javax.sound.sampled.*;

public class Sound { // 음악 관련 클래스
	AudioInputStream audioInputStream;
	Clip clip; 

	
	public Sound( String fileName ) { // 생성자, 파일경로와 이름을 지정해준다
		try {
	        audioInputStream = AudioSystem.getAudioInputStream(new File(fileName).getAbsoluteFile()); // 파일을 읽어 들인다
	        clip = AudioSystem.getClip(); // 클립이 사용할 수 있게 변경해준다.
	        clip.open(audioInputStream);
	    } catch(Exception ex) { // 파일이 없을 때 exception handle
	        System.out.println("Error with playing sound."); // 파일이 없다는 것을 알린다.
	        ex.printStackTrace();
	    }
	}
	
	
	public void On() { // 음악을 무한반복으로 재생하는 함수
        clip.start();
        clip.loop(clip.LOOP_CONTINUOUSLY);
	}
	public void Off() { // 음악 중지 함수
		clip.stop();
	}
	public void On_1() {// 음악 한번만 재생하는 함수
		clip.start();
	}
}