package com.example.santhuongmai.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommonRestFullWs {

    private final RestTemplate restTemplate;  // Khai báo một đối tượng RestTemplate, sẽ được inject vào qua constructor

    public ResponseEntity<Object> get(String url) {  // Phương thức GET để thực hiện yêu cầu HTTP GET
        HttpHeaders headers = new HttpHeaders();  // Tạo một đối tượng HttpHeaders
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));  // Thiết lập header Accept để nhận JSON
        headers.setContentType(MediaType.APPLICATION_JSON);  // Thiết lập header Content-Type là JSON
        headers.set("token", Const.GHN.TOKEN);  // Thêm header token với giá trị từ hằng số Const.GHN.TOKEN

        HttpEntity entity = new HttpEntity(headers);  // Tạo một đối tượng HttpEntity với các headers đã thiết lập

        try {
            ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class, (Object) null);  // Thực hiện yêu cầu HTTP GET
            log.info("response: " + DataUtil.prettyObject(response));  // Ghi log phản hồi nhận được

            return response;  // Trả về đối tượng ResponseEntity nhận được
        } catch (Exception e) {
            log.error(e.toString());  // Ghi log lỗi nếu có ngoại lệ xảy ra
        }

        return null;  // Trả về null nếu có lỗi
    }

    public ResponseEntity<Object> post(String url, Object body) {  // Phương thức POST để thực hiện yêu cầu HTTP POST
        HttpHeaders headers = new HttpHeaders();  // Tạo một đối tượng HttpHeaders
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));  // Thiết lập header Accept để nhận JSON
        headers.setContentType(MediaType.APPLICATION_JSON);  // Thiết lập header Content-Type là JSON
        headers.set("token", Const.GHN.TOKEN);  // Thêm header token với giá trị từ hằng số Const.GHN.TOKEN

        HttpEntity requestBody = new HttpEntity<>(body, headers);  // Tạo một đối tượng HttpEntity với body và headers đã thiết lập
        log.info("request: " + DataUtil.prettyObject(requestBody));  // Ghi log yêu cầu được gửi đi
        try {
            Object response = restTemplate.postForEntity(url, requestBody, Object.class).getBody();  // Thực hiện yêu cầu HTTP POST
            log.info("response: " + DataUtil.prettyObject(response));  // Ghi log phản hồi nhận được
            return new ResponseEntity<>(response, HttpStatus.OK);  // Trả về một đối tượng ResponseEntity với phản hồi nhận được
        } catch (Exception e) {
            log.error("Error during POST request: " + e.getMessage(), e);  // Ghi log lỗi nếu có ngoại lệ xảy ra
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);  // Trả về một đối tượng ResponseEntity với mã lỗi 500
        }
    }

}
