package com.weichi.erp.domain;

public class SysConfig extends SuperDomain {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_config.parm_name
     *
     * @mbggenerated Fri Nov 09 13:27:00 CST 2018
     */
    private String parmName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_config.parm_value
     *
     * @mbggenerated Fri Nov 09 13:27:00 CST 2018
     */
    private String parmValue;

    private String descp;

    public String getDescp() {
        return descp;
    }

    public void setDescp(String descp) {
        this.descp = descp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_config.parm_name
     *
     * @return the value of sys_config.parm_name
     * @mbggenerated Fri Nov 09 13:27:00 CST 2018
     */
    public String getParmName() {
        return parmName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_config.parm_name
     *
     * @param parmName the value for sys_config.parm_name
     * @mbggenerated Fri Nov 09 13:27:00 CST 2018
     */
    public void setParmName(String parmName) {
        this.parmName = parmName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_config.parm_value
     *
     * @return the value of sys_config.parm_value
     * @mbggenerated Fri Nov 09 13:27:00 CST 2018
     */
    public String getParmValue() {
        return parmValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_config.parm_value
     *
     * @param parmValue the value for sys_config.parm_value
     * @mbggenerated Fri Nov 09 13:27:00 CST 2018
     */
    public void setParmValue(String parmValue) {
        this.parmValue = parmValue;
    }
}