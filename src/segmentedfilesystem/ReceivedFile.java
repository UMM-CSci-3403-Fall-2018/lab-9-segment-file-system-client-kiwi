package segmentedfilesystem;

import java.util.ArrayList;

public class ReceivedFile {
    private byte fileId;
    private String filename;
    private ArrayList<DataPacket> data;

    public void ReceivedFile() {

    }

    public void addPacket(DataPacket newPacket) {
        data.add(newPacket);
    }

    public boolean isComplete() {
        return false;
    }
}
