package com.example.santhuongmai.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.example.santhuongmai.entity.Order;


@Component
public class EmailUtil {
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendOtpEmail(String email, String verificationCode) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
 
		// Tạo nội dung email chứa mã otp để xác thực tài khoản
        String emailContent = "<div>"
        		  + "<p>Mã OTP của bạn là: " + verificationCode + "</p>"
        		  + "<p>Gmail của bạn là: " + email + "</p>"
                + "<a href=\"http://localhost:8080/api/user/verify-account?email=" + email + "&otp=" + verificationCode + "\" target=\"_blank\">Xác minh OTP TẠI ĐÂY</a>"
                + "</div>";
        

        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Xác minh OTP để Đăng Nhập");
        mimeMessageHelper.setText(emailContent, true);

        javaMailSender.send(mimeMessage);
    }
	 public void sendOrderConfirmationEmail(String email, Order order) throws MessagingException {
	        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

	        // Tạo nội dung email chứa thông tin về đơn hàng
	        String emailContent = 
      
	        		"<div style=\"font-family: Arial, sans-serif;\">";
	        emailContent += "<p style=\"font-size: 16px;\">Xin chào bạn " + order.getLastname() + ",</p>";
	        emailContent += "<p style=\"font-size: 14px;\">Cảm ơn bạn đã đặt hàng từ chúng tôi. Đơn hàng của bạn có mã là: <strong>" + order.getOrderCode() + "</strong></p>";


	        emailContent += "</ul>";
	        emailContent += "<p style=\"font-size: 14px;\">Tổng số tiền là: <strong>" + order.getTotalPrice() + "</strong></p>";
	        emailContent += "<p style=\"font-size: 14px;\">Vui lòng kiểm tra thông tin đơn hàng của bạn.</p>";
	        emailContent += "</div>";


	        mimeMessageHelper.setTo(email);
	        mimeMessageHelper.setSubject("Xác nhận đơn hàng #" + order.getOrderCode());
	        mimeMessageHelper.setText(emailContent, true);

	        javaMailSender.send(mimeMessage);
	    }
}