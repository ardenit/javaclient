import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ByteSequenceLoader {

    private static final String BYTES_FILE_PATH = "./input.txt";

    public int[][] loadByteSequences(int byteCount) {
        try {
            File bytesFile = new File(BYTES_FILE_PATH);
            InputStream bytesInputStream = new FileInputStream(bytesFile);
            int[] allBytes = readBytesFromInputStream(bytesInputStream);
            int sequencesCount = allBytes.length / byteCount;
            int[][] sequences = new int[sequencesCount][];
            for (int i = 0; i < sequencesCount; ++i) {
                sequences[i] = Arrays.copyOfRange(allBytes, i * byteCount, (i + 1) * byteCount);
            }
            return sequences;
        }
        catch (FileNotFoundException ex) {
            Log.log.info("Input file not found. Generating random byte sequence....");
            int[] sequence = new int[byteCount];
            for (int i = 0; i < byteCount; ++i) {
                sequence[i] = (int)(Math.random() * 256);
            }
            return new int[][] { sequence };
        }
        catch (Exception ex) {
            Log.log.warning("Error while reading the input file. Please ensure that input file is formatted properly and restart the client.");
            return new int[][] {};
        }
    }

    int[] readBytesFromInputStream(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        List<Integer> bytesList = new ArrayList<>();
        while (scanner.hasNext()) {
            String token = scanner.next();
            int intToken = parseToken(token);
            if (intToken == -1) {
                Log.log.warning("Token " + token + " is not a valid byte token and will be ignored.");
            }
            else {
                bytesList.add(intToken);
            }
        }
        int[] bytes = new int[bytesList.size()];
        for (int i = 0; i < bytesList.size(); ++i) {
            bytes[i] = bytesList.get(i);
        }
        return bytes;
    }

    int parseToken(String token) {
        try {
            int intToken = Integer.parseInt(token);
            if (intToken < 0 || intToken > 255) return -1;
            else return intToken;
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

}
