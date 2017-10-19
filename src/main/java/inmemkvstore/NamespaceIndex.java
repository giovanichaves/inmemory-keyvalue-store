package inmemkvstore;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NamespaceIndex {
    private Map<String, Long> indexedKeys;

    public NamespaceIndex() {
        this.indexedKeys = new ConcurrentHashMap<>();
    }

    public void put(String key, Long value) {
        this.indexedKeys.put(key, value);
    }

    public List<String> find(Long from, Long to) {

        List<String> keysList = new ArrayList<>();

         this.indexedKeys.forEach((key, val) -> {
             if (val >= from && val < to){
                 keysList.add(key);
             }
        });

         return keysList;
    }

    public void delete(String key) {

        this.indexedKeys.remove(key);
    }



}
