package cn.itcast.fastdfs;

import org.csource.fastdfs.*;
import org.junit.Test;

import java.io.IOException;

public class FastDFSTest {

    @Test
    public void test() throws Exception {

        //配置文件路径
        String confFlieName = ClassLoader.getSystemResource("fastdfs/tracker.conf").getPath();

        //设置全局的配置信息
        ClientGlobal.init(confFlieName);

        //创建TrackerClient
        TrackerClient trackerClient = new TrackerClient();

        //创建trackerServer
        TrackerServer trackerServer = trackerClient.getConnection();

        //创建StorageServer
        StorageServer storageServer = null;

        //创建存储服务器客户端StorageClient
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);

        //上传文件
        /**
         * 参数1：文件路径
         * 参数2：后缀，文件的扩展名
         * 参数3：文件信息
         */
        String[] upload_file = storageClient.upload_file("D:\\itcast\\pics\\575968fcN2faf4aa4.jpg", "jpg", null);

        /**
         * 返回的数组包含:组名、文件路径
         * group1
         * M00/00/00/wKgMqF0pXAKAWEutAABw0se6LsY546.jpg
         */
        if (upload_file != null && upload_file.length > 0) {
            for (String str : upload_file) {
                System.out.println(str);
            }
            String groupName = upload_file[0];
            String fileName = upload_file[1];

            //获取存储服务器信息
            ServerInfo[] serverInfos = trackerClient.getFetchStorages(trackerServer, groupName, fileName);
            for (ServerInfo serverInfo : serverInfos) {
                System.out.println("ip=" + serverInfo.getIpAddr() +"；port=" + serverInfo.getPort());
            }

            String url = "http://" + serverInfos[0].getIpAddr() + "/" + groupName + "/" + fileName;
            System.out.println("url = " + url);
        }

    }
}
