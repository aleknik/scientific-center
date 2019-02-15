package io.github.aleknik.scientificcenterservice.model.dto;

import org.camunda.bpm.engine.rest.dto.VariableValueDto;

public class TaskFormFieldDto {

    private String name;

    private VariableValueDto value;

    public TaskFormFieldDto() {
    }

    public TaskFormFieldDto(String name, Object value) {
        this.name = name;
        final VariableValueDto variableValueDto = new VariableValueDto();
        variableValueDto.setValue(value);
        this.value = variableValueDto;
    }

    public TaskFormFieldDto(String name, VariableValueDto value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VariableValueDto getValue() {
        return value;
    }

    public void setValue(VariableValueDto value) {
        this.value = value;
    }
}
