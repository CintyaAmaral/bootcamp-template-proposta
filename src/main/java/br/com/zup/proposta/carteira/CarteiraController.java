package br.com.zup.proposta.carteira;

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
public class CarteiraController {

    private EntityManager entityManager;
    private CarteiraService carteiraService;
    private Logger logger = LoggerFactory.getLogger(CarteiraController.class);

    public CarteiraController(EntityManager entityManager, CarteiraService carteiraService) {
        this.entityManager = entityManager;
        this.carteiraService = carteiraService;
    }

    @PostMapping("/{idCartao}/carteiras/paypal")
    public ResponseEntity associaCartaoComPaypal(@PathVariable String idCartao,
                                                 @RequestBody @Valid CarteiraRequest carteiraRequest,
                                                 UriComponentsBuilder uri){
        return processarSolicitacao(TipoCarteira.PAYPAL, idCartao, carteiraRequest, uri);
    }

    ResponseEntity processarSolicitacao(TipoCarteira tipoCarteira, String idCartao, CarteiraRequest carteiraRequest, UriComponentsBuilder uri){
        Optional<Cartao> buscaCartao = Optional.ofNullable(entityManager.find(Cartao.class, idCartao));

        if (buscaCartao.isEmpty()){
            logger.info("[ASSOCIA CARTEIRA] Cartão não encontrado, id {}", idCartao);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroPadrao(Arrays.asList("Cartão não encontrado")));
        }

        Cartao cartao = buscaCartao.get();

        if (cartao.verificaSeJaExisteCarteiraAssiciadaComCartao(tipoCarteira)){
            logger.info("[ASSOCIA CARTEIRA] Carteira já cadastrada no cartão, cartao: {}", cartao.getId());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ErroPadrao(Arrays.asList("Cartão já está associado a carteira")));
        }

        return carteiraService.processarAssociacaoDaCarteiraComCartao(tipoCarteira, cartao, carteiraRequest.getEmail(), uri);
    }

    @PostMapping("/{idCartao}/carteiras/samsungpay")
    @Transactional
    public ResponseEntity vinculaCartaoComSamsungPay(@PathVariable String idCartao,
                                                     @RequestBody @Valid CarteiraRequest carteiraRequest,
                                                     UriComponentsBuilder uri){
            return processarSolicitacao(TipoCarteira.SAMSUNG_PAY, idCartao, carteiraRequest, uri);
    }
}
