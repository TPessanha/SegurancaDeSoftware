package Exceptions;

import org.springframework.web.util.HtmlUtils;

import java.util.List;

public class MyException extends Exception {
	private String htmlMsg;
	
	public MyException(String msg, String htmlMsg) {
		super(msg);
		this.htmlMsg = "</p>" + HtmlUtils.htmlEscape(htmlMsg) + "</p>";
	}
	
	public MyException(String msg, List<String> htmlMsg) {
		super(msg);
		this.htmlMsg = "";
		for (String s : htmlMsg) {
			this.htmlMsg += "<p>" + HtmlUtils.htmlEscape(s) + "</p>";
		}
	}
	
	public MyException(String msg) {
		super(msg);
		this.htmlMsg = "<p>" + HtmlUtils.htmlEscape(msg) + "</p>";
	}
	
	public String getHtmlMsg() {
		return htmlMsg;
	}
}
