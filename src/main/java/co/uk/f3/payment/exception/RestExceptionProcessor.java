package co.uk.f3.payment.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import co.uk.f3.payment.exception.handler.DocumentNotFoundException;
import co.uk.f3.payment.exception.handler.InvalidRequestException;
import co.uk.f3.payment.exception.message.ErrorInfo;
import co.uk.f3.payment.exception.message.ErrorResource;
import co.uk.f3.payment.exception.message.FieldErrorDTO;
import co.uk.f3.payment.exception.message.FieldErrorResource;

@ControllerAdvice
public class RestExceptionProcessor extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(DocumentNotFoundException.class)
	@ResponseBody
	public ResponseEntity<ErrorInfo> userNotFound(HttpServletRequest req, DocumentNotFoundException ex) {

		String errorMessage = "";
		errorMessage = "Payment with id: " + ex.getDocumentId() + " does not exist!";
		String errorURL = req.getRequestURL().toString();
		ErrorInfo errorInfor = new ErrorInfo(errorURL, errorMessage);
		return new ResponseEntity<ErrorInfo>(errorInfor, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ InvalidRequestException.class })
	protected ResponseEntity<Object> handleInvalidRequest(RuntimeException e, WebRequest request) {
		InvalidRequestException ire = (InvalidRequestException) e;
		List<FieldErrorResource> fieldErrorResources = new ArrayList<>();

		List<FieldError> fieldErrors = ire.getErrors().getFieldErrors();
		for (FieldError fieldError : fieldErrors) {
			FieldErrorResource fieldErrorResource = new FieldErrorResource();
			fieldErrorResource.setResource(fieldError.getObjectName());
			fieldErrorResource.setField(fieldError.getField());
			fieldErrorResource.setCode(fieldError.getCode());
			fieldErrorResource.setMessage(fieldError.getDefaultMessage());
			fieldErrorResources.add(fieldErrorResource);
		}

		ErrorResource error = new ErrorResource("InvalidRequest", ire.getMessage());
		error.setFieldErrors(fieldErrorResources);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		return handleExceptionInternal(e, error, headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
	}

	/**
	 * Method populates {@link List} of {@link FieldErrorDTO} objects. Each list
	 * item contains localized error message and name of a form field which caused
	 * the exception. Use the {@link #localizeErrorMessage(String)
	 * localizeErrorMessage} method.
	 * 
	 * @param fieldErrorList
	 *            - {@link List} of {@link FieldError} items
	 * @return {@link List} of {@link FieldErrorDTO} items
	 */
	public List<FieldErrorDTO> populateFieldErrors(List<FieldError> fieldErrorList) {
		List<FieldErrorDTO> fieldErrors = new ArrayList<FieldErrorDTO>();
		StringBuilder errorMessage = new StringBuilder("");

		for (FieldError fieldError : fieldErrorList) {

			errorMessage.append(fieldError.getCode()).append(".");
			errorMessage.append(fieldError.getObjectName()).append(".");
			errorMessage.append(fieldError.getField());

			String localizedErrorMsg = localizeErrorMessage(errorMessage.toString());

			fieldErrors.add(new FieldErrorDTO(fieldError.getField(), localizedErrorMsg));
			errorMessage.delete(0, errorMessage.capacity());
		}
		return fieldErrors;
	}

	/**
	 * Method retrieves appropriate localized error message from the
	 * {@link MessageSource}.
	 * 
	 * @param errorCode
	 *            - key of the error message
	 * @return {@link String} localized error message
	 */
	public String localizeErrorMessage(String errorCode) {
		Locale locale = LocaleContextHolder.getLocale();
		String errorMessage = messageSource.getMessage(errorCode, null, locale);
		return errorMessage;
	}

}
