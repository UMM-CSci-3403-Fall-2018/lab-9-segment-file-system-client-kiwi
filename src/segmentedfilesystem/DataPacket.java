package segmentedfilesystem;

import java.lang.reflect.Array;
import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class DataPacket {
    //  private ReceivedFile file;
    private byte status;
    private byte fileId;
    private byte[] packetNumber = new byte[2];
    private byte[] data;

    public DataPacket(DatagramPacket newPacket) {
        byte[] dataPacket = newPacket.getData();
        this.status = dataPacket[0];
        this.fileId = dataPacket[1];
        this.packetNumber = Arrays.copyOfRange(dataPacket, 2, 3);
        this.data = Arrays.copyOfRange(dataPacket, 4, dataPacket.length);


    }

    public boolean isLastPacket() {
        return (this.status % 4 == 3);
    }

    public byte getFileId() {
        return fileId;
    }

    public int getPacketNumber() {
        // solution found at https://stackoverflow.com/questions/7619058/
        // convert-a-byte-array-to-integer-in-java-and-vice-versa
        ByteBuffer wrapped = ByteBuffer.wrap(this.packetNumber);
        return wrapped.getInt();
    }
}
