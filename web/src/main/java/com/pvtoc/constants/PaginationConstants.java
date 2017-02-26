package com.pvtoc.constants;


import java.util.ArrayList;
import java.util.List;

public class PaginationConstants {
    private static final int[] NUMBER_PER_PAGE_ARRAY = {5, 10, 25, 50, 100};
    public static final int DEFAULT_NUMBER_PER_PAGE = NUMBER_PER_PAGE_ARRAY[1];
    public static final int DEFAULT_CURRENT_PAGE_NUMBER = 1;
    public static final List<Integer> NUMBER_PER_PAGE_LIST = fromArrayToList(NUMBER_PER_PAGE_ARRAY);

    private static List<Integer> fromArrayToList(int[] array) {
        List<Integer> intList = new ArrayList<>();
        for (int num : array) {
            intList.add(num);
        }
        return intList;
    }

    private PaginationConstants() {
    }
}
