package com.dazlyn.dpo.startup;

import com.dazlyn.dpo.dao.CategoryRepository;
import com.dazlyn.dpo.dao.CourseRepository;
import com.dazlyn.dpo.dao.PersonRepository;
import com.dazlyn.dpo.dao.StudioRepository;
import com.dazlyn.dpo.model.Category;
import com.dazlyn.dpo.model.CategoryType;
import com.dazlyn.dpo.model.Course;
import com.dazlyn.dpo.model.Family;
import com.dazlyn.dpo.model.FamilyManager;
import com.dazlyn.dpo.model.Person;
import com.dazlyn.dpo.model.Studio;
import com.dazlyn.dpo.model.StudioSettings;
import com.dazlyn.dpo.model.StudioSettingsManager;
import com.dazlyn.dpo.security.RealmManager;
import com.dazlyn.dpo.security.RealmRole;
import com.dazlyn.dpo.security.UserManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.inject.Inject;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Builder;
import lombok.extern.slf4j.Slf4j;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.model.basic.Realm;
import org.picketlink.idm.model.basic.User;

//@javax.ejb.Singleton
//@javax.ejb.Startup
@Slf4j
public class IdmPopulator {

    private static final ClassDef[] GROUP_CLASSES = {
        ClassDef.builder().title("Ballet 5").genre("Ballet").level("Advanced").build(),
        ClassDef.builder().title("Ballet 3").genre("Ballet").level("Intermediate").build(),
        ClassDef.builder().title("Ballet 1").genre("Ballet").level("Beginner").build(),
        ClassDef.builder().title("DanceFUN").genre("Combo").level("Toddler").build()
    };

    @Inject
    private StudioRepository studioManager;

    @Inject
    private PersonRepository personManager;

    @Inject
    private CourseRepository groupClassManager;

    @Inject
    private PartitionManager partitionManager;

    @Inject
    private UserManager userManager;

    @Inject
    private RealmManager realmManager;

    @Inject
    private FamilyManager familyManager;

    @Inject
    private CategoryRepository categoryManager;

    @Inject
    private StudioSettingsManager studioSettingsManager;

    private List<NameEntry> names;

    private Random rand = new Random(System.currentTimeMillis());

//    @PostConstruct
    public void create() {

        // Make sure we are in development mode
        if (true) {

            // Dazlyn
            Realm dazlynRealm = realmManager.createRealm("dazlyn");
            Studio dazlynStudio = createStudio(dazlynRealm, "Dazlyn");

            Person adamPerson = createPerson(dazlynRealm, dazlynStudio, "adam", "adam",
                    "adam@dazlyn.com", "Adam", "Admin", true, false, false, RealmRole.ADMIN);

            // Coronado Dance
            Realm cadRealm = realmManager.createRealm("coronadodance");
            Studio cadStudio = createStudio(cadRealm, "Coronado Academy of Dance");

            StudioSettings cadSettings = new StudioSettings();
            cadSettings.setStudio(cadStudio);
            studioSettingsManager.persist(cadSettings);

            Person indiaPerson = createPerson(cadRealm, cadStudio, "india", "india",
                    "india@coronadodance.com", "India", "Instructor", true, false, false, RealmRole.INSTRUCTOR);
            addClasses(cadStudio, indiaPerson);

            // Epic Dance
            Realm epicRealm = realmManager.createRealm("epicdance");
            Studio epicStudio = createStudio(epicRealm, "Epic Dance");
            addClasses(epicStudio, indiaPerson);

            try {
                loadNames();
                Iterator<NameEntry> it = names.iterator();
                it = createClients(cadStudio, it, 100);
                it = createClients(epicStudio, it, 150);
            } catch (IOException ex) {
                log.error("action=create", ex);
            }
        }
    }

