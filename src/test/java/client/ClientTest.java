package client;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.Before;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class ClientTest {

  private CMT client;

  @Before // Runs when class is initialized
  public void setup() {
    client = new CMT();
  }

  @Test
  public void testIfClientConstructed() {
    assertNotNull(client);
  }

}
