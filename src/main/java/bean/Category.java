package bean;

import java.util.ArrayList;
import java.util.Map;

/**
 * 好友分组
 */
public class Category {
    /**
     * uid用户ID
     */
    private String userId;
    /**
     * 分组名称map, key表示分组ID, value表示分组名称
     */
    private Map<String, String> categoryInfo;
    /**
     * 分组成员map, key表示分组ID, value表示分组成员，成员为Users的ArrayList
     */
    private Map<String, ArrayList<Users>> categoryMember;

    public Category(String userId, Map<String, String> categoryInfo, Map<String, ArrayList<Users>> categoryMember) {
        this.userId = userId;
        this.categoryInfo = categoryInfo;
        this.categoryMember = categoryMember;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @SuppressWarnings("unused")
    public Map<String, String> getCategoryInfo() {
        return categoryInfo;
    }

    @SuppressWarnings("unused")
    public void setCategoryInfo(Map<String, String> categoryInfo) {
        this.categoryInfo = categoryInfo;
    }

    @SuppressWarnings("unused")
    public Map<String, ArrayList<Users>> getCategoryMember() {
        return categoryMember;
    }

    @SuppressWarnings("unused")
    public void setCategoryMember(Map<String, ArrayList<Users>> categoryMember) {
        this.categoryMember = categoryMember;
    }

    @Override
    public String toString() {
        return "Category{" +
                "userId='" + userId + '\'' +
                ", categoryInfo=" + categoryInfo +
                ", categoryMember=" + categoryMember +
                '}';
    }
}
