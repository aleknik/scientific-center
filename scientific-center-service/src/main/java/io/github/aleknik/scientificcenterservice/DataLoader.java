package io.github.aleknik.scientificcenterservice;

import io.github.aleknik.scientificcenterservice.model.domain.*;
import io.github.aleknik.scientificcenterservice.repository.JournalRepository;
import io.github.aleknik.scientificcenterservice.repository.RoleRepository;
import io.github.aleknik.scientificcenterservice.repository.ScienceFieldRepository;
import io.github.aleknik.scientificcenterservice.repository.UserRepository;
import io.github.aleknik.scientificcenterservice.security.RoleConstants;
import io.github.aleknik.scientificcenterservice.service.UserService;
import io.github.aleknik.scientificcenterservice.service.elasticsearch.ReviewerSearchService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {

    private final UserRepository userRepository;
    private final JournalRepository journalRepository;
    private final ScienceFieldRepository scienceFieldRepository;
    private final RoleRepository roleRepository;

    private final ReviewerSearchService reviewerSearchService;
    private final UserService userService;

    public DataLoader(UserRepository userRepository,
                      JournalRepository journalRepository,
                      ScienceFieldRepository scienceFieldRepository,
                      RoleRepository roleRepository, ReviewerSearchService reviewerSearchService, UserService userService) {
        this.userRepository = userRepository;
        this.journalRepository = journalRepository;
        this.scienceFieldRepository = scienceFieldRepository;
        this.roleRepository = roleRepository;
        this.reviewerSearchService = reviewerSearchService;
        this.userService = userService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (!userRepository.findAll().isEmpty()) {
            return;
        }

        addRoles();

        final ScienceField field1 = new ScienceField("field1");
        final ScienceField field2 = new ScienceField("field2");
        final ScienceField field3 = new ScienceField("field3");
        scienceFieldRepository.save(field1);
        scienceFieldRepository.save(field2);
        scienceFieldRepository.save(field3);


        Journal journal1 = addJournal("Journal 1", BigDecimal.valueOf(1), BigDecimal.valueOf(5), false);
        Journal journal2 = addJournal("Journal 2", BigDecimal.valueOf(1), BigDecimal.valueOf(5), true);
        addEditor("nikolic95@gmail.com", "editor", "pass", "Aleksandar", "Nkolic", AddressConstants.NOVI_SAD, "dipl. inz.", journal1);
        addEditor("nikolic95@gmail.com", "editor2", "pass", "Monika", "Erdeg", AddressConstants.NOVI_SAD, "dipl. inz.", journal2);

        final Editor editor3 = addEditor("nikolic95@gmail.com", "editor3", "pass", "Bojan", "Nikolic", AddressConstants.NOVI_SAD, "dipl. inz.", journal2);

        journal2 = journalRepository.findById(journal2.getId()).get();
        journal2.getJournalEditors().add(new JournalEditor(journal2, editor3, field1));
        journal2 = journalRepository.save(journal2);

        addAuthor("nikolic95@gmail.com", "author", "pass", "Luka", "Maletin", AddressConstants.NOVI_SAD);

        addReviewer("nikolic95@gmail.com", "reviewer", "pass", "Milijana", "Smiljanic", AddressConstants.BERLIN, "dipl. inz.",
                Arrays.asList(journal1, journal2), Arrays.asList(field1));


        addReviewer("nikolic95@gmail.com", "reviewer1", "pass", "Helena", "Zecevic", AddressConstants.BELGRADE, "dipl. inz.",
                Arrays.asList(journal1, journal2), Arrays.asList(field1, field2));

        addReviewer("nikolic95@gmail.com", "reviewer2", "pass", "Sara", "Peric", AddressConstants.NOVI_SAD, "dipl. inz.",
                Arrays.asList(journal1, journal2), Arrays.asList(field1, field2, field3));
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
        final Editor editor = new Editor(email, username, password, firstName, lastName, address);
        editor.setTitle(title);
        editor.setJournal(journal);
        journal.setEditor(editor);

        return (Editor) userService.createUser(editor, RoleConstants.EDITOR);
    }

    private Reviewer addReviewer(String email, String username, String password, String firstName, String lastName, Address address, String title,
                                 List<Journal> journals, List<ScienceField> fields) {
        final Reviewer reviewer = new Reviewer(email, username, password, firstName, lastName, address);
        reviewer.setTitle(title);
        reviewer.setJournals(new HashSet<>(journals));
        reviewer.setScienceFields(new HashSet<>(fields));

        final Reviewer user = (Reviewer) userService.createUser(reviewer, RoleConstants.REVIEWER);
        reviewerSearchService.IndexReviewer(user.getId());

        return user;
    }

    private Author addAuthor(String email, String username, String password, String firstName, String lastName, Address address) {
        final Author author = new Author(email, username, password, firstName, lastName, address);

        return (Author) userService.createUser(author, RoleConstants.AUTHOR);
    }


    private void addRoles() {
        final Role author = new Role(RoleConstants.AUTHOR);
        roleRepository.save(author);

        final Role editor = new Role(RoleConstants.EDITOR);
        roleRepository.save(editor);

        final Role reviewer = new Role(RoleConstants.REVIEWER);
        roleRepository.save(reviewer);
    }
}
