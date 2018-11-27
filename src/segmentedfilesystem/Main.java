package segmentedfilesystem;

public class Main {

    public static void main(String[] args) {

        if (args.length != 1) {
          System.out.println("Please specify a valid server");
          return;
        }

        // set up the connection by sending a meaningless packet to server
        DatagramSocket socket = new DatagramSocket();

        int port = 6014;
        InetAddress address = InetAddress.getByName(args[0]);
        byte[] buf = new byte[1000];

        DatagramPacket hello = new DatagramPacket(buf, buf.length, address, port);
        socket.send(packet);

        

    }

}
