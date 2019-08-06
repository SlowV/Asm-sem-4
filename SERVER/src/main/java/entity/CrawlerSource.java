package entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import util.StringUtil;

import java.util.Calendar;
import java.util.HashMap;

@Entity
public class CrawlerSource {
    @Id
    private long id;
    private String url;
    private String linkSelector;
    private int linkLimit;
    private String titleSelector;
    private String descriptionSelector;
    private String contentSelector;
    private String authorSelector;
    private String thumbnailSelector;

    @Index
    private long createdAtMLS;
    @Index
    private long updatedAtMLS;
    @Index
    private long deletedAtMLS;
    @Index
    private int status;

    public CrawlerSource() {
        long now = Calendar.getInstance().getTimeInMillis();
        this.id = now;
        this.createdAtMLS = now;
        this.updatedAtMLS = now;
        this.status = Status.ACTIVE.getCode();
    }

    public enum Status{
        ACTIVE(1), DEACTIVE(0), DELETED(-1);
        int code;

        Status(int code) {
            this.code = code;
        }

        public int getCode(){
            return code;
        }

        public static Status findByCode(int code){
            for (Status status : Status.values()){
                if (status.code == code) return status;
            }
            throw new IllegalArgumentException("Kiểu trạng thái không tồn tại!");
        }
    }

    public void setStatus(Status status){
        if (status== null){
            status = Status.DEACTIVE;
        }
        this.status = status.getCode();
    }

    public HashMap<String, String> validSource(){
        HashMap<String, String> errors = new HashMap<>();

        if (this.url.isEmpty()) errors.put("url", String.format(StringUtil.FIELD_EMPTY_ERROR_MSG, "Url"));
        if (this.linkSelector.isEmpty()) errors.put("linkSelector", String.format(StringUtil.FIELD_EMPTY_ERROR_MSG, "Link Selector"));
        if (this.linkLimit == 0) errors.put("linkLimit", String.format(StringUtil.FIELD_EMPTY_ERROR_MSG, "Link Limit"));
        if (this.titleSelector.isEmpty()) errors.put("titleSelector", String.format(StringUtil.FIELD_EMPTY_ERROR_MSG, "Title Selector"));
        if (this.descriptionSelector.isEmpty()) errors.put("Description Selector", String.format(StringUtil.FIELD_EMPTY_ERROR_MSG, "Description Selector"));
        if (this.contentSelector.isEmpty()) errors.put("contentSelector", String.format(StringUtil.FIELD_EMPTY_ERROR_MSG, "Content Selector"));
        if (this.authorSelector.isEmpty()) errors.put("authorSelector", String.format(StringUtil.FIELD_EMPTY_ERROR_MSG, "Author Selector"));

        return errors;
    }

    @Override
    public String toString() {
        return "CrawlerSource{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", linkSelector='" + linkSelector + '\'' +
                ", linkLimit=" + linkLimit +
                ", titleSelector='" + titleSelector + '\'' +
                ", descriptionSelector='" + descriptionSelector + '\'' +
                ", contentSelector='" + contentSelector + '\'' +
                ", authorSelector='" + authorSelector + '\'' +
                ", thumbnailSelector='" + thumbnailSelector + '\'' +
                ", createdAtMLS=" + createdAtMLS +
                ", updatedAtMLS=" + updatedAtMLS +
                ", deletedAtMLS=" + deletedAtMLS +
                ", status=" + status +
                '}';
    }

    public String getThumbnailSelector() {
        return thumbnailSelector;
    }

    public void setThumbnailSelector(String thumbnailSelector) {
        this.thumbnailSelector = thumbnailSelector;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLinkSelector() {
        return linkSelector;
    }

    public void setLinkSelector(String linkSelector) {
        this.linkSelector = linkSelector;
    }

    public int getLinkLimit() {
        return linkLimit;
    }

    public void setLinkLimit(int linkLimit) {
        this.linkLimit = linkLimit;
    }

    public String getTitleSelector() {
        return titleSelector;
    }

    public void setTitleSelector(String titleSelector) {
        this.titleSelector = titleSelector;
    }

    public String getDescriptionSelector() {
        return descriptionSelector;
    }

    public void setDescriptionSelector(String descriptionSelector) {
        this.descriptionSelector = descriptionSelector;
    }

    public String getContentSelector() {
        return contentSelector;
    }

    public void setContentSelector(String contentSelector) {
        this.contentSelector = contentSelector;
    }

    public String getAuthorSelector() {
        return authorSelector;
    }

    public void setAuthorSelector(String authorSelector) {
        this.authorSelector = authorSelector;
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

    public static final class Builder {
        private long id;
        private String url;
        private String linkSelector;
        private int linkLimit;
        private String titleSelector;
        private String descriptionSelector;
        private String contentSelector;
        private String authorSelector;
        private String thumbnailSelector;
        private long createdAtMLS;
        private long updatedAtMLS;
        private long deletedAtMLS;
        private int status;

        private Builder() {
        }

        public static Builder aCrawlerSource() {
            return new Builder();
        }

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setLinkSelector(String linkSelector) {
            this.linkSelector = linkSelector;
            return this;
        }

        public Builder setLinkLimit(int linkLimit) {
            this.linkLimit = linkLimit;
            return this;
        }

        public Builder setTitleSelector(String titleSelector) {
            this.titleSelector = titleSelector;
            return this;
        }

        public Builder setDescriptionSelector(String descriptionSelector) {
            this.descriptionSelector = descriptionSelector;
            return this;
        }

        public Builder setContentSelector(String contentSelector) {
            this.contentSelector = contentSelector;
            return this;
        }

        public Builder setAuthorSelector(String authorSelector) {
            this.authorSelector = authorSelector;
            return this;
        }

        public Builder setThumbnailSelector(String thumbnailSelector) {
            this.thumbnailSelector = thumbnailSelector;
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

        public CrawlerSource build() {
            CrawlerSource crawlerSource = new CrawlerSource();
            crawlerSource.setId(id);
            crawlerSource.setUrl(url);
            crawlerSource.setLinkSelector(linkSelector);
            crawlerSource.setLinkLimit(linkLimit);
            crawlerSource.setTitleSelector(titleSelector);
            crawlerSource.setDescriptionSelector(descriptionSelector);
            crawlerSource.setContentSelector(contentSelector);
            crawlerSource.setAuthorSelector(authorSelector);
            crawlerSource.setThumbnailSelector(thumbnailSelector);
            crawlerSource.setCreatedAtMLS(createdAtMLS);
            crawlerSource.setUpdatedAtMLS(updatedAtMLS);
            crawlerSource.setDeletedAtMLS(deletedAtMLS);
            crawlerSource.setStatus(status);
            return crawlerSource;
        }
    }
}
