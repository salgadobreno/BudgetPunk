package com.br.widgettest.core.dao;

import android.content.Context;
import android.content.res.Resources;
import android.os.Environment;
import android.util.Log;

import com.aop.annotations.Trace;
import com.br.widgettest.R;
import com.br.widgettest.core.BuyEntry;
import com.br.widgettest.core.Category;
import com.br.widgettest.core.DailyEntry;
import com.br.widgettest.core.Entry;
import com.br.widgettest.core.FixedEntry;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

/**
 * Created by Breno on 2/7/2016.
 */
class InternalStorageDB implements DB {
    private static final String TAG = "InternalStorageDB";

    private Context context;

    public InternalStorageDB(Context context) {
        this.context = context;

        try {
            Log.d(TAG, "InternalStorageDB: trying to read reader");
            Reader reader = new InputStreamReader(context.openFileInput(Entity.Entry.name()));
        } catch (FileNotFoundException e) {
            Log.d(TAG, "InternalStorageDB: file not found, seeding");
            try {
                Resources resources = context.getResources();
                Reader reader = new InputStreamReader(resources.openRawResource(R.raw.entry));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(Entity.Entry.name(), Context.MODE_PRIVATE)));
                char[] buffer = new char[1024];
                int bytesRead;
                while ((bytesRead = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, bytesRead);
                }
                writer.flush(); writer.close();
            } catch (IOException e1) {
                throw new RuntimeException("vtnc");
            }
        }
    }

    @Override
    public void persist(Entity entity, List list) {
        CSVWriter writer;
        switch (entity) {
            case Category:
                try {
                    writer = new CSVWriter(new OutputStreamWriter(context.openFileOutput(Entity.Category.name(), Context.MODE_PRIVATE)));
                    List<Category> categoryList = list;
                    for (Category c : categoryList) {
                        writer.writeNext(new CsvCategory(c).toCsv());
                    }
                    writer.flush(); writer.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case Entry:
                try {
                    writer = new CSVWriter(new OutputStreamWriter(context.openFileOutput(Entity.Entry.name(), Context.MODE_PRIVATE)));
                    List<Entry> entryList = list;
                    for (Entry e : entryList) {
                        writer.writeNext(new CsvEntry(e).toCsv());
                    }
                    writer.flush(); writer.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            default: throw new IllegalArgumentException(String.format("%s not implemented", entity.toString()));
        }
    }

    @Override
    public <T> List<T> get(Entity entity) {
        CSVReader reader;
        List list;
        switch (entity) {
            case Entry:
                try {
                    reader = new CSVReader(new InputStreamReader(context.openFileInput(Entity.Entry.name())));
                    list = new ArrayList<>();
                    Log.d(TAG, "reading CSV:");
                    for (String[] csv : reader.readAll()) {
                        list.add(new CsvEntry().fromCsv(csv));
                    }
                } catch (FileNotFoundException e) {
                    list = Collections.emptyList();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case Category:
                try {
                    reader = new CSVReader(new InputStreamReader(context.openFileInput(Entity.Category.name())));
                    list = new ArrayList<>();
                    for (String[] csv : reader.readAll()) {
                        list.add(new CsvCategory().fromCsv(csv));
                    }
                } catch (FileNotFoundException e) {
                    list = Collections.emptyList();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            default: throw new IllegalArgumentException(String.format("%s not implemented", entity.toString()));
        }
        return list;
    }

    private class CsvCategory {
        private Category c;

        public CsvCategory(Category c) {
            this.c = c;
        }

        public CsvCategory() {
        }

        public String[] toCsv() {
            String[] csv = new String[] {c.getName(), String.valueOf(c.getColor())};
            return csv;
        }

        public Category fromCsv(String[] csv) {
            return new Category(csv[0], Integer.valueOf(csv[1]));
        }
    }

    private class CsvEntry {
        private Entry e;

        public CsvEntry(Entry e) {
            this.e = e;
        }

        public CsvEntry() {
        }

        public String[] toCsv() {
            String[] csv = new String[] {
                    e.getName(),
                    String.valueOf(e.getValue().getAmount().doubleValue()),
                    e.getStartDate() == null ? "" : String.valueOf(e.getStartDate().getTime()),
                    e.getEndDate() == null ? "" : String.valueOf(e.getEndDate().getTime()),
                    String.valueOf(Category.getCategories().indexOf(e.getCategory())),
                    String.valueOf(e.getEntryType())
            };
            return csv;
        }

        public Entry fromCsv(String[] csv) {
            Entry.EntryType entryType = Entry.EntryType.valueOf(csv[5]);
            switch (entryType) {
                case DAILY:
                    return new DailyEntry(
                            csv[0],
                            csv[1].equals("") ? null : Double.valueOf(csv[1]),
                            csv[2].equals("") ? null : new Date(Long.valueOf(csv[2])),
                            csv[3].equals("") ? null : new Date(Long.valueOf(csv[3])),
                            csv[4].equals("-1") ? Category.NULL : Category.getCategories().get(Integer.valueOf(csv[4])) //TODO stupid
                    );
                case FIXED:
                    return new FixedEntry(
                            csv[0],
                            csv[1].equals("") ? null : Double.valueOf(csv[1]),// * 30,
                            csv[2].equals("") ? null : new Date(Long.valueOf(csv[2])),
                            csv[3].equals("") ? null : new Date(Long.valueOf(csv[3])),
                            csv[4].equals("-1") ? Category.NULL : Category.getCategories().get(Integer.valueOf(csv[4])) //TODO stupid
                    );
                case BOUGHT:
                    return new BuyEntry(
                            csv[0],
                            csv[1].equals("") ? null : Double.valueOf(csv[1]),
                            csv[2].equals("") ? null : new Date(Long.valueOf(csv[2])),
                            csv[3].equals("") ? null : new Date(Long.valueOf(csv[3])),
                            null //TODO stupid
                    );
                default:
                    throw new IllegalArgumentException(String.format("I don't know %s", entryType));
            }
        }
    }
}
