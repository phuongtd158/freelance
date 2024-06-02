package com.example.santhuongmai.model.request;

import java.util.Set;

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
public class OrderstatusRequest {
	@NotNull(message = "Tên danh mục rỗng")
    @NotEmpty(message = "Tên danh mục rỗng")
    @Size(min=5,max=50,message="Độ dài danh mục từ 5-50 ký tự")
    private String name;
	 
	  @NotNull(message = "Danh mục rỗng")
	    @NotEmpty(message = "Danh mục rỗng")
	    @Schema(description = "ID của danh mục",example="1")
	  private long status;
}
