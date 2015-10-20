package com.dazlyn.dpo.startup;

import com.dazlyn.dpo.dao.CategoryRepository;
import com.dazlyn.dpo.dao.StudioRepository;
import com.dazlyn.dpo.model.Family;
import com.dazlyn.dpo.model.Person;
import com.dazlyn.dpo.model.PersonManager;
import com.dazlyn.dpo.model.Studio;
import com.dazlyn.dpo.security.RealmManager;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.picketlink.idm.model.basic.Realm;

@javax.ejb.Singleton
@javax.ejb.Startup
@Slf4j
public class DemoLoader {

    private Random rand = new Random(System.currentTimeMillis());

    @Inject
    private StudioRepository studioRepo;

    @Inject
    private CategoryRepository categoryManager;

    @Inject
    private RealmManager realmManager;

    @Inject
    private PersonManager personManager;

    @PostConstruct
    public void init() {
        ObjectMapper mapper = new ObjectMapper();
        DemoContactIterable dci = null;
        try {
            dci = new DemoContactIterable(DemoLoader.class.getResource("/contacts.csv"));
        } catch (IOException ex) {
            Logger.getLogger(DemoLoader.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (InputStream studioIs = DemoLoader.class.getResourceAsStream("/demo_studios.json")) {
            JsonNode rootNode = mapper.readTree(studioIs);
            for (JsonNode studioNode : rootNode) {

                Studio studio = mapper.readValue(studioNode, Studio.class);
                Realm realm = realmManager.createRealm(studio.getCode());
                studio.setRealmId(realm.getId());
                studioRepo.persist(studio);

                addClients(200, dci, studio);

            }
        } catch (IOException ex) {
            log.error("action=init, message=\"exception while loading studios\"", ex);
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

            Person mainPerson = Person.builder()
                    .email(mainEntry.getFirstName().toLowerCase() + '.' + mainEntry.getLastName().toLowerCase() + "@example.com")
                    .firstName(mainEntry.getFirstName())
                    .lastName(mainEntry.getLastName())
                    .studio(studio)
                    .typeEmployee(false)
                    .typeGuardian(numStudents > 0)
                    .typeStudent(numStudents == 0)
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

            for (int ndx = 0; ndx < numStudents; ndx++) {
                if (!it.hasNext()) {
                    it = names.iterator();
                }
                IdmPopulator.NameEntry studentEntry = it.next();

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
    }

    private Person createPerson(Studio studio, Person mainPerson, DemoContact contact) {
        Person person = Person.builder()
                .email(contact.getEmail())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .mainPerson(mainPerson)
                .studio(studio)
                .typeStudent(mainPerson != null)
                .build();
        return person;
    }

}
