package segmentedfilesystem;

import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.util.Arrays;

public class HeaderPacket {
    private byte status;
    private byte fileId;
    private byte[] filenameData;

    public void HeaderPacket(DatagramPacket packet) {
        byte[] packetData = packet.getData();
        this.status = packetData[0];
        this.fileId = packetData[1];
        this.filenameData = Arrays.copyOfRange(packetData, 2, packetData.length);
    }

    public byte getFileId() {
        return this.fileId;
    }

    public String getFilename() {
        String filename = null;
        try {
            filename = new String(filenameData, "UTF-8");
        } catch (UnsupportedEncodingException uee) {
            System.out.println("Invalid filename encoding");
        }
        return filename;
    }
}
