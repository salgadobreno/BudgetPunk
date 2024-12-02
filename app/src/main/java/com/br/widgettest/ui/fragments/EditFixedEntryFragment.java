package com.br.widgettest.ui.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.br.widgettest.R;
import com.br.widgettest.core.Category;
import com.br.widgettest.core.FixedEntry;
import com.br.widgettest.core.ILedger;
import com.br.widgettest.core.dao.EntryDao;
import com.br.widgettest.core.ledger.LightLedger;
import com.br.widgettest.ui.MainUI;

//@Trace
public class EditFixedEntryFragment extends Fragment implements View.OnClickListener {
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

    public static final int DATE_DISPLAY = R.id.date_display;

    private UI ui;

    private ILedger ledger = new LightLedger(new EntryDao());

    public static final int[] BUTTONS = {
            DIGIT_0, DIGIT_1, DIGIT_2, DIGIT_3, DIGIT_3, DIGIT_4, DIGIT_5, DIGIT_6, DIGIT_7,
            DIGIT_8, DIGIT_9, INFO, COMMIT, DEL, EXPENSE_BUTTON,
            INCOME_BUTTON
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout._fragment_edit_fixed_entry, container, false);
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
        switch (view.getId()) {
            case EXPENSE_BUTTON:
                inputMode = UI.InputMode.EXPENSE;
                break;
            case INCOME_BUTTON:
                inputMode = UI.InputMode.INCOME;
                break;
            case DEL:
                if(value.length() > 0) value = value.substring(0, value.length() - 1);
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
                FixedEntry entry = new FixedEntry(name, input, Category.NULL);
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
        ui.setValue(value);
        ui.flush();
    }

    public void setHolder(MainUI holder) {
        this.holder = holder;
    }

//    interface EditFixedEntryUI {
//        void setInputMode(UI.InputMode inputMode);
//        UI.InputMode getInputMode();
//        String getName();
//        void setName(String name);
//        void setValue(String value);
//        String getValue();
//        void flush();
//    }

    static class UI {
        private final InputMethodManager imm;
        private final View rootView;
        private final String TAG = "UI/Fragment";

        enum InputMode { EXPENSE, INCOME }

        private String value;
        private String name;
        private InputMode inputMode;

        private View.OnClickListener nameOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText popupEditText = new EditText(rootView.getContext());

                popupEditText.setHint("Name");
                popupEditText.setText(getName());

                new AlertDialog.Builder(rootView.getContext())
                        .setTitle("Input Entry name")
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

        public UI(final View rootView) {
            imm = (InputMethodManager) rootView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

            this.rootView = rootView;
            this.value = "";
            this.name = "";
            this.inputMode = InputMode.EXPENSE;

            final EditText nameField = (EditText) rootView.findViewById(R.id.edit_text_name);
            nameField.setFocusable(false);
            nameField.setOnClickListener(nameOnClickListener);
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setInputMode(InputMode inputMode) {
            this.inputMode = inputMode;
        }

        public InputMode getInputMode() {
            return this.inputMode;
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
            EditText nameInput = (EditText) rootView.findViewById(R.id.edit_text_name);
            TextView display = (TextView) rootView.findViewById(R.id.display);
            View expenseButton = rootView.findViewById(R.id.expense_button);
            View incomeButton = rootView.findViewById(R.id.income_button);

            nameInput.setText(getName());
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

            Log.i(TAG, "flush");
        }
    }
}
