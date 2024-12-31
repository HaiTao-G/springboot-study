package com.haitao.excel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ListUtils {

    /**
     * 判空
     */
    public static boolean isEmptyList(List list) {
        return list == null || list.isEmpty();
    }


    /**
     * 更具key将list 分组
     * <br> ListUtil.groupingBy(list,TestDomin::getAge) </br>
     */
    public static <K, T> Map<K, List<T>> groupingBy(List<T> list, Function<? super T, ? extends K> keyMapper) {
        if (isEmptyList(list)) {
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.groupingBy(keyMapper));
    }

    /**
     * 将list转换成map,当key发生冲突时使用找到的第一个键值
     * <br>  ListUtil.toMap(list,TestDomin::getAge,TestDomin->TestDomin)  </br>
     *
     * @param key   键
     * @param value 值
     */
    public static <K, T, U> Map<K, U> toMap(List<T> list, Function<? super T, ? extends K> key, Function<? super T, ? extends U> value) {
        if (isEmptyList(list)) {
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.toMap(key, value, (x, y) -> x));
    }

    /**
     * 根据list 拆封成包含位置的list
     */
    public static <T> List<Partition> getPartitionList(List<T> list, int limit) {
        List<Partition> partitions = new ArrayList<>();
        if (!isEmptyList(list)) {
            if (list.size() > limit) {
                for (int j = 0; j < ((list.size() + limit - 1) / limit); j++) {
                    partitions.add(new Partition(j + 1, list.subList(j * limit, Math.min((j + 1) * limit, list.size()))));
                }
            } else {
                partitions.add(new Partition(0, list));
            }
        }
        return partitions;
    }

    public static class Partition {

        private int index;

        private List list;

        public Partition(int index, List list) {
            this.index = index;
            this.list = list;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public List getList() {
            return list;
        }

        public void setList(List list) {
            this.list = list;
        }
    }

}
