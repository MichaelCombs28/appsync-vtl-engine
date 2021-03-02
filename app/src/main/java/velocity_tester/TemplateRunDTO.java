package velocity_tester;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class TemplateRunDTO {
    private String templateName;
    private String template;
    private Map<String, Object> context;
    private Map<String, Object> utilMocks;

    public String getTemplate() {
        return template;
    }

    public Map<String, Object> getUtilMocks() {
        return utilMocks;
    }

    public String getTemplateName() {
        return templateName;
    }

    public Map<String, Object> getContext() {
        return context;
    }
}
