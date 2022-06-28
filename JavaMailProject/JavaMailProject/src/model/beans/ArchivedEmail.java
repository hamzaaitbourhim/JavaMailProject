package model.beans;

public class ArchivedEmail {
	
	private Long id;
	private String from;
	private String to;
	private String date;
	private String subject;
	private String body;
	
	public ArchivedEmail(Long id, String from, String to, String date, String subject, String body) {
		this.id = id;
		this.from = from;
		this.to = to;
		this.date = date;
		this.subject = subject;
		this.body = body;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
}
