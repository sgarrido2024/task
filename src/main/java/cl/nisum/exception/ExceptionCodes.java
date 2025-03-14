package cl.nisum.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionCodes {
    CREATED (HttpStatus.CREATED,"CREACION EXITOSA"),
    FOUND_EMAIL (HttpStatus.FOUND,"EL CORREO YA REGISTRADO"),
    ERROR_EMAIL (HttpStatus.BAD_REQUEST,"CORREO MAL INGRESADO"),
    ERROR_PASSWORD (HttpStatus.BAD_REQUEST,"PASSWORD INCORRECTA");

    ExceptionCodes(HttpStatus code, String message)
    {
        this.code = code;
        this.message=message;
    }

    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private HttpStatus code;
    private String message;

}
