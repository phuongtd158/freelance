package com.example.santhuongmai.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.santhuongmai.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    
    @Query(value ="Select * from Orders where user_id = :id order by id desc",nativeQuery = true)
    List<Order> getOrderByUser(long id);
    
//    Optional<Order> findByOrderCode(String code);   

    
    @Query(value ="SELECT o.*, u.* FROM orders as o JOIN order_detail as u ON o.id = u.order_id WHERE o.id = :id", nativeQuery = true)
    List<Order> getListOrder(long id);
    
//    @Query(value ="SELECT month_year, total_revenue FROM (SELECT CONCAT(MONTH(create_at), '/', YEAR(create_at)) AS month_year,SUM(total_price) AS total_revenue FROM orders GROUP BY CONCAT(MONTH(create_at), '/', YEAR(create_at)) ORDER BY month_year DESC LIMIT :number) AS subquery ORDER BY month_year ASC;", nativeQuery = true)
//    List<Object[]> getListOrdercharts(int number);
    
    @Query(value ="SELECT month_year, total_revenue FROM (SELECT CONCAT(MONTH(create_at), '/', YEAR(create_at)) AS month_year,SUM(total_price) AS total_revenue, MAX(create_at) AS max_create_at FROM orders WHERE status = 3 GROUP BY CONCAT(MONTH(create_at), '/', YEAR(create_at))ORDER BY month_year DESC LIMIT :number) AS subquery ORDER BY max_create_at ASC;", nativeQuery = true)
    List<Object[]> getListOrdercharts(int number);
    @Query("SELECT MAX(o.id) FROM Order o")
    Long getMaxOrderId();
    Optional<Order> findByOrderCode(String orderCode);
}
