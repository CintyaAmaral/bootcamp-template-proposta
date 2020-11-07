package br.com.zup.proposta.novaproposta;

public enum ResultadoStatusProposta {

    COM_RESTRICAO{
        @Override
        public StatusProposta toStatusProposta(){
            return StatusProposta.ELEGIVEL;
        }
    },

    SEM_RESTRICAO{
        @Override
        public StatusProposta toStatusProposta(){
            return StatusProposta.NAO_ELEGIVEL;
        }
    };

    public abstract StatusProposta toStatusProposta();
}
