package entity;

import util.StringUtil;

import java.util.HashMap;

public class ArticleTypeToJSON {
    private String url;
    private String title;
    private String description;
    private String content;
    private String author;
    private long sourceId;
    private String thumbnail;
    private long categoryId;
    private long createdAtMLS;
    private long updatedAtMLS;
    private long deletedAtMLS;
    private int status;

    public boolean checkNull(){
        return this.title.isEmpty() || this.description.isEmpty() || this.author.isEmpty() || this.content.isEmpty() || categoryId == 0;
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

    public long getCreatedAtMLS() {
        return createdAtMLS;
    }

    public void setCreatedAtMLS(long createdAtMLS) {
        this.createdAtMLS = createdAtMLS;
    }

    public long getUpdatedAtMLS() {
        return updatedAtMLS;
    }

    public void setUpdatedAtMLS(long updatedAtMLS) {
        this.updatedAtMLS = updatedAtMLS;
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

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getSourceId() {
        return sourceId;
    }

    public void setSourceId(long sourceId) {
        this.sourceId = sourceId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
