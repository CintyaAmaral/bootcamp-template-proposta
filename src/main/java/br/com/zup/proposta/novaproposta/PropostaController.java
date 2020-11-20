package br.com.zup.proposta.novaproposta;

import br.com.zup.proposta.analisefinanceira.AnaliseFinanceiraService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;


@RestController
public class PropostaController {
    //1
    private final PropostaRepository repository;
    //2
    @Autowired
    private AnaliseFinanceiraService analiseFinanceiraService;

    private Logger logger = LoggerFactory.getLogger(PropostaController.class);

    public PropostaController(PropostaRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/propostas")
    @Transactional
    public ResponseEntity<?> criaProposta(@Valid @RequestBody NovaPropostaRequest novaPropostaRequest, UriComponentsBuilder builder){
        //3
        if (repository.findByDocumento(novaPropostaRequest.getDocumento()).isPresent()){
            logger.warn("[CRIAÇÃO DA PROPOSTA] Mais de uma tentativa de criação da proposta com o mesmo documento: {}", novaPropostaRequest.getDocumento());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        //4
        //5
        Proposta novaProposta = novaPropostaRequest.toModel();
        repository.save(novaProposta);
        logger.info("[CRIAÇÃO DA PROPOSTA] Proposta criada com sucesso: {}", novaProposta.getId());


        novaProposta = analiseFinanceiraService.executarAnaliseFinanceiraProposta(novaProposta);
        repository.save(novaProposta);
        logger.info("[ANÁLISE FINANCEIRA] Análise financeira da proposta realizada: {}", novaProposta.getId());

        URI enderecoConsulta = builder.path("/propostas/{id}").build(novaProposta.getId());
        return ResponseEntity.created(enderecoConsulta).build();
    }
}
