package bean;

import java.util.Map;

/**
 * 好友分组
 */
public class Category {
    private String userId;
    private Map<String, String> categoryInfo;

    public Category(String userId, Map<String, String> categoryInfo) {
        this.userId = userId;
        this.categoryInfo = categoryInfo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Map<String, String> getCategoryInfo() {
        return categoryInfo;
    }

    public void setCategoryInfo(Map<String, String> categoryInfo) {
        this.categoryInfo = categoryInfo;
    }

    @Override
    public String toString() {
        return "Category{" +
                "userId='" + userId + '\'' +
                ", categoryInfo=" + categoryInfo +
                '}';
    }
}
