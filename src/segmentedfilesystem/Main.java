package segmentedfilesystem;

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
        socket.send(packet);

        HashMap<Byte, ReceivedFile> receivedFilesById = new HashMap();
        boolean done = false;

        // start receiving packets
        while (!done) {
          // check to see whether all files have been received completely
          for (ReceivedFile file : receivedFilesById.values()) {
            if (!file.isComplete()) {
              done = false;
              break;
            }
            else { done = true; }
          }

          // receive another packet
          DatagramPacket packet = new DatagramPacket(buf, buf.length);
          socket.receive(packet);
        }

    }

}
