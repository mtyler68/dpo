package com.dazlyn.dpo.startup;

import com.dazlyn.dpo.dao.CategoryRepository;
import com.dazlyn.dpo.dao.PersonRepository;
import com.dazlyn.dpo.dao.StudioRepository;
import com.dazlyn.dpo.model.Person;
import com.dazlyn.dpo.model.Studio;
import com.dazlyn.dpo.security.RealmManager;
import com.dazlyn.dpo.security.RealmRole;
import com.dazlyn.dpo.security.UserManager;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.picketlink.idm.model.basic.Realm;
import org.picketlink.idm.model.basic.User;

@javax.ejb.Singleton
@javax.ejb.Startup
@Slf4j
public class DemoLoader {

    private Random rand = new Random(System.currentTimeMillis());

    @Inject
    private StudioRepository studioRepo;

    @Inject
    private CategoryRepository categoryRepo;

    @Inject
    private RealmManager realmManager;

    @Inject
    private PersonRepository personRepo;

    @Inject
    private UserManager userManager;

    private DemoContactIterable dci;

    private ObjectMapper mapper = new ObjectMapper();

    @PostConstruct
    public void init() {

        initContactsIterator();

        try (InputStream studioIs = DemoLoader.class.getResourceAsStream("/demo_studios.json")) {
            JsonNode rootNode = mapper.readTree(studioIs);
            for (JsonNode configNode : rootNode) {
                initStudio(configNode);
            }
        } catch (IOException ex) {
            log.error("action=init, message=\"exception while loading studios\"", ex);
        }
    }

    private void initStudio(JsonNode configNode) throws IOException {
        JsonNode studioNode = configNode.get("studio");
        Studio studio = createStudio(studioNode);

        if (configNode.has("familyCount")) {
            int familyCount = configNode.get("familyCount").getIntValue();
            addClients(familyCount, dci, studio);
        }

        JsonNode rolesNode = configNode.get("roles");
        if (configNode.has("users")) {
            JsonNode usersNode = configNode.get("users");
            addUsers(studio, usersNode, rolesNode);
        }
    }

    private Studio createStudio(JsonNode studioNode) throws IOException {
        Studio studio = mapper.readValue(studioNode, Studio.class);
        Realm realm = realmManager.createRealm(studio.getCode());
        studio.setRealmId(realm.getId());
        studioRepo.persist(studio);
        categoryRepo.addDefaultCategories(studio);
        studioRepo.flush();
        return studio;
    }

    private void initContactsIterator() throws RuntimeException {
        try {
            dci = new DemoContactIterable(DemoLoader.class.getResource("/contacts.csv"));
        } catch (IOException ex) {
            log.error("action=init(), message=\"could not initialize demo contacts\"", ex);
            throw new RuntimeException(ex);
        }
    }

    private void addClients(int amount, DemoContactIterable dci, Studio studio) {
        Iterator<DemoContact> it = dci.iterator();
        while (amount > 0) {
            amount--;
            DemoContact contact = it.next();
            int numStudents = rand.nextInt(3);

            Person mainPerson = createPerson(studio, null, contact);
            mainPerson.setTypeStudent(numStudents == 0);

            for (int ndx = 0; ndx < numStudents; ndx++) {
                contact = it.next();
                Person student = createPerson(studio, mainPerson, contact);
                student.setUid(UUID.randomUUID().toString());
                student.setLastName(mainPerson.getLastName());
                personRepo.persist(student);
                mainPerson.getMembers().add(student);
            }

            personRepo.persist(mainPerson);
            personRepo.flush();
        }
    }

    private Person createPerson(Studio studio, Person mainPerson, DemoContact contact) {
        Person person = Person.builder()
                .email(contact.getEmail())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .typeStudent(mainPerson != null)
                .members(new ArrayList<Person>())
                .build();
        person.setStudio(studio);
        return person;
    }

    private void addUsers(Studio studio, JsonNode usersNode, JsonNode rolesNode) {
        Realm realm = realmManager.findById(studio.getRealmId());
        for (JsonNode userNode : usersNode) {
            User user = userManager.createUser(realm,
                    userNode.get("username").getTextValue(),
                    userNode.get("password").getTextValue(),
                    userNode.get("email").getTextValue(),
                    userNode.get("firstName").getTextValue(),
                    userNode.get("lastName").getTextValue());

            Person person = createPerson(studio, user, userNode.get("typeEmployee").getBooleanValue());
            personRepo.persist(person);
            personRepo.flush();
        }

        for (JsonNode roleNode : rolesNode) {
            RealmRole role = RealmRole.valueOf(roleNode.get("role").getTextValue());
            for (JsonNode usernameNode : roleNode.get("users")) {
                User user = userManager.find(realm, usernameNode.getTextValue());
                realmManager.grantRoles(realm, user, role);
            }
        }
    }

    private Person createPerson(Studio studio, User user, boolean typeEmployee) {
        Person person = Person.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .typeEmployee(typeEmployee)
                .userId(user.getId())
                .build();
        person.setStudio(studio);
        return person;
    }

}
