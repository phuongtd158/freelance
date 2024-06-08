package com.example.santhuongmai.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

import com.example.santhuongmai.repository.*;
import com.example.santhuongmai.util.Const;
import com.example.santhuongmai.util.DataUtil;
import lombok.extern.slf4j.Slf4j;
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
import static com.example.santhuongmai.util.Const.ORDER_STATUS.*;

@Service
@Slf4j
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

    /***
      Hàm sử dụng để tạo Order
     */
    @Override
    public void placeOrder(CreateOrderRequest request) {
        // Tạo 1 đối tượng Order mới
        Order order = new Order();
        // Tìm kiếm user được lưu trong database bằng username
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new NotFoundException("Not Found User With Username:" + request.getUsername()));

        // Set các tham số từ request vào đối tượng Order được khởi tạo ở trên
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
        order.setUser(user);
        // Set các tham số từ request vào đối tượng Order được khởi tạo ở trên
        // Lưu đối tượng Order
        orderRepository.save(order);

        // Lặp qua các Order detail để lưu Hoá đơn chi tiết
        for (CreateOrderDetailRequest rq : request.getOrderDetails()) {
            // Tạo đối tượng OrderDetail mới
            OrderDetail orderDetail = new OrderDetail();
            // Set các tham số vào đối tượng OrderDetail
            orderDetail.setName(rq.getName());
            orderDetail.setColor(rq.getColor());
            orderDetail.setSize(rq.getSize());
            orderDetail.setRoom(rq.getRoom());
            orderDetail.setPrice(rq.getPrice());
            orderDetail.setSoluong(rq.getSoluong());
            orderDetail.setSubTotal(rq.getPrice() * rq.getSoluong());
            orderDetail.setOrder(order);
            orderDetail.setProductId(rq.getProductId());

            // Lưu đối tượng OrderDetail vào database
            orderDetailRepository.save(orderDetail);
        }
        try {
            // Gửi thông tin về đơn hàng vào email của khách hàng
            emailUtil.sendOrderConfirmationEmail(request.getEmail(), order);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /*
    * Lấy tất cả danh sách Order sắp xếp theo ID giảm dần
    * */
    @Override
    public List<Order> getList() {
        return orderRepository.findAll(Sort.by("id").descending());
    }

    /*
    * Danh sách order cho biểu đồ thống kê
    * */
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

    /*
    * Lấy danh sách trạng thái đơn hàng theo Trạng thái hiện tại của đơn hàng
    * */
    @Override
    public List<Orderstatus> getListstatus(String currentStatusCode) {
        // Nếu trạng thái là chờ Giao hàng thì trả ra Chờ lấy hàng và Huỷ
        if (WAITING.equals(currentStatusCode)) {
            return orderstatusRepository.findByListCode(Arrays.asList(WAITING_DELIVERY, CANCELED));
        }
        // Nếu trạng thái là Chờ lấy hàng thì trả ra Đang giao hàng
        else if (WAITING_DELIVERY.equals(currentStatusCode)) {
            return orderstatusRepository.findByListCode(Arrays.asList(ON_DELIVERY));
        }
        // Nếu trạng thái là Đang giao hàng thì trả ra Đã giao hàng
        else if (ON_DELIVERY.equals(currentStatusCode)) {
            return orderstatusRepository.findByListCode(Arrays.asList(DELIVERED));
        }
        // Nếu trạng thái là Đã giao hàng thì trả ra Trả hàng
        else if (DELIVERED.equals(currentStatusCode)) {
            return orderstatusRepository.findByListCode(Arrays.asList(RETURNS));
        }
        // Nếu không thuộc các trạng thái tên thì trả ra Tất cả các trạng thái
        return orderstatusRepository.findAll(Sort.by("id").descending());
    }

    /*
    * Lấy danh sách Order theo username
    * */
    @Override
    public List<Order> getOrderByUser(String username) {
        // Tìm User theo username
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Not Found User With Username:" + username));
        // Lấy danh sách Order theo User vừa tìm được ở trên
        List<Order> orders = orderRepository.getOrderByUser(user.getId());
        return orders;
    }

    /*
    * Tìm kiếm Order theo Order code
    * */
    @Override
    public Order checkOrder(String orderCode) {
        Order order = orderRepository.findByOrderCode(orderCode).orElseThrow(() -> new NotFoundException("Not Found Blog"));
        return order;
    }

    @Override
    public ResponseEntity<?> removeOrder(String code) {
        return ResponseEntity.ok(new HashMap<>());
    }

    /*
    * Hàm cập nhật trạng thái của Order
    * */
    @Override
    public Order updateOrder(long id, CreateOrderRequest request) {

        // Tìm Order theo Id
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Product With Id: " + id));
        // Tìm OrderStatus theo id
        Orderstatus orderstatus = orderstatusRepository.findById(request.getStatus()).orElseThrow(() -> new NotFoundException("Not Found Category With Id: " + request.getStatus()));

        // Nếu trạng thái là Đang giao thì cập nhật số lượng
        if (orderstatus.getCode().equals(ON_DELIVERY)) {
            // Lấy danh sách OrderDetail theo order_id
            List<OrderDetail> orderDetails = orderDetailRepository.getOrderDetailsByOrderId(order.getId());
            // Kiểm tra nếu danh sách OrderDetail không rỗng thì tiếp tục
            if (!isNullOrEmpty(orderDetails)) {
                // Lặp qua danh sách OrderDetail
                for (OrderDetail orderDetail : orderDetails) {
                    // Tìm kiếm Product trong OrderDetail để cập nhật số lượng
                    Product product = productRepository.findById(orderDetail.getProductId()).orElse(null);
                    if (product != null) {
                        // Trừ số lượng của Product
                        product.setQuantity(product.getQuantity() - orderDetail.getSoluong());
                        // Tăng số lượng đã bán của Product
                        product.setQuantitybuy(product.getQuantitybuy() + orderDetail.getSoluong());
                        // Lưu product vào database
                        productRepository.save(product);
                    }
                }
            }
        }

        // Update lại số lượng của sản phẩn nếu Trạng thái là Trả hàng
        if (orderstatus.getCode().equals(RETURNS)) {
            // Lấy danh sách OrderDetail theo order_id
            List<OrderDetail> orderDetails = orderDetailRepository.getOrderDetailsByOrderId(order.getId());
            // Kiểm tra nếu danh sách OrderDetail không rỗng thì tiếp tục
            if (!isNullOrEmpty(orderDetails)) {
                // Lặp qua danh sách OrderDetail
                for (OrderDetail orderDetail : orderDetails) {
                    // Tìm kiếm Product trong OrderDetail để cập nhật số lượng
                    Product product = productRepository.findById(orderDetail.getProductId()).orElse(null);
                    if (product != null) {
                        // Cộng lại số lượng của Product
                        product.setQuantity(product.getQuantity() + orderDetail.getSoluong());
                        // Trừ số lượng đã bán của Prodcut
                        product.setQuantitybuy(product.getQuantitybuy() - orderDetail.getSoluong());
                        // Lưu product vào database
                        productRepository.save(product);
                    }
                }
            }
        }
        // Set trạng thái mới của Order
        order.setOrderstatus(orderstatus);

        // Lưu Order vào database
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

    @Override
    public Order returnOrder(long id, CreateOrderRequest request) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Product With Id: " + id));
        order.setReason(request.getReason());
        order.setUrlImg(request.getUrlImg());
        Orderstatus orderstatus = orderstatusRepository.findById(request.getStatus()).orElseThrow(() -> new NotFoundException("Not Found Category With Id: " + request.getStatus()));
        order.setOrderstatus(orderstatus);
        orderRepository.save(order);
        return order;
    }

    @Override
    public Order getById(long id) {
        return orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Order With Id: " + id));
    }
}
