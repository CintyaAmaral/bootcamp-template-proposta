package br.com.zup.proposta.novaproposta;

import br.com.zup.proposta.analisefinanceira.AnaliseFinanceiraRequest;
import br.com.zup.proposta.validation.CpfCnpj;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @CpfCnpj
    @NotBlank
    private String documento;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    private String endereco;

    @Positive
    private BigDecimal salario;

    @Deprecated
    public Proposta() {
    }

    public Proposta(@NotBlank @CpfCnpj String documento, @NotBlank @Email String email,
                    @NotBlank String nome, @NotBlank String endereco,
                    @NotBlank @Positive BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public String getId() {
        return id;
    }

    public String getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proposta proposta = (Proposta) o;
        return Objects.equals(documento, proposta.documento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documento);
    }

    public AnaliseFinanceiraRequest toAnaliseFinanceiraRequest(){
        return new AnaliseFinanceiraRequest(this.documento, this.nome, this.id);
    }

    public void modificaStatusProposta(StatusProposta statusProposta){
        
    }
}
