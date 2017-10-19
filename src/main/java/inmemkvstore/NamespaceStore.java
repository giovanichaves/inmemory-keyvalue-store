package inmemkvstore;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NamespaceStore {
    private Map<String, Serializable> namespaceStore;
    private Map<String, NamespaceIndex> indexes;

    public NamespaceStore() {
        this.namespaceStore = new ConcurrentHashMap<>();
        this.indexes = new ConcurrentHashMap<>();
    }

    public void put(String key, Serializable value, Map<String, Long> indexesNamesForSearch) {
        if (this.keyExists(key)) {
            this.removeIndexElement(key);
        }

        this.namespaceStore.put(key, value);

        indexesNamesForSearch.forEach((index, val) -> this.putIndexElement(index, key, val));
    }

    public Serializable get(String key) {
        return this.namespaceStore.get(key);
    }

    public void delete(String key) {
        this.namespaceStore.remove(key);
        this.removeIndexElement(key);
    }

    public List<String> findInRange(String indexName, Long from, Long to) {
        if (!this.indexExists(indexName)) {
            return Collections.emptyList();
        }

        return this.indexes.get(indexName).find(from, to);
    }

    private boolean keyExists(String key) {
        return this.namespaceStore.containsKey(key);
    }

    private boolean indexExists(String index) {
        return this.indexes.containsKey(index);
    }

    private void putIndexElement(String index, String key, Long val) {
        synchronized (this) {
            if (!this.indexExists(index)) {
                this.indexes.put(index, new NamespaceIndex());
            }
        }

        this.indexes.get(index).put(key, val);
    }

    private void removeIndexElement(String key) {
        this.indexes.forEach((index, namespaceIndex) -> namespaceIndex.delete(key));
    }
}
