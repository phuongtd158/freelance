package com.example.santhuongmai.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "setting")
public class Setting {
	  @Id
	  private Long id;
	private String gmail;
	private String phone;
	private String logo;
	private String facebook;
	private String youtube;
	private String telegram;
	private String instagram;
	private String diachi;
	
}
