package br.com.zup.proposta.carteira;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Carteira {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Email
    @NotBlank
    private String email;
    //1
    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoCarteira tipoCarteira;

    @Deprecated
    public Carteira() {
    }

    public Carteira(@Email @NotBlank String email, @NotNull TipoCarteira tipoCarteira) {
        this.email = email;
        this.tipoCarteira = tipoCarteira;
    }

    public String getId() {
        return id;
    }

    public boolean verificaSeExisteTipoCarteira(TipoCarteira tipoCarteira){
        return this.tipoCarteira.equals(tipoCarteira);
    }
}
