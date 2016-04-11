package com.br.widgettest.core;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Breno on 1/8/2016.
 */
public class Category {
    private Integer color;
    private String name;

    //['#e41a1c','#377eb8','#4daf4a','#984ea3','#ff7f00','#ffff33','#a65628','#f781bf']
    public static final Category FOOD = new Category("Lunch", Color.parseColor("#ff7f00"));
    public static final Category GROCERIES = new Category("Groceries", Color.parseColor("#f05075"));
    public static final Category CIGS = new Category("Cigs", Color.parseColor("#5b0cb7"));
    public static final Category CLOTHES = new Category("Clothes", Color.parseColor("#377eb8"));
    public static final Category TRANSPORT = new Category("Transport", Color.parseColor("#4daf4a"));
    public static final Category HOUSE = new Category("House", Color.parseColor("#984ea3"));
    public static final Category PERSONAL_CARE = new Category("Personal", Color.parseColor("#17e771"));
    public static final Category BILLS = new Category("Bills", Color.parseColor("#8c467c"));
    public static final Category FARRA = new Category("Farra", Color.parseColor("#ce0c01"));

    public static final Category NULL = new Category("null", Color.GRAY);

    private static final List<Category> CATEGORIES = new ArrayList<>();
    static {
        Collections.addAll(
                CATEGORIES,
                FOOD,
                GROCERIES, CIGS,
                CLOTHES, TRANSPORT,
                HOUSE, PERSONAL_CARE,
                BILLS, FARRA
        );
    }

    public Category(String name, Integer color) {
        this.color = color;
        this.name = name;
    }

    public static List<Category> getCategories() {
        return CATEGORIES;
    }

    public int getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "color=" + color +
                ", name='" + name + '\'' +
                '}';
    }
}
