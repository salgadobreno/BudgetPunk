package com.br.widgettest.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.br.widgettest.R;
import com.br.widgettest.core.BuyEntry;
import com.br.widgettest.core.Category;
import com.br.widgettest.core.DailyEntry;
import com.br.widgettest.core.Entry;
import com.br.widgettest.core.FixedEntry;
import com.br.widgettest.core.dao.CategoryDao;
import com.br.widgettest.core.dao.EntryDao;
import com.br.widgettest.core.ledger.Ledger;

import java.sql.Date;

/**
 * Created by Breno on 4/24/2016.
 */
public class NewEntryFragment extends Fragment {
    private static final String TAG = NewEntryFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Ledger ledger = new Ledger(new EntryDao(), new CategoryDao(getContext()));
        View view = inflater.inflate(R.layout.generic_add_entry, null);

        final TextView nameLabel, valueLabel, startDateLabel, endDateLabel, categoryLabel;
        final Spinner categorySpinner, typeSpinner;
        final EditText entryNameField, entryValueField, startDateField, endDateField;
        final Button submitButton;

        nameLabel = (TextView) view.findViewById(R.id.entry_name_label);
        valueLabel = (TextView) view.findViewById(R.id.entry_value_label);
        startDateLabel = (TextView) view.findViewById(R.id.entry_start_date_label);
        endDateLabel = (TextView) view.findViewById(R.id.entry_end_date_label);
        categoryLabel = (TextView) view.findViewById(R.id.entry_category_label);

        entryNameField = (EditText) view.findViewById(R.id.entry_name_field);
        entryValueField = (EditText) view.findViewById(R.id.entry_value_field);
        startDateField = (EditText) view.findViewById(R.id.entry_start_date_field);
        endDateField = (EditText) view.findViewById(R.id.entry_end_date_field);

        categorySpinner = (Spinner) view.findViewById(R.id.entry_category_spinner);
        categorySpinner.setAdapter(
                new ArrayAdapter<>(getContext(),
                        R.layout.support_simple_spinner_dropdown_item,
                        Category.getCategories())
        );
        typeSpinner = (Spinner) view.findViewById(R.id.entry_type_spinner);
        typeSpinner.setAdapter(
                new ArrayAdapter<>(getContext(),
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
                Log.e(TAG, "onNothingSelected() called with: " + "parent = [" + parent + "]");
            }
        });

        submitButton = (Button) view.findViewById(R.id.entry_submit_button);
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

        return view;
    }
}
