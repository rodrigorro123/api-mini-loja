package br.com.api.application.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ApiException extends Exception
{
	private static final long serialVersionUID = 9005879812902850496L;
	
	public static final String VALIDATION_ERROR = "validation_error";
    public static final String GENERAL_ERROR = "general_error";
    public static final String NOTFOUND_ERROR = "notfound_error";
    private final String code;
    private final String reason;
    private final Integer statusCode;

    @Builder
    public ApiException(String code, String reason, String message, Integer statusCode)
    {
        super(message);
        this.code = code;
        this.reason = reason;
        this.statusCode = statusCode;
    }
}
