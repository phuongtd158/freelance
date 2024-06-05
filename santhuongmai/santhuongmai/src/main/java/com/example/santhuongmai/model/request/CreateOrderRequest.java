package com.example.santhuongmai.model.request;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {
    
    private String firstname;
    
    private String lastname;

    private String address;

    private String town;

    private String state;

    private String ward;

    private long postCode;

    private String email;

    private String phone;

    private String note;
    private long sale;
    private long totalPrice;
    private long shippingAmount;

    private String username;

    private long status;
    
    private int bank;

    private List<CreateOrderDetailRequest> orderDetails;
    private String orderCode;
    private String reason;
    private String urlImg;
}
