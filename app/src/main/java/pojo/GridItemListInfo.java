package pojo;

import java.io.Serializable;

/**
 * Created by yanni on 2016/9/1.
 */
public class GridItemListInfo implements Serializable{
    String content;
    int typeId;
    int id;

    public GridItemListInfo(String content, int typeId, int id) {
        this.content = content;
        this.typeId = typeId;
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
