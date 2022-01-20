import java.util.List;
import org.apache.ignite.Ignition;
import org.apache.ignite.client.*;
import org.apache.ignite.configuration.ClientConfiguration;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

/**
 * Example of a Java thin client that connects to the cluster and publishes some random data.
 */
public class ThinClientApp {
    public static void main(String[] args) throws Exception {
        // Creates client configuration.
        ClientConfiguration cfg = new ClientConfiguration()
                .setAddresses("Your-Nebula-Address") // Use the address of a Nebula Cluster.
                .setUserName("USERNAME") // Specify the name of a cluster user.
                .setUserPassword("PASSWORD") // Specify the password of a cluster user.
                .setSslMode(SslMode.REQUIRED);

        try (IgniteClient client = Ignition.startClient(cfg)) {
            // Creates a new cache or gets it as an object if it already exists.
            ClientCache<Integer, String> cache =  client.getOrCreateCache(new ClientCacheConfiguration()
                    .setName("GridGainDemo")
                    .setBackups(1)
            );

            // Puts random data into cluster cache.
            for (int i = 0; i < 10; i++) {
                String randomText =  randomAlphabetic(10);
                cache.put(i, randomText);
            }

            // Gets data from the cluster and posts it to the log.
            for (int i = 0; i < 10; i++) {
                System.out.println(">>>    " + cache.get(i));
            }
        }
    }
}
