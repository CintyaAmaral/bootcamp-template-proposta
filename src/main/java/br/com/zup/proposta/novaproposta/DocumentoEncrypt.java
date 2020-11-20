package br.com.zup.proposta.novaproposta;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotBlank;

public class DocumentoEncrypt {

    @NotBlank
    private String documento;

    public DocumentoEncrypt(@NotBlank String documento) {
        this.documento = documento;
    }

    public String hash(){
        return new BCryptPasswordEncoder().encode(documento);
    }
}
