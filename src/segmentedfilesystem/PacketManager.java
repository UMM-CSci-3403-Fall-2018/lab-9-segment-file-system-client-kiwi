package segmentedfilesystem;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.HashMap;

public class PacketManager {

    // <fileId, file>
    private HashMap<Byte, ReceivedFile> receivedFiles = new HashMap<>();

    public PacketManager() {

    }

    // Add packet to its file as either a HeaderPacket or a DataPacket
    public void handle(DatagramPacket packet) {
        if (isHeader(packet)) {
            HeaderPacket headerPacket = new HeaderPacket(packet);
            byte fileId = headerPacket.getFileId();
            ReceivedFile destinationFile = getOrCreateFile(fileId);
            destinationFile.setHeader(headerPacket);
        } else {
            DataPacket dataPacket = new DataPacket(packet);
            byte fileID = dataPacket.getFileId();
            ReceivedFile destinationFile = getOrCreateFile(fileID);
            destinationFile.addPacket(dataPacket);
        }
        System.out.println("Finished handling packet " + packet.toString());
    }

    // Helper method for handle(). Returns ReceivedFile possessing fileId. If file doesn't exist yet,
    // create and put in receivedFiles.
    private ReceivedFile getOrCreateFile(byte fileId) {
        ReceivedFile file = receivedFiles.get(fileId);

        // if file isn't in hashmap yet, add it with fileId
        if (file == null) {
            file = new ReceivedFile();
            receivedFiles.put(fileId, file);
        }

        return file;
    }

    // Returns whether a DatagramPacket is a header.
    public boolean isHeader(DatagramPacket packet) {
        // check if status byte is even (implies header)
        byte statusByte = packet.getData()[0];
        return statusByte % 2 == 0;
    }

    // check whether all files that were started are completed
    public boolean done() {
        if (receivedFiles.isEmpty()) {
            return false;
        }
        for (ReceivedFile receivedFile : receivedFiles.values()) {
            if (!receivedFile.isComplete()) {
                return false; }
        }
        return true;
    }

    public void writeAllFiles(String directory) {
        for (ReceivedFile receivedFile : receivedFiles.values()) {
            // receivedFile.sortPackets();
            try {
                receivedFile.writeToDisk(directory + "/" + receivedFile.getFilename());
                System.out.println("Successfully wrote file " + receivedFile.getFilename() + " to disk.");
            } catch (IOException ioe) {
                System.out.println("File " + receivedFile.getFilename() + " failed to write to disk.");
                System.err.println(ioe.getMessage());
            }
        }
    }

    public HashMap<Byte, ReceivedFile> getReceivedFiles() {
        return this.receivedFiles;
    }
}
