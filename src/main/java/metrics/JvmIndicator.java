package metrics;

import java.util.Objects;

/**
 * @author lzn
 * @date 2023/12/27 15:00
 * @description
 */
public class JvmIndicator {

    private String name;
    private String area;
    private String id;
    private String description;

    public JvmIndicator(String name, String area, String id) {
        this.name = name;
        this.area = area;
        this.id = id;
    }

    public JvmIndicator(String name, String area, String id, String description) {
        this.name = name;
        this.area = area;
        this.id = id;
        this.description = description;
    }

    public static JvmIndicator with(String name, String area, String id) {
        return new JvmIndicator(name, area, id);
    }

    public String getName() {
        return name;
    }

    public String getArea() {
        return area;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
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
