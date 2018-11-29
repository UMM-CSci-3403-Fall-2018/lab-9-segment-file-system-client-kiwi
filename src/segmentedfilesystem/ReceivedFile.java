package segmentedfilesystem;

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
        // if don't know how many packets we're getting yet
        if (numPackets == Integer.MIN_VALUE) {
            return false;
        }
        // if don't have all packets yet
        if (this.data.size() != numPackets) {
            return false;
        }
        // if don't have filename from header yet
        if (filename == null) {
            return false;
        }
        return true;
    }
}
