package segmentedfilesystem;

import java.io.IOException;
import java.net.*;

public class Main {

    public static void main(String[] args) {

        if (args.length != 2) {
          System.out.println("Args need to be of form <server> <directory>");
          return;
        }

        // basic server info
        DatagramSocket socket;
        int port = 6014;
        InetAddress address;

        // create the socket
        try {
            socket = new DatagramSocket();
        } catch (SocketException se) {
            System.out.println("Socket was not successfully created.");
            System.err.println(se.getMessage());
            return;
        }

        // create the address
        try {
            address = InetAddress.getByName(args[0]);
        } catch (UnknownHostException uhe) {
            System.out.println("Unknown host! Please provide a valid server.");
            System.err.println(uhe.getMessage());
            return;
        }


        // set up the connection by sending a meaningless packet to server
        byte[] buf = new byte[1000];
        DatagramPacket hello = new DatagramPacket(buf, buf.length, address, port);

        try {
            socket.send(hello);
        } catch (IOException ioe) {
            System.out.println("Failed to send packet to server!");
            System.err.println(ioe.getMessage());
        }

        PacketManager manager = new PacketManager();

        while (!manager.done()) {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                System.out.println("Hangs at socket.receive(packet)");
                socket.receive(packet);
                System.out.println("This won't print on last attempt");
            } catch (IOException ioe) {
                System.out.println("Failed to receive a packet from the server. This is a Bad Thing.");
                System.err.println(ioe.getMessage());
            }

            System.out.println("Handling packet " + packet.toString());
            manager.handle(packet);
        }

        System.out.println("Exited while loop");

        // write all the files to specified directory
        manager.writeAllFiles(args[1]);

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
