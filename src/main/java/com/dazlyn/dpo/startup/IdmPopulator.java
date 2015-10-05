package com.dazlyn.dpo.startup;

import com.dazlyn.dpo.model.GroupClass;
import com.dazlyn.dpo.model.GroupClassManager;
import com.dazlyn.dpo.model.Person;
import com.dazlyn.dpo.model.PersonManager;
import com.dazlyn.dpo.model.Studio;
import com.dazlyn.dpo.model.StudioManager;
import com.dazlyn.dpo.model.StudioRole;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

@javax.ejb.Singleton
@javax.ejb.Startup
public class IdmPopulator {

    @Inject
    private StudioManager studioManager;

    @Inject
    private PersonManager personManager;

    @Inject
    private GroupClassManager groupClassManager;


    @PostConstruct
    public void create() {

        // Make sure we are in development mode
        if (true) {

            // Dazlyn
            Studio dazlyn = studioManager.createStudio("dazlyn", "Dazlyn");
            Person adam = personManager.createPerson(dazlyn, "adam", "adam", "Adam", "Admin", "adam@dazlyn.com");
            studioManager.grantRoles(dazlyn, adam, StudioRole.STUDIO_EMPLOYEE, StudioRole.ADMIN);

            // CAD
            Studio cad = studioManager.createStudio("coronadodance", "Coronado Academy of Dance");
            Person india = personManager.createPerson(cad, "india", "india", "India", "Instructor", "india@coronadodance.com");
            studioManager.grantRoles(cad, india, StudioRole.STUDIO_EMPLOYEE, StudioRole.STUDIO_INSTRUCTOR);

            GroupClass cadBallet5 = groupClassManager.createGroupClass(cad, "Ballet 5");
            GroupClass cadContemp2 = groupClassManager.createGroupClass(cad, "Contemporary 2");
        }
    }
}
