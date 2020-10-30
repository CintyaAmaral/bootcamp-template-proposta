package br.com.zup.proposta.novaproposta;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class NovaPropostaRequest {

    @NotBlank
    @CpfCnpj
    private String documento;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    private String endereco;

    @NotNull
    @Positive
    private BigDecimal salario;

    public NovaPropostaRequest(@NotBlank String documento, @NotBlank @Email String email,
                               @NotBlank String nome, @NotBlank String endereco,
                               @NotNull @Positive BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }


    public String getDocumento() {
        return documento;
    }

    public String getDocumentoApenasDigitos() {
        return documento.replaceAll("[^0-9]","");
    }

    public Proposta toModel(){
        String documentoProposta = this.getDocumentoApenasDigitos();
        return new Proposta(documentoProposta, email, nome, endereco, salario);

    }

}
