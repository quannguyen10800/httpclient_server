import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    //First test for Client Request and Server Response with sockets
    public static void main(String[] args)throws Exception {

        System.out.println("Server is started");
        ServerSocket ss = new ServerSocket(5000);
        System.out.println("Server waiting for client request");
        Socket s = ss.accept();
        System.out.println("Client connected");
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String str = br.readLine();
        System.out.print("Data: " + str);



    }

}
