package com.rml.subsatellite.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Response {

	private static final Logger LOG = LoggerFactory.getLogger(Response.class);

	private static final String XMLNS="xmlns=\"http://www.avsubapp.co.uk/subsatellite/restapi\"";
	private static final String API_VN="0.2";
	
	public String createOKResponse(String xml){
		StringBuilder strb = new StringBuilder();
		
		strb.append("<subsatellite-response " + XMLNS + " status=\"ok\" version=\""+ API_VN + "\">");
		strb.append(xml);
		strb.append("</subsatellite-response>");
		
		LOG.debug(strb.toString());
		return strb.toString();
		
	}
}
