package segmentedfilesystem;

import java.util.ArrayList;

public class ReceivedFile {
    private byte fileId;
    private String filename;
    private int numPackets;
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
    }

    public boolean isComplete() {
        return false;
    }
}
