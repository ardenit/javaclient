import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

public class Client {

    private final Configuration configuration;
    private Socket socket = null;

    public Client(Configuration configuration) {
        this.configuration = configuration;
    }

    public void start() {
        try {
            if (socket == null) {
                Log.log.info("Connecting to host " + configuration.getHost() + " on port " + configuration.getServerPort() + "....");
                socket = new Socket(configuration.getHost(), configuration.getServerPort());
                Log.log.info("Successfully connected to the server.");
            }
            else {
                Log.log.info("Client is already started.");
            }
        } catch (UnknownHostException ex) {
            Log.log.warning("Host is not valid. Fix the host address in configuration file and restart the client.");
        } catch (IOException ex) {
            Log.log.warning("Error while messaging with host. Try to fix possible connection issues and restart the client.");
            ex.printStackTrace();
        }
    }

    public void stop() {
        try {
            socket.close();
        } catch (IOException ex) {
            Log.log.warning("Error while closing the socket.");
        }
    }

    public int getResult(int[] bytes) {
        Log.log.info("Getting the result for sequence " + Arrays.toString(bytes));
        if (bytes.length != configuration.getByteCount()) {
            throw new IllegalArgumentException("bytes array must have the length equal to byteCount value in configuration");
        }
        for (int byteValue : bytes) {
            if (byteValue < 0 || byteValue > 255) {
                throw new IllegalArgumentException("All values in bytes array must be in range 0 up to 255");
            }
        }
        int result = -1;
        try {
            result = askServerLogic(bytes);
        } catch (IOException ex) {
            Log.log.warning("Error while messaging with host. Try to fix possible connection issues and restart the client.");
        }
        if (result < 0 || result > 255) {
            Log.log.warning("Invalid response from server. This was not supposed to happen :( Post a bug ticket!");
            return 0;
        }
        Log.log.info("The result is " + result + ".");
        return result;
    }

    int askServerLogic(int[] bytes) throws IOException {
        OutputStream output = socket.getOutputStream();
        InputStream input = socket.getInputStream();
        for (int byteValue : bytes) {
            output.write(byteValue);
        }
        output.flush();
        return input.read();
    }

}
