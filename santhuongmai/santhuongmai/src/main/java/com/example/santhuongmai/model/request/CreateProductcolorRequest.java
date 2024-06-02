package com.example.santhuongmai.model.request;

import java.util.HashSet;
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
public class CreateProductcolorRequest {
	@NotNull(message = "Tên sản phẩm rỗng")
    @NotEmpty(message="Tên sản phẩm rỗng")
    @Schema(description = "Tên sản phẩm",example="Product1",required=true)
    @Size(min=5,max=50,message="Tên sản phẩm từ 3-50 ký tự")
    private String name;
	private String username;
}
