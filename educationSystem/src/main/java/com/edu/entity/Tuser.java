package com.edu.entity;

import javax.persistence.*;

@Table(name = "t_user")
public class Tuser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String bz;

    private String password;

    @Column(name = "true_name")
    private String trueName;

    @Column(name = "user_name")
    private String userName;

    private Integer remarks;

    private String sex;

    private String workingtime;

    private String political;

    private String collegeid;

    private String classno;

    private String graduate;

    private String phone;

    private String nativeplace;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return bz
     */
    public String getBz() {
        return bz;
    }

    /**
     * @param bz
     */
    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * @return true_name
     */
    public String getTrueName() {
        return trueName;
    }

    /**
     * @param trueName
     */
    public void setTrueName(String trueName) {
        this.trueName = trueName == null ? null : trueName.trim();
    }

    /**
     * @return user_name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * @return remarks
     */
    public Integer getRemarks() {
        return remarks;
    }

    /**
     * @param remarks
     */
    public void setRemarks(Integer remarks) {
        this.remarks = remarks;
    }

    /**
     * @return sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex
     */
    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    /**
     * @return workingtime
     */
    public String getWorkingtime() {
        return workingtime;
    }

    /**
     * @param workingtime
     */
    public void setWorkingtime(String workingtime) {
        this.workingtime = workingtime == null?null:workingtime.trim();
    }

    /**
     * @return political
     */
    public String getPolitical() {
        return political;
    }

    /**
     * @param political
     */
    public void setPolitical(String political) {
        this.political = political == null ? null : political.trim();
    }

    /**
     * @return collegeid
     */
    public String getCollegeid() {
        return collegeid;
    }

    /**
     * @param collegeid
     */
    public void setCollegeid(String collegeid) {
        this.collegeid = collegeid == null ? null : collegeid.trim();
    }

    /**
     * @return classno
     */
    public String getClassno() {
        return classno;
    }

    /**
     * @param classno
     */
    public void setClassno(String classno) {
        this.classno = classno == null ? null : classno.trim();
    }

    /**
     * @return graduate
     */
    public String getGraduate() {
        return graduate;
    }

    /**
     * @param graduate
     */
    public void setGraduate(String graduate) {
        this.graduate = graduate == null ? null : graduate.trim();
    }

    /**
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * @return nativeplace
     */
    public String getNativeplace() {
        return nativeplace;
    }

    /**
     * @param nativeplace
     */
    public void setNativeplace(String nativeplace) {
        this.nativeplace = nativeplace == null ? null : nativeplace.trim();
    }


    @Transient
    private String roles;

    @Transient
    private String oldPassword;

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }




}