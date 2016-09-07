package pojo;

import java.io.Serializable;

/**
 * Created by yanni on 2016/9/1.
 */
public class GridItemListInfo implements Serializable{
    String answer;
    long pubTime;
    int cataid;
    int typeid;
    int id;
    String content;
    String options;

    public GridItemListInfo() {
    }
    public GridItemListInfo(String answer, long pubTime, int cataid, int typeid, int id, String content) {
        this.answer = answer;
        this.pubTime = pubTime;
        this.cataid = cataid;
        this.typeid = typeid;
        this.id = id;
        this.content = content;
    }

    public GridItemListInfo(String answer, long pubTime, int cataid, int typeid, int id, String content, String options) {
        this.answer = answer;
        this.pubTime = pubTime;
        this.cataid = cataid;
        this.typeid = typeid;
        this.id = id;
        this.content = content;
        this.options = options;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public long getPubTime() {
        return pubTime;
    }

    public void setPubTime(long pubTime) {
        this.pubTime = pubTime;
    }

    public int getCataid() {
        return cataid;
    }

    public void setCataid(int cataid) {
        this.cataid = cataid;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public static class Item implements Serializable{

        public String title;
        public boolean checked;


        public String toString() {
            return "Item [title=" + this.title + ", checked=" + this.checked
                    + "]";
        }
    }
}
