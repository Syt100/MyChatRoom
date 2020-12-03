package domain;

/**
 * category_info表对应的实体类
 */
public class CategoryInfo {
    private Integer category_id;
    private Integer user_id;
    private String name;

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CategoryInfo{" +
                "category_id=" + category_id +
                ", user_id=" + user_id +
                ", name='" + name + '\'' +
                '}';
    }
}
