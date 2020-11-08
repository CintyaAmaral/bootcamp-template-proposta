package br.com.zup.proposta.analisefinanceira;

import br.com.zup.proposta.novaproposta.Proposta;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class AnaliseFinanceiraService {

    private AnaliseFinanceiraClient analiseFinanceiraClient;

    public void validaAnaliseFinanceiraProposta(Proposta proposta){
        Assert.notNull(proposta, "A proposta não pode ser nula");

        AnaliseFinanceiraResponse analiseFinanceiraResponse = analiseFinanceiraClient.validaAnaliseFinanceira(proposta.toAnaliseFinanceiraRequest());

        Assert.notNull(analiseFinanceiraResponse, "O resultado sa análise financeira não pode ser nulo");
        proposta.modificaStatusProposta(analiseFinanceiraResponse.getResultadoSolicitacao().toStatusProposta());
    }
}
