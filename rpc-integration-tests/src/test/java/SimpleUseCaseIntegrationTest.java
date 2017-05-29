import net.de1mos.remotedictionaryservice.cliclient.CliClient;
import net.de1mos.remotedictionaryservice.cliserver.rpc.CliServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

/**
 * A simplest message exchange between client and dictionary server
 */
public class SimpleUseCaseIntegrationTest {

    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 8888;
    private CliServer server;
    private CliClient client;

    @Before
    public void setUp() throws Exception {
        server = new CliServer(PORT);
        server.startWebServer();
        client = new CliClient(ADDRESS, PORT);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdownServer();
    }

    @Test
    public void testSendAddAndRemoveTranslation() throws Exception {
        client.prepareRpcClient().addTranslation("word1", "trans1");
        assertThat(client.prepareRpcClient().getTranslations("word1"), hasItem("trans1"));

        client.prepareRpcClient().removeTranslation("word1", "trans1");
        assertThat(client.prepareRpcClient().getTranslations("word1"), not(hasItem("trans1")));
    }
}
