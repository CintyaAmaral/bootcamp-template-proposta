package br.com.zup.proposta.aviso;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

public class AvisoRequest {

    private String destino;

    private LocalDateTime validoAte;

    public Aviso toAviso(HttpServletRequest request){
        return new Aviso(destino, validoAte, request.getRemoteAddr(), request.getHeader("User-Agent"));
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public LocalDateTime getValidoAte() {
        return validoAte;
    }

    public void setValidoAte(LocalDateTime validoAte) {
        this.validoAte = validoAte;
    }
}
