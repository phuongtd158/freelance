package com.example.santhuongmai.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.example.santhuongmai.entity.OrderDetail;
import com.example.santhuongmai.entity.Order;


@Component
public class EmailUtil {
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendOtpEmail(String email, String verificationCode) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        String emailContent = "<div>"
        		  + "<p>Mã OTP cua ban la: " + verificationCode + "</p>"
        		  + "<p>Gmail cua ban la: " + email + "</p>"
                + "<a href=\"http://localhost:8080/api/user/verify-account?email=" + email + "&otp=" + verificationCode + "\" target=\"_blank\">Verify OTP HERE</a>"
                + "</div>";
        

        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Verify OTP Để LOGIN");
        mimeMessageHelper.setText(emailContent, true);

        javaMailSender.send(mimeMessage);
    }
	 public void sendOrderConfirmationEmail(String email, Order order) throws MessagingException {
	        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

	        // Tạo nội dung email chứa thông tin về đơn hàng
	        String emailContent = 
//	        		"<div>"
//	                + "<p>Xin chào,</p>"+ order.getLastname()
//	                + "<p>Cảm ơn bạn đã đặt hàng của chúng tôi. Đơn hàng của bạn có mã số: " + order.getOrderCode() + "</p>"
//	                + "<p>Chi tiết đơn hàng:</p>"
//	                + "<ul>";
//
////	        for (OrderDetail orderDetail : order.getOrderdetails()) {
////	            emailContent += "<li>Sản phẩm: " + orderDetail.getName() + "</li>"
////	                    + "<li>Số lượng: " + orderDetail.getSoluong() + "</li>"
////	                    + "<li>Giá: " + orderDetail.getPrice() + "</li>"
////	                    + "<li>Tổng: " + orderDetail.getSubTotal() + "</li>";
////	        }
//
//	        emailContent += "</ul>"
//	                + "<p>Tổng cộng: " + order.getTotalPrice() + "</p>"
//	                + "<p>Xin vui lòng kiểm tra thông tin đơn hàng của bạn.</p>"
//	                + "</div>";
	        		"<div style=\"font-family: Arial, sans-serif;\">";
	        emailContent += "<p style=\"font-size: 16px;\">Xin chào " + order.getLastname() + ",</p>";
	        emailContent += "<p style=\"font-size: 14px;\">Thank you for ordering from us. Your order has a code: <strong>" + order.getOrderCode() + "</strong></p>";
//	        emailContent += "<p style=\"font-size: 14px;\">Chi tiết đơn hàng:</p>";
//	        emailContent += "<ul style=\"font-size: 14px;\">";

//	        for (OrderDetail orderDetail : order.getOrderdetails()) {
//	            emailContent += "<li><strong>Sản phẩm:</strong> " + orderDetail.getName() + "</li>"
//	                    + "<li><strong>Số lượng:</strong> " + orderDetail.getSoluong() + "</li>"
//	                    + "<li><strong>Giá:</strong> " + orderDetail.getPrice() + "</li>"
//	                    + "<li><strong>Tổng:</strong> " + orderDetail.getSubTotal() + "</li>";
//	        }

	        emailContent += "</ul>";
	        emailContent += "<p style=\"font-size: 14px;\">Total Amount: <strong>" + order.getTotalPrice() + "</strong></p>";
	        emailContent += "<p style=\"font-size: 14px;\">Please check your order information.</p>";
	        emailContent += "</div>";


	        mimeMessageHelper.setTo(email);
	        mimeMessageHelper.setSubject("Xác nhận đơn hàng #" + order.getOrderCode());
	        mimeMessageHelper.setText(emailContent, true);

	        javaMailSender.send(mimeMessage);
	    }
}