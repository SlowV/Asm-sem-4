package entity;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;
import util.StringUtil;

import java.util.Calendar;
import java.util.HashMap;

@Entity
public class Article {
    @Id
    private String url;
    private String title;
    private String description;
    private String content;
    private String author;
    private long sourceId;
    private String thumbnail;
    private long createdAtMLS;
    private long updatedAtMLS;
    private long deletedAtMLS;
    private int status;
    @Index
    @Load
    private Ref<Category> category;

    public Article() {
        long now = Calendar.getInstance().getTimeInMillis();
        this.createdAtMLS = now;
        this.updatedAtMLS = now;
        this.status = Category.Status.ACTIVE.getCode();
    }

    public enum Status {
        ACTIVE(1), DEACTIVE(0), DELETED(-1);
        int code;

        Status(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public static CrawlerSource.Status findByCode(int code) {
            for (CrawlerSource.Status status : CrawlerSource.Status.values()) {
                if (status.code == code) return status;
            }
            throw new IllegalArgumentException("Kiểu trạng thái không tồn tại!");
        }
    }



    public void setStatus(CrawlerSource.Status status) {
        if (status == null) {
            status = CrawlerSource.Status.DEACTIVE;
        }
        this.status = status.getCode();
    }

    public boolean checkNull(){
        return this.title.isEmpty() && this.description.isEmpty() && this.author.isEmpty() && this.content.isEmpty();
    }

    public HashMap<String, String> validArticle(){
        HashMap<String, String> errors = new HashMap<>();
        if (this.title.isEmpty()){
            errors.put("title", String.format(StringUtil.FIELD_EMPTY_ERROR_MSG, "Ten bai viet"));
        }

        if(this.description.isEmpty()){
            errors.put("description", String.format(StringUtil.FIELD_EMPTY_ERROR_MSG, "Mo ta bai viet"));
        }

        if(this.author.isEmpty()){
            errors.put("author", String.format(StringUtil.FIELD_EMPTY_ERROR_MSG, "Tac gia bai viet"));
        }

        if(this.content.isEmpty()){
            errors.put("content", String.format(StringUtil.FIELD_EMPTY_ERROR_MSG, "Noi dung bai viet"));
        }
        return errors;
    }

    @Override
    public String toString() {
        return "Article{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", sourceId=" + sourceId +
                ", thumbnail='" + thumbnail + '\'' +
                ", createdAtMLS=" + createdAtMLS +
                ", updatedAtMLS=" + updatedAtMLS +
                ", deletedAtMLS=" + deletedAtMLS +
                ", status=" + status +
                ", category=" + category +
                '}';
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public Article setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Article setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Article setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Article setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Article setContent(String content) {
        this.content = content;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Article setAuthor(String author) {
        this.author = author;
        return this;
    }

    public long getSourceId() {
        return sourceId;
    }

    public Article setSourceId(long sourceId) {
        this.sourceId = sourceId;
        return this;
    }

    public long getCreatedAtMLS() {
        return createdAtMLS;
    }

    public Article setCreatedAtMLS(long createdAtMLS) {
        this.createdAtMLS = createdAtMLS;
        return this;
    }

    public long getUpdatedAtMLS() {
        return updatedAtMLS;
    }

    public Article setUpdatedAtMLS(long updatedAtMLS) {
        this.updatedAtMLS = updatedAtMLS;
        return this;
    }

    public long getDeletedAtMLS() {
        return deletedAtMLS;
    }

    public Article setDeletedAtMLS(long deletedAtMLS) {
        this.deletedAtMLS = deletedAtMLS;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public Article setStatus(int status) {
        this.status = status;
        return this;
    }

    public Ref<Category> getCategory() {
        return category;
    }

    public Article setCategory(Ref<Category> category) {
        this.category = category;
        return this;
    }

    public static final class Builder {
        private String url;
        private String title;
        private String description;
        private String content;
        private String author;
        private long sourceId;
        private String thumbnail;
        private long createdAtMLS;
        private long updatedAtMLS;
        private long deletedAtMLS;
        private int status;
        private Ref<Category> category;

        private Builder() {
        }

        public static Builder anArticle() {
            return new Builder();
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setAuthor(String author) {
            this.author = author;
            return this;
        }

        public Builder setSourceId(long sourceId) {
            this.sourceId = sourceId;
            return this;
        }

        public Builder setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
            return this;
        }

        public Builder setCreatedAtMLS(long createdAtMLS) {
            this.createdAtMLS = createdAtMLS;
            return this;
        }

        public Builder setUpdatedAtMLS(long updatedAtMLS) {
            this.updatedAtMLS = updatedAtMLS;
            return this;
        }

        public Builder setDeletedAtMLS(long deletedAtMLS) {
            this.deletedAtMLS = deletedAtMLS;
            return this;
        }

        public Builder setStatus(int status) {
            this.status = status;
            return this;
        }

        public Builder setCategory(Ref<Category> category) {
            this.category = category;
            return this;
        }

        public Article build() {
            Article article = new Article();
            article.setUrl(url);
            article.setTitle(title);
            article.setDescription(description);
            article.setContent(content);
            article.setAuthor(author);
            article.setSourceId(sourceId);
            article.setThumbnail(thumbnail);
            article.setCreatedAtMLS(createdAtMLS);
            article.setUpdatedAtMLS(updatedAtMLS);
            article.setDeletedAtMLS(deletedAtMLS);
            article.setStatus(status);
            article.setCategory(category);
            return article;
        }
    }
}
