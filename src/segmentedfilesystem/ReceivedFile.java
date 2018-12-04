package segmentedfilesystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ReceivedFile {
    private byte fileId;
    private String filename;
    private int numPackets = Integer.MIN_VALUE;
    private ArrayList<DataPacket> data;
    private HeaderPacket header;

    public void ReceivedFile() {

    }

    public void addPacket(DataPacket newPacket) {
        data.add(newPacket);

        if (newPacket.isLastPacket()) {
            this.numPackets = newPacket.getPacketNumber();
        }
    }

    public void setHeader(HeaderPacket header) {
        this.header = header;
        this.filename = header.getFilename();
    }

    public boolean isComplete() {
        // false if don't know how many packets we're getting yet
        if (numPackets == Integer.MIN_VALUE) {
            return false;
        }
        // false if don't have all packets yet
        if (this.data.size() != numPackets) {
            return false;
        }
        // false if don't have filename from header yet
        if (filename == null) {
            return false;
        }
        return true;
    }

    public void sortPackets() {
        this.data.sort(new PacketNumberComparator());
    }

    public void writeToDisk(String directory) throws IOException {
        if (this.isComplete()) {
            this.sortPackets();

            for (DataPacket p : this.data) {
                // source: https://stackoverflow.com/questions/2885173/how-do-i-create-a-file-and-write-to-it-in-java
                Path file = Paths.get(directory);
                Files.write(file, p.getData());
            }

        } else {
            System.out.println("File " + this.fileId + "was told to write to disk, but is not complete!");
        }

    }

    public byte getFileId() {
        return this.fileId;
    }
}
