package br.com.zup.proposta.analisefinanceira;

import br.com.zup.proposta.novaproposta.ResultadoStatusProposta;

public class AnaliseFinanceiraResponse {

    private String documento;
    private String nome;
    private String idProposta;
    //1
    private ResultadoStatusProposta resultadoSolicitacao;

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdProposta() {
        return idProposta;
    }

    public void setIdProposta(String idProposta) {
        this.idProposta = idProposta;
    }

    public ResultadoStatusProposta getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }

    public void setResultadoSolicitacao(ResultadoStatusProposta resultadoSolicitacao) {
        this.resultadoSolicitacao = resultadoSolicitacao;
    }
}
