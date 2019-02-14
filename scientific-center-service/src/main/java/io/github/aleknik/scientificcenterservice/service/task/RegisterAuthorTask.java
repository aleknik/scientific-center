package io.github.aleknik.scientificcenterservice.service.task;

import io.github.aleknik.scientificcenterservice.model.domain.Address;
import io.github.aleknik.scientificcenterservice.model.domain.Author;
import io.github.aleknik.scientificcenterservice.service.UserService;
import io.github.aleknik.scientificcenterservice.service.process.ProcessService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.rest.dto.identity.UserCredentialsDto;
import org.camunda.bpm.engine.rest.dto.identity.UserDto;
import org.camunda.bpm.engine.rest.dto.identity.UserProfileDto;
import org.springframework.stereotype.Service;

@Service
public class RegisterAuthorTask implements JavaDelegate {

    private final UserService userService;
    private final ProcessService processService;

    public RegisterAuthorTask(UserService userService, ProcessService processService) {
        this.userService = userService;
        this.processService = processService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Author author = createAuthor(delegateExecution);

        final UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setId(author.getUsername());
        userProfileDto.setEmail(author.getEmail());
        userProfileDto.setFirstName(author.getFirstName());
        userProfileDto.setLastName(author.getLastName());
        final UserCredentialsDto userCredentialsDto = new UserCredentialsDto();
        userCredentialsDto.setPassword(author.getPassword());
        final UserDto userDto = new UserDto();
        userDto.setProfile(userProfileDto);
        userDto.setCredentials(userCredentialsDto);

        userService.createUser(author);
        processService.createUser(userDto);
    }


    private Author createAuthor(DelegateExecution delegateExecution) {
        String email = (String) delegateExecution.getVariable("email");
        String username = (String) delegateExecution.getVariable("username");
        String password = (String) delegateExecution.getVariable("password");
        String firstName = (String) delegateExecution.getVariable("firstName");
        String lastName = (String) delegateExecution.getVariable("lastName");
        String city = (String) delegateExecution.getVariable("city");
        String country = (String) delegateExecution.getVariable("country");

        return new Author(email, username, password, firstName, lastName, new Address(city, country));
    }
}
