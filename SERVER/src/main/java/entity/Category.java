package entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import util.StringUtil;

import java.util.Calendar;
import java.util.HashMap;

@Entity
public class Category {
    @Id
    private long id;
    @Index
    private String name;
    @Index
    private String description;
    @Index
    private long createdAtMLS;
    @Index
    private long updatedAtMLS;
    private long deletedAtMLS;
    @Index
    private int status;

    public Category() {
        long now = Calendar.getInstance().getTimeInMillis();
        this.id = now;
        this.createdAtMLS = now;
        this.updatedAtMLS = now;
        this.status = Status.ACTIVE.getCode();
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

    public HashMap<String, String> validCategory() {
        HashMap<String, String> errors = new HashMap<>();
        if (this.name.isEmpty()) errors.put("name", String.format(StringUtil.FIELD_EMPTY_ERROR_MSG, "Ten danh muc"));
        if (this.description.isEmpty())
            errors.put("description", String.format(StringUtil.FIELD_EMPTY_ERROR_MSG, "Mo ta danh muc"));
        return errors;
    }

    public boolean isDeactiveAndDeleted() {
        return this.status == Article.Status.DEACTIVE.getCode() || this.status == Article.Status.DELETED.getCode();
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdAtMLS=" + createdAtMLS +
                ", updatedAtMLS=" + updatedAtMLS +
                ", deletedAtMLS=" + deletedAtMLS +
                ", status=" + status +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Category setStatus(int status) {
        this.status = status;
        return this;
    }

    public static final class Builder {
        private long id;
        private String name;
        private String description;
        private long createdAtMLS;
        private long updatedAtMLS;
        private long deletedAtMLS;
        private int status;

        private Builder() {
        }

        public static Builder aCategory() {
            return new Builder();
        }

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
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

        public Category build() {
            Category category = new Category();
            category.setId(id);
            category.setName(name);
            category.setDescription(description);
            category.setCreatedAtMLS(createdAtMLS);
            category.setUpdatedAtMLS(updatedAtMLS);
            category.setDeletedAtMLS(deletedAtMLS);
            category.setStatus(status);
            return category;
        }
    }
}
