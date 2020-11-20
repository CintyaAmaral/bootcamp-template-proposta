package br.com.zup.proposta.analisefinanceira;

import br.com.zup.proposta.novaproposta.Proposta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class AnaliseFinanceiraService {
    //1
    @Autowired
    private AnaliseFinanceiraClient analiseFinanceiraClient;

    private Logger logger = LoggerFactory.getLogger(AnaliseFinanceiraService.class);
    //2
    public Proposta executarAnaliseFinanceiraProposta(Proposta proposta){
        Assert.notNull(proposta, "A proposta não pode ser nula");
        logger.info("[ANÁLISE FINANCEIRA] Executa análise financeira ");
        //3
        AnaliseFinanceiraResponse analiseFinanceiraResponse = analiseFinanceiraClient.executaAnaliseFinanceira(proposta.toAnaliseFinanceiraRequest());

        Assert.notNull(analiseFinanceiraResponse, "O resultado sa análise financeira não pode ser nulo");

        logger.info("Resultado {} recebido para proposta de id {} ", analiseFinanceiraResponse.getResultadoSolicitacao(), proposta.getId());

        proposta.modificaStatusProposta(analiseFinanceiraResponse.getResultadoSolicitacao().toStatusProposta());

        return proposta;
    }
}
