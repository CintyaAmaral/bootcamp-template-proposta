package br.com.zup.proposta.analisefinanceira;

import br.com.zup.proposta.novaproposta.ResultadoStatusProposta;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "analisefinanceira", url = "${host.analise-financeira}", fallbackFactory = AnaliseFinanceiraClient.AnaliseFinanceiraClientFallbackFactory.class)
public interface AnaliseFinanceiraClient {

    @PostMapping("/api/solicitacao")
    AnaliseFinanceiraResponse validaAnaliseFinanceira(@RequestBody AnaliseFinanceiraRequest request);

    @Component
    class AnaliseFinanceiraClientFallbackFactory implements FallbackFactory<AnaliseFinanceiraClient>{

        @Override
        public AnaliseFinanceiraClient create(Throwable throwable){
            return (request) -> {
                AnaliseFinanceiraResponse analiseFinanceiraResponse = new AnaliseFinanceiraResponse();
                analiseFinanceiraResponse.setResultadoSolicitacao(ResultadoStatusProposta.COM_RESTRICAO);
                return analiseFinanceiraResponse;
            };
        }
    }
}
