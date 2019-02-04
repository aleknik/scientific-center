package io.github.aleknik.scientificcenterservice.model.dto;

public class RegisteredMethodResponse {

    private String method;

    private boolean linked;

    private String url;

    public RegisteredMethodResponse(String method, boolean linked, String url) {
        this.method = method;
        this.linked = linked;
        this.url = url;
    }

    public RegisteredMethodResponse() {
    }

    public boolean isLinked() {
        return linked;
    }

    public void setLinked(boolean linked) {
        this.linked = linked;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
