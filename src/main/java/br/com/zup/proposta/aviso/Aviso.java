package br.com.zup.proposta.aviso;

import br.com.zup.proposta.cartao.Cartao;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Aviso {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotBlank
    private String destino;

    @NotNull
    private LocalDateTime validoAte;

    private LocalDateTime instanteDoAviso = LocalDateTime.now();

    private String ipCliente;

    private String userAgent;

    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public Aviso() {
    }

    public Aviso(@NotBlank String destino, @NotNull LocalDateTime validoAte, String ipCliente, String userAgent) {
        this.destino = destino;
        this.validoAte = validoAte;
        this.ipCliente = ipCliente;
        this.userAgent = userAgent;
    }

    public String getId() {
        return id;
    }
}
