package server;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Matchers;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;

@RunWith(PowerMockRunner.class)
@PrepareForTest(EchoServer.class)
public class ServerTest {

  private EchoServer fc;

  @Before // Runs when class is initialized
  public void setup() {
    fc = PowerMockito.spy(new EchoServer(55555,1));
  }

  @Test
  public void testIfServerConstructed() {
    assertNotNull(fc);

  }

}
