package br.com.zup.proposta.novaproposta;

import br.com.zup.proposta.handler.ErroPadrao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Optional;

@RestController
public class ConsultaPropostaController {

    private Logger logger = LoggerFactory.getLogger(ConsultaPropostaController.class);

    private PropostaRepository propostaRepository;

    public ConsultaPropostaController(PropostaRepository propostaRepository) {
        this.propostaRepository = propostaRepository;
    }

    @GetMapping("/propostas/{id}")
    public ResponseEntity consultaPropostaId(@PathVariable String id){
        //1
        Optional<Proposta> proposta = propostaRepository.findById(id);
        //2
        //3
        if(proposta.isEmpty()){
            logger.warn("[CONSULTA DE PROPOSTA] Proposta não encontrada, id consultado: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroPadrao(Arrays.asList("Não existe proposta cadastrada")));
        }
        //4
        NovaPropostaResponse response = new NovaPropostaResponse(proposta.get());
        logger.warn("[CONSULTA DE PROPOSTA] Proposta encontrada, id consultado: {}", id);
        return ResponseEntity.ok(response);
    }

    

}
