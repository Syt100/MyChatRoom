package bean;

/**
 * 用户的所有分组所有好友
 */
public class Friend {
    private Integer category_id;
    private String category_name;
    private Integer friend_id;
    private String friend_name;
    private String friend_signature;

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public Integer getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(Integer friend_id) {
        this.friend_id = friend_id;
    }

    public String getFriend_name() {
        return friend_name;
    }

    public void setFriend_name(String friend_name) {
        this.friend_name = friend_name;
    }

    public String getFriend_signature() {
        return friend_signature;
    }

    public void setFriend_signature(String friend_signature) {
        this.friend_signature = friend_signature;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "category_id=" + category_id +
                ", category_name='" + category_name + '\'' +
                ", friend_id=" + friend_id +
                ", friend_name='" + friend_name + '\'' +
                ", friend_signature='" + friend_signature + '\'' +
                '}';
    }
}
