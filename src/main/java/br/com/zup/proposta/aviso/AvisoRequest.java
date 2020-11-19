package br.com.zup.proposta.aviso;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

public class AvisoRequest {

    private String destino;

    private LocalDateTime dataTermino;

    public Aviso toAviso(HttpServletRequest request){
        return new Aviso(destino, dataTermino, request.getRemoteAddr(), request.getHeader("User-Agent"));
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public LocalDateTime getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(LocalDateTime dataTermino) {
        this.dataTermino = dataTermino;
    }
}
