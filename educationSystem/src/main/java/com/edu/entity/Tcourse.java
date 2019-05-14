package com.edu.entity;

import javax.persistence.*;

public class Tcourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String courseno;

    private String coursename;

    private Integer teacherid;

    private String collegeid;

    private String credit;

    private String studytime;

    private String status;

    private String starttime;

    private String classtime;

    private String place;

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

    public String getCourseno() {
        return courseno;
    }

    public void setCourseno(String courseno) {
        this.courseno = courseno;
    }

    /**
     * @return coursename
     */
    public String getCoursename() {
        return coursename;
    }

    /**
     * @param coursename
     */
    public void setCoursename(String coursename) {
        this.coursename = coursename == null ? null : coursename.trim();
    }

    /**
     * @return teacherid
     */
    public Integer getTeacherid() {
        return teacherid;
    }

    /**
     * @param teacherid
     */
    public void setTeacherid(Integer teacherid) {
        this.teacherid = teacherid;
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
     * @return credit
     */
    public String getCredit() {
        return credit;
    }

    /**
     * @param credit
     */
    public void setCredit(String credit) {
        this.credit = credit == null ? null : credit.trim();
    }

    /**
     * @return studytime
     */
    public String getStudytime() {
        return studytime;
    }

    /**
     * @param studytime
     */
    public void setStudytime(String studytime) {
        this.studytime = studytime == null ? null : studytime.trim();
    }

    /**
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * @return starttime
     */
    public String getStarttime() {
        return starttime;
    }

    /**
     * @param starttime
     */
    public void setStarttime(String starttime) {
        this.starttime = starttime == null ? null : starttime.trim();
    }

    /**
     * @return classtime
     */
    public String getClasstime() {
        return classtime;
    }

    /**
     * @param classtime
     */
    public void setClasstime(String classtime) {
        this.classtime = classtime == null ? null : classtime.trim();
    }

    /**
     * @return place
     */
    public String getPlace() {
        return place;
    }

    /**
     * @param place
     */
    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }


    @Transient
    private String teachername;
    @Transient
    private String collegename;

    public String getCollegename() {
        return collegename;
    }

    public String getTeachername() {
        return teachername;
    }

    public void setTeachername(String teachername) {
        this.teachername = teachername;
    }

    public void setCollegename(String collegename) {
        this.collegename = collegename;
    }
}