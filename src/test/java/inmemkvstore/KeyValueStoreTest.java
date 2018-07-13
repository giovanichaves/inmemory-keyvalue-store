package inmemkvstore;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Optional;

public class KeyValueStoreTest {

    private KeyValueStore store;

    @Before
    public void setUp() {
        store = new KeyValueStore();
    }

    @Test
    public void shouldStoreNewKeyValue() {
        store.put("namespace", "key1", "value1", Collections.singletonMap("index1", 1L));

        Assert.assertEquals(store.get("namespace", "key1"), Optional.of("value1"));
    }

    @Test
    public void shouldUpdateExistingKeyValue() {
        store.put("namespace", "key1", "value1", Collections.singletonMap("index1", 1L));
        store.put("namespace", "key1", "value2", Collections.singletonMap("index1", 1L));

        Assert.assertEquals(store.get("namespace", "key1"), Optional.of("value2"));
    }

    @Test
    public void shouldUpdateIndexesOnUpdate() {
        store.put("namespace", "key1", "value1", Collections.singletonMap("index1", 1L));
        store.put("namespace", "key1", "value2", Collections.singletonMap("index2", 1L));

        Assert.assertEquals(store.findInRange("namespace", "index1", 1L,  4L), Collections.emptyList());
        Assert.assertEquals(store.findInRange("namespace", "index2", 1L,  4L), Collections.singletonList("key1"));
    }

    @Test
    public void shouldSeparateNamespaces() {
        store.put("namespace1", "key1", "value1", Collections.singletonMap("index1", 1L));
        store.put("namespace2", "key1", "value2", Collections.singletonMap("index1", 1L));

        Assert.assertEquals(store.get("namespace1", "key1"), Optional.of("value1"));
        Assert.assertEquals(store.get("namespace2", "key1"), Optional.of("value2"));
    }

}