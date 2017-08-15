package com.br.widgettest;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.br.widgettest.core.entity.BuyEntryEntity;
import com.br.widgettest.core.entity.DailyEntryEntity;
import com.br.widgettest.core.entity.FixedEntryEntity;
import com.br.widgettest.core.entity.util.EntryEntityToEntry;
import com.br.widgettest.core.ledger.Ledger;

import org.joda.money.Money;
import org.joda.time.Instant;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class AddEntryActivity extends AppCompatActivity {
    enum Action {
        NEW, EDIT
    }

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Action action;
        final Entry.EntryType entryType;
        Bundle extras = getIntent().getExtras();
        final Long id;
        if (extras != null) {
            id = extras.getLong("id");
            action = id == 0L ? Action.NEW : Action.EDIT;
            entryType = Entry.EntryType.valueOf(extras.getString("entryType"));
        } else {
            action = Action.NEW;
            id = null;
            entryType = Entry.EntryType.valueOf(extras.getString("entryType"));
        }

        //setup GUI
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // logic
        final EntryDao entryDao = new EntryDao(); // FIXME: 8/28/2016
        final Ledger ledger = new Ledger(entryDao, new CategoryDao(getApplicationContext()));

        final TextView nameLabel, valueLabel, startDateLabel, endDateLabel, categoryLabel, parcelaLabel;
        final Spinner categorySpinner, typeSpinner;
        final EditText entryNameField, entryValueField, startDateField, endDateField, parcelaField, totalParcelaField;
        final Button submitButton;

        nameLabel = (TextView) findViewById(R.id.entry_name_label);
        valueLabel = (TextView) findViewById(R.id.entry_value_label);
        startDateLabel = (TextView) findViewById(R.id.entry_start_date_label);
        endDateLabel = (TextView) findViewById(R.id.entry_end_date_label);
        categoryLabel = (TextView) findViewById(R.id.entry_category_label);
        parcelaLabel = (TextView) findViewById(R.id.entry_parcela_label);

        entryNameField = (EditText) findViewById(R.id.entry_name_field);
        entryValueField = (EditText) findViewById(R.id.entry_value_field);
        startDateField = (EditText) findViewById(R.id.entry_start_date_field);
        endDateField = (EditText) findViewById(R.id.entry_end_date_field);
        parcelaField = (EditText) findViewById(R.id.entry_parcela_field);
        totalParcelaField = (EditText) findViewById(R.id.entry_total_parcelas_field);

        startDateField.setClickable(true);
        startDateField.setFocusable(false);
        startDateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View current = getCurrentFocus();
                if (current != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(current.getWindowToken(), 0);
                }

                DatePickerFragment newFragment = new DatePickerFragment();
                newFragment.setEditText(startDateField);
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

        endDateField.setClickable(true);
        endDateField.setFocusable(false);
        endDateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View current = getCurrentFocus();
                if (current != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(current.getWindowToken(), 0);
                }

                DatePickerFragment newFragment = new DatePickerFragment();
                newFragment.setEditText(endDateField);
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

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
                    nameLabel, valueLabel, startDateLabel, endDateLabel, categoryLabel, parcelaLabel,
                    entryNameField, entryValueField, startDateField, endDateField, categorySpinner,
                    parcelaField, totalParcelaField
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
                    nameLabel, valueLabel, parcelaLabel, categoryLabel,
                    entryNameField, entryValueField, parcelaField, totalParcelaField, categorySpinner
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
        //setup GUI

        submitButton = (Button) findViewById(R.id.entry_submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Entry.EntryType entryType = (Entry.EntryType) typeSpinner.getSelectedItem();
                Entry entry; // FIXME: 8/28/2016
                if (action == Action.EDIT) {
                    switch (entryType) {
                        case DAILY:
                            entry = new EntryEntityToEntry(DailyEntryEntity.findById(DailyEntryEntity.class, id)).get();
                            break;
                        case FIXED:
                            entry = new EntryEntityToEntry(FixedEntryEntity.findById(FixedEntryEntity.class, id)).get();
                            break;
                        case BOUGHT:
                            entry = new EntryEntityToEntry(BuyEntryEntity.findById(BuyEntryEntity.class, id)).get();
                            break;
                        default: throw new IllegalArgumentException();
                    }
                } else { entry = null; }
                // FIXME: 8/28/2016
//                = action == Action.EDIT ? entryDao.get(id) : null;
                String name = entryNameField.getText().toString();
                Double value = Double.valueOf(entryValueField.getText().toString());
                Date startDate = startDateField.getText().toString().equals("") ? null : Date.valueOf(startDateField.getText().toString());
                Date endDate = endDateField.getText().toString().equals("") ? null : Date.valueOf(endDateField.getText().toString());
                Category category = (Category) categorySpinner.getSelectedItem();

                if (action == Action.EDIT) {
                    entry.setName(name);
                    entry.setValue(Money.of(Entry.CU, value));
                    entry.setStartDate(startDate);
                    entry.setEndDate(endDate);
                    entry.setCategory(category);
                    entryDao.save(entry);
                } else {
                    switch (entryType) {
                        case BOUGHT:
//                            entry = new BuyEntry(
//                                    entryNameField.getText().toString(),
//                                    Double.valueOf(entryValueField.getText().toString()),
//                                    Date.valueOf(startDateField.getText().toString()),
//                                    Date.valueOf(endDateField.getText().toString()),
//                                    (Category) categorySpinner.getSelectedItem());
                            entry = BuyEntry.criarPorParcela(
                                    entryNameField.getText().toString(),
                                    Integer.valueOf(parcelaField.getText().toString()),
                                    Integer.valueOf(totalParcelaField.getText().toString()),
                                    Double.valueOf(entryValueField.getText().toString()),
                                    Instant.now().toDate(),
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
                //go back
                Intent infoIntent = new Intent(getApplicationContext(), InfoDisplayActivity.class);
                infoIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(infoIntent);
            }

        });

        //extras
        if (action == Action.EDIT) { //TODO
            // FIXME: 8/28/2016
//            Entry editEntry = entryDao.get(id);
            final Entry editEntry;
            switch (entryType) {
                case DAILY:
                    editEntry = new EntryEntityToEntry(DailyEntryEntity.findById(DailyEntryEntity.class, id)).get();
                    break;
                case FIXED:
                    editEntry = new EntryEntityToEntry(FixedEntryEntity.findById(FixedEntryEntity.class, id)).get();
                    break;
                case BOUGHT:
                    editEntry = new EntryEntityToEntry(BuyEntryEntity.findById(BuyEntryEntity.class, id)).get();
                    break;
                default: throw new IllegalArgumentException();
            }
            Entry.EntryType editEntryType = editEntry.getEntryType();

            int position = Arrays.asList(Entry.EntryType.values()).indexOf(editEntryType);
            typeSpinner.setSelection(position);


            entryNameField.setText(editEntry.getName());
            entryValueField.setText(String.valueOf(editEntry.getValue().getAmount().doubleValue()));
            if (editEntry.getStartDate() != null) { startDateField.setText(sdf.format(editEntry.getStartDate()));
            } else { startDateField.setText(""); }
            if (editEntry.getEndDate() != null) { endDateField.setText(sdf.format(editEntry.getEndDate()));
            } else { endDateField.setText(""); }

            //TODO
            categorySpinner.setSelection(Category.getCategories().indexOf(editEntry.getCategory()));
        } else {
            int position = Arrays.asList(Entry.EntryType.values()).indexOf(entryType);
            typeSpinner.setSelection(position);
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        private EditText editText;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);
            editText.setText(sdf.format(calendar.getTime()));
        }

        public void setEditText(EditText editText) {
            this.editText = editText;
        }
    }
}
