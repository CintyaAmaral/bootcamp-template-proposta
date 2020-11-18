package br.com.zup.proposta.bloqueiocartao;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
public class BloqueioCartao {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotBlank
    private String ip;

    @NotBlank
    private String userAgent;

    private LocalDateTime instante = LocalDateTime.now();

    @Deprecated
    public BloqueioCartao() {
    }

    public BloqueioCartao(@NotBlank String ip, @NotBlank String userAgent) {
        this.ip = ip;
        this.userAgent = userAgent;
    }

    public String getId() {
        return id;
    }
}
