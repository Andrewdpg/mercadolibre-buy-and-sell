package model;
import java.util.ArrayList;
import java.util.Collections;

public class Search {
    public static <T extends Compare<T>> void orderBy(ArrayList<T> array, String attribute, boolean asc){
        Collections.sort(array, (a,b)->{
            if(asc) return a.compare(b, attribute);
            else return b.compare(a, attribute);
        });
    }

    public static <T extends Compare<T>> int findStartIndex(ArrayList<T> array, Object target, String attribute) {
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

    public static <T extends Compare<T>> int findEndIndex(ArrayList<T> array, Object target, String attribute) {
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
