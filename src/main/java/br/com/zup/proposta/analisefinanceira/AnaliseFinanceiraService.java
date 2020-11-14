package br.com.zup.proposta.analisefinanceira;

import br.com.zup.proposta.novaproposta.Proposta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class AnaliseFinanceiraService {

    private AnaliseFinanceiraClient analiseFinanceiraClient;

    private Logger logger = LoggerFactory.getLogger(AnaliseFinanceiraService.class);

    public void executarAnaliseFinanceiraProposta(Proposta proposta){
        Assert.notNull(proposta, "A proposta não pode ser nula");
        logger.info("[ANÁLISE FINANCEIRA] Executa análise financeira ");

        AnaliseFinanceiraResponse analiseFinanceiraResponse = analiseFinanceiraClient.executaAnaliseFinanceira(proposta.toAnaliseFinanceiraRequest());

        Assert.notNull(analiseFinanceiraResponse, "O resultado sa análise financeira não pode ser nulo");
        proposta.modificaStatusProposta(analiseFinanceiraResponse.getResultadoSolicitacao().toStatusProposta());
    }
}
