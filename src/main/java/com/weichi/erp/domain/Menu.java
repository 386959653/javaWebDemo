package com.weichi.erp.domain;

public class Menu extends SuperDomain<Menu> {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column menu.dish_name
     *
     * @mbggenerated Fri Jun 15 15:50:05 CST 2018
     */
    private String dishName;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column menu.dish_name
     *
     * @return the value of menu.dish_name
     * @mbggenerated Fri Jun 15 15:50:05 CST 2018
     */
    public String getDishName() {
        return dishName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column menu.dish_name
     *
     * @param dishName the value for menu.dish_name
     * @mbggenerated Fri Jun 15 15:50:05 CST 2018
     */
    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

}