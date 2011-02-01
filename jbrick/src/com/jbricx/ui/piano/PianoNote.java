package com.jbricx.ui.piano;

public class PianoNote {
	private Integer tone;
	//16 for 1/1, 8 for 1/2, 4 for 1/4, 2 for 1/8, 1 for 1/16
	private Integer duration;
	private Integer noteTime = 10;
	private Integer waitTime = 12;
	PianoNote(Integer tone, Integer duration){
		this.tone = tone;
		this.setDuration(duration);
	}
	public Integer getTone() {
		return tone;
	}
	public void setTone(Integer tone) {
		this.tone = tone;
	}
	public void setDuration(Integer newDuration){
		switch(newDuration)
		{
			case 1: duration = 16; break;
			case 2: duration = 8; break;
			case 4: duration = 4; break;
			case 8: duration = 2; break;
			case 16: duration = 1; break;
		}
	}
	public Integer getNoteTime() {
		return duration * noteTime;
	}
	public Integer getWaitTime(){
		return duration * waitTime;
	}
}
