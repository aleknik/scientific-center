package io.github.aleknik.scientificcenterservice.service.process;


import io.github.aleknik.scientificcenterservice.model.dto.FormFieldDto;
import io.github.aleknik.scientificcenterservice.model.dto.TaskFormDataDto;
import io.github.aleknik.scientificcenterservice.util.CamundaConstants;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.rest.dto.VariableValueDto;
import org.camunda.bpm.engine.rest.dto.runtime.ProcessInstanceDto;
import org.camunda.bpm.engine.rest.dto.runtime.StartProcessInstanceDto;
import org.camunda.bpm.engine.rest.dto.task.CompleteTaskDto;
import org.camunda.bpm.engine.rest.dto.task.TaskDto;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProcessService {

    @Value("${camunda-rest-base-path}")
    private String basePath;

    private final RuntimeService runtimeService;

    private final TaskService taskService;

    private final FormService formService;

    private final RestTemplate restTemplate;

    public ProcessService(RuntimeService runtimeService, TaskService taskService, FormService formService, RestTemplate restTemplate) {
        this.runtimeService = runtimeService;
        this.taskService = taskService;
        this.formService = formService;
        this.restTemplate = restTemplate;
    }

    public String startProcess(String key) {
        final StartProcessInstanceDto startProcessInstanceDto = new StartProcessInstanceDto();
        final ProcessInstanceDto response = restTemplate.postForObject(String.format(basePath + CamundaConstants.START_PROCESS, key), startProcessInstanceDto, ProcessInstanceDto.class);
        return response.getId();
    }

    public List<TaskDto> getProcessTasks(String processId) {
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(basePath + CamundaConstants.TASK)
                .queryParam("processInstanceId", processId);

        ParameterizedTypeReference<List<TaskDto>> returnType = new ParameterizedTypeReference<List<TaskDto>>() {
        };
        final List<TaskDto> res = restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, null, returnType).getBody();
        return res;
    }

    public Task getTask(String taskId) {
        return taskService.createTaskQuery().taskId(taskId).singleResult();
    }

    public TaskFormDataDto getTaskFormData(String taskId) {

        String url = String.format(basePath + CamundaConstants.FORM_VARIABLES, taskId);

        ParameterizedTypeReference<Map<String, VariableValueDto>> returnType = new ParameterizedTypeReference<Map<String, VariableValueDto>>() {
        };
        final Map<String, VariableValueDto> res = restTemplate.exchange(url, HttpMethod.GET, null, returnType).getBody();
        return new TaskFormDataDto(taskId, res);
    }

    public void submitTaskForm(String taskId, Map<String, VariableValueDto> formInputs) {
        String url = String.format(basePath + CamundaConstants.SUBMIT_FORM, taskId);

        final CompleteTaskDto completeTaskDto = new CompleteTaskDto();
        completeTaskDto.setVariables(formInputs);

        restTemplate.postForLocation(url, completeTaskDto);
    }

    private Map<String, Object> convertInputListToMap(List<FormFieldDto> formInputs) {
        return formInputs.stream().collect(Collectors.toMap(FormFieldDto::getFieldId, FormFieldDto::getValue));
    }
}
