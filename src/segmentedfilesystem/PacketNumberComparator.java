package segmentedfilesystem;

import java.util.Comparator;

public class PacketNumberComparator implements Comparator<DataPacket> {
    public int compare(DataPacket p1, DataPacket p2) {
        int n1 = p1.getPacketNumber();
        int n2 = p2.getPacketNumber();
        return Integer.compare(n1, n2);
    }
}
