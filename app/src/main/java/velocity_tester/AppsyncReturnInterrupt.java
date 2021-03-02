package velocity_tester;

public class AppsyncReturnInterrupt extends RuntimeException {
    private static final long serialVersionUID = 7875913610897768061L;
    private Object returnValue;

    public Object getReturnValue() {
        return returnValue;
    }

    public AppsyncReturnInterrupt(Object returnValue) {
        super();
        this.returnValue = returnValue;
    }
}
