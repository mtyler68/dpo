package com.dazlyn.dpo.startup;

import com.dazlyn.dpo.model.GroupClassManager;
import com.dazlyn.dpo.model.Person;
import com.dazlyn.dpo.model.PersonManager;
import com.dazlyn.dpo.model.Studio;
import com.dazlyn.dpo.model.StudioManager;
import com.dazlyn.dpo.security.RealmRole;
import com.dazlyn.dpo.security.RealmManager;
import com.dazlyn.dpo.security.UserManager;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.model.basic.Realm;
import org.picketlink.idm.model.basic.User;

@javax.ejb.Singleton
@javax.ejb.Startup
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
}
