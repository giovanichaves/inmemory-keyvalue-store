import inmemkvstore.KeyValueStore;

import java.io.Serializable;
import java.util.*;

public class Main {

    private static KeyValueStore keyValueStore;

    public static void main(String[] args) {
        keyValueStore = new KeyValueStore();

        Map<String, Long> giorgio = new HashMap<>();
        giorgio.put("lala", 3L);
        giorgio.put("lele", 1L);
        giorgio.put("lili", 2L);

        keyValueStore.put("giorgio", "casanova", "babe", giorgio);
        keyValueStore.put("giorgio", "armani", "vest", Collections.singletonMap("lala", 2L));
        keyValueStore.put("giorgio", "grigori", "yuri", Collections.singletonMap("lala", 1L));
        keyValueStore.put("grigori", "italiani", "bianchi", Collections.singletonMap("lala", 3L));

        keyValueStore.put("giorgio", "casanova", "vesteee", Collections.singletonMap("lala", 1L));

        List<String> keys = keyValueStore.findInRange("giorgio", "lala", 1L, 4L);
        Optional<Serializable> res3 = keyValueStore.get("giorgio", "casanova");

    }
}
