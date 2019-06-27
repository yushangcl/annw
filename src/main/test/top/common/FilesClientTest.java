package top.common;

/**
 * Created by wjp on 16/7/8.
 */

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:spring-cloudfiles.xml")
//@Category(cn.com.iscs.common.test.IscsIgnoreTest.class)
//public class FilesClientTest {
//    @Resource
//    FilesClient filesClient;
//
//    public void delContainer(String container) throws Exception {
////        if (filesClient.containerExists(container)) {
////            return ;
////        }
////        FilesContainerInfo info = filesClient.getContainerInfo(container);
////        while (info.getObjectCount() > 0) {
////            List<FilesObject> files = filesClient.listObjects(container, 2000);
////            for (FilesObject obj : files) {
////                filesClient.deleteObject(container, obj.getName());
////                System.out.println("success delete : " + container + " / " + obj.getName());
////            }
////            info = filesClient.getContainerInfo(container);
////        }
////        filesClient.deleteContainer(container);
//    }
//
//    @Test
//    public void createContainer() throws Exception {
//        for (int i = 0; i < 1000; i++) {
//            delContainer("xuchun" + i);
//        }
//    }
//
//    @Test
//    public void listlimit() throws Exception {
//        List<FilesObject> obj = filesClient.listObjects("temp", 4);
//        System.out.println(obj);
//    }
//
//    @Test
//    public void upload() throws Exception {
//        boolean bo = filesClient.storeObject("temp", new File("c:\\1.jpg"));
//        System.out.println(bo);
//    }
//
//    @Test
//    public void listContainer() throws Exception {
//        List<FilesContainer> list = filesClient.listContainers();
//        for (FilesContainer container : list) {
//            System.out.println(container.getName());
//        }
//    }
//
//    @Test
//    public void createDir() throws Exception {
//        filesClient.createContainer("pic");
////        System.out.println(obj);
//    }
//
//    @Test
//    public void delContainers() throws Exception {
//        for (int i = 0; i < 1000; i++) {
//            delContainer("xuchun" + i);
//        }
//    }
//
//}
