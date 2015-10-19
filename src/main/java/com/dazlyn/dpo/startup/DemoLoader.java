package com.dazlyn.dpo.startup;

import com.dazlyn.dpo.dao.CategoryRepository;
import com.dazlyn.dpo.dao.StudioRepository;
import com.dazlyn.dpo.model.Studio;
import com.dazlyn.dpo.security.RealmManager;
import java.io.IOException;
import java.io.InputStream;
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

    @Inject
    private StudioRepository studioRepo;

    @Inject
    private CategoryRepository categoryManager;

    @Inject
    private RealmManager realmManager;

    @PostConstruct
    public void init() {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream studioIs = DemoLoader.class.getResourceAsStream("/demo_studios.json")) {
            JsonNode rootNode = mapper.readTree(studioIs);
            for (JsonNode studioNode : rootNode) {

                Studio studio = mapper.readValue(studioNode, Studio.class);
                Realm realm = realmManager.createRealm(studio.getCode());
                studio.setRealmId(realm.getId());
                studioRepo.persist(studio);

            }
        } catch (IOException ex) {
            log.error("action=init, message=\"exception while loading studios\"", ex);
        }
    }

}
