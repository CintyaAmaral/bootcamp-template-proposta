package br.com.zup.proposta.cartao;

import br.com.zup.proposta.novaproposta.Proposta;
import br.com.zup.proposta.novaproposta.StatusProposta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@Component
public class CadastroCartaoService {
    //1
    private CartaoClient cartaoClient;
    private EntityManager entityManager;
    private Logger logger;

    public CadastroCartaoService(CartaoClient cartaoClient, EntityManager entityManager) {
        this.cartaoClient = cartaoClient;
        this.entityManager = entityManager;
        this.logger = LoggerFactory.getLogger(CadastroCartaoService.class);
    }

    @Scheduled(fixedDelayString = "${tempo.verificadordecartao}")
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public void verificarSeExisteCartaoNaProposta(){
    logger.info("[SCHEDULED] Verfica se existe cartão cadastrado na proposta");
        //2
        TypedQuery<Proposta> propostas = entityManager.createNamedQuery("findPropostaByStatus", Proposta.class)
                .setParameter("statusProposta", StatusProposta.ELEGIVEL);
        //3
        //4
        for(Proposta proposta : propostas.getResultList()){
            if(proposta.verificarSeNaoExisteCartao()){
                CartaoResponse cartaoResponse = pesquisaCartao(proposta.getId());
                Assert.notNull(cartaoResponse, "Cartão não pode ser nulo");
                //5
                Cartao cartao = cartaoResponse.toCartao();
                cadastraCartaoEAssociaAProposta(proposta, cartao);
            }
        }
    }
    //6
    private CartaoResponse pesquisaCartao(String id){
        return cartaoClient.pesquisarCartaoPorIdProposta(id);
    }

    @Transactional
    private void cadastraCartaoEAssociaAProposta(Proposta proposta, Cartao cartao){
        cartao.incluirPropostaNoCartao(proposta);
        entityManager.persist(cartao);
        proposta.incluirCartaoNaProposta(cartao);
        entityManager.merge(proposta);
        logger.info("[INCLUSÃO DE CARTAO NA PROPOSTA] Cartão incluso na proposta {}", proposta.getId());
    }

}
