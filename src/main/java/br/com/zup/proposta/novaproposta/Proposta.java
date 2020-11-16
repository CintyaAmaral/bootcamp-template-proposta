package br.com.zup.proposta.novaproposta;

import br.com.zup.proposta.analisefinanceira.AnaliseFinanceiraRequest;
import br.com.zup.proposta.cartao.Cartao;
import br.com.zup.proposta.validation.CpfCnpj;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@NamedQuery(name = "findPropostaByStatus", query = "select p from Proposta p where p.statusProposta = :statusProposta")
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
    @NotNull
    private BigDecimal salario;

    @Enumerated(EnumType.STRING)
    private StatusProposta statusProposta;

    @OneToOne
    private Cartao cartao;

    @Deprecated
    public Proposta() {
    }

    public Proposta(@NotBlank @CpfCnpj String documento, @NotBlank @Email String email,
                    @NotBlank String nome, @NotBlank String endereco,
                    @NotNull @Positive BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
        this.statusProposta = StatusProposta.PENDENTE;
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

    public StatusProposta getStatusProposta() {
        return statusProposta;
    }


    public AnaliseFinanceiraRequest toAnaliseFinanceiraRequest(){
        return new AnaliseFinanceiraRequest(this.documento, this.nome, this.id);
    }

    public void modificaStatusProposta(StatusProposta statusProposta){
        Assert.notNull(statusProposta, "O status da proposta não pode ser nulo");
        this.statusProposta = statusProposta;
    }

    public void incluirCartaoNaProposta(Cartao cartao){
        this.cartao = cartao;
    }

    public boolean verificarSeNaoExisteCartao(){
        return Objects.isNull(cartao);
    }

}
