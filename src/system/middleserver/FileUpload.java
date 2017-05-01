package system.middleserver;

import java.io.*;

/**
 * Created by 曾 on 2017/4/30.
 */
public class FileUpload {
    public DataOutputStream dos ;
    public DataInputStream dis ;



    /**
     *
     *
     * @param dis
     */
    public FileUpload( DataInputStream dis) {
        this.dis = dis;
    }

    public void uploadFile(String filename) throws IOException {
        File file = new File("../../../data/"+filename);
        byte[] bufferbyte = new byte[8192];
        long upLen = 0L;
        FileOutputStream fos = new FileOutputStream(file);
        int len = 0;
        while (true){
            if (dis!=null){
               len = dis.read(bufferbyte);
            }
            if (len==-1){
                break;
            }
            fos.write(bufferbyte);
            upLen+= len;
        }
    }
}
