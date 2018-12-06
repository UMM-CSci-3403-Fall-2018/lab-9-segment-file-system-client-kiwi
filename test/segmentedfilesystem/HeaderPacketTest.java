package segmentedfilesystem;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.DatagramPacket;

import static org.junit.Assert.*;

public class HeaderPacketTest {

    @Test
    public void getFilename() throws Exception {
        String filename = "test-filename.txt";
        byte[] nameData = filename.getBytes();

        byte[] data = new byte[nameData.length + 2];
        data[0] = 0;
        data[1] = 0;

        for (int i = 2; i < data.length; i++) {
            data[i] = nameData[i-2];
        }

        DatagramPacket testDatagramPacket  = new DatagramPacket(data, data.length);

        HeaderPacket testPacket = new HeaderPacket(testDatagramPacket);

        assertTrue(testPacket.getFilename().equals(filename));
    }

}