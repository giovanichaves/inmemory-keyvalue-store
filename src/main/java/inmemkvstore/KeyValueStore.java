package inmemkvstore;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class KeyValueStore {
    private Map<String, NamespaceStore> store;


    public KeyValueStore() {
        this.store = new ConcurrentHashMap<>();
    }

    public void put(String namespace, String key, Serializable value, Map<String,Long> indexesNamesForSearch) {

        synchronized (this) {
            if (!this.namespaceExists(namespace)) {
                this.store.put(namespace, new NamespaceStore());
            }
        }

        this.store.get(namespace).put(key, value, indexesNamesForSearch);

    }

    public Optional<Serializable> get(String namespace, String key) {
        if (!this.namespaceExists(namespace)) {
            return Optional.empty();
        }

        return Optional.ofNullable(this.store.get(namespace).get(key));

    }

    public void delete(String namespace, String key) {
        if (this.namespaceExists(namespace)) {
            this.store.get(namespace).delete(key);
        }
    }

    public List<String> findInRange(String namespace, String indexName, Long from, Long to) {
        if (!this.namespaceExists(namespace)) {
            return Collections.emptyList();
        }

        return this.store.get(namespace).findInRange(indexName, from, to);

    }


    private boolean namespaceExists(String namespace) {
        return this.store.containsKey(namespace);
    }

}
