package com.dazlyn.dpo.startup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DemoContactIterable implements Iterable<DemoContact> {

    private URL sourceUrl;

    private List<DemoContact> contacts;

    private int index = 0;

    public DemoContactIterable(URL sourceUrl) throws IOException {
        this.sourceUrl = sourceUrl;

        contacts = new ArrayList<>();
        try (InputStream is = sourceUrl.openStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = br.readLine();
            while (line != null) {
                if (!line.trim().startsWith("#") && !"".equals(line.trim())) {
                    String[] tokens = line.split(",");
                    contacts.add(DemoContact.builder()
                            .firstName(tokens[0].trim())
                            .lastName(tokens[1].trim())
                            .streetAddress(tokens[2].trim())
                            .city(tokens[3].trim())
                            .state(tokens[4].trim())
                            .zipCode(tokens[5].trim())
                            .country(tokens[6].trim())
                            .phone(tokens[7].trim())
                            .build());
                }

                line = br.readLine();
            }
        }
    }

    @Override
    public Iterator<DemoContact> iterator() {
        return new Iterator<DemoContact>() {

            @Override
            public boolean hasNext() {
                return !contacts.isEmpty();
            }

            @Override
            public DemoContact next() {
                if (contacts.isEmpty()) {
                    throw new IllegalStateException();
                }
                DemoContact item = contacts.get(index++);
                index = (index < contacts.size() ? index : 0);
                return item;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }

}
