package pojo;

import java.io.Serializable;

/**
 * Created by yanni on 2016/8/30.
 */
public class TypeKnowledge implements Serializable{
    int id;
    String icon;
    String name;

    public TypeKnowledge() {
    }

    public TypeKnowledge(int id, String icon, String name) {
        this.id = id;
        this.icon = icon;
        this.name = name;
    }

    @Override
    public String toString() {
        return "knowledgeType{" +
                "id=" + id +
                ", icon='" + icon + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
