package com.dpt.treader3.exception;

public class ParserException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ParserException() {
        super();
    }

    public ParserException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ParserException(String detailMessage) {
        super(detailMessage);
    }

    public ParserException(Throwable throwable) {
        super(throwable);
    }
    
    

}
