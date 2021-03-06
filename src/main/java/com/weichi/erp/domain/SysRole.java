package com.weichi.erp.domain;

public class SysRole extends SuperDomain<SysRole> {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role.id
     *
     * @mbggenerated Thu Jun 07 11:17:46 CST 2018
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role.role_name
     *
     * @mbggenerated Thu Jun 07 11:17:46 CST 2018
     */
    private String roleName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_permission.pid
     *
     * @mbggenerated Thu Jun 07 15:18:45 CST 2018
     */
    private Long pid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_role.id
     *
     * @return the value of sys_role.id
     * @mbggenerated Thu Jun 07 11:17:46 CST 2018
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_role.id
     *
     * @param id the value for sys_role.id
     * @mbggenerated Thu Jun 07 11:17:46 CST 2018
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_role.role_name
     *
     * @return the value of sys_role.role_name
     * @mbggenerated Thu Jun 07 11:17:46 CST 2018
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_role.role_name
     *
     * @param roleName the value for sys_role.role_name
     * @mbggenerated Thu Jun 07 11:17:46 CST 2018
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_permission.pid
     *
     * @return the value of sys_permission.pid
     * @mbggenerated Thu Jun 07 15:18:45 CST 2018
     */
    public Long getPid() {
        return pid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_permission.pid
     *
     * @param pid the value for sys_permission.pid
     * @mbggenerated Thu Jun 07 15:18:45 CST 2018
     */
    public void setPid(Long pid) {
        this.pid = pid;
    }
}