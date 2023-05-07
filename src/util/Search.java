package util;
import java.util.ArrayList;
import java.util.Collections;

public class Search {

    public static <T extends Compare<T>> ArrayList<T> searchBy(ArrayList<T> array, Filter... filters) {
        ArrayList<T> result = new ArrayList<>(array);
        for (Filter filter : filters) {
            result = searchBy(result, filter.getTop(), filter.getBottom(), filter.getAttribute());
        }
        return result;
    }

    private static <T extends Compare<T>> ArrayList<T> searchBy(ArrayList<T> array, Object top, Object bot, String attribute) {
        ArrayList<T> elements = new ArrayList<>(array);
        ArrayList<T> result = null;
        Search.orderBy(elements, attribute, true);
        int start = Search.findStartIndex(elements, bot, attribute);
        int finish = Search.findEndIndex(elements, top, attribute);
        if (start != -1 && finish != -1) {
            result = new ArrayList<>();
            for (int i = start; i <= finish; i++) {
                result.add(elements.get(i));
            }
        }
        return result;
    }

    public static <T extends Compare<T>> void orderBy(ArrayList<T> array, String attribute, boolean asc){
        Collections.sort(array, (a,b)->{
            if(asc) return a.compare(b, attribute);
            else return b.compare(a, attribute);
        });
    }

    private static <T extends Compare<T>> int findStartIndex(ArrayList<T> array, Object target, String attribute) {
        int left = 0;
        int right = array.size() - 1;
        int startIndex = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (array.get(mid).compareAttr(target, attribute) >= 0) {
                startIndex = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return startIndex;
    }

    private static <T extends Compare<T>> int findEndIndex(ArrayList<T> array, Object target, String attribute) {
        int left = 0;
        int right = array.size() - 1;
        int endIndex = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (array.get(mid).compareAttr(target, attribute) <= 0) {
                endIndex = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return endIndex;
    }


}
