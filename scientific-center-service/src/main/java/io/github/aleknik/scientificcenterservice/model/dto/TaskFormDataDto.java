package io.github.aleknik.scientificcenterservice.model.dto;

import org.camunda.bpm.engine.rest.dto.VariableValueDto;

import java.util.Map;

public class TaskFormDataDto {
    private String taskId;

    private Map<String, VariableValueDto> formFields;

    public TaskFormDataDto() {
    }

    public TaskFormDataDto(String taskId, Map<String, VariableValueDto> formFields) {
        this.taskId = taskId;
        this.formFields = formFields;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Map<String, VariableValueDto> getFormFields() {
        return formFields;
    }

    public void setFormFields(Map<String, VariableValueDto> formFields) {
        this.formFields = formFields;
    }
}
