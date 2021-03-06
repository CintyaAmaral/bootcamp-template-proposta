package br.com.zup.proposta.cartao;

import br.com.zup.proposta.aviso.Aviso;
import br.com.zup.proposta.biometria.Biometria;
import br.com.zup.proposta.bloqueiocartao.BloqueioCartao;
import br.com.zup.proposta.bloqueiocartao.StatusCartao;
import br.com.zup.proposta.carteira.Carteira;
import br.com.zup.proposta.carteira.TipoCarteira;
import br.com.zup.proposta.novaproposta.Proposta;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotNull
    private String numeroCartao;

    @NotNull
    private LocalDateTime emitidoEm;
    //1
    @OneToOne
    private Proposta proposta;
    //2
    @OneToMany
    private Set<Biometria> biometrias;
    //3
    @OneToMany
    private Set<BloqueioCartao> bloqueiosCataos;
    //4
    @Enumerated(EnumType.STRING)
    private StatusCartao statusCartao;
    //5
    @OneToMany
    private Set<Aviso> avisos;
    //6
    @OneToMany
    private Set<Carteira> carteiras;

    @Deprecated
    public Cartao() {
    }

    public Cartao(@NotBlank String numeroCartao, @NotNull LocalDateTime emitidoEm) {
        this.numeroCartao = numeroCartao;
        this.emitidoEm = emitidoEm;
        this.statusCartao = StatusCartao.DESBLOQUEADO;

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

    public void incluirBiometriaNoCartao(Biometria biometria){
        Assert.notNull(biometria, "A biometria não pode ser nula");
        biometrias.add(biometria);
    }

    public void incluirBloqueioDoCartao(BloqueioCartao bloqueioCartao){
        Assert.notNull(bloqueioCartao, "O bloqueio do cartão não pode ser nulo");
        bloqueiosCataos.add(bloqueioCartao);
    }

    public void bloqueiaCartao(){
        this.statusCartao = StatusCartao.BLOQUEADO;
    }

    public boolean verificaSeCartaoEstaBoqueado(){
        return statusCartao.equals(StatusCartao.BLOQUEADO);
    }

    public void incluirAvisoDeViagem(Aviso aviso){
        Assert.notNull(proposta, "A proposta não pode ser nula para assosciar ao cartão");
        avisos.add(aviso);
    }
    //7
    public boolean verificaSeJaExisteCarteiraAssiciadaComCartao(TipoCarteira tipoCarteira){
        Assert.notNull(tipoCarteira, "A carteira não pode ser nula ");
        return carteiras.stream().anyMatch(carteira -> carteira.verificaSeExisteTipoCarteira(tipoCarteira));
    }

    public void incluirCarteira(Carteira carteira){
        Assert.notNull(carteira, "A carteira não pode ser nula para associação com cartão");
        carteiras.add(carteira);
    }
}




