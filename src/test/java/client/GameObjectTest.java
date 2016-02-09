package client;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertNotNull;

/**
 * @author Brandon Williams
 * @date   2/9/2016 - Last updated
 */
public class GameObjectTest {

    private GameObject go;

    @Before
    public void setup() {

	go = new GameObject();
    }

    @Test
    public void testGameObjectConstructor() throws Exception {

	assertNotNull(go);
    }
}