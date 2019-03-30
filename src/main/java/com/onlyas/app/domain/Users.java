package com.onlyas.app.domain;

import java.time.LocalDateTime;

/**
 * 表名: users 
 * 
 * @author  MBG  2019-03-31
 */
public class Users {
    /**
     * id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 性别
     */
    private String userSex;

    /**
     * 昵称
     */
    private String userNickname;

    /**
     * 生日
     */
    private LocalDateTime userBirthday;

    /**
     * 邮箱
     */
    private String userEmail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public LocalDateTime getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(LocalDateTime userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userName=").append(userName);
        sb.append(", userSex=").append(userSex);
        sb.append(", userNickname=").append(userNickname);
        sb.append(", userBirthday=").append(userBirthday);
        sb.append(", userEmail=").append(userEmail);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Users other = (Users) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserName() == null ? other.getUserName() == null : this.getUserName().equals(other.getUserName()))
            && (this.getUserSex() == null ? other.getUserSex() == null : this.getUserSex().equals(other.getUserSex()))
            && (this.getUserNickname() == null ? other.getUserNickname() == null : this.getUserNickname().equals(other.getUserNickname()))
            && (this.getUserBirthday() == null ? other.getUserBirthday() == null : this.getUserBirthday().equals(other.getUserBirthday()))
            && (this.getUserEmail() == null ? other.getUserEmail() == null : this.getUserEmail().equals(other.getUserEmail()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
        result = prime * result + ((getUserSex() == null) ? 0 : getUserSex().hashCode());
        result = prime * result + ((getUserNickname() == null) ? 0 : getUserNickname().hashCode());
        result = prime * result + ((getUserBirthday() == null) ? 0 : getUserBirthday().hashCode());
        result = prime * result + ((getUserEmail() == null) ? 0 : getUserEmail().hashCode());
        return result;
    }
}