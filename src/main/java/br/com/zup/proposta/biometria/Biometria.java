package br.com.zup.proposta.biometria;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Base64;

@Entity
public class Biometria {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotNull
    private byte[] impressaoDigital;

    @NotNull
    private LocalDateTime criadaEm = LocalDateTime.now();

    @Deprecated
    public Biometria() {
    }

    public Biometria(@NotNull String impressaoDigital) {
        this.impressaoDigital = Base64.getDecoder().decode(impressaoDigital);
    }

    public String getId() {
        return id;
    }


}
