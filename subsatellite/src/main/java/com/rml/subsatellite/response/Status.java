package com.rml.subsatellite.response;

import com.rml.subsatellite.audio.PlaylistController;

public class Status {


	private PlaylistController player;
	
	public Status(PlaylistController player) {
		this.player=player;
	}
	
	public String toXMLResponse(){
		StringBuilder strb = new StringBuilder();
		
		strb.append("<satelliteStatus");
		strb.append(" currentIndex=\"").append(this.player.getCurrentIndex());
		strb.append("\" playing=\"").append(this.player.isPlaying());
		strb.append("\" gain=\"").append(this.player.getGain());
		strb.append("\" position=\"").append(this.player.getCurrentOffset());
		strb.append("\"/>");
		return strb.toString();
	}
}
