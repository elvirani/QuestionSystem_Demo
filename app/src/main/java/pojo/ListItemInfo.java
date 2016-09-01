package pojo;

/**
 * Created by yanni on 2016/9/1.
 */
public class ListItemInfo {
    int imgId;
    int titleId;

    public ListItemInfo(int imgId, int titleId) {
        this.imgId = imgId;
        this.titleId = titleId;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }
}
