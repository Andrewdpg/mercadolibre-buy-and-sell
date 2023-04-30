package model;

public interface Compare<T> {
    public int compare(T other, String attribute);
    public int compareAttr(Object target, String attribute);
} 

