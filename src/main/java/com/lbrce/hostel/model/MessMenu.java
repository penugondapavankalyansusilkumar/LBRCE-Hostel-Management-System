package com.lbrce.hostel.model;

import java.time.LocalDate;

public class MessMenu {

    private int id;
    private String mealType;
    private String menuItems;
    private LocalDate menuDate;

    public MessMenu() {
    }

    public MessMenu(int id, String mealType,
                    String menuItems, LocalDate menuDate) {
        this.id = id;
        this.mealType = mealType;
        this.menuItems = menuItems;
        this.menuDate = menuDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(String menuItems) {
        this.menuItems = menuItems;
    }

    public LocalDate getMenuDate() {
        return menuDate;
    }

    public void setMenuDate(LocalDate menuDate) {
        this.menuDate = menuDate;
    }
}