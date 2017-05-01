package system.client;

import java.io.*;
import java.net.Socket;

import org.apache.log4j.Logger;

/**
 * Created by Lcrit_Z on 2017/4/29.
 */
public class FileClient {
    private Logger logger = Logger.getLogger(FileClient.class);

    private Socket clientsocket = null;
    private DataInputStream dis = null;

    private DataOutputStream dos = null;


    public FileClient() {

        try {
            clientsocket = new Socket("127.0.0.1",12306);
            dis = new DataInputStream(clientsocket.getInputStream());
            dos = new DataOutputStream(clientsocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @param filepath
     * @return
     * @throws IOException
     */
    public boolean upload(String filepath) throws IOException {
        boolean flag = false;
        dos.writeUTF("upload");
        dos.flush();
        logger.info("向服务器发送上传指令");
        File file = new File(filepath);
        long size = file.length();
        dos.writeLong(size);
        char canUp=dis.readChar();
        if (canUp=='N'){
            String errinfo = dis.readUTF();
            logger.info(errinfo);
            logger.info("文件上传失败");
            dos.close();
            dis.close();
            clientsocket.close();
            return flag;
        }
        logger.info("服务器确认可以上传");
        String filename = file.getName();
        dos.writeUTF(filename); //发送文件名到服务器
        logger.info("客户端开始上传");
        int buffer = 8192;
        byte[] bufferbyte = new byte[8096];
        FileInputStream fis = new FileInputStream(file);
        int len = 0;
        long uplen = 0L;
        while((len = fis.read(bufferbyte))>0){
            uplen+= buffer;
            dos.write(bufferbyte,0,len);
        }
        dos.flush();
        fis.close();
        if (uplen==size){
            logger.info("文件上传成功");
            dos.close();
            dis.close();
            clientsocket.close();
            flag = true;
            return flag;
        }else {
            logger.info("文件上传失败");
            dos.close();
            dis.close();
            clientsocket.close();
            flag = false;
            return flag;
        }
    }

    /**
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public boolean download(String filename) throws IOException {
        dos.writeUTF("download");
        dos.flush();
        dos.writeUTF(filename);
        dos.flush();
        logger.info("发送下载文件名到服务器");
        char res = dis.readChar();
        if (res == 'N'){
            String errinfo = dis.readUTF();
            logger.info("下载失败"+errinfo);
            return false;
        }
        logger.info("客户端收到服务器发来的可下载信息");
        Long size = dis.readLong();
        byte[] bufferbyte = new byte[8192];
        File file = new File(filename);
        logger.info("客户端开始下载文件");
        Long downloadLen = 0L;
        FileOutputStream fos = new FileOutputStream(file);
        int len = 0;
        while(true){
            if (dis!=null){
                len = dis.read(bufferbyte);
            }
            if (len==-1){
                break;
            }
            fos.write(bufferbyte);
            downloadLen+=len;
        }
        fos.close();
        dos.close();
        dis.close();
        clientsocket.close();
        if (downloadLen==size){
            logger.info("下载成功");
            return true;
        }else {
            logger.info("下载失败");
            return  false;
        }
    }

    /**
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public boolean delete(String filename) throws IOException {
        dos.writeUTF("delete");
        dos.flush();
        dos.writeUTF(filename);
        dos.flush();
        char res = dis.readChar();
        if (res == 'Y'){
            logger.info("服务器收到消息并删除文件");
            dis.close();
            dos.close();
            return true;
        }else {
            String errinfo = dis.readUTF();
            logger.info("删除失败:"+errinfo);
            dis.close();
            dos.close();
            return false;
        }
    }

    /**
     *
     * @param name
     * @param newname
     * @return
     * @throws IOException
     */
    public boolean alterName(String name,String newname) throws IOException {
        boolean flag = false;
        dos.writeUTF("alter");
        dos.flush();
        char res = dis.readChar();
        if (res == 'Y'){
            logger.info("服务器确认更改请求");
            dos.writeUTF(name);
            dos.flush();
            flag = true;
        }else {
            String errinfo = dis.readUTF();
            logger.info("更改文件名失败"+errinfo);
        }
        dis.close();
        dos.close();
        clientsocket.close();
        return flag;
    }

    public static void main(String[] args) {
        FileClient fc = new FileClient();
        try {
            fc.upload("data/1.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
