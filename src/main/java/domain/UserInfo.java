package domain;

/**
 * 实体类，对应数据库中的user_info表
 */
public class UserInfo {
    private Integer id;
    private Integer user_id;
    private String user_name;
    private String user_password;
    private String user_signature;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_signature() {
        return user_signature;
    }

    public void setUser_signature(String user_signature) {
        this.user_signature = user_signature;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", user_password='" + user_password + '\'' +
                ", user_signature='" + user_signature + '\'' +
                '}';
    }
}
