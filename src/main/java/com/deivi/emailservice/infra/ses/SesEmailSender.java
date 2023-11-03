package com.deivi.emailservice.infra.ses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

import com.deivi.emailservice.adapters.EmailSenderGateway;

@Service
public class SesEmailSender implements EmailSenderGateway{

    private final AmazonSimpleEmailService amazonSimpleEmaiService;

    @Autowired
    public SesEmailSender(AmazonSimpleEmailService amazonSimpleEmaiService){
        this.amazonSimpleEmaiService = amazonSimpleEmaiService;
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        SendEmailRequest request = new SendEmailRequest()
            .withSource("massages.deivisson-noreply@outlook.com")
            .withDestination(new Destination().withToAddresses(to))
            .withMessage(new Message()
                .withSubject(new Content(subject))
                .withBody(new Body().withText(new Content(body)))
            );
    try{
        this.amazonSimpleEmaiService.sendEmail(request);
    } catch (AmazonServiceException exception) {
        throw new EmailRequestException("Failure while sending email");
    }
    }
    
}
