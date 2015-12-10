package com.idsmanager.oauth.domain.user;

import com.idsmanager.oauth.domain.shared.GuidGenerator;
import com.idsmanager.oauth.infrastructure.DateUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;


import java.io.Serializable;
import java.util.*;

/**
 * @author Shengzhao Li
 */
@Document(collection = "user_")
public class User implements Serializable {


    private static final long serialVersionUID = -6117108610171201352L;


    @Id
    private String guid = GuidGenerator.generate();

    @CreatedDate
    private Date createTime = DateUtils.now();

    @Version
    private int version = 0;

    //unique
    private String username;

    private String password;

    private String phone;
    private String email;
    //Default user is initialed
    private boolean defaultUser = false;

    private Date lastLoginTime;

    private Set<Privilege> privileges = new HashSet<Privilege>();


    private boolean archived = false;

    private Boolean state = true;//是否开启二次认证
	private Boolean supportFido = false;//是否支持Fido协议
	private Integer loginNum = 0;//登录次数
    public User() {
    }

    public User(String username, String password, String phone, String email) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
    }


    public int version() {
        return version;
    }

    public String guid() {
        return guid;
    }

    public boolean defaultUser() {
        return defaultUser;
    }

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }

    public String phone() {
        return phone;
    }

    public String email() {
        return email;
    }

    public Set<Privilege> privileges() {
        return privileges;
    }

    public Date createTime() {
        return createTime;
    }

    public User createTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public boolean archived() {
        return archived;
    }

    public User archived(boolean archived) {
        this.archived = archived;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("{username='").append(username).append('\'')
                .append(", phone='").append(phone).append('\'')
                .append(", version='").append(version).append('\'')
                .append(", defaultUser='").append(defaultUser).append('\'')
                .append(", email='").append(email).append('\'').append('}');
        return sb.toString();
    }

    public User email(String email) {
        this.email = email;
        return this;
    }

    public User phone(String phone) {
        this.phone = phone;
        return this;
    }


    public User username(String username) {
        this.username = username;
        return this;
    }


    public Date lastLoginTime() {
        return lastLoginTime;
    }

    public void lastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	public Boolean getSupportFido() {
		return supportFido;
	}

	public void setSupportFido(Boolean supportFido) {
		this.supportFido = supportFido;
	}

	public Integer getLoginNum() {
		return loginNum;
	}

	public void setLoginNum(Integer loginNum) {
		this.loginNum = loginNum;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
    
}