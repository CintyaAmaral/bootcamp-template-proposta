package br.com.zup.proposta.bloqueiocartao;

import br.com.zup.proposta.cartao.Cartao;
import br.com.zup.proposta.handler.ErroPadrao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/cartoes")
public class BloqueioCartaoController {

    private EntityManager entityManager;
    private Logger logger = LoggerFactory.getLogger(BloqueioCartao.class);

    public BloqueioCartaoController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping("/{idCartao}/bloqueio")
    @Transactional
    public ResponseEntity bloqueiaCartao(@PathVariable String idCartao,
                                         HttpServletRequest httpServletRequest,
                                         UriComponentsBuilder uri) {
        //1
        Optional<Cartao> buscaCartao = Optional.ofNullable(entityManager.find(Cartao.class, idCartao));
        //2
        //3
        if (buscaCartao.isEmpty()) {
            logger.info("[BLOQUEIO CARTÃO] Cartão não encontrado, id: {}", idCartao);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroPadrao(Arrays.asList("O cartão não foi encontrado")));
        }

        Cartao cartao = buscaCartao.get();
        //4
        BloqueioCartao bloqueioCartao = new BloqueioCartao(httpServletRequest.getRemoteAddr(),
                httpServletRequest.getHeader("User-Agent"));
        entityManager.persist(bloqueioCartao);


        cartao.incluirBloqueioDoCartao(bloqueioCartao);
        entityManager.merge(cartao);
        logger.info("[BLOQUEIO DE CARTÃO] Bloqueio cadastrado, cartão: {}", cartao.getId());

        return ResponseEntity
                .created(uri.path("/bloqueios/{id}")
                        .buildAndExpand(bloqueioCartao.getId())
                        .toUri())
                .build();
    }
}