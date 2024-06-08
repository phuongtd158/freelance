package com.example.santhuongmai.controller;

import com.example.santhuongmai.util.CommonRestFullWs;
import com.example.santhuongmai.util.Const;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shipping")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ShippingController {

    private final CommonRestFullWs commonRestFullWs;

    @GetMapping("province")
    public ResponseEntity<?> getProvince() {
        return commonRestFullWs.get(Const.GHN.PREFIX_ADDRESS_API + "/province");
    }

    @GetMapping("district")
    public ResponseEntity<?> getDistrict(@RequestParam("provinceId") Object provinceId) {
        return commonRestFullWs.get(Const.GHN.PREFIX_ADDRESS_API + "/district?province_id=" + provinceId);
    }

    @GetMapping("ward")
    public ResponseEntity<?> getWard(@RequestParam("districtId") Object districtId) {
        return commonRestFullWs.get(Const.GHN.PREFIX_ADDRESS_API + "/ward?district_id=" + districtId);
    }

    @PostMapping("available-services")
    public ResponseEntity<?> getService(@RequestBody Object body) {
        return commonRestFullWs.post(Const.GHN.PREFIX_SHIPPING_API + "/available-services", body);
    }

    @PostMapping("fee")
    public ResponseEntity<?> getFee(@RequestBody Object body) {
        return commonRestFullWs.post(Const.GHN.PREFIX_SHIPPING_API + "/fee", body);
    }


}