    private Studio createStudio(Realm realm, String name) {
        Studio studio = Studio.builder()
                .code(realm.getName())
                .name(name)
                .realmId(realm.getId())
                .build();
        studioManager.persist(studio);
        categoryManager.addDefaultCategories(studio);
        return studio;
    }

    private Person createPerson(Realm realm, Studio studio, String username, String password,
            String email, String firstName, String lastName, boolean isEmployee, boolean isStudent,
            boolean isGuardian, RealmRole... roles) {

        User user = userManager.createUser(realm, username, password, email, firstName, lastName);

        Person person = Person.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .typeEmployee(isEmployee)
                .typeStudent(isStudent)
                .userId(user.getId())
                .build();
        person.setStudio(studio);
        personManager.persist(person);

        realmManager.grantRoles(realm, user, roles);

        return person;
    }

    private void loadNames() throws IOException {
        if (names == null) {
            names = new ArrayList<>();
            InputStream is = IdmPopulator.class.getResourceAsStream("/randomnames.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = br.readLine();
            while (line != null) {
                try {
                    String firstName = line.substring(0, line.indexOf(' '));
                    String lastName = line.substring(line.indexOf(' ') + 1, line.length() - 1);
                    names.add(new NameEntry(firstName.trim(), lastName.trim()));
                } catch (Exception e) {
                    log.info("action=loadNames", e);
                    break;
                }
                line = br.readLine();
            }
        }
    }

    private Iterator<NameEntry> createClients(Studio studio, Iterator<NameEntry> it, int familyCount) {

        while (familyCount > 0) {
            if (!it.hasNext()) {
                it = names.iterator();
            }

            NameEntry mainEntry = it.next();
            int numStudents = rand.nextInt(3);

            Person mainPerson = Person.builder()
                    .email(mainEntry.getFirstName().toLowerCase() + '.' + mainEntry.getLastName().toLowerCase() + "@example.com")
                    .firstName(mainEntry.getFirstName())
                    .lastName(mainEntry.getLastName())
                    .typeEmployee(false)
                    .typeStudent(numStudents == 0)
                    .userId(null)
                    .build();
            mainPerson.setStudio(studio);
            personManager.persist(mainPerson);
            mainPerson = personManager.find(mainPerson.getUid());

            Family family = new Family();
            family.setMainPerson(mainPerson);
            family.setStudio(studio);
            family.getMembers().add(mainPerson);
            familyManager.add(family);

            family = familyManager.find(family.getUid());

            for (int ndx = 0; ndx < numStudents; ndx++) {
                if (!it.hasNext()) {
                    it = names.iterator();
                }
                NameEntry studentEntry = it.next();

                Person student = Person.builder()
                        .firstName(studentEntry.getFirstName())
                        .lastName(mainEntry.getLastName())
                        .typeStudent(true)
                        .build();
                student.setStudio(studio);
                personManager.persist(student);
                family.getMembers().add(student);
            }
            familyManager.update(family);
            familyCount--;
        }

        return it;
    }

    private void addClasses(Studio cadStudio, Person... instructors) {
        int insIndex = 0;
        for (ClassDef classDef : GROUP_CLASSES) {
            Category genreOption = classDef.getGenre() == null ? null
                    : categoryManager.findForOption(cadStudio, CategoryType.CLASS_GENRE, classDef.getGenre());
            Category levelOption = classDef.getLevel() == null ? null
                    : categoryManager.findForOption(cadStudio, CategoryType.CLASS_LEVEL, classDef.getLevel());
            Course gc = Course.builder()
                    .genre(genreOption)
                    .classLevel(levelOption)
                    .title(classDef.title)
                    .build();
            gc.setStudio(cadStudio);
            groupClassManager.persist(gc);
        }
    }

    @Data
    @RequiredArgsConstructor
    private class NameEntry {

        private final String firstName;

        private final String lastName;
    }

    @Data
    @Builder
    private static class ClassDef {

        private String title;

        private String genre;

        private String level;
    }
}
