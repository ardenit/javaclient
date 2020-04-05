
public class Launcher {

    public static void main(String[] args) {
        Log.configureLoggingToFile();
        Configuration configuration = Configuration.load();
        Client client = new Client(configuration);
        client.start();
        ByteSequenceLoader byteSequenceLoader = new ByteSequenceLoader();
        int[][] byteSequences = byteSequenceLoader.loadByteSequences(configuration.getByteCount());
        for (int[] byteSequence : byteSequences) {
            client.getResult(byteSequence);
        }
        client.stop();
    }

}
