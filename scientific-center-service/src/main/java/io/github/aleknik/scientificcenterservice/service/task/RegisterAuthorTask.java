package io.github.aleknik.scientificcenterservice.service.task;

import io.github.aleknik.scientificcenterservice.model.domain.Address;
import io.github.aleknik.scientificcenterservice.model.domain.Author;
import io.github.aleknik.scientificcenterservice.service.AuthorService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.stereotype.Service;

@Service
public class RegisterAuthorTask implements JavaDelegate {

    private final AuthorService authorService;

    private final IdentityService identityService;

    public RegisterAuthorTask(AuthorService authorService, IdentityService identityService) {
        this.authorService = authorService;
        this.identityService = identityService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Author author = createAuthor(delegateExecution);
        try {
            author = authorService.createAuthor(author);
        } catch (Exception e) {
            throw new BpmnError("UserDataInvalid");
        }
        final User user = identityService.newUser(String.valueOf(author.getUsername()));
        user.setPassword(author.getPassword());
        identityService.saveUser(user);
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
