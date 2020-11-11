package br.com.zup.proposta.cartao;

import br.com.zup.proposta.novaproposta.Proposta;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotBlank
    private String numeroCartao;

    @NotNull
    private LocalDateTime emitidoEm;

    @OneToOne
    private Proposta proposta;

    public Cartao(@NotBlank String numeroCartao, @NotNull LocalDateTime emitidoEm) {
        this.numeroCartao = numeroCartao;
        this.emitidoEm = emitidoEm;
    }

    public String getId() {
        return id;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public void incluirPropostaNoCartao(Proposta proposta){
        Assert.notNull(proposta, "A proposta não pode ser nula para associar ao cartão");
        this.proposta = proposta;
    }
}




