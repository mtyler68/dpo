package com.dazlyn.dpo.model;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.extern.slf4j.Slf4j;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.credential.Password;

@Named
@ApplicationScoped
@Slf4j
public class PersonManager {

    @Inject
    private PartitionManager partitionManager;

    public Person createPerson(Studio studio, String username, String password, String firstName, String lastName, String email) {
        IdentityManager idm = partitionManager.createIdentityManager(studio);
        Person person = new Person(username);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setEmail(email);

        idm.add(person);

        Password pwd = new Password(password);
        idm.updateCredential(person, pwd);

        log.info("action=createPerson, person={}", person.getId());

        return person;
    }
}
