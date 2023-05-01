package util;

public class Filter {
    private Object top;
    private Object bottom;
    private String attribute;

    public Filter(Object top, Object bottom, String attribute) {
        this.top = top;
        this.bottom = bottom;
        this.attribute = attribute;
    }

    public Object getTop() {
        return top;
    }

    public void setTop(Object top) {
        this.top = top;
    }

    public Object getBottom() {
        return bottom;
    }

    public void setBottom(Object bottom) {
        this.bottom = bottom;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

}
