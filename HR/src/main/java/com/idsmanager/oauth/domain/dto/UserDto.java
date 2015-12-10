package com.idsmanager.oauth.domain.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.idsmanager.oauth.domain.user.Privilege;
import com.idsmanager.oauth.domain.user.User;
import com.idsmanager.oauth.infrastructure.DateUtils;
import com.idsmanager.oauth.infrastructure.PasswordHandler;



public class UserDto {
	 private static final long serialVersionUID = 229740639141916411L;

	    private String guid;
	    private String createTime;
	    private boolean archived;

	    private String username;
	    private String password;
	    private String phone;
	    private String email;

	    private List<String> privileges = new ArrayList<String>();

	    public UserDto() {
	    }

	    public UserDto(User user) {
	        this.guid = user.guid();
	        this.createTime = DateUtils.toDateTime(user.createTime());

	        this.archived = user.archived();
	        this.username = user.username();

	        this.password = user.password();
	        this.phone = user.phone();
	        this.email = user.email();

	        final Set<Privilege> privilegeList = user.privileges();
	        for (Privilege privilege : privilegeList) {
	            this.privileges.add(privilege.name());
	        }
	    }

	    public String getCreateTime() {
	        return createTime;
	    }

	    public void setCreateTime(String createTime) {
	        this.createTime = createTime;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }


	    public List<Privilege> getAvailablePrivileges() {
	        return Privilege.availablePrivileges();
	    }

	    public boolean isArchived() {
	        return archived;
	    }

	    public void setArchived(boolean archived) {
	        this.archived = archived;
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

	    public String getPhone() {
	        return phone;
	    }

	    public void setPhone(String phone) {
	        this.phone = phone;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public List<String> getPrivileges() {
	        return privileges;
	    }

	    public void setPrivileges(List<String> privileges) {
	        this.privileges = privileges;
	    }

	    public static List<UserDto> toDtos(List<User> users) {
	        List<UserDto> dtos = new ArrayList<UserDto>(users.size());
	        for (User user : users) {
	            dtos.add(new UserDto(user));
	        }
	        return dtos;
	    }

	    public User newDomain() {
	        String pass = PasswordHandler.encryptPassword(password);
	        User user = new User(username, pass, phone, email);

	        final Set<Privilege> privilegeSet = user.privileges();
	        for (String privilege : privileges) {
	            privilegeSet.add(Privilege.valueOf(privilege));
	        }

	        return user;
	    }
	}