package segmentedfilesystem;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.DatagramPacket;

import static org.junit.Assert.*;

public class DataPacketTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void isLastPacket() throws Exception {
        byte[] data = new byte[5];
        DatagramPacket testDatagramPacket  = new DatagramPacket(data, 5);

        data[0] = 3;
        for (int i = 1; i < 5; i++) {
            data[i] = 0;
        }

        DataPacket testPacket = new DataPacket(testDatagramPacket);
        assertTrue(testPacket.isLastPacket());

        data[0] = 2;
        testPacket = new DataPacket(testDatagramPacket);
        assertFalse(testPacket.isLastPacket());
    }

    @Test
    public void getFileId() throws Exception {
        byte[] data = new byte[6];
        DatagramPacket testDatagramPacket  = new DatagramPacket(data, 6);

        data[1] = 1;

        DataPacket testPacket = new DataPacket(testDatagramPacket);
        assertTrue(testPacket.getFileId() == 1);

        data[1] = 5;
        testPacket = new DataPacket(testDatagramPacket);
        assertTrue(testPacket.getFileId() == 5);
    }


    @Test
    public void getPacketNumber() throws Exception {
        byte[] data = new byte[6];
        DatagramPacket testDatagramPacket  = new DatagramPacket(data, 6);

        // 0000 0010 0000 0011
        // 515
        data[2] = 2;
        data[3] = 3;
        DataPacket testPacket = new DataPacket(testDatagramPacket);
        assertTrue(testPacket.getPacketNumber() == 515);

        //0000 0100 0000 0101
        //1029
        data[2] = 4;
        data[3] = 5;
        testPacket = new DataPacket(testDatagramPacket);
        assertTrue(testPacket.getPacketNumber() == 1029);
    }

    @Test
    public void getData() throws Exception {
    }

}