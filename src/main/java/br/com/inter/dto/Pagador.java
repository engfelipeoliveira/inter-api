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
public class Pagador implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SerializedName("cnpjCpf")
	private String cnpjCpf;
	
	@SerializedName("nome")
	private String nome;
	
	@SerializedName("email")
	private String email;
	
	@SerializedName("telefone")
	private String telefone;
	
	@SerializedName("cep")
	private String cep;
	
	@SerializedName("numero")
	private String numero;
	
	@SerializedName("complemento")
	private String complemento;
	
	@SerializedName("bairro")
	private String bairro;
	
	@SerializedName("cidade")
	private String cidade;
	
	@SerializedName("uf")
	private String uf;
	
	@SerializedName("endereco")
	private String endereco;
	
	@SerializedName("ddd")
	private String ddd;
	
	@SerializedName("tipoPessoa")
	private String tipoPessoa;

}
