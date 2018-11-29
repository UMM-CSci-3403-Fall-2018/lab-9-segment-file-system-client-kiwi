package segmentedfilesystem;

import java.net.DatagramPacket;

public class DataPacket {
    //  private ReceivedFile file;
    private byte status;
    private byte fileId;
    private byte[] packetNumber = new byte[2];
    private byte[] data;

    public DataPacket(DatagramPacket newPacket) {

    }

    public byte getFileId() {
        return fileId;
    }
}
