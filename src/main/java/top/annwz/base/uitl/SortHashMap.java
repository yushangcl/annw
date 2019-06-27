package top.annwz.base.uitl;

import java.util.*;

/**
 * Created by huahui.wu on 2017/7/31.
 */
public class SortHashMap {

    public static void main(String[] args) {

        Map<String, Integer> map = new HashMap<String, Integer>();

        map.put("d",4);

        map.put("a",1);

        map.put("c",3);

        map.put("e",5);

        map.put("b",2);

        //排序前

        System.out.println("before sort");

        for(Map.Entry<String, Integer> entry:map.entrySet()){

            System.out.println(entry.getKey()+"->"+entry.getValue());

        }

        System.out.println();



        //将map转成list

        List<Map.Entry<String, Integer>> infos = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());

        //对list排序,实现新的比较器

        Collections.sort(infos, new Comparator<Map.Entry<String, Integer>>(){

            @Override

            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {

                return o1.getValue() - o2.getValue();

            }

        });

        //申明新的有序 map,根据放入的数序排序

        Map<String, Integer> lhm = new LinkedHashMap<String, Integer>();

        //遍历比较过后的map,将结果放到LinkedHashMap

        for(Map.Entry<String, Integer> entry:infos){

            lhm.put(entry.getKey(), entry.getValue());

        }

        //遍历LinkedHashMap,打印值

        System.out.println("after sort");

        for(Map.Entry<String, Integer> entry:lhm.entrySet()){

            System.out.println(entry.getKey()+"->"+entry.getValue());

        }

    }

    /**
     * 遍历map取出最大的值
     *
     * @param map
     * @return
     */
    private static Long maxValue(HashMap<Long, Integer> map) {
        Integer t = 0;
        Long maxKey = Long.MIN_VALUE;
        Set<Map.Entry<Long, Integer>> set = map.entrySet();
        for (Map.Entry<Long, Integer> entry : set) {
            if (entry.getValue() > t) {
                t = entry.getValue();
                maxKey = entry.getKey();
            }
        }

        return maxKey;
    }
}
