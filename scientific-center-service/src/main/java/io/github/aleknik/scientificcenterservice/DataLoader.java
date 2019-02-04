package io.github.aleknik.scientificcenterservice;

import io.github.aleknik.scientificcenterservice.model.domain.*;
import io.github.aleknik.scientificcenterservice.repository.JournalRepository;
import io.github.aleknik.scientificcenterservice.repository.UserRepository;
import io.github.aleknik.scientificcenterservice.security.RoleConstants;
import io.github.aleknik.scientificcenterservice.service.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {

    private final UserRepository userRepository;
    private final JournalRepository journalRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    public DataLoader(UserRepository userRepository, JournalRepository journalRepository, PasswordEncoder passwordEncoder, UserService userService) {
        this.userRepository = userRepository;
        this.journalRepository = journalRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        final Journal journal = addJournal("Journal 1");
        addEditor("editor@editor", "pass", "Aleksandar", "Nkolic", AddressConstants.NOVI_SAD, "dipl. inz.", journal);


    }

    private Journal addJournal(String name) {
        final Journal journal = new Journal();
        journal.setName(name);

        return journalRepository.save(journal);
    }

    private Editor addEditor(String email, String password, String firstName, String lastName, Address address, String title, Journal journal) {
        final Editor editor = new Editor();
        editor.setEmail(email);
        editor.setPassword(passwordEncoder.encode(password));
        editor.setFirstName(firstName);
        editor.setLastName(lastName);
        editor.setAddress(address);
        editor.setTitle(title);
        editor.setJurnal(journal);

        final List<Role> roles = new ArrayList<>();
        final Role role = new Role(RoleConstants.EDITOR);
        roles.add(role);
        editor.setRoles(roles);

        return (Editor) userService.createUser(editor);
    }
}
