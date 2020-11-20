package br.com.zup.proposta.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerHandlerAdvice {

    private MessageSource messageSource;

    private Logger logger = LoggerFactory.getLogger(ControllerHandlerAdvice.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handlerMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<String> errors = e.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> messageSource.getMessage(error, Locale.getDefault()))
                .collect(Collectors.toList());
        //1
        ErroPadrao erroPadrao = new ErroPadrao(errors);
        logger.warn("[TRATATAMENTO DE ERRO] Tratando erro(s) de MethodArgumentValidationException: {}", erroPadrao);

        return ResponseEntity.badRequest().body(erroPadrao);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handlerConstraintViolationException(ConstraintViolationException e){
        ErroPadrao erroPadrao = new ErroPadrao(Arrays.asList(e.getLocalizedMessage().split(":")[1].trim()));
        logger.warn("[TRATAMENTO DE ERRO] Tratando erro(s) de ConstraintVaiolationException: {}", erroPadrao);

        return ResponseEntity.badRequest().body(erroPadrao);
    }


}
