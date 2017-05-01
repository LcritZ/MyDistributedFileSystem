package system.middleserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by 曾 on 2017/4/30.
 */
public class MainMiddleServer {
    public ServerSocket ssocket = null;
    public DataInputStream dis ;
    public DataOutputStream dos ;

    public MainMiddleServer (){
        try {
            ssocket = new ServerSocket(12306);
            Socket accsocket = ssocket.accept();
            dis = new DataInputStream(accsocket.getInputStream());
            dos = new DataOutputStream(accsocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        MainMiddleServer mms = new MainMiddleServer();

        while (true){
            String ins = "";
            try {
               ins = mms.dis.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
            switch (ins){
                case "upload":

            }
        }
    }

}
