package br.com.zup.proposta.carteira;

import br.com.zup.proposta.cartao.Cartao;
import br.com.zup.proposta.cartao.CartaoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

@Service
public class CarteiraService {
    //1
    private CartaoClient cartaoClient;
    private EntityManager entityManager;
    private Logger logger = LoggerFactory.getLogger(CarteiraService.class);

    public CarteiraService(CartaoClient cartaoClient, EntityManager entityManager) {
        this.cartaoClient = cartaoClient;
        this.entityManager = entityManager;
    }

    public ResponseEntity processarAssociacaoDaCarteiraComCartao(TipoCarteira tipoCarteira,
                                                                 Cartao cartao, String email,
                                                                 UriComponentsBuilder uri){
        Map carteiraRequest = new HashMap();
        carteiraRequest.put("email", email);
        carteiraRequest.put("carteira", tipoCarteira);

        logger.info("[ASSOCIA CARTEIRA] Enviando carteira para o sistema de cartões ");
        ResponseEntity responseEntity = cartaoClient.associarCarteira(carteiraRequest, cartao.getNumeroCartao());
        //2
        //3
        //4
        if (responseEntity.getStatusCode() == HttpStatus.OK){
            logger.info("[ASSOCIA CARTEIRA] Salva carteira e associa com cartão {}", cartao.getId());
            Carteira carteira = new Carteira(email, tipoCarteira);
            entityManager.persist(carteira);

            cartao.incluirCarteira(carteira);
            entityManager.merge(cartao);

            return ResponseEntity
                    .created(uri.path("/carteiras/{id}")
                    .buildAndExpand(carteira.getId())
                    .toUri())
                    .build();
        }

        logger.info("[ASSOCIA CARTEIRA] Erro {} no retorno do sistema de cartões", responseEntity.getStatusCode());
        return ResponseEntity.badRequest().build();
    }
}
