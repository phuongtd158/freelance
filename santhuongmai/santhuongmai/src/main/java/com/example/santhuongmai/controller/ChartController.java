package com.example.santhuongmai.controller;

import com.example.santhuongmai.dto.sdi.ChartSdi;
import com.example.santhuongmai.dto.sdo.ChartSdo;
import com.example.santhuongmai.service.ChartService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/chart")
@CrossOrigin(origins = "*",maxAge = 3600)
@RequiredArgsConstructor
public class ChartController {
    private final ChartService chartService;

    @PostMapping("/search-revenue")
    @Operation(summary="Tim kiem du lieu doanh thu")
    public ResponseEntity<List<ChartSdo>> searchRevenue(@RequestBody ChartSdi request){
        return ResponseEntity.ok(chartService.searchRevenue(request));
    }

    @PostMapping("/search-product")
    @Operation(summary="Tim kiem du lieu san pham")
    public ResponseEntity<List<ChartSdo>> searchProduct(@RequestBody ChartSdi request){
        return ResponseEntity.ok(chartService.searchProduct(request));
    }

    @GetMapping("/get-sum-quantity-product")
    @Operation(summary="Lay tong san pham trong kho")
    public ResponseEntity<Integer> getSumQuantityProduct(){
        return ResponseEntity.ok(chartService.getSumQuantityProduct());
    }
    @GetMapping("/get-sum-revenue")
    @Operation(summary="Lay tong doanh thu")
    public ResponseEntity<BigDecimal> getSumRevenue(){
        return ResponseEntity.ok(chartService.getSumRevenue());
    }

    @GetMapping("/get-sum-order-done")
    @Operation(summary="Lay tong don hang hoan thanh")
    public ResponseEntity<Integer> getSumOrderDone(){
        return ResponseEntity.ok(chartService.getSumOrderDone());
    }

    @GetMapping("/get-sum-product-sell")
    @Operation(summary="Lay tong san pham da ban")
    public ResponseEntity<Integer> getSumProductSell(){
        return ResponseEntity.ok(chartService.getSumProductSell());
    }

}
