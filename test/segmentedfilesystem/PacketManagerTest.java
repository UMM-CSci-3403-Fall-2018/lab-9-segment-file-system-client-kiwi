package segmentedfilesystem;

import org.junit.Test;

import java.net.DatagramPacket;

import static org.junit.Assert.*;

public class PacketManagerTest {

    @Test
    public void isHeader() throws Exception {
        PacketManager testManager = new PacketManager();

        byte[] headerData = {2,0,0,0,0};
        byte[] regularData = {1,0,0,0,0};
        DatagramPacket testHeaderPacket = new DatagramPacket(headerData, 5);
        DatagramPacket testDataPacket = new DatagramPacket(regularData, 5);

        assertTrue(testManager.isHeader(testHeaderPacket));
        assertFalse(testManager.isHeader(testDataPacket));
    }

    @Test
    public void getOrCreateFile() throws Exception {
    }

}