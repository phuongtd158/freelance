package com.example.santhuongmai.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.santhuongmai.repository.*;
import com.example.santhuongmai.util.Const;
import com.example.santhuongmai.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.santhuongmai.entity.Orderstatus;
import com.example.santhuongmai.entity.Blog;
import com.example.santhuongmai.entity.Category;
import com.example.santhuongmai.entity.Image;
import com.example.santhuongmai.entity.Order;
import com.example.santhuongmai.entity.OrderDetail;
import com.example.santhuongmai.entity.Product;
import com.example.santhuongmai.entity.User;
import com.example.santhuongmai.entity.Voucher;
import com.example.santhuongmai.exception.NotFoundException;
import com.example.santhuongmai.model.request.CreateOrderDetailRequest;
import com.example.santhuongmai.model.request.CreateOrderRequest;
import com.example.santhuongmai.model.request.CreateProductRequest;

import com.example.santhuongmai.service.OrderService;
import com.example.santhuongmai.util.EmailUtil;

import javax.mail.MessagingException;

import static com.example.santhuongmai.util.DataUtil.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private EmailUtil emailUtil;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderstatusRepository orderstatusRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public void placeOrder(CreateOrderRequest request) {
        // TODO Auto-generated method stub
        Order order = new Order();
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new NotFoundException("Not Found User With Username:" + request.getUsername()));
        order.setFirstname(request.getFirstname());
        order.setLastname(request.getLastname());
        order.setAddress(request.getAddress());
        order.setTown(request.getTown());
        order.setWard(request.getWard());
        order.setState(request.getState());
        order.setPostCode(request.getPostCode());
        order.setEmail(request.getEmail());
        order.setPhone(request.getPhone());
        order.setTotalPrice(request.getTotalPrice());
        order.setShippingAmount(request.getShippingAmount());
        order.setNote(request.getNote());
        order.setSale(request.getSale());
        order.setOrderCode(request.getOrderCode());
        order.setCreateAt(new Timestamp(System.currentTimeMillis()));
        order.setOrderState(0);
//        Orderstatus orderstatus = orderstatusRepository.findById(request.getStatus()).orElseThrow(()-> new NotFoundException("Not Found Category With Id: " + request.getStatus()));
//    	order.setOrderstatus(orderstatus); 
        //    lấy mặc định bằng 1 để deploy
        Long defaultStatusId = Long.valueOf(1); // Chuyển đổi giá trị int thành Long
        Orderstatus orderstatus = orderstatusRepository.findById(defaultStatusId).orElseThrow(() -> new NotFoundException("Not Found Category With Id: 1"));
        order.setOrderstatus(orderstatus);

        order.setBank(request.getBank());
        orderRepository.save(order);
        for (CreateOrderDetailRequest rq : request.getOrderDetails()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setName(rq.getName());
            orderDetail.setColor(rq.getColor());
            orderDetail.setSize(rq.getSize());
            orderDetail.setRoom(rq.getRoom());
            orderDetail.setPrice(rq.getPrice());
            orderDetail.setSoluong(rq.getSoluong());
            orderDetail.setSubTotal(rq.getPrice() * rq.getSoluong());
            orderDetail.setOrder(order);
            orderDetail.setProductId(rq.getProductId());
            orderDetailRepository.save(orderDetail);
        }
        order.setUser(user);
        orderRepository.save(order);
        try {
            // Gửi thông tin về đơn hàng vào email của khách hàng
            emailUtil.sendOrderConfirmationEmail(request.getEmail(), order);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send otp please try again");
        }
    }

    @Override
    public List<Order> getList() {
        return orderRepository.findAll(Sort.by("id").descending());
    }


    //    @Override
//    public List<Order> getListOrdercharts(int number) {
//        // TODO Auto-generated method stub
//        List<Order> list = orderRepository.getListOrdercharts(number);
//        return list;
//    }
    @Override
    public List<Order> getListOrdercharts(int number) {
        List<Object[]> list = orderRepository.getListOrdercharts(number);
        List<Order> orders = new ArrayList<>();
        for (Object[] objArray : list) {
            Order order = new Order();
            order.setMonthYear((String) objArray[0]);
            order.setTotalRevenue((BigDecimal) objArray[1]);
            orders.add(order);
        }
        return orders;
    }

    @Override
    public List<Orderstatus> getListstatus() {
        return orderstatusRepository.findAll(Sort.by("id").descending());
    }

    @Override
    public List<Order> getOrderByUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Not Found User With Username:" + username));

        List<Order> orders = orderRepository.getOrderByUser(user.getId());
        return orders;
    }

    //    @Override
//    public ResponseEntity<?> checkOrder(String orderCode) {
//        Order order = orderRepository.findByOrderCode(orderCode).orElseThrow();
////        order.setOrderState(1);
//        orderRepository.save(order);
//        return ResponseEntity.ok(new HashMap<>());
//    }
    @Override
    public Order checkOrder(String orderCode) {
        Order order = orderRepository.findByOrderCode(orderCode).orElseThrow(() -> new NotFoundException("Not Found Blog"));
        return order;
    }

    @Override
    public ResponseEntity<?> removeOrder(String code) {
        return ResponseEntity.ok(new HashMap<>());
    }

    @Override
    public Order updateOrder(long id, CreateOrderRequest request) {

        // TODO Auto-generated method stub
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Product With Id: " + id));

        Orderstatus orderstatus = orderstatusRepository.findById(request.getStatus()).orElseThrow(() -> new NotFoundException("Not Found Category With Id: " + request.getStatus()));
        if (orderstatus.getCode().equals(Const.ORDER_STATUS.ON_DELIVERY)) {
            Set<OrderDetail> orderDetails = order.getOrderdetails();
            if (!isNullOrEmpty(orderDetails)) {
                for (OrderDetail orderDetail : orderDetails) {
                    Product product = productRepository.findById(orderDetail.getProductId()).orElse(null);
                    if (product != null) {
                        product.setQuantity(product.getQuantity() - orderDetail.getSoluong());
                        product.setQuantitybuy(product.getQuantitybuy() + orderDetail.getSoluong());
                        productRepository.save(product);
                    }
                }
            }
        }

        order.setOrderstatus(orderstatus);
        orderRepository.save(order);

        return order;
    }

    @Override
    public Order updateOrderstatus(long id, CreateOrderRequest request) {

        // TODO Auto-generated method stub
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Product With Id: " + id));
        Orderstatus orderstatus = orderstatusRepository.findById(4L).orElseThrow(() -> new NotFoundException("Not Found Category With Id: " + request.getStatus()));
        order.setOrderstatus(orderstatus);
        orderRepository.save(order);

        return order;
    }

    @Override
    public Long getMaxOrderId() {
        return orderRepository.getMaxOrderId();
    }

    @Override
    public List<Order> getListOrder(long id) {
        List<Order> list = orderRepository.getListOrder(id);
        return list;
    }
}
