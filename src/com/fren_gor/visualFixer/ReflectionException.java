package com.fren_gor.visualFixer;

public class ReflectionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ReflectionException() {
	}

	public ReflectionException(String error) {
		super(error);
	}

	public ReflectionException(Throwable throwable) {
		super(throwable);
	}

	public ReflectionException(String var1, Throwable var2) {
		super(var1, var2);
	}

}
