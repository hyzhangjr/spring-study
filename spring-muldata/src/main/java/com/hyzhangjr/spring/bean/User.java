package com.hyzhangjr.spring.bean;




public class User {
    private String id;
    private String username;
    private String userage;
    private String useraddress;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserage() {
        return userage;
    }

    public void setUserage(String userage) {
        this.userage = userage;
    }

    public String getUseraddress() {
        return useraddress;
    }

    public void setUseraddress(String useaddress) {
        this.useraddress = useaddress;
    }
}
