package br.com.zup.proposta.aviso;

import br.com.zup.proposta.cartao.Cartao;
import br.com.zup.proposta.cartao.CartaoClient;
import br.com.zup.proposta.handler.ErroPadrao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Optional;

@RequestMapping("/cartoes")
@RestController
public class AvisoController {

    private EntityManager entityManager;
    private Logger logger = LoggerFactory.getLogger(Aviso.class);
    //1
    private CartaoClient cartaoClient;

    public AvisoController(EntityManager entityManager, CartaoClient cartaoClient) {
        this.entityManager = entityManager;
        this.cartaoClient = cartaoClient;
    }

    @PostMapping("/{idCartao}/aviso")
    @Transactional
    public ResponseEntity cadastraAviso(@PathVariable String idCartao,
                                        @RequestBody @Valid AvisoRequest avisoRequest,
                                        HttpServletRequest request,
                                        UriComponentsBuilder uri){
        //2
        Optional<Cartao> buscaCartao = Optional.ofNullable(entityManager.find(Cartao.class, idCartao));
        //3
        //4
        if (buscaCartao.isEmpty()){
            logger.warn("[CADASTRO DE AVISO] O numero do cartão não foi encontrado, id: {}", idCartao);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroPadrao(Arrays.asList("Cartão não econtrado")));
        }

        Cartao cartao = buscaCartao.get();

        logger.info("[CADASTRO DE AVISO] Aviso de viagem enviado para sistema de cartões");
        cartaoClient.enviarAvisoDeViagem(cartao.getNumeroCartao(), avisoRequest);
        //5
        Aviso aviso = avisoRequest.toAviso(request);
        entityManager.persist(aviso);
        logger.info("[CADASTRO DE AVISO] Aviso cadastrado: {}", aviso.getId());

        cartao.incluirAvisoDeViagem(aviso);
        entityManager.merge(cartao);
        logger.info("[CADASTRO DE AVISO] Aviso associado ao cartão: {}", cartao.getNumeroCartao());

        return ResponseEntity
                .created(uri.path("/avisos/{id}")
                .buildAndExpand(aviso.getId())
                .toUri())
                .build();

    }
}
