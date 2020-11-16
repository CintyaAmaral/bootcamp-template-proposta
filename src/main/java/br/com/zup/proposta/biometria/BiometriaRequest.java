package br.com.zup.proposta.biometria;

import javax.validation.constraints.NotBlank;

public class BiometriaRequest {

    @NotBlank
    private String impressaoDigital;

    public String getImpressaoDigital() {
        return impressaoDigital;
    }

    public void setImpressaoDigital(String impressaoDigital) {
        this.impressaoDigital = impressaoDigital;
    }

    public Biometria toBiometria(){
        return new Biometria(impressaoDigital);
    }
}
