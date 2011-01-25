package com.jbricx.ui.piano;

public class PianoNote {
	private Integer tone;
	private Integer duration;
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
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
}
