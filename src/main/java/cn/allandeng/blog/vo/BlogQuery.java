package cn.allandeng.blog.vo;/**
 * @Auther: Allan
 * @Date: 2020/2/6 13:46
 * @Description:
 */

/**
 * @ClassName BlogQuery
 * @Date:2020/2/6 13:46
 * @Description: 博客查询VO
 * @Author: Allan Deng
 * @Version: 1.0
 **/
public class BlogQuery {

    private String title;

    private Long typeId;

    private boolean recommend;

    public BlogQuery() {
    }

    @Override
    public String toString() {
        return "BlogQuery{" +
                "title='" + title + '\'' +
                ", typeId=" + typeId +
                ", recommend=" + recommend +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }
}
