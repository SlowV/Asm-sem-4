package entity;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import util.StringUtil;

import java.util.Calendar;
import java.util.HashMap;

import static com.googlecode.objectify.ObjectifyService.ofy;

@Entity
public class Account {
    @Id
    private String username;
    @Index
    private String email;
    private String passwordHash;
    private String avatar;
    private String salt;
    @Index
    private long createdAt;
    @Index
    private long updatedAt;
    private long deletedAt;
    @Index
    private int status;

    @Index
    @Load
    private Ref<Role> role;

    public Account() {
        long now = Calendar.getInstance().getTimeInMillis();
        this.createdAt = now;
        this.updatedAt = now;
        this.status = Status.ACTIVE.getCode();
    }

    public String hashPassword(String password) {
        if (this.salt == null || this.salt.length() == 0) {
            this.salt = StringUtil.generateSalt();
        }
        return StringUtil.hashPassword(password, this.salt);
    }

    public Ref<Role> getRole() {
        return role;
    }

    public void setRole(Ref<Role> role) {
        this.role = role;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public HashMap<String, String> validAccount() {
        HashMap<String, String> errors = new HashMap<>();
        if (this.username.isEmpty()){
            errors.put("username", String.format(StringUtil.FIELD_EMPTY_ERROR_MSG, "Tai khoan"));

        }else if (this.email.isEmpty()){
            errors.put("email", String.format(StringUtil.FIELD_EMPTY_ERROR_MSG, "Email"));

        }else if (!this.email.contains("@")){
            errors.put("email", StringUtil.FORMAT_EMAIL);

        }else if (this.passwordHash.isEmpty()){
            errors.put("password", String.format(StringUtil.FIELD_EMPTY_ERROR_MSG, "Mật khẩu"));

        }else if (this.passwordHash.length() < 6 || this.passwordHash.length() > 20){
            errors.put("password", StringUtil.LENGTH_PASSWORD_ERROR);

        }
        return errors;
    }


    public Account login() {
        Account account = ofy().load().type(Account.class).id(this.username).now();
        System.out.println(account.toString());
        if (account.isDeactiveAndDeleted() || !StringUtil.comparePasswordWithSalt(this.passwordHash, account.getSalt(), account.getPasswordHash())){
            return null;
        }
        return account;
    }

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", avatar='" + avatar + '\'' +
                ", salt='" + salt + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                ", status=" + status +
                ", role=" + role +
                '}';
    }

    public boolean isDeactiveAndDeleted(){
        return this.status == Article.Status.DEACTIVE.getCode() || this.status == Article.Status.DELETED.getCode();
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

        public static Account.Status findByCode(int code) {
            for (Account.Status status : Account.Status.values()) {
                if (status.code == code) return status;
            }
            throw new IllegalArgumentException("Kiểu trạng thái không tồn tại!");
        }
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public long getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(long deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
