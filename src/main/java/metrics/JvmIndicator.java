package metrics;

import java.util.Objects;

/**
 * @author lzn
 * @date 2023/12/27 15:00
 * @description
 */
public class JvmIndicator {

    private String name;
    private String tag1;
    private String area;
    private String tag2;
    private String id;
    private String description;

    public JvmIndicator(String name, String area, String id) {
        this.name = name;
        this.area = area;
        this.id = id;
    }

    public JvmIndicator(String name, String tag1, String area, String tag2, String id, String description) {
        this.name = name;
        this.tag1 = tag1;
        this.area = area;
        this.tag2 = tag2;
        this.id = id;
        this.description = description;
    }

    public JvmIndicator(String name, String tag1, String area, String tag2, String id) {
        this.name = name;
        this.tag1 = tag1;
        this.area = area;
        this.tag2 = tag2;
        this.id = id;
    }

    public static JvmIndicator with(String name, String tag1, String area, String tag2, String id) {
        return new JvmIndicator(name, tag1, area, tag2, id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag1() {
        return tag1;
    }

    public void setTag1(String tag1) {
        this.tag1 = tag1;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTag2() {
        return tag2;
    }

    public void setTag2(String tag2) {
        this.tag2 = tag2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JvmIndicator that = (JvmIndicator) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(area, that.area) &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, area, id);
    }

    public void clear() {
        name = "";
        area = "";
        id = "";
    }

    @Override
    public String toString() {
        return "JvmIndicator{" +
                "name='" + name + '\'' +
                ", area='" + area + '\'' +
                ", id='" + id + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
