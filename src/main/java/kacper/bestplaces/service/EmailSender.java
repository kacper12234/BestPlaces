package kacper.bestplaces.service;

public interface EmailSender {
	void sendEmail(String to,String subject,String content);
}
