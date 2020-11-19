package br.com.zup.proposta.recuperasenha;

import br.com.zup.proposta.cartao.Cartao;
import br.com.zup.proposta.handler.ErroPadrao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Optional;

@RestController
public class RecuperaSenhaController {

    private EntityManager entityManager;
    private Logger logger = LoggerFactory.getLogger(RecuperaSenhaController.class);

    @PostMapping("/{idCartao}/recuperasenha")
    @Transactional
    public ResponseEntity solicitarRecuperacaoDeSenha(@PathVariable String idCartao,
                                                      HttpServletRequest request,
                                                      UriComponentsBuilder uri){

        Optional<Cartao> buscaCartao = Optional.ofNullable(entityManager.find(Cartao.class, idCartao));

        if (buscaCartao.isEmpty()){
            logger.warn("[RECUPERAÇÃO DE SENHA] O número do cartão não foi encontrado, id: {}", idCartao);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroPadrao(Arrays.asList("Cartão não encontrado")));
        }

        Cartao cartao = buscaCartao.get();

        RecuperaSenha recuperaSenha = new RecuperaSenha(request.getRemoteAddr(), request.getHeader("User-Agent"), cartao);
        entityManager.persist(recuperaSenha);
        logger.warn("[RECUPERAÇÃO DE SENHA] Solicitação de recuperação de senha cadastrada, id: {}", recuperaSenha.getId());

        return ResponseEntity
                .created(uri.path("/recuperasenha/{id}")
                .buildAndExpand(recuperaSenha.getId())
                .toUri())
                .build();
    }

}
