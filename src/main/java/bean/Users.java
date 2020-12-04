package bean;

import domain.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * @author xuxin
 */
public class Users {
    private String id;
    private String name;
    private String signature;
    private Map<Integer, List<Friend>> friends;

    /**
     * 默认的无参构造方法
     */
    public Users() {

    }

    public Users(String id, String name, String signature) {
        this.id = id;
        this.name = name;
        this.signature = signature;
    }

    public Users(String id, String name, String signature, Map<Integer, List<Friend>> friends) {
        this.id = id;
        this.name = name;
        this.signature = signature;
        this.friends = friends;
    }

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id 要设置的 id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return friends
     */
    public Map<Integer, List<Friend>> getFriends() {
        return friends;
    }

    /**
     * @param friends 要设置的 friends
     */
    public void setFriends(Map<Integer, List<Friend>> friends) {
        this.friends = friends;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 要设置的 name
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * 将userInfo类转换为Users类
     *
     * @param userInfo UserInfo类对象
     * @return Users类对象
     */
    public static Users valueOf(UserInfo userInfo) {
        if (userInfo != null) {
            Users users = new Users();
            users.setId(String.valueOf(userInfo.getUser_id()));
            users.setName(userInfo.getUser_name());
            users.setSignature(userInfo.getUser_signature());
            return users;
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return "Users{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", signature='" + signature + '\'' +
                ", friends='" + friends + '\'' +
                '}';
    }
}
