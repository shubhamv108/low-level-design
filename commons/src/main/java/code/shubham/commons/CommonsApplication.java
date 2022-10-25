package code.shubham.commons;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class CommonsApplication {
    public static void main(String[] args) {
        Set<Object> set = new TreeSet();
        set.add(1);
        set.add("A");
        System.out.println(set.size());
    }
}
