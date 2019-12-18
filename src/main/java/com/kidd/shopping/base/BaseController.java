package com.kidd.shopping.base;

import com.kidd.shopping.base.entity.ValidationErrorDto;
import com.kidd.shopping.base.response.BadRequestResponse;
import com.kidd.shopping.utils.ResponseConstant;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

public abstract class BaseController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BadRequestResponse processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<ObjectError> typeErrors = result.getAllErrors();
        return processFieldErrors(typeErrors);
    }

    private BadRequestResponse processFieldErrors(List<ObjectError> fieldErrors) {
        ValidationErrorDto dto = new ValidationErrorDto();
        for (ObjectError objectError : fieldErrors) {
            if (objectError instanceof FieldError) {
                FieldError fieldError = (FieldError) objectError;
                String localizedErrorMessage = resolveLocalizedErrorMessage(fieldError);
                dto.addError(fieldError.getField(), localizedErrorMessage);
            } else {
                dto.addError(objectError.getObjectName(), objectError.getDefaultMessage());
            }
        }
        return new BadRequestResponse(ResponseConstant.ErrorMessage.INVALID_FIELDS, dto);
    }

    private String resolveLocalizedErrorMessage(FieldError fieldError) {
        return fieldError.getDefaultMessage();
    }

    protected String getAuthenticatedUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
