package com.br.widgettest.core.dao;

import android.content.Context;

import com.br.widgettest.core.Category;

import java.util.List;

/**
 * Created by Breno on 2/9/2016.
 */
public class CategoryDao extends AbstractDao<Category> {
    private static final DB.Entity ENTITY = DB.Entity.Category;

    private static Category nullCategory = new Category("null", 0);

    public CategoryDao(Context context) {
        super(context, ENTITY);
    }

    @Override
    public Category find(String id) {
        for (Object c : db.get(ENTITY)) {
            Category category = (Category) c;
            if (category.getName().equals(id)) return category;
        }
        return nullCategory;
    }

    @Override
    DB.Entity getEntity() {
        return ENTITY;
    }
}
