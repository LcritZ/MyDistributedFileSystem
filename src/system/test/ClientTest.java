package system.test;

import com.sun.deploy.trace.SocketTraceListener;

import java.io.*;
import java.net.Socket;

/**
 * Created by Lcrit_Z on 2017/5/1.
 */
public class ClientTest {
    Socket clientsocket ;
    DataInputStream dis;
    DataOutputStream dos;
    public ClientTest() throws IOException {
        clientsocket = new Socket("localhost",12306);
        dis = new DataInputStream(clientsocket.getInputStream());
        dos = new DataOutputStream(clientsocket.getOutputStream());

    }

    public static void main(String[] args) throws IOException {

        File file = new File("G:/swt.html");
        System.out.println(file.getCanonicalPath());
        String name = "1.html";
        ClientTest ct = new ClientTest();
        ct.dos.writeUTF(name);
        byte[] buffer = new byte[8192];
        FileInputStream fis = new FileInputStream(file);
        while (true){
            int len = fis.read(buffer);
            if (len==-1){
                break;
            }
            ct.dos.write(buffer,0,len);
        }
        ct.dos.flush();
        fis.close();

    }

}
