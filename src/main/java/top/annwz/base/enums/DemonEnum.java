package top.annwz.base.enums;


/**
 * 反枚举使用demo
 * Created by WJP on 2017/1/13.
 */
public class DemonEnum extends BasicEnum<Integer>{
    public static final DemonEnum DEMO1 = new DemonEnum("周一",1);
    public static final DemonEnum DEMO2 = new DemonEnum("周二",2);
    public static final DemonEnum DEMO3 = new DemonEnum("周三",3);
    public static final DemonEnum DEMO4 = new DemonEnum("周四",4);
    public static final DemonEnum DEMO5 = new DemonEnum("周五",5);
    public static final DemonEnum DEMO6 = new DemonEnum("周六",6);
    public static final DemonEnum DEMO7 = new DemonEnum("周七",7);

    protected DemonEnum(String name){
        super(name,null);
    }

    protected DemonEnum(String name, Integer value) {
        super(name, value);
    }

    /**
     * 根据name查找
     * @param name
     * @return
     */
    public static DemonEnum getEnum(String name) {
        return (DemonEnum) BasicEnum.getEnum(DemonEnum.class, name);
    }

    /**
     * 根据value查找
     * @param value
     * @return
     */
    public static DemonEnum getEnumByValue(Integer value) {
        return (DemonEnum) BasicEnum.getEnumByValue(DemonEnum.class, value);
    }

    /**
     * 查找当前类型的所有枚举类
     */
//    public static List<DemonEnum> getEnumList(){
//        return BasicEnum.getEnumList(DemonEnum.class);
//    }

    /**
     * 查询当前类型所有的枚举类map
     * @return
     */
//    public static Map getEnumMap(){
//        return BasicEnum.getEnumMap(DemonEnum.class);
//    }

}
