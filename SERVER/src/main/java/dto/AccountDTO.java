package dto;


import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import entity.Account;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AccountDTO {

    private String username;
    private String email;
    private String avatar;
    private String createdAt;
    private String updatedAt;
    private String status;
    private long token;
    public AccountDTO(Account account,long token) {
        this.username = account.getUsername();
        this.avatar = account.getAvatar();
        this.email = account.getEmail();
        this.token = token;
        this.createdAt = convertMilliToDateString(account.getCreatedAt());
        this.updatedAt = convertMilliToDateString(account.getUpdatedAt());
        this.status = Account.Status.findByCode(account.getStatus()).name();

    }

    private String convertMilliToDateString(long time){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dateFormat.format(new Date(time));
    }

    public long getToken() {
        return token;
    }

    public void setToken(long token) {
        this.token = token;
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



    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
