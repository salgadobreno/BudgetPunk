package com.br.widgettest.ui.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.aop.annotations.Trace;
import com.br.widgettest.R;
import com.br.widgettest.core.BuyEntry;
import com.br.widgettest.core.Category;
import com.br.widgettest.core.ILedger;
import com.br.widgettest.core.dao.EntryDao;

import com.br.widgettest.core.ledger.LightLedger;
import com.br.widgettest.ui.MainUI;
import org.joda.time.Instant;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
@Trace
public class EditBuyEntryFragment extends Fragment implements View.OnClickListener {
    private MainUI holder;

    public static final int DIGIT_0 = R.id.digit0;
    public static final int DIGIT_1 = R.id.digit1;
    public static final int DIGIT_2 = R.id.digit2;
    public static final int DIGIT_3 = R.id.digit3;
    public static final int DIGIT_4 = R.id.digit4;
    public static final int DIGIT_5 = R.id.digit5;
    public static final int DIGIT_6 = R.id.digit6;
    public static final int DIGIT_7 = R.id.digit7;
    public static final int DIGIT_8 = R.id.digit8;
    public static final int DIGIT_9 = R.id.digit9;
    public static final int INFO = R.id.info;
    public static final int COMMIT = R.id.commit;
    public static final int DEL = R.id.delete;

    public static final int EXPENSE_BUTTON = R.id.expense_button;
    public static final int INCOME_BUTTON = R.id.income_button;

    public static final int PARCEL_BUTTON = R.id.parcel_button;
    public static final int TOTAL_BUTTON = R.id.total_button;

    public static final int DATE_DISPLAY = R.id.date_display;

    private UI ui;

    private ILedger ledger = new LightLedger(new EntryDao());

    public static final int[] BUTTONS = {
            DIGIT_0, DIGIT_1, DIGIT_2, DIGIT_3, DIGIT_3, DIGIT_4, DIGIT_5, DIGIT_6, DIGIT_7,
            DIGIT_8, DIGIT_9, INFO, COMMIT, DEL, EXPENSE_BUTTON,
            INCOME_BUTTON, PARCEL_BUTTON, TOTAL_BUTTON
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout._fragment_edit_buy_entry, container, false);
        for (int id : BUTTONS) {
            view.findViewById(id).setOnClickListener(this);
        }
        ui = new UI(view);
        ui.flush();

