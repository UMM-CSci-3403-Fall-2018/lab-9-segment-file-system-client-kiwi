package segmentedfilesystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ReceivedFile {
    private String filename = "";
    private int numPackets = Integer.MIN_VALUE;
    private ArrayList<DataPacket> data = new ArrayList<>();
    private HeaderPacket header;

    public ReceivedFile() {

    }

    public void addPacket(DataPacket newPacket) {
        data.add(newPacket);

        if (newPacket.isLastPacket()) {
            System.out.println("Received last packet for file " + this.filename);
            // +1 since packetNum zero-based
            this.numPackets = newPacket.getPacketNumber()+1;
        }
    }

    public void setHeader(HeaderPacket header) {
        this.header = header;
        this.filename = header.getFilename();
        System.out.println("Received header packet for file " + this.header + ". Filename=" + this.filename);
    }

    public boolean isComplete() {
        // false if don't know how many packets we're getting yet
        if (numPackets == Integer.MIN_VALUE) {
            System.out.println("ReceivedFile.isComplete branch: numPackets == Integer.MIN_VALUE");
            return false;
        }
        // false if don't have all packets yet
        if (this.data.size() != numPackets) {
            System.out.println("ReceivedFile.isComplete branch: this.data.size() != numPackets");
            return false;
        }
        // false if don't have filename from header yet
        if (filename.equals("")) {
            System.out.println("ReceivedFile.isComplete branch: filename.equals(\"\")");
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

            ArrayList<Byte> allBytes = new ArrayList<>();

            // put every byte from every packet into an ArrayList<Byte>
            for (DataPacket p : this.data) {
                for (Byte b : p.getData()) {
                    allBytes.add(b);
                }
            }

            byte[] toWrite = new byte[allBytes.size()];

            // put every byte from ArrayList into a byte array
            for (int i = 0; i < allBytes.size(); i++) {
                byte b = allBytes.get(i);
                toWrite[i] = b;
            }

            // write array to disk
            Path file = Paths.get(directory);
            Files.write(file, toWrite);

//            for (DataPacket p : this.data) {
//                // source: https://stackoverflow.com/questions/2885173/how-do-i-create-a-file-and-write-to-it-in-java
//                Path file = Paths.get(directory);
//                Files.write(file, p.getData());
//
//            }

        } else {
            System.out.println("File " + this.filename + "was told to write to disk, but is not complete!");
        }

    }

    public String getFilename() { return this.filename; }
}
