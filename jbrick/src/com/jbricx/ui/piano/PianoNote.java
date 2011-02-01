package com.jbricx.ui.piano;

public class PianoNote {
	private Integer tone;
	//16 for 1/1, 8 for 1/2, 4 for 1/4, 2 for 1/8, 1 for 1/16
	private Integer duration;
	private Integer noteTime = 10;
	private Integer waitTime = 12;
	PianoNote(Integer tone, Integer duration){
		this.tone = tone;
		this.duration = duration;
	}
	public Integer getTone() {
		return tone;
	}
	public void setTone(Integer tone) {
		this.tone = tone;
	}
	public void setDuration(Integer newDuration){
		duration = newDuration;
	}
	public Integer getNoteTime() {
		return duration * noteTime;
	}
	public Integer getWaitTime(){
		return duration * waitTime;
	}
}