        return view;
    }

    @Override
    public void onClick(View view) {
        String value = ui.getValue();
        UI.InputMode inputMode = ui.getInputMode();
        UI.ValueMode valueMode = ui.getValueMode();
        switch (view.getId()) {
            case EXPENSE_BUTTON:
                inputMode = UI.InputMode.EXPENSE;
                break;
            case INCOME_BUTTON:
                inputMode = UI.InputMode.INCOME;
                break;
            case PARCEL_BUTTON:
                valueMode = UI.ValueMode.PARCEL;
                break;
            case TOTAL_BUTTON:
                valueMode = UI.ValueMode.FULL;
                break;
            case DEL:
                if (value.length() > 0) value = value.substring(0, value.length() - 1);
                break;
            case DIGIT_0:
                value += "0";
                break;
            case DIGIT_1:
                value += "1";
                break;
            case DIGIT_2:
                value += "2";
                break;
            case DIGIT_3:
                value += "3";
                break;
            case DIGIT_4:
                value += "4";
                break;
            case DIGIT_5:
                value += "5";
                break;
            case DIGIT_6:
                value += "6";
                break;
            case DIGIT_7:
                value += "7";
                break;
            case DIGIT_8:
                value += "8";
                break;
            case DIGIT_9:
                value += "9";
                break;
            case COMMIT:
                Double input = Double.valueOf(value);
                if (inputMode == UI.InputMode.EXPENSE) {
                    input = -input;
                }
                String name = ui.getName();
                BuyEntry entry;
                switch (valueMode) {
                    case FULL:
                        entry = new BuyEntry(
                                name,
                                input,
                                ui.getStartDate(),
                                ui.getEndDate(),
                                Category.NULL);
                        break;
                    case PARCEL:
                        entry = BuyEntry.criarPorParcela(
                                name,
                                ui.getParcel(),
                                ui.getTotalParcels(),
                                input,
                                Instant.now().toDate(),
                                Category.NULL);
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
                ledger.add(entry);
                value = "";
//                holder.showList(true);
                holder.hideEditor();
                holder.scrollBottom();
                break;
            case INFO:
//                holder.showList(false);
                holder.hideEditor();
                break;
            case DATE_DISPLAY:
                // pop up date select
                // apply date to ui
                break;
        }
        ui.setInputMode(inputMode);
        ui.setValueMode(valueMode);
        ui.setValue(value);
        ui.flush();
    }

    public void setHolder(MainUI holder) {
        this.holder = holder;
    }

    static class UI {
        private final InputMethodManager imm;
        private View rootView;
        private final String TAG = "UI/Fragment";

        enum InputMode { EXPENSE, INCOME }
        enum ValueMode { PARCEL, FULL }

        private String value;
        private InputMode inputMode;
        private ValueMode valueMode;

        private EditText nameInput;
        private EditText parcelInput;
        private EditText totalParcelsInput;

        private View.OnClickListener nameOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText popupEditText = new EditText(rootView.getContext());

                popupEditText.setHint("Name");
                popupEditText.setText(getName());

                new AlertDialog.Builder(rootView.getContext())
                        .setTitle("Input Entry Name")
                        .setView(popupEditText)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                final String newName = popupEditText.getText().toString();

                                setName(newName);
                                flush();
                                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.RESULT_UNCHANGED_SHOWN);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.RESULT_UNCHANGED_SHOWN);
                            }
                        })
                        .show();
                popupEditText.requestFocus();
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
        };
        private View.OnClickListener parcelOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText popupEditText = new EditText(rootView.getContext());

//                popupEditText.setHint("1");
                popupEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                popupEditText.setText(getParcel() != 0 ? getParcel().toString() : "");

                new AlertDialog.Builder(rootView.getContext())
                        .setTitle("Input Parcel")
                        .setView(popupEditText)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                final String newVal = popupEditText.getText().toString();

                                setParcels(newVal);
                                flush();
                                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.RESULT_UNCHANGED_SHOWN);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.RESULT_UNCHANGED_SHOWN);
                            }
                        })
                        .show();
                popupEditText.requestFocus();
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
        };
        private View.OnClickListener totalParcelsOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText popupEditText = new EditText(rootView.getContext());

