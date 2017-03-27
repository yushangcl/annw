package top.common;

/**
 * Created by wjp on 16/5/26.
 */

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:spring-mongodb.xml")
//@Category(cn.com.iscs.common.test.IscsIgnoreTest.class)
//public class MongoDaoTest {
//    @Resource
//    MongoDao<Book> mongodbao;
//
//    @Test
//    public void insert() {
//        Book book = new Book();
//        book.setAuthor("xuchun");
//        book.setId("test1");
//        book.setPrice(1.4D);
//        mongodbao.insert(book);
//    }
//
//    @Test
//    public void find() {
//        Query query = Query.query(Criteria.where("id").is("test1"));
//        List<Book> list = mongodbao.gets(query, Book.class);
//        for (Book location : list) {
//            System.out.println(location.toString());
//        }
//    }
//
//    @Test
//    public void update() {
//        Query query = Query.query(Criteria.where("id").is("test1"));
//        Update update = Update.update("id","test1");
//        mongodbao.update(query, update, Book.class);
//        this.find();
//    }
//
//
//}
