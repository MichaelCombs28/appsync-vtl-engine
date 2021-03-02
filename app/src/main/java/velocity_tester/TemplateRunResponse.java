package velocity_tester;

import java.util.List;
import java.util.Map;

class TemplateRunResponse {
    private String templateResult;
    private Map<String, Object> stash;
    private List<AppsyncException> errors;
    private Object returnResponse;

    public TemplateRunResponse(String templateResult, Map<String, Object> stash, List<AppsyncException> errors,
            Object returnResponse) {
        this.templateResult = templateResult;
        this.stash = stash;
        this.errors = errors;
        this.returnResponse = returnResponse;
    }

    public Map<String, Object> getStash() {
        return stash;
    }

    public String getTemplateResult() {
        return templateResult;
    }

    public List<AppsyncException> getErrors() {
        return errors;
    }

    public Object getReturnResponse() {
        return this.returnResponse;
    }

}
