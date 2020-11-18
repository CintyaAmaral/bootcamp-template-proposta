package br.com.zup.proposta.bloqueiocartao;

import br.com.zup.proposta.cartao.Cartao;
import br.com.zup.proposta.cartao.CartaoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BloqueioCartaoSevice {

        private CartaoClient cartaoClient;
        private Logger logger = LoggerFactory.getLogger(BloqueioCartaoSevice.class);

        public void executaBloqueioDoCartao(Cartao cartao){

            if (cartao.verificaSeCartaoEstaBoqueado()){
                logger.info("[BLOQUEIO DE CARTÃO] O cartão já está boqueado no sistema, cartão id: {}", cartao.getId());
                return;
            }

            Map bloqueioRequest = new HashMap();
            bloqueioRequest.put("siatemaResponsavel", "Proposta");
            ResponseEntity responseEntity = cartaoClient.bloquearCartao(cartao.getNumeroCartao(), bloqueioRequest);


            if (responseEntity.getStatusCode().is2xxSuccessful()){
                cartao.bloqueiaCartao();
                logger.info("[BLQUEIO CARTÃO] Cartão bloqueado: {}", cartao.getId());
            }
        }
}
