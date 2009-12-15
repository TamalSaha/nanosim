package com.nanosim.client.mail;

/**
 * A simple structure containing the basic components of an email.
 */
public final class MailItem {
	/**
	 * The mail's id.
	 */
	public int mailId;
	
	/**
	 * The recipient's name. (toGroup)
	 */
	public String recipient;
	
	/**
	 * The sender's name. (fromGroup)
	 */
	public String sender;
	
	/**
	 * The email subject line.
	 */
	public String subject;
	
	/**
	 * The sender's email. (message)
	 */
	public String email;

	/**
	 * The email's sent date.
	 */
	//TODO fix date 
	//public Date date;

	/**
	 * Read flag.
	 */
	public boolean read;
	
	/**
	 * The email's HTML body.
	 */
	public String body;

	public MailItem(int mailId, String recipient, String sender,String subject, 
			String email, Boolean read, String body) {
		this.mailId = mailId;
		this.recipient = recipient;
		this.sender = sender;
		this.subject = subject;
		this.email = email;
		//this date = date;
		this.read = read;		
		this.body = body;
	}
}
