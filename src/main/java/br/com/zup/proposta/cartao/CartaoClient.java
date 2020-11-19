package br.com.zup.proposta.cartao;

import br.com.zup.proposta.aviso.AvisoRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "cartao", url = "${host.cartao}")
public interface CartaoClient {

    @GetMapping("/api/cartoes")
    CartaoResponse pesquisarCartaoPorIdProposta(@RequestParam String idProposta);

    @PostMapping("/api/cartoes/{idCartao}/bloqueios")
    ResponseEntity bloquearCartao(@PathVariable String idCartao, @RequestBody Map bloqueioRequest);

    @PostMapping("/api/cartoes/{idCartao}/avisos")
    ResponseEntity enviarAvisoDeViagem(@PathVariable String idCartao, @RequestBody AvisoRequest avisoRequest);
}
