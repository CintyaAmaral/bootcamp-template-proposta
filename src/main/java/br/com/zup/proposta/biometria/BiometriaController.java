package br.com.zup.proposta.biometria;

import br.com.zup.proposta.cartao.Cartao;
import br.com.zup.proposta.handler.ErroPadrao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/cartoes")
public class BiometriaController {

    private EntityManager entityManager;
    private Logger logger = LoggerFactory.getLogger(Biometria.class);

    public BiometriaController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping("/{idCartao}/biometria")
    @Transactional
    public ResponseEntity cadastraBiometria(@PathVariable String idCartao,
                                            @RequestBody @Valid BiometriaRequest biometriaRequest, UriComponentsBuilder uriComponentsBuilder){
        //1
        //2
        //3
        Optional<Cartao> buscaCartao = Optional.ofNullable(entityManager.find(Cartao.class, idCartao));
        if (buscaCartao.isEmpty()){
            logger.warn("[CADASTRO DE BIOMETRIA] O número do cartão não foi encontrado, id: {}", idCartao);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroPadrao(Arrays.asList("Cartão não encontrado")));
        }

        Cartao cartao = buscaCartao.get();
        //4
        Biometria biometria = biometriaRequest.toBiometria();
        entityManager.persist(biometria);
        logger.warn("[CADASTRO DE BIOMETRIA] Biometria cadastrada: {}", biometria.getId());


        cartao.incluirBiometriaNoCartao(biometria);
        entityManager.merge(cartao);
        logger.warn("[CADASTRO DE BIOMETRIA] Biometria assosciada ao cartão: {}", cartao.getNumeroCartao());

        return ResponseEntity.created(uriComponentsBuilder.path("/biometrias/{id}").buildAndExpand(idCartao).toUri()).build();
    }
}
