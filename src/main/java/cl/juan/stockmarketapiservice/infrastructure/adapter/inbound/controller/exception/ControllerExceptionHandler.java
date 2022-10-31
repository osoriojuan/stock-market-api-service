package cl.juan.stockmarketapiservice.infrastructure.adapter.inbound.controller.exception;

import cl.juan.stockmarketapiservice.infrastructure.adapter.inbound.controller.exception.dto.ErrorResponse;
import cl.juan.stockmarketapiservice.infrastructure.adapter.inbound.controller.security.AuthController;
import cl.juan.stockmarketapiservice.infrastructure.adapter.inbound.controller.stockmarket.StockMarketController;
import cl.juan.stockmarketapiservice.infrastructure.adapter.inbound.controller.stockmarket.dto.TimeSeriesRequest;
import cl.juan.stockmarketapiservice.infrastructure.exception.ApiBadResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@ControllerAdvice(assignableTypes = {StockMarketController.class, AuthController.class})
public class ControllerExceptionHandler {

    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ApiBadResponseException.class)
    public ErrorResponse handleApiBadResponseException(ApiBadResponseException ex) {
        log.error("Error consuming third party service:", ex);
        return new ErrorResponse(BAD_REQUEST.value(), ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorResponse handleApiBadResponseException(MethodArgumentTypeMismatchException ex) {
        log.error("Error consuming third party service:", ex);

        return new ErrorResponse(BAD_REQUEST.value(),
                String.format("Please check the value of the time-series parameter. The allowed values are the following [%s]",
                        Arrays.toString(TimeSeriesRequest.values())));
    }

    @ResponseBody
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ErrorResponse handleApiBadResponseException(RuntimeException ex) {
        log.error("Runtime error:", ex);
        return new ErrorResponse(INTERNAL_SERVER_ERROR.value(), "We were unable to process your request. please try again later");
    }

    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler({WebExchangeBindException.class, MethodArgumentNotValidException.class})
    public ErrorResponse handleWebExchangeBindException(BindingResult ex) {
        var errors = ex.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        return new ErrorResponse(BAD_REQUEST.value(), String.format("%s : %s", "Required fields not present", errors));
    }

}
