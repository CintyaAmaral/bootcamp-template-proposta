package br.com.zup.proposta.recuperasenha;

import br.com.zup.proposta.cartao.Cartao;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.GenericGenerators;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class RecuperaSenha {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private LocalDateTime instante = LocalDateTime.now();

    private String ipCliente;

    private String userAgent;

    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public RecuperaSenha() {
    }

    public RecuperaSenha(String ipCliente, String userAgent, Cartao cartao) {
        this.ipCliente = ipCliente;
        this.userAgent = userAgent;
        this.cartao = cartao;
    }

    public String getId() {
        return id;
    }
}
