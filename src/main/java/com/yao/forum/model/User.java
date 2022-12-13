package com.yao.forum.model;

public class User {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.ID
     *
     * @mbg.generated Tue Dec 13 19:35:30 CST 2022
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.ACCOUNT_ID
     *
     * @mbg.generated Tue Dec 13 19:35:30 CST 2022
     */
    private String accountId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.NAME
     *
     * @mbg.generated Tue Dec 13 19:35:30 CST 2022
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.TOKEN
     *
     * @mbg.generated Tue Dec 13 19:35:30 CST 2022
     */
    private String token;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.GMT_CREATE
     *
     * @mbg.generated Tue Dec 13 19:35:30 CST 2022
     */
    private Long gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.GMT_MODIFIED
     *
     * @mbg.generated Tue Dec 13 19:35:30 CST 2022
     */
    private Long gmtModified;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.avatar_url
     *
     * @mbg.generated Tue Dec 13 19:35:30 CST 2022
     */
    private String avatarUrl;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.ID
     *
     * @return the value of user.ID
     *
     * @mbg.generated Tue Dec 13 19:35:30 CST 2022
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.ID
     *
     * @param id the value for user.ID
     *
     * @mbg.generated Tue Dec 13 19:35:30 CST 2022
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.ACCOUNT_ID
     *
     * @return the value of user.ACCOUNT_ID
     *
     * @mbg.generated Tue Dec 13 19:35:30 CST 2022
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.ACCOUNT_ID
     *
     * @param accountId the value for user.ACCOUNT_ID
     *
     * @mbg.generated Tue Dec 13 19:35:30 CST 2022
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.NAME
     *
     * @return the value of user.NAME
     *
     * @mbg.generated Tue Dec 13 19:35:30 CST 2022
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.NAME
     *
     * @param name the value for user.NAME
     *
     * @mbg.generated Tue Dec 13 19:35:30 CST 2022
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.TOKEN
     *
     * @return the value of user.TOKEN
     *
     * @mbg.generated Tue Dec 13 19:35:30 CST 2022
     */
    public String getToken() {
        return token;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.TOKEN
     *
     * @param token the value for user.TOKEN
     *
     * @mbg.generated Tue Dec 13 19:35:30 CST 2022
     */
    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.GMT_CREATE
     *
     * @return the value of user.GMT_CREATE
     *
     * @mbg.generated Tue Dec 13 19:35:30 CST 2022
     */
    public Long getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.GMT_CREATE
     *
     * @param gmtCreate the value for user.GMT_CREATE
     *
     * @mbg.generated Tue Dec 13 19:35:30 CST 2022
     */
    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.GMT_MODIFIED
     *
     * @return the value of user.GMT_MODIFIED
     *
     * @mbg.generated Tue Dec 13 19:35:30 CST 2022
     */
    public Long getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.GMT_MODIFIED
     *
     * @param gmtModified the value for user.GMT_MODIFIED
     *
     * @mbg.generated Tue Dec 13 19:35:30 CST 2022
     */
    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.avatar_url
     *
     * @return the value of user.avatar_url
     *
     * @mbg.generated Tue Dec 13 19:35:30 CST 2022
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.avatar_url
     *
     * @param avatarUrl the value for user.avatar_url
     *
     * @mbg.generated Tue Dec 13 19:35:30 CST 2022
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl == null ? null : avatarUrl.trim();
    }
}