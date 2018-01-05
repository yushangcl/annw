package top.annwz.base.cloud.qcloud;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.request.StatFileRequest;
import com.qcloud.cos.sign.Credentials;

/**
 * Created by huahui.wu on 2017/9/18.
 */
public class Demo {

    public Demo() {
        // 设置用户属性, 包括appid, secretId和SecretKey
        // 这些属性可以通过cos控制台获取(https://console.qcloud.com/cos)
        long appId = 10064421;
        String secretId = "AKIDy5Qb2m96V4qqSd8JiKm8svtwsetep3o5";
        String secretKey = "7shVFdkkySHqmWIK74eFXEF38X9CsE7L";
        // 设置要操作的bucket
        String bucketName = "yushang";
        // 初始化客户端配置
        ClientConfig clientConfig = new ClientConfig();
        // 设置bucket所在的区域，比如广州(gz), 天津(tj)
        clientConfig.setRegion("sh");
        // 初始化秘钥信息
        Credentials cred = new Credentials(appId, secretId, secretKey);
        // 初始化cosClient
        COSClient cosClient = new COSClient(clientConfig, cred);
        // 获取object属性
        // 4. 获取文件属性
        StatFileRequest statFileRequest = new StatFileRequest(bucketName, "/8K.PNG");
        String statFileRet = cosClient.statFile(statFileRequest);
        System.out.println("stat file ret:" + statFileRet);


    }

    public static void main(String[] args) {
        Demo demo = new Demo();

    }
}
