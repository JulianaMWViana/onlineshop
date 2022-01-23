package ie.com.vegetableshop.onlineshop.config.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

	//MessageSource to get the error message according to the local idiom
	@Autowired
	private MessageSource messageSource;
	
	// when MethodArgumentNotValidException happens, the handle() method deal with
	// it. It returns HTTP Bad Request
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<FormErrorDTO> handle(MethodArgumentNotValidException e) {
		List<FormErrorDTO> errorList = new ArrayList<>();
		
		List<FieldError> fieldError = e.getBindingResult().getFieldErrors();
		//fill the error list
		fieldError.forEach(exception -> {
			String msg = messageSource.getMessage(exception, LocaleContextHolder.getLocale());
			FormErrorDTO error = new FormErrorDTO(exception.getField(), msg);
			errorList.add(error);
			
		});
		
		return errorList;
	}

}