//                popupEditText.setHint("1");
                popupEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                popupEditText.setText(getTotalParcels() != 0 ? getTotalParcels().toString() : "");

                new AlertDialog.Builder(rootView.getContext())
                        .setTitle("Input Total Parcels")
                        .setView(popupEditText)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                final String newVal = popupEditText.getText().toString();

                                setTotalParcels(newVal);
                                flush();
                                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.RESULT_UNCHANGED_SHOWN);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.RESULT_UNCHANGED_SHOWN);
                            }
                        })
                        .show();
                popupEditText.requestFocus();
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
        };

        public UI(View rootView) {
            this.imm = (InputMethodManager) rootView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            this.rootView = rootView;

            nameInput = (EditText) rootView.findViewById(R.id.edit_text_name);
            nameInput.setFocusable(false);
            nameInput.setOnClickListener(nameOnClickListener);

            parcelInput = (EditText) rootView.findViewById(R.id.entry_parcela_field);
            parcelInput.setFocusable(false);
            parcelInput.setOnClickListener(parcelOnClickListener);

            totalParcelsInput = (EditText) rootView.findViewById(R.id.entry_total_parcelas_field);
            totalParcelsInput.setFocusable(false);
            totalParcelsInput.setOnClickListener(totalParcelsOnClickListener);

            this.value = "";
            this.inputMode = InputMode.EXPENSE;
            this.valueMode = ValueMode.PARCEL;
        }

        public String getName() {
            return nameInput.getText().toString();
        }

        public void setName(String name) {
            nameInput.setText(name);
        }

        public void setInputMode(InputMode inputMode) {
            this.inputMode = inputMode;
        }

        public InputMode getInputMode() {
            return this.inputMode;
        }

        public ValueMode getValueMode() {
            return valueMode;
        }

        public void setValueMode(ValueMode valueMode) {
            this.valueMode = valueMode;
        }

        public Date getStartDate() {
            EditText startDateField = (EditText) rootView.findViewById(R.id.entry_start_date_field);
            return java.sql.Date.valueOf(startDateField.getText().toString());
        }

        public Date getEndDate() {
            EditText endDateField = (EditText) rootView.findViewById(R.id.entry_end_date_field);
            return java.sql.Date.valueOf(endDateField.getText().toString());
        }

        public Integer getParcel() {
            return !parcelInput.getText().toString().equals("") ? Integer.valueOf(parcelInput.getText().toString()) : 0;
        }

        public void setParcels(String value) {
            parcelInput.setText(value);
        }

        public Integer getTotalParcels() {
            return !totalParcelsInput.getText().toString().equals("") ? Integer.valueOf(totalParcelsInput.getText().toString()) : 0;
        }

        public void setTotalParcels(String value) {
            totalParcelsInput.setText(value);
        }

        public void setValue(String newValue) {
            String oldValue = getValue();
            if (oldValue.length() < newValue.length()) {
                if (newValue.length() == 2) {
                    newValue += ".";
                }
                if (newValue.length() > 2) {
                    newValue = newValue.replace(".", "");
                    newValue = new StringBuilder(newValue).insert(newValue.length() - 2, '.').toString();
                }
            }
            if (oldValue.length() > newValue.length()) {
//            if (newValue.length() == 3 && newValue.charAt(2) == '.') newValue = newValue.replace(".", "");
                if (newValue.length() > 2) {
                    newValue = newValue.replace(".", "");
                    newValue = new StringBuilder(newValue).insert(newValue.length() - 2, '.').toString();
                }
            }

            this.value = newValue;
        }

        public String getValue() {
            return this.value;
        }

        public void flush() {
            TextView display = (TextView) rootView.findViewById(R.id.display);
            View expenseButton = rootView.findViewById(R.id.expense_button);
            View incomeButton = rootView.findViewById(R.id.income_button);

            display.setText(getValue());
            switch (getInputMode()) {
                case EXPENSE:
                    expenseButton.setBackgroundResource(R.color.expense_on);
                    incomeButton.setBackgroundResource(R.color.income_off);
                    break;
                case INCOME:
                    expenseButton.setBackgroundResource(R.color.expense_off);
                    incomeButton.setBackgroundResource(R.color.income_on);
                    break;
            }

            switch (valueMode) {
                case PARCEL:
                    rootView.findViewById(R.id.parcel_parcel_view).setVisibility(View.VISIBLE);
                    rootView.findViewById(R.id.parcel_total_view).setVisibility(View.GONE);
                    rootView.findViewById(R.id.parcel_button).setBackgroundResource(R.color.colorPrimaryDark);
                    rootView.findViewById(R.id.total_button).setBackgroundResource(R.color.colorPrimary);
                    break;
                case FULL:
                    rootView.findViewById(R.id.parcel_parcel_view).setVisibility(View.GONE);
                    rootView.findViewById(R.id.parcel_total_view).setVisibility(View.VISIBLE);
                    rootView.findViewById(R.id.total_button).setBackgroundResource(R.color.colorPrimaryDark);
                    rootView.findViewById(R.id.parcel_button).setBackgroundResource(R.color.colorPrimary);
                    break;
            }

            Log.i(TAG, "flush");
        }
    }
}
