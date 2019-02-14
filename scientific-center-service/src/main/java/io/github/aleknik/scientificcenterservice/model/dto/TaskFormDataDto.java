package io.github.aleknik.scientificcenterservice.model.dto;

import java.util.List;

public class TaskFormDataDto {
    private String taskId;

    private List<TaskFormFieldDto> formFields;

    public TaskFormDataDto() {
    }


    public TaskFormDataDto(String taskId, List<TaskFormFieldDto> formFields) {
        this.taskId = taskId;
        this.formFields = formFields;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<TaskFormFieldDto> getFormFields() {
        return formFields;
    }

    public void setFormFields(List<TaskFormFieldDto> formFields) {
        this.formFields = formFields;
    }
}
