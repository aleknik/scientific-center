package io.github.aleknik.scientificcenterservice.service.process;

import io.github.aleknik.scientificcenterservice.model.dto.FormFieldDto;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProcessService {

    private final RuntimeService runtimeService;

    private final TaskService taskService;

    private final FormService formService;

    public ProcessService(RuntimeService runtimeService, TaskService taskService, FormService formService) {
        this.runtimeService = runtimeService;
        this.taskService = taskService;
        this.formService = formService;
    }

    public String startProcess(String key) {
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(key);
        return pi.getId();
    }

    public List<Task> getProcessTasks(String processId) {
        return taskService.createTaskQuery().processInstanceId(processId).list();
    }

    public Task getTask(String taskId) {
        return taskService.createTaskQuery().taskId(taskId).singleResult();
    }

    public TaskFormData getTaskFormData(String taskId) {
        return formService.getTaskFormData(taskId);
    }

    public void submmitTaskForm(String taskId, List<FormFieldDto> formInputs) {
        formService.submitTaskForm(taskId, convertInputListToMap(formInputs));
    }

    private Map<String, Object> convertInputListToMap(List<FormFieldDto> formInputs) {
        return formInputs.stream().collect(Collectors.toMap(FormFieldDto::getFieldId, FormFieldDto::getValue));
    }
}
