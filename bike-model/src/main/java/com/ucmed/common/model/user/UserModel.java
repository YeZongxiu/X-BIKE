package com.ucmed.common.model.user;

import com.ucmed.common.util.Constants;
import com.ucmed.common.util.StringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

public class UserModel {
    private Long id;

    private String phone;

    private String password;

    private String securityKey;

    private Date createTime;

    private Date lastLoginTime;

    private Integer loginTime;

    private String photo;

    private Date updateTime;

    private String isDelete;

    private Integer deposit;

    private Integer wallet;

    private String isAdmin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecurityKey() {
        return securityKey;
    }

    public void setSecurityKey(String securityKey) {
        this.securityKey = securityKey;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Integer loginTime) {
        this.loginTime = loginTime;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getDeposit() {
        return deposit;
    }

    public void setDeposit(Integer deposit) {
        this.deposit = deposit;
    }

    public Integer getWallet() {
        return wallet;
    }

    public void setWallet(Integer wallet) {
        this.wallet = wallet;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public JSONObject getJsonObject(){
        JSONObject json = new JSONObject();
        if (StringUtil.isNotBlank(this.id)){
            json.put("user_id", this.id);
        }
        if (StringUtil.isNotBlank(this.phone)){
            json.put("phone", this.phone);
        }
        if (StringUtils.isNotBlank(this.photo) && null != this.photo){
            json.put("photo", Constants.USER_IMG_URL + this.photo);
        }else {
            json.put("photo", Constants.DOMAIN + "/admin/images/dog.png");
        }
        if (null == this.deposit){
            json.put("deposit", 0);
        } else {
            json.put("deposit", this.deposit);
        }
        if (null == this.wallet){
            json.put("wallet", 0);
        } else {
            json.put("wallet", this.wallet);
        }
        if (StringUtil.isNotBlank(this.isAdmin) && "1".equals(this.isAdmin)){
            json.put("is_admin", true);
        } else {
            json.put("is_admin", false);
        }
        return  json;
    }
}