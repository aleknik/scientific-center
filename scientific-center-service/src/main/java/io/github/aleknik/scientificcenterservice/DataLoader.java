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

import java.math.BigDecimal;
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

        final Journal journal1 = addJournal("Journal 1", BigDecimal.valueOf(1), BigDecimal.valueOf(5), false);
        final Journal journal2 = addJournal("Journal 2", BigDecimal.valueOf(1), BigDecimal.valueOf(5), true);
        addEditor("editor@editor", "pass", "Aleksandar", "Nkolic", AddressConstants.NOVI_SAD, "dipl. inz.", journal1);
        addEditor("editor2@editor", "pass", "Aleksandar", "Nkolic", AddressConstants.NOVI_SAD, "dipl. inz.", journal2);

        addAuthor("author@author", "pass", "Luka", "Maletin", AddressConstants.NOVI_SAD);
    }

    private Journal addJournal(String name, BigDecimal paperPrice, BigDecimal subscriptionPrice, boolean openAccess) {
        final Journal journal = new Journal();
        journal.setName(name);
        journal.setPaperPrice(paperPrice);
        journal.setSubscriptionPrice(subscriptionPrice);
        journal.setOpenAccess(openAccess);

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
        editor.setJournal(journal);
        journal.setEditor(editor);

        final List<Role> roles = new ArrayList<>();
        final Role role = new Role(RoleConstants.EDITOR);
        roles.add(role);
        editor.setRoles(roles);

        return (Editor) userService.createUser(editor);
    }

    private Author addAuthor(String email, String password, String firstName, String lastName, Address address) {
        final Author author = new Author();
        author.setEmail(email);
        author.setPassword(passwordEncoder.encode(password));
        author.setFirstName(firstName);
        author.setLastName(lastName);
        author.setAddress(address);

        final List<Role> roles = new ArrayList<>();
        final Role role = new Role(RoleConstants.AUTHOR);
        roles.add(role);
        author.setRoles(roles);

        return (Author) userService.createUser(author);
    }
}
