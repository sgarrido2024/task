package cl.nisum.exception;

import org.springframework.web.server.ResponseStatusException;

public class CustomException extends ResponseStatusException {

    private ExceptionCodes exceptionCodes;

    public CustomException(ExceptionCodes code) {
        super(code.getCode(),  code.getMessage());

    }


}
