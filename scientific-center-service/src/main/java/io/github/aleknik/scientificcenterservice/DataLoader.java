package io.github.aleknik.scientificcenterservice;

import io.github.aleknik.scientificcenterservice.model.domain.*;
import io.github.aleknik.scientificcenterservice.repository.JournalRepository;
import io.github.aleknik.scientificcenterservice.repository.ScienceFieldRepository;
import io.github.aleknik.scientificcenterservice.repository.UserRepository;
import io.github.aleknik.scientificcenterservice.security.RoleConstants;
import io.github.aleknik.scientificcenterservice.service.UserService;
import io.github.aleknik.scientificcenterservice.service.elasticsearch.ReviewerSearchService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {

    private final UserRepository userRepository;
    private final JournalRepository journalRepository;
    private final PasswordEncoder passwordEncoder;
    private final ScienceFieldRepository scienceFieldRepository;

    private final ReviewerSearchService reviewerSearchService;
    private final UserService userService;

    public DataLoader(UserRepository userRepository,
                      JournalRepository journalRepository,
                      PasswordEncoder passwordEncoder,
                      ScienceFieldRepository scienceFieldRepository,
                      ReviewerSearchService reviewerSearchService, UserService userService) {
        this.userRepository = userRepository;
        this.journalRepository = journalRepository;
        this.passwordEncoder = passwordEncoder;
        this.scienceFieldRepository = scienceFieldRepository;
        this.reviewerSearchService = reviewerSearchService;
        this.userService = userService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (!userRepository.findAll().isEmpty()) {
            return;
        }

        final ScienceField field1 = new ScienceField("field1");
        final ScienceField field2 = new ScienceField("field2");
        scienceFieldRepository.save(field1);
        scienceFieldRepository.save(field2);


        final Journal journal1 = addJournal("Journal 1", BigDecimal.valueOf(1), BigDecimal.valueOf(5), false);
        final Journal journal2 = addJournal("Journal 2", BigDecimal.valueOf(1), BigDecimal.valueOf(5), true);
        addEditor("editor@editor", "editor", "pass", "Aleksandar", "Nkolic", AddressConstants.NOVI_SAD, "dipl. inz.", journal1);
        addEditor("editor2@editor", "editor2", "pass", "Monika", "Erdeg", AddressConstants.NOVI_SAD, "dipl. inz.", journal2);

        addAuthor("author@author", "author", "pass", "Luka", "Maletin", AddressConstants.NOVI_SAD);
        addReviewer("author2@author2", "author2", "pass", "Milijana", "Smiljanic", AddressConstants.BERLIN, "dipl. inz.",
                Arrays.asList(journal1, journal2), Arrays.asList(field1, field2));


        addReviewer("reviewer@reviewer", "reviewer", "pass", "Helena", "Zecevic", AddressConstants.NOVI_SAD, "dipl. inz.",
                Arrays.asList(journal1, journal2), Arrays.asList(field1, field2));
    }

    private Journal addJournal(String name, BigDecimal paperPrice, BigDecimal subscriptionPrice, boolean openAccess) {
        final Journal journal = new Journal();
        journal.setName(name);
        journal.setPaperPrice(paperPrice);
        journal.setSubscriptionPrice(subscriptionPrice);
        journal.setOpenAccess(openAccess);

        return journalRepository.save(journal);
    }

    private Editor addEditor(String email, String username, String password, String firstName, String lastName, Address address, String title, Journal journal) {
        final Editor editor = new Editor(email, username, passwordEncoder.encode(password), firstName, lastName, address);
        editor.setTitle(title);
        editor.setJournal(journal);
        journal.setEditor(editor);

        final List<Role> roles = new ArrayList<>();
        final Role role = new Role(RoleConstants.EDITOR);
        roles.add(role);
        editor.setRoles(roles);

        return (Editor) userService.createUser(editor);
    }

    private Reviewer addReviewer(String email, String username, String password, String firstName, String lastName, Address address, String title,
                                 List<Journal> journals, List<ScienceField> fields) {
        final Reviewer reviewer = new Reviewer(email, username, passwordEncoder.encode(password), firstName, lastName, address);
        reviewer.setTitle(title);
        reviewer.setJournals(new HashSet<>(journals));
        reviewer.setScienceFields(new HashSet<>(fields));

        final List<Role> roles = new ArrayList<>();
        final Role role = new Role(RoleConstants.REVIEWER);
        roles.add(role);
        reviewer.setRoles(roles);

        final Reviewer user = (Reviewer) userService.createUser(reviewer);
        reviewerSearchService.IndexReviewer(user.getId());

        return user;
    }

    private Author addAuthor(String email, String username, String password, String firstName, String lastName, Address address) {
        final Author author = new Author(email, username, passwordEncoder.encode(password), firstName, lastName, address);

        final List<Role> roles = new ArrayList<>();
        final Role role = new Role(RoleConstants.AUTHOR);
        roles.add(role);
        author.setRoles(roles);

        return (Author) userService.createUser(author);
    }
}
