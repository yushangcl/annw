package top.annwz.base.entity;

import java.util.Date;

public class BaUser {
    private Integer userId;

    private String userName;

    private String password;

    private String mobile;

    private String email;

    private Integer passwordChangeNext;

    private Integer locked;

    private Date lockedDate;

    private String faceUrl;

    private Date createTime;

    private Date updateTime;

    private Integer createUserId;

    private Integer updateUserId;

    private Integer emailStatus;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getPasswordChangeNext() {
        return passwordChangeNext;
    }

    public void setPasswordChangeNext(Integer passwordChangeNext) {
        this.passwordChangeNext = passwordChangeNext;
    }

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    public Date getLockedDate() {
        return lockedDate;
    }

    public void setLockedDate(Date lockedDate) {
        this.lockedDate = lockedDate;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl == null ? null : faceUrl.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }


    public Integer getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(Integer emailStatus) {
        this.emailStatus = emailStatus;
    }
}