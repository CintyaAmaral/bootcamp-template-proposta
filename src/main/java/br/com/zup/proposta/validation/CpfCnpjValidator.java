package br.com.zup.proposta.validation;

import br.com.zup.proposta.validation.CpfCnpj;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CpfCnpjValidator implements ConstraintValidator<CpfCnpj, String> {


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context){
        if(value == null){
            return true;
        }

        CNPJValidator cnpjValidator = new CNPJValidator();
        CPFValidator cpfValidator = new CPFValidator();

        cnpjValidator.initialize(null);
        cpfValidator.initialize(null);

        return cnpjValidator.isValid(value, context) || cpfValidator.isValid(value, context);
    }


}
