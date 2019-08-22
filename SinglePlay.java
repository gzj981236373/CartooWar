package com.neuedu.util;

import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

/**
* @ClassName: SinglePlay
* @Description: 子弹音乐类
* @author IG-Jack five
* @date 2019年8月22日 上午10:44:18
*
*/
public class SinglePlay extends Thread {
	
	private String mp3Name;
	
	public SinglePlay() {
		// TODO Auto-generated constructor stub
	}
	
	public SinglePlay(String mp3Name) {
		
		this.mp3Name = mp3Name;
	}
	
	public void play(String mp3Name) {
		
		SinglePlay singlePlay = new SinglePlay(mp3Name);
		singlePlay.start();
	}
	
	
	
	
	@Override
	public void run() {
		InputStream resourceAsStream = SoundPlayer.class.getClassLoader().getResourceAsStream(mp3Name);
		try {
			
			AdvancedPlayer advancedPlayer = new AdvancedPlayer(resourceAsStream);
			advancedPlayer.play();
		} catch (JavaLayerException e) {
			e.printStackTrace();
		}
	}

}
