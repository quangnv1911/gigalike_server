package com.gigalike.platform.util;

import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MailUtil {
    JavaMailSender mailSender;

    public void sendSimpleEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
            log.info("Simple email sent successfully to: {}", to);
        } catch (Exception e) {
            log.error("Failed to send simple email to {}: {}", to, e.getMessage());
            throw new RuntimeException("Email sending failed", e);
        }
    }

    public void sendHtmlEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            
            mailSender.send(message);
            log.info("HTML email sent successfully to: {}", to);
        } catch (Exception e) {
            log.error("Failed to send HTML email to {}: {}", to, e.getMessage());
            throw new RuntimeException("HTML email sending failed", e);
        }
    }

    public void sendWelcomeEmail(String to, String username) {
        String subject = "Welcome to Gigalike Shop";
        String content = String.format(
            "<h2>Welcome to Gigalike Shop, %s!</h2>" +
            "<p>Thank you for joining our platform for digital services.</p>" +
            "<p>You can now browse and purchase various digital services including:</p>" +
            "<ul>" +
            "<li>Social Media Accounts</li>" +
            "<li>Engagement Boost Services</li>" +
            "<li>ChatGPT Accounts</li>" +
            "<li>Web Design Services</li>" +
            "</ul>" +
            "<p>Best regards,<br>Gigalike Team</p>",
            username
        );
        sendHtmlEmail(to, subject, content);
    }

    public void sendOrderConfirmationEmail(String to, String orderNumber, double amount) {
        String subject = "Order Confirmation - " + orderNumber;
        String content = String.format(
            "<h2>Order Confirmation</h2>" +
            "<p>Thank you for your order!</p>" +
            "<p><strong>Order Number:</strong> %s</p>" +
            "<p><strong>Total Amount:</strong> $%.2f</p>" +
            "<p>We will process your order shortly.</p>" +
            "<p>Best regards,<br>Gigalike Team</p>",
            orderNumber, amount
        );
        sendHtmlEmail(to, subject, content);
    }

    public void sendPaymentSuccessEmail(String to, String transactionId, double amount) {
        String subject = "Payment Successful - Transaction " + transactionId;
        String content = String.format(
            "<h2>Payment Successful</h2>" +
            "<p>Your payment has been processed successfully!</p>" +
            "<p><strong>Transaction ID:</strong> %s</p>" +
            "<p><strong>Amount:</strong> $%.2f</p>" +
            "<p>Thank you for your business.</p>" +
            "<p>Best regards,<br>Gigalike Team</p>",
            transactionId, amount
        );
        sendHtmlEmail(to, subject, content);
    }
}
