package system.middleserver;

import sun.security.x509.SubjectInfoAccessExtension;

import java.io.*;

/**
 * Created by 曾 on 2017/4/30.
 */
public class FileUpload {
    public DataOutputStream dos;
    public DataInputStream dis;


    /**
     * @param dis
     */
    public FileUpload(DataInputStream dis,DataOutputStream dos) {
        this.dis = dis;
        this.dos = dos;
    }

    /**
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public boolean uploadFile(String filename,long size) throws IOException {
        File file = new File("../../../data/" + filename);
        //dos.writeChar('Y');
        byte[] bufferbyte = new byte[8192];
        long upLen = 0L;
        FileOutputStream fos = new FileOutputStream(file);
        int len = 0;
        while (true) {
            if (dis != null) {
                len = dis.read(bufferbyte);
            }
            if (len == -1) {
                break;
            }
            fos.write(bufferbyte);
            upLen += len;
        }
        return true;
    }
}
