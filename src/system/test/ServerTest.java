package system.test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Lcrit_Z on 2017/5/1.
 */
public class ServerTest {
    public ServerSocket ss = new ServerSocket(12306);
    public DataInputStream dis ;
    public DataOutputStream dos ;


    public ServerTest() throws IOException {
        Socket sersocket = ss.accept();
        dis = new DataInputStream(sersocket.getInputStream());
        dos = new DataOutputStream(sersocket.getOutputStream());

    }

    public static void main(String[] args) throws IOException {
        ServerTest st = new ServerTest();
        String name = st.dis.readUTF();
        File file = new File("G:/" + name);
        //dos.writeChar('Y');
        byte[] bufferbyte = new byte[8096];
        long upLen = 0L;
        FileOutputStream fos = new FileOutputStream(file);
        int len = 0;
        while (true) {
                try {
                    len = st.dis.read(bufferbyte);
                }catch (IOException e){
                    break;
                }

            if (len == -1) {
                break;
            }
            System.out.println("now write:"+len);
            fos.write(bufferbyte,0,len);
            upLen += len;
        }
        fos.close();
    }
}
