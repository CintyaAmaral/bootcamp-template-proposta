package br.com.zup.proposta.novaproposta;

import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;



import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CpfCnpjValidator
        implements ConstraintValidator<CpfCnpj, String> {

    //determina a qual clsse esse validador deve ser aplicado
    @Override
    public boolean isValid(String value,
                           ConstraintValidatorContext context){
        if(value == null){
            return true;
        }

        value = value.replaceAll("[^0-9]", "");

        if(value.length() != 11 && value.length() !=14){
            return false;
        }

        if(value.equals("00000000000")
                || value.equals("11111111111")
                || value.equals("22222222222")
                || value.equals("33333333333")
                || value.equals("44444444444")
                || value.equals("55555555555")
                || value.equals("66666666666")
                || value.equals("77777777777")
                || value.equals("88888888888")
                || value.equals("99999999999")) {
            return false;
        }

        if (value.equals("00000000000000")
                || value.equals("11111111111111")
                || value.equals("22222222222222")
                || value.equals("33333333333333")
                || value.equals("44444444444444")
                || value.equals("55555555555555")
                || value.equals("66666666666666")
                || value.equals("77777777777777")
                || value.equals("88888888888888")
                || value.equals("99999999999999")) {
            return false;
        }




        CNPJValidator cnpjValidator = new CNPJValidator();
        CPFValidator cpfValidator = new CPFValidator();

        cnpjValidator.initialize(null);
        cpfValidator.initialize(null);

        return cnpjValidator.isValid(value, context) || cpfValidator.isValid(value, context);
    }


}
