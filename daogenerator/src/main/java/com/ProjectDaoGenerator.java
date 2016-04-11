package com;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by Breno on 2/1/2016.
 */
public class ProjectDaoGenerator {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(0, "br.com.oficina.app");

        createSchema(schema);

        new DaoGenerator().generateAll(schema, "app/src/main/java");
    }

    private static void createSchema(Schema schema) {
        Entity category = schema.addEntity("Category");
        category.addIdProperty();
        category.addStringProperty("name").unique().notNull();
        category.addIntProperty("color").notNull();

        Entity entry = schema.addEntity("Entry");
        entry.addIdProperty();
        entry.addStringProperty("name");
        entry.addDoubleProperty("value").notNull();
        entry.addDateProperty("startDate");
        entry.addDateProperty("endDate");
        entry.addShortProperty("entryType");
        Property categoryIdProperty = entry.addLongProperty("categoryId").notNull().getProperty();
        entry.addToOne(category, categoryIdProperty, "category");
    }
}
