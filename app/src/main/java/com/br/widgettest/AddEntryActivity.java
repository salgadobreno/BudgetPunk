package com.br.widgettest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.br.widgettest.core.BuyEntry;
import com.br.widgettest.core.Category;
import com.br.widgettest.core.DailyEntry;
import com.br.widgettest.core.Entry;
import com.br.widgettest.core.FixedEntry;
import com.br.widgettest.core.dao.CategoryDao;
import com.br.widgettest.core.dao.EntryDao;
import com.br.widgettest.core.ledger.Ledger;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class AddEntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // logic
        final Ledger ledger = new Ledger(new EntryDao(getApplicationContext()), new CategoryDao(getApplicationContext()));

        final TextView nameLabel, valueLabel, startDateLabel, endDateLabel, categoryLabel;
        final Spinner categorySpinner, typeSpinner;
        final EditText entryNameField, entryValueField, startDateField, endDateField;
        final Button submitButton;

        nameLabel = (TextView) findViewById(R.id.entry_name_label);
        valueLabel = (TextView) findViewById(R.id.entry_value_label);
        startDateLabel = (TextView) findViewById(R.id.entry_start_date_label);
        endDateLabel = (TextView) findViewById(R.id.entry_end_date_label);
        categoryLabel = (TextView) findViewById(R.id.entry_category_label);

        entryNameField = (EditText) findViewById(R.id.entry_name_field);
        entryValueField = (EditText) findViewById(R.id.entry_value_field);
        startDateField = (EditText) findViewById(R.id.entry_start_date_field);
        endDateField = (EditText) findViewById(R.id.entry_end_date_field);

        categorySpinner = (Spinner) findViewById(R.id.entry_category_spinner);
        categorySpinner.setAdapter(
                new ArrayAdapter<>(getApplicationContext(),
                        R.layout.support_simple_spinner_dropdown_item,
                        Category.getCategories())
        );
        typeSpinner = (Spinner) findViewById(R.id.entry_type_spinner);
        typeSpinner.setAdapter(
                new ArrayAdapter<>(getApplicationContext(),
                        R.layout.support_simple_spinner_dropdown_item,
                        Entry.EntryType.values()
                )
        );

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            final View[] hideInputs = new View[] {
                    nameLabel, valueLabel, startDateLabel, endDateLabel, categoryLabel,
                    entryNameField, entryValueField, startDateField, endDateField, categorySpinner
                    //submit, type: ignored
            };
            final View[] dailyInputs = new View[] {
                    valueLabel, categoryLabel,
                    entryValueField, categorySpinner,
                    startDateLabel, startDateField
            };
            final View[] fixedInputs = new View[] {
                    nameLabel, valueLabel, categoryLabel,
                    entryNameField, entryValueField, categorySpinner
            };
            final View[] boughtInputs = new View[] {
                    nameLabel, valueLabel, startDateLabel, endDateLabel, categoryLabel,
                    entryNameField, entryValueField, startDateField, endDateField, categorySpinner
            };

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Entry.EntryType type = (Entry.EntryType) typeSpinner.getItemAtPosition(position);

                for (View v : hideInputs) { v.setVisibility(View.GONE); }
                switch (type) {
                    case DAILY:
                        //value, category
                        for (View v : dailyInputs) { v.setVisibility(View.VISIBLE); }
                        break;
                    case FIXED:
                        //name, category, value
                        for (View v : fixedInputs) { v.setVisibility(View.VISIBLE); }
                        break;
                    case BOUGHT:
                        //name, value, startDate, endDate, category
                        for (View v : boughtInputs) { v.setVisibility(View.VISIBLE); }
                        break;
                    default:
                        throw new IllegalArgumentException(String.format("I don't know what %s is", type));

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e("", "onNothingSelected() called with: " + "parent = [" + parent + "]");
            }
        });

        submitButton = (Button) findViewById(R.id.entry_submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Entry.EntryType entryType = (Entry.EntryType) typeSpinner.getSelectedItem();
                Entry entry;

                switch (entryType) {
                    case BOUGHT:
                        entry = new BuyEntry(
                                entryNameField.getText().toString(),
                                Double.valueOf(entryValueField.getText().toString()),
                                Date.valueOf(startDateField.getText().toString()),
                                Date.valueOf(endDateField.getText().toString()),
                                (Category) categorySpinner.getSelectedItem()

                        );
                        break;
                    case DAILY:
                        entry = new DailyEntry(
                                Double.valueOf(entryValueField.getText().toString()),
                                (Category) categorySpinner.getSelectedItem(),
                                startDateField.getText().equals("") ? new java.util.Date() : Date.valueOf(startDateField.getText().toString())
                        );
                        break;
                    case FIXED:
                        entry = new FixedEntry(
                                entryNameField.getText().toString(),
                                Double.valueOf(entryValueField.getText().toString()),
                                (Category) categorySpinner.getSelectedItem()
                        );
                        break;
                    default:
                        throw new IllegalArgumentException(String.format("Dunno what %s is", entryType));
                }

                ledger.add(entry);
            }
        });

        //extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String action = extras.getString("action");
            if (action.equals("edit")) { //TODO
                Entry.EntryType entryType = Entry.EntryType.valueOf(extras.getString("entryType")); //TODO: constant
                Entry entry = ledger.getEntries(entryType).get(extras.getInt("entryPos")); //TODO: constant

                int position = Arrays.asList(Entry.EntryType.values()).indexOf(entryType);
                typeSpinner.setSelection(position);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                entryNameField.setText(entry.getName());
                entryValueField.setText(String.valueOf(entry.getValue().getAmount().doubleValue()));
                if (entry.getStartDate() != null) { startDateField.setText(sdf.format(entry.getStartDate()));
                } else { startDateField.setText(""); }
                if (entry.getEndDate() != null) { endDateField.setText(sdf.format(entry.getEndDate()));
                } else { endDateField.setText(""); }

                int catPosition = new EntryDao(getApplicationContext()).list().indexOf(entry.getCategory());
                categorySpinner.setSelection(catPosition);
            }
        }
    }
}
