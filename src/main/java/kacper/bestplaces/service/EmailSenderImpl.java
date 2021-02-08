package kacper.bestplaces.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service("emailSender")
@AllArgsConstructor
public class EmailSenderImpl implements EmailSender{

	private final JavaMailSender javaMailSender;
	
	@Override
	public void sendEmail(String to,String subject,String content)
	{
		MimeMessage mail=javaMailSender.createMimeMessage();
		try
		{
			MimeMessageHelper helper=new MimeMessageHelper(mail,true);
			helper.setTo(to);
			helper.setFrom("BestPlaces");
			helper.setSubject(subject);
			helper.setText(content, true);
		}catch(MessagingException e)
		{
			e.printStackTrace();
		}
		javaMailSender.send(mail);
	}
	
}
