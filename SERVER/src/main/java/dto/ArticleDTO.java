package dto;

import entity.Article;
import entity.Category;

public class ArticleDTO {
    private String url;
    private String title;
    private String description;
    private String content;
    private String author;
    private long sourceId;
    private long createdAtMLS;
    private long updatedAtMLS;
    private String image;
    private long deletedAtMLS;
    private int status;
    private long categoryId;
    private String categoryName;
    private String categoryDescription;
    private String thumbnail;

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public ArticleDTO() {
    }

    public ArticleDTO(Article article) {
        this.url = article.getUrl();
        this.title = article.getTitle();
        Category category = article.getCategory().get();
        this.categoryId = category.getId();
        this.categoryName = category.getName();
        this.categoryDescription = category.getDescription();
        this.sourceId = article.getSourceId();
        this.content = article.getContent();
        this.description = article.getDescription();
        this.author = article.getAuthor();
        this.image = article.getThumbnail();
        this.thumbnail = article.getThumbnail();
        this.status = article.getStatus();
    }

    public String getImage() {
        return image;
    }

    public ArticleDTO setImage(String image) {
        this.image = image;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public ArticleDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ArticleDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getContent() {
        return content;
    }

    public ArticleDTO setContent(String content) {
        this.content = content;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public ArticleDTO setAuthor(String author) {
        this.author = author;
        return this;
    }

    public long getSourceId() {
        return sourceId;
    }

    public void setSourceId(long sourceId) {
        this.sourceId = sourceId;
    }

    public long getCreatedAtMLS() {
        return createdAtMLS;
    }

    public ArticleDTO setCreatedAtMLS(long createdAtMLS) {
        this.createdAtMLS = createdAtMLS;
        return this;
    }

    public long getUpdatedAtMLS() {
        return updatedAtMLS;
    }

    @Override
    public String toString() {
        return "ArticleDTO{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", sourceId=" + sourceId +
                ", createdAtMLS=" + createdAtMLS +
                ", updatedAtMLS=" + updatedAtMLS +
                ", image='" + image + '\'' +
                ", deletedAtMLS=" + deletedAtMLS +
                ", status=" + status +
                ", categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", categoryDescription='" + categoryDescription + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }

    public ArticleDTO setUpdatedAtMLS(long updatedAtMLS) {
        this.updatedAtMLS = updatedAtMLS;
        return this;
    }

    public long getDeletedAtMLS() {
        return deletedAtMLS;
    }

    public void setDeletedAtMLS(long deletedAtMLS) {
        this.deletedAtMLS = deletedAtMLS;
    }

    public int getStatus() {
        return status;
    }

    public ArticleDTO setStatus(int status) {
        this.status = status;
        return this;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public ArticleDTO setCategoryId(long categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public ArticleDTO setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public ArticleDTO setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
        return this;
    }
}
