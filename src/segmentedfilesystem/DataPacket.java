package segmentedfilesystem;

import java.net.DatagramPacket;

public class DataPacket {
    private byte status;
    private byte fileId;

    public DataPacket(DatagramPacket newPacket) {

    }

    public byte getFileId() {
        return fileId;
    }
}
