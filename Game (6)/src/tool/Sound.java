package tool;

import java.io.*;
import javax.sound.sampled.*;

public class Sound { // 음악 관련 클래스
	AudioInputStream audioInputStream;
	Clip clip; 

	
	public Sound( String fileName ) { // 생성자
		try {
	        audioInputStream = AudioSystem.getAudioInputStream(new File(fileName).getAbsoluteFile());
	        clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	    } catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	        ex.printStackTrace();
	    }
	}
	
	
	public void On() { // 음악 재생 함수
        clip.start();
        clip.loop(clip.LOOP_CONTINUOUSLY);
	}
	public void Off() { // 음악 중지 함수
		clip.stop();
	}
	public void On_1() {
		clip.start();
	}
}