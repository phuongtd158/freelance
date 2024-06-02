package com.example.santhuongmai.model.request;

import java.util.Set;
import java.time.LocalDateTime;
import java.sql.Timestamp;
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
public class CreateVoucherRequest {
	
	 @NotNull(message = "Tên sản phẩm rỗng")
	    @NotEmpty(message="Tên sản phẩm rỗng")
	    @Schema(description = "Tên sản phẩm",example="Product1",required=true)
	    @Size(min=5,max=50,message="Tên sản phẩm từ 3-50 ký tự")
	    private String name;
	 
	 private long id;

	@NotNull(message = "Giá tiền rỗng")
    @NotEmpty(message = "Giá tiền rỗng")
    @Schema(description = "Giá sản phẩm",example = "12")
    @Size(min=0,message="Giá tiền sản phẩm lớn hơn 0")
    private long money;
	
	@NotNull(message = "Số lượng sản phẩm")
    @NotEmpty(message="Số lượng sản phẩm")
    @Schema(description = "Số lượng sản phẩm",example="12")
    @Size(min=0,message="Số lượng sản phẩm từ 0")
    private int count;
	@Schema(description = "Thời gian tạo", required = false)
    private LocalDateTime hsd;
}
