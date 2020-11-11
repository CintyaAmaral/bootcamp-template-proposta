package br.com.zup.proposta.cartao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cartao", url = "${host.cartao}")
public interface CartaoClient {

    @GetMapping("/api/cartoes")
    CartaoResponse pesquisarCartaoPorIdProposta(@RequestParam String idProposta);

}
