package segmentedfilesystem;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        if (args.length != 1) {
          System.out.println("Please specify a valid server");
          return;
        }

        // basic server info
        DatagramSocket socket = new DatagramSocket();
        int port = 6014;
        InetAddress address = InetAddress.getByName(args[0]);

        // set up the connection by sending a meaningless packet to server
        byte[] buf = new byte[1000];
        DatagramPacket hello = new DatagramPacket(buf, buf.length, address, port);
        socket.send(hello);

        PacketManager manager = new PacketManager();

        while (!manager.done()) {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);

            manager.handle(packet);
        }



//        HashMap<Byte, ReceivedFile> receivedFilesById = new HashMap<>();
//        boolean done = false;
//
//        // start receiving packets
//        while (!done) {
//          // check to see whether all files have been received completely
//          for (ReceivedFile file : receivedFilesById.values()) {
//            if (!file.isComplete()) {
//              done = false;
//              break;
//            }
//            else { done = true; }
//          }
//
//          // receive another packet
//          DatagramPacket packet = new DatagramPacket(buf, buf.length);
//          socket.receive(packet);
//
//
//          DataPacket newPacket = new DataPacket(packet);
//
//          // get the file we want to add the packet to
//          ReceivedFile destinationFile = receivedFilesById.get(newPacket.getFileId());
//
//          // add packet to file, creating a new file if necessary and adding to HashMap of <fileId, files>
//          if (destinationFile != null) {
//              destinationFile.addPacket(newPacket);
//          } else {
//              destinationFile = new ReceivedFile();
//              receivedFilesById.put(newPacket.getFileId(), destinationFile);
//              destinationFile.addPacket(newPacket);
//          }
//        }

    }

}
