package io.github.aleknik.scientificcenterservice.model.dto;

import org.camunda.bpm.engine.form.FormField;

import java.util.List;

public class TaskFormDataDto {
    private String taskId;

    private List<FormField> formFields;

    public TaskFormDataDto() {
    }

    public TaskFormDataDto(String taskId, List<FormField> formFields) {
        this.taskId = taskId;
        this.formFields = formFields;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<FormField> getFormFields() {
        return formFields;
    }

    public void setFormFields(List<FormField> formFields) {
        this.formFields = formFields;
    }
}
