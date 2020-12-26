package br.com.inter.dto;

import java.io.Serializable;

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
public class Desconto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SerializedName("codigoDesconto")
	private String codigoDesconto;
	
	@SerializedName("taxa")
	private Double taxa;
	
	@SerializedName("valor")
	private Double valor;
	
	@SerializedName("data")
	private String data;

}
