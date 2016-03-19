package com.sinyuk.jianyimaterial.entity;

import java.util.List;
import com.sinyuk.jianyimaterial.greendao.dao.DaoSession;
import de.greenrobot.dao.DaoException;

import com.sinyuk.jianyimaterial.greendao.dao.UserDao;
import com.sinyuk.jianyimaterial.greendao.dao.YihuoDetailsDao;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "USER".
 */
public class User implements java.io.Serializable {


    private String id;
    private String status;
    private String name;
    private String lastlogin;
    private String lastip;
    private String email;
    private String openid;
    private String sex;
    private String role_id;
    private String realname;
    private String province;
    private String city;
    private String country;
    private String heading;
    private String language;
    private String Gamount;
    private String tel;
    private String self_words;
    private String edu_id;
    private String idcard;
    private String self_introduction;
    private String school;
    private String last_x;
    private String last_y;
    private String current_school;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient UserDao myDao;

    private List<YihuoDetails> yihuoLikes;

    // KEEP FIELDS - put your custom fields here
    public static final String TAG = "User";
    public static final String UPDATE_REQUEST = "update";
    // KEEP FIELDS END

    public User() {
    }

    public User(String id) {
        this.id = id;
    }

    public User(String id, String status, String name, String lastlogin, String lastip, String email, String openid, String sex, String role_id, String realname, String province, String city, String country, String heading, String language, String Gamount, String tel, String self_words, String edu_id, String idcard, String self_introduction, String school, String last_x, String last_y, String current_school) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.lastlogin = lastlogin;
        this.lastip = lastip;
        this.email = email;
        this.openid = openid;
        this.sex = sex;
        this.role_id = role_id;
        this.realname = realname;
        this.province = province;
        this.city = city;
        this.country = country;
        this.heading = heading;
        this.language = language;
        this.Gamount = Gamount;
        this.tel = tel;
        this.self_words = self_words;
        this.edu_id = edu_id;
        this.idcard = idcard;
        this.self_introduction = self_introduction;
        this.school = school;
        this.last_x = last_x;
        this.last_y = last_y;
        this.current_school = current_school;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserDao() : null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(String lastlogin) {
        this.lastlogin = lastlogin;
    }

    public String getLastip() {
        return lastip;
    }

    public void setLastip(String lastip) {
        this.lastip = lastip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getGamount() {
        return Gamount;
    }

    public void setGamount(String Gamount) {
        this.Gamount = Gamount;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getSelf_words() {
        return self_words;
    }

    public void setSelf_words(String self_words) {
        this.self_words = self_words;
    }

    public String getEdu_id() {
        return edu_id;
    }

    public void setEdu_id(String edu_id) {
        this.edu_id = edu_id;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getSelf_introduction() {
        return self_introduction;
    }

    public void setSelf_introduction(String self_introduction) {
        this.self_introduction = self_introduction;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getLast_x() {
        return last_x;
    }

    public void setLast_x(String last_x) {
        this.last_x = last_x;
    }

    public String getLast_y() {
        return last_y;
    }

    public void setLast_y(String last_y) {
        this.last_y = last_y;
    }

    public String getCurrent_school() {
        return current_school;
    }

    public void setCurrent_school(String current_school) {
        this.current_school = current_school;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<YihuoDetails> getYihuoLikes() {
        if (yihuoLikes == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            YihuoDetailsDao targetDao = daoSession.getYihuoDetailsDao();
            List<YihuoDetails> yihuoLikesNew = targetDao._queryUser_YihuoLikes(id);
            synchronized (this) {
                if(yihuoLikes == null) {
                    yihuoLikes = yihuoLikesNew;
                }
            }
        }
        return yihuoLikes;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetYihuoLikes() {
        yihuoLikes = null;
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

    // KEEP METHODS - put your custom methods here
        
    // KEEP METHODS END

}
