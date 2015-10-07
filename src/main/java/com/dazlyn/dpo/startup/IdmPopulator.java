package com.dazlyn.dpo.startup;

import com.dazlyn.dpo.model.Family;
import com.dazlyn.dpo.model.FamilyManager;
import com.dazlyn.dpo.model.GroupClassManager;
import com.dazlyn.dpo.model.Person;
import com.dazlyn.dpo.model.PersonManager;
import com.dazlyn.dpo.model.Studio;
import com.dazlyn.dpo.model.StudioManager;
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
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.model.basic.Realm;
import org.picketlink.idm.model.basic.User;

@javax.ejb.Singleton
@javax.ejb.Startup
@Slf4j
public class IdmPopulator {

    @Inject
    private StudioManager studioManager;

    @Inject
    private PersonManager personManager;

    @Inject
    private GroupClassManager groupClassManager;

    @Inject
    private PartitionManager partitionManager;

    @Inject
    private UserManager userManager;

    @Inject
    private RealmManager realmManager;

    @Inject
    private FamilyManager familyManager;

    private List<NameEntry> names;

    private Random rand = new Random(System.currentTimeMillis());

    @PostConstruct
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

            Person indiaPerson = createPerson(cadRealm, cadStudio, "india", "india",
                    "india@coronadodance.com", "India", "Instructor", true, false, false, RealmRole.INSTRUCTOR);

            try {
                loadNames();
                Iterator<NameEntry> it = names.iterator();
                it = createClients(cadStudio, it, 50);
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
        studioManager.add(studio);
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
                .studio(studio)
                .typeEmployee(isEmployee)
                .typeGuardian(isGuardian)
                .typeStudent(isStudent)
                .userId(user.getId())
                .build();
        personManager.add(person);

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
                    String lastName = line.substring(line.indexOf(' ') + 1);
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
            Person mainPerson = Person.builder()
                    .email(String.format("%s.%s" + "@example.com", mainEntry.getFirstName(), mainEntry.getLastName()))
                    .firstName(mainEntry.getFirstName())
                    .lastName(mainEntry.getLastName())
                    .studio(studio)
                    .typeEmployee(false)
                    .typeGuardian(true)
                    .typeStudent(false)
                    .userId(null)
                    .build();
            personManager.add(mainPerson);
            mainPerson = personManager.find(mainPerson.getUid());

            Family family = new Family();
            family.setMainPerson(mainPerson);
            family.setStudio(studio);
            family.getMembers().add(mainPerson);
            familyManager.add(family);

            family = familyManager.find(family.getUid());

            int numStudents = 1 + rand.nextInt(2);
            for (int ndx = 0; ndx < numStudents; ndx++) {
                if (!it.hasNext()) {
                    it = names.iterator();
                }
                NameEntry studentEntry = it.next();

                Person student = Person.builder()
                        .firstName(studentEntry.getFirstName())
                        .lastName(mainEntry.getLastName())
                        .studio(studio)
                        .typeStudent(true)
                        .build();
                personManager.add(student);
                family.getMembers().add(student);
            }
            familyManager.update(family);
            familyCount--;
        }

        return it;
    }

    @Data
    @RequiredArgsConstructor
    private class NameEntry {

        private final String firstName;

        private final String lastName;
    }
}
