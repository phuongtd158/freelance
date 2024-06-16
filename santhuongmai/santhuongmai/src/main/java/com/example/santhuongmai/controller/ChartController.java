package com.example.santhuongmai.controller;

import com.example.santhuongmai.dto.sdi.ChartSdi;
import com.example.santhuongmai.dto.sdo.ChartSdo;
import com.example.santhuongmai.service.ChartService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PostMapping("/search-product-detail")
    @Operation(summary="Tim kiem du lieu san pham chi tiet")
    public ResponseEntity<List<ChartSdo>> searchProductDetail(@RequestBody ChartSdi request){
        return ResponseEntity.ok(chartService.searchProductDetail(request));
    }

    @PostMapping("/search-product-v2")
    @Operation(summary="Tim kiem du lieu san pham v2")
    public ResponseEntity<List<ChartSdo>> searchProductV2(@RequestBody ChartSdi request){
        return ResponseEntity.ok(chartService.searchProductV2(request));
    }

    @PostMapping("/search-product-v2-detail")
    @Operation(summary="Tim kiem du lieu san pham v2 chi tiet")
    public ResponseEntity<List<ChartSdo>> searchProductV2Detail(@RequestBody ChartSdi request){
        return ResponseEntity.ok(chartService.searchProductV2Detail(request));
    }

    @PostMapping("/get-sum-quantity-product")
    @Operation(summary="Lay tong san pham trong kho")
    public ResponseEntity<Integer> getSumQuantityProduct(@RequestBody ChartSdi request){
        return ResponseEntity.ok(chartService.getSumQuantityProduct(request));
    }
    @PostMapping("/get-sum-revenue")
    @Operation(summary="Lay tong doanh thu")
    public ResponseEntity<BigDecimal> getSumRevenue(@RequestBody ChartSdi request){
        return ResponseEntity.ok(chartService.getSumRevenue(request));
    }

    @PostMapping("/get-sum-order-done")
    @Operation(summary="Lay tong don hang hoan thanh")
    public ResponseEntity<Integer> getSumOrderDone(@RequestBody ChartSdi request){
        return ResponseEntity.ok(chartService.getSumOrderDone(request));
    }

    @PostMapping("export")
    @Operation(summary = "[Xuất excel]")
    public ResponseEntity<ByteArrayResource> export(@RequestBody ChartSdi request) throws Exception {
        ByteArrayResource resource = new ByteArrayResource(IOUtils.toByteArray(chartService.export(request)));
        String fileName = "excel_chart";
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + fileName + ".xlsx")
                .body(resource);
    }
}
