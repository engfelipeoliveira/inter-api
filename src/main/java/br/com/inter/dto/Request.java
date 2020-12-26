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
public class Request implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SerializedName("pagador")
	private Pagador pagador;
	
	@SerializedName("dataEmissao")
	private String dataEmissao;
	
	@SerializedName("seuNumero")
	private String seuNumero;
	
	@SerializedName("dataLimite")
	private String dataLimite;
	
	@SerializedName("dataVencimento")
	private String dataVencimento;
	
	@SerializedName("mensagem")
	private Mensagem mensagem;
	
	@SerializedName("desconto1")
	private Desconto desconto1;
	
	@SerializedName("desconto2")
	private Desconto desconto2;
	
	@SerializedName("desconto3")
	private Desconto desconto3;
	
	@SerializedName("valorNominal")
	private BigDecimal valorNominal;
	
	@SerializedName("valorAbatimento")
	private BigDecimal valorAbatimento;
	
	@SerializedName("multa")
	private Multa multa;
	
	@SerializedName("mora")
	private Mora mora;
	
	@SerializedName("cnpjCPFBeneficiario")
	private String cnpjCPFBeneficiario;
	
	@SerializedName("numDiasAgenda")
	private String numDiasAgenda;

}
