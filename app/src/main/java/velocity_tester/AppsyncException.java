package velocity_tester;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "cause", "stackTrace", "suppressed", "localizedMessage" })
class AppsyncException extends Exception {
    private static final long serialVersionUID = 5438538879141929822L;
    private String errorType;
    private Object data;
    private Object errorInfo;

    public AppsyncException(String message, String errorType, Object data, Object errorInfo) {
        super(message);
        this.errorType = errorType;
        this.data = data;
        this.errorInfo = errorInfo;
    }

    public String getErrorType() {
        return errorType;
    }

    public Object getData() {
        return data;
    }

    public Object getErrorInfo() {
        return errorInfo;
    }
}
