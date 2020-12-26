package br.com.inter.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Mora implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SerializedName("codigoMora")
	private String codigoMora;
	
	@SerializedName("taxa")
	private BigDecimal taxa;
	
	@SerializedName("valor")
	private BigDecimal valor;
	
	@SerializedName("data")
	private String data;

}
