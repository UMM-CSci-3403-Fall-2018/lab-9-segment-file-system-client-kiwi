package segmentedfilesystem;

import java.net.DatagramPacket;
import java.util.HashMap;

public class PacketManager {

    // <fileId, file>
    private HashMap<Byte, ReceivedFile> receivedFiles;

    public PacketManager() {

    }

    public void handle(DatagramPacket packet) {
        if (isHeader(packet)) {
            HeaderPacket headerPacket = new HeaderPacket();
            byte fileId = headerPacket.getFileId();
            ReceivedFile destinationFile = getOrCreateFile(fileId);
            destinationFile.setHeader(headerPacket);
        } else {
            DataPacket dataPacket = new DataPacket(packet);
        }
    }

    private ReceivedFile getOrCreateFile(byte fileId) {
        ReceivedFile file = receivedFiles.get(fileId);

        // if file isn't in hashmap yet, add it with fileId
        if (file == null) {
            file = new ReceivedFile();
            receivedFiles.put(fileId, file);
        }

        return file;
    }

    private boolean isHeader(DatagramPacket packet) {
        return false;
    }

    public boolean done() {
        return false;
    }
}
