package io.github.aleknik.scientificcenterservice.model.dto;

public class FormFieldDto {

    private String fieldId;

    private String value;

    public FormFieldDto() {
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
