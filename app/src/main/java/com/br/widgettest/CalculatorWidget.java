package com.br.widgettest;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.aop.annotations.Trace;
//import com.br.widgettest.core.Category;
import com.br.widgettest.core.Category;
import com.br.widgettest.core.DailyEntry;
import com.br.widgettest.core.ILedger;
//import com.br.widgettest.core.dao.CategoryDao;
import com.br.widgettest.core.dao.EntryDao;
import com.br.widgettest.core.exceptions.NotImplementedException;
//import com.br.widgettest.core.ledger.Ledger;
//import com.br.widgettest.ui.extensions.CyclableList;
import com.br.widgettest.core.ledger.LightLedger;
import com.xlythe.math.Base;
import com.xlythe.math.Constants;
import com.xlythe.math.EquationFormatter;
import com.xlythe.math.History;
import com.xlythe.math.Persist;
import com.xlythe.math.Solver;

import org.javia.arity.SyntaxException;

/**
 * Created by Breno on 11/30/2015.
 */
@Trace
public class CalculatorWidget extends AppWidgetProvider {
    public final static String PREFERENCE_WIDGET_PREAMBLE = "com.android.calculator2.CALC_WIDGET_VALUE_";
    public static final String DIGIT_0 = "com.android.calculator2.0";
    public static final String DIGIT_1 = "com.android.calculator2.1";
    public static final String DIGIT_2 = "com.android.calculator2.2";
    public static final String DIGIT_3 = "com.android.calculator2.3";
    public static final String DIGIT_4 = "com.android.calculator2.4";
    public static final String DIGIT_5 = "com.android.calculator2.5";
    public static final String DIGIT_6 = "com.android.calculator2.6";
    public static final String DIGIT_7 = "com.android.calculator2.7";
    public static final String DIGIT_8 = "com.android.calculator2.8";
    public static final String DIGIT_9 = "com.android.calculator2.9";
    public static final String DOT = "com.android.calculator2.dot";
    public static final String PLUS = "com.android.calculator2.plus";
    public static final String MINUS = "com.android.calculator2.minus";
    public static final String COMMIT = "com.android.calculator2.commit";
    public static final String INFO = "com.android.calculator2.info";
    public static final String EQUALS = "com.android.calculator2.equals";
    public static final String CLR = "com.android.calculator2.clear";
    public static final String DEL = "com.android.calculator2.delete";
    public static final String SHOW_CLEAR = "com.android.calculator2.show_clear";

    public static final String CATEGORY = "com.android.calculator2.toggle_category";
    public static final String CATEGORY_BACK = "com.android.calculator2.category_back";
    public static final String EXPENSE_BUTTON = "com.android.calculator2.expense_button";
    public static final String INCOME_BUTTON = "com.android.calculator2.income_button";

    private static final String TAG = "WIDGETTEST";

    private boolean mClearText = false;

//    static CyclableList<Category> categories = new CyclableList<>(Category.getCategories());

//    static Category category;
    static UI.InputMode inputMode;

    static ILedger ledger;

    @Override
    public void onReceive(Context context, Intent intent) {
//        ledger = new Ledger(new EntryDao(), new CategoryDao(context));
        ledger = new LightLedger(new EntryDao());

        UI ui = new UI(context, new RemoteViews(context.getPackageName(), R.layout.main_widget));

        int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 0);
        String value = getValue(context, appWidgetId);
        if(value.equals(context.getResources().getString(R.string.error_syntax))) value = "";
        mClearText = intent.getBooleanExtra(SHOW_CLEAR, false);

        Log.i(TAG, intent.getAction() + " clicked");
        if(intent.getAction().equals(DIGIT_0)) {
            if(mClearText) {
                value = "";
                mClearText = false;
            }
            value += "0";
        } else if(intent.getAction().equals(DIGIT_1)) {
            if(mClearText) {
                value = "";
                mClearText = false;
            }
            value += "1";
        } else if(intent.getAction().equals(DIGIT_2)) {
            if(mClearText) {
                value = "";
                mClearText = false;
            }
            value += "2";
        } else if(intent.getAction().equals(DIGIT_3)) {
            if(mClearText) {
                value = "";
                mClearText = false;
            }
            value += "3";
        } else if(intent.getAction().equals(DIGIT_4)) {
            if(mClearText) {
                value = "";
                mClearText = false;
            }
            value += "4";
        } else if(intent.getAction().equals(DIGIT_5)) {
            if(mClearText) {
                value = "";
                mClearText = false;
            }
            value += "5";
        } else if(intent.getAction().equals(DIGIT_6)) {
            if(mClearText) {
                value = "";
                mClearText = false;
            }
            value += "6";
        } else if(intent.getAction().equals(DIGIT_7)) {
            if(mClearText) {
                value = "";
                mClearText = false;
            }
            value += "7";
        } else if(intent.getAction().equals(DIGIT_8)) {
            if(mClearText) {
                value = "";
                mClearText = false;
            }
            value += "8";
        } else if(intent.getAction().equals(DIGIT_9)) {
            if(mClearText) {
                value = "";
                mClearText = false;
            }
            value += "9";
        } else if(intent.getAction().equals(DOT)) {
            if(mClearText) {
                value = "";
                mClearText = false;
            }
            value += getDecimal();
        } else if(intent.getAction().equals(COMMIT)) {
            //TODO: force equals before?
            //TODO: change to INCOME or EXPENSE if input begins with + or -??
            Double input = Double.valueOf(value);
            if (inputMode == UI.InputMode.EXPENSE) {
                input = -input;
            }
//            DailyEntry entry = new DailyEntry(input, category);
            DailyEntry entry = new DailyEntry(input, Category.NULL);
            ledger.add(entry); //TODO: shitty
            value = "";
        } else if(intent.getAction().equals(MINUS)) {
            value = addOperator(value, Constants.MINUS);
        } else if(intent.getAction().equals(PLUS)) {
            value = addOperator(value, Constants.PLUS);
        } else if(intent.getAction().equals(EQUALS)) {
            if(mClearText) {
                value = "";
                mClearText = false;
            } else {
                mClearText = true;
            }
            final String input = value;
            if(input.isEmpty()) return;

            final Solver logic = new Solver();
            logic.setLineLength(7);

            try {
                value = logic.solve(input);
            } catch(SyntaxException e) {
                value = context.getResources().getString(R.string.error_syntax);
            }

            // Try to save it to history
            if(!value.equals(context.getResources().getString(R.string.error_syntax))) {
                final Persist persist = new Persist(context);
                persist.load();
                if(persist.getMode() == null) persist.setMode(Base.DECIMAL);
                final History history = persist.getHistory();
                history.enter(input, value);
                persist.save();
            }
        } else if(intent.getAction().equals(CLR)) {
            value = "";
        } else if(intent.getAction().equals(DEL)) {
            if(value.length() > 0) value = value.substring(0, value.length() - 1);
//        } else if (intent.getAction().equals(CATEGORY)) {
//            category = categories.cycle(CyclableList.Direction.FORWARD);
//            ui.updateCategory(category);
//        } else if (intent.getAction().equals(CATEGORY_BACK)) {
//            category = categories.cycle(CyclableList.Direction.BACKWARD);
//            ui.updateCategory(category);
        } else if (intent.getAction().equals(INCOME_BUTTON)) {
            inputMode = UI.InputMode.INCOME;
        } else if (intent.getAction().equals(EXPENSE_BUTTON)) {
            inputMode = UI.InputMode.EXPENSE;
        }
        setValue(context, appWidgetId, value);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, CalculatorWidget.class));
        for(int appWidgetID : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetID);
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for(int appWidgetID : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetID);
        }
    }

    private static void setValue(Context context, int appWidgetId, String newValue) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(PREFERENCE_WIDGET_PREAMBLE + appWidgetId, newValue).commit();
    }

    private static String getDecimal() {
        return ".";
    }

    private static String addOperator(String equation, char op) {
        if(equation.length() > 0) {
            // Grab the last character to see if it's an operator
            char lastChar = equation.charAt(equation.length()-1);

            // Remove the previous operator if needed
            if((Solver.isOperator(lastChar) && lastChar != Constants.MINUS)
                    || (op == Constants.MINUS && op == lastChar)) {
                equation = equation.substring(0, equation.length() - 1);
            }

            // Append the new operator
            equation += op;
        }
        else if(op == Constants.MINUS) {
            equation += op;
        }

        return equation;
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        UI ui = new UI(context, new RemoteViews(context.getPackageName(), R.layout.main_widget));
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.main_widget);

        String value = getValue(context, appWidgetId);

        EquationFormatter formatter = new EquationFormatter();
//        value = formatter.addComas(new Solver(), value);

        int displayId = android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.JELLY_BEAN_MR1 ? R.id.display_long_clickable : R.id.display;

        remoteViews.setViewVisibility(displayId, View.VISIBLE);
        remoteViews.setTextViewText(displayId, value);
        remoteViews.setTextViewText(R.id.display, value);
        remoteViews.setViewVisibility(R.id.delete, mClearText ? View.GONE : View.VISIBLE);
        remoteViews.setViewVisibility(R.id.clear, mClearText ? View.VISIBLE : View.GONE);
        setOnClickListeners(context, appWidgetId, remoteViews);

//        if (category == null) category = categories.get(0);
//        ui.updateCategory(category);

        if (inputMode == null) inputMode = UI.InputMode.EXPENSE;
        ui.setInputMode(inputMode);

        try {
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        } catch(Exception e) {
        }
    }

    private static String getValue(Context context, int appWidgetId) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PREFERENCE_WIDGET_PREAMBLE + appWidgetId, "");
    }

    private void setOnClickListeners(Context context, int appWidgetId, RemoteViews remoteViews) {
        final Intent intent = new Intent(context, CalculatorWidget.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.putExtra(SHOW_CLEAR, mClearText);

        // The pending intent request code must be unique
        // Not just for these 17 buttons, but for each widget as well
        // Painful T_T Right?
        // So take the id and shift it over 5 bits (enough to store our 17
        // values)
        int shiftedAppWidgetId = appWidgetId << 5;
        // And save our button values (0-16)

        intent.setAction(DIGIT_0);
        remoteViews.setOnClickPendingIntent(R.id.digit0, PendingIntent.getBroadcast(context, shiftedAppWidgetId + 0, intent, 0));

        intent.setAction(DIGIT_1);
        remoteViews.setOnClickPendingIntent(R.id.digit1, PendingIntent.getBroadcast(context, shiftedAppWidgetId + 1, intent, 0));

        intent.setAction(DIGIT_2);
        remoteViews.setOnClickPendingIntent(R.id.digit2, PendingIntent.getBroadcast(context, shiftedAppWidgetId + 2, intent, 0));

        intent.setAction(DIGIT_3);
        remoteViews.setOnClickPendingIntent(R.id.digit3, PendingIntent.getBroadcast(context, shiftedAppWidgetId + 3, intent, 0));

        intent.setAction(DIGIT_4);
        remoteViews.setOnClickPendingIntent(R.id.digit4, PendingIntent.getBroadcast(context, shiftedAppWidgetId + 4, intent, 0));

        intent.setAction(DIGIT_5);
        remoteViews.setOnClickPendingIntent(R.id.digit5, PendingIntent.getBroadcast(context, shiftedAppWidgetId + 5, intent, 0));

        intent.setAction(DIGIT_6);
        remoteViews.setOnClickPendingIntent(R.id.digit6, PendingIntent.getBroadcast(context, shiftedAppWidgetId + 6, intent, 0));

        intent.setAction(DIGIT_7);
        remoteViews.setOnClickPendingIntent(R.id.digit7, PendingIntent.getBroadcast(context, shiftedAppWidgetId + 7, intent, 0));

        intent.setAction(DIGIT_8);
        remoteViews.setOnClickPendingIntent(R.id.digit8, PendingIntent.getBroadcast(context, shiftedAppWidgetId + 8, intent, 0));

        intent.setAction(DIGIT_9);
        remoteViews.setOnClickPendingIntent(R.id.digit9, PendingIntent.getBroadcast(context, shiftedAppWidgetId + 9, intent, 0));

        intent.setAction(DOT);
        remoteViews.setOnClickPendingIntent(R.id.dot, PendingIntent.getBroadcast(context, shiftedAppWidgetId + 10, intent, 0));

//        Intent infoIntent = new Intent(context, InfoDisplayActivity2.class);
        Intent infoIntent = new Intent(context, InfoDisplayActivity.class);
        infoIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent launchInfoPendingIntent = PendingIntent.getActivity(context, 0, infoIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        intent.setAction(INFO);
//        remoteViews.setOnClickPendingIntent(R.id.info, PendingIntent.getBroadcast(context, shiftedAppWidgetId + 11, intent, 0));
        remoteViews.setOnClickPendingIntent(R.id.info, launchInfoPendingIntent);

        intent.setAction(COMMIT);
        remoteViews.setOnClickPendingIntent(R.id.commit, PendingIntent.getBroadcast(context, shiftedAppWidgetId + 12, intent, 0));

        intent.setAction(MINUS);
        remoteViews.setOnClickPendingIntent(R.id.minus, PendingIntent.getBroadcast(context, shiftedAppWidgetId + 13, intent, 0));

        intent.setAction(PLUS);
        remoteViews.setOnClickPendingIntent(R.id.plus, PendingIntent.getBroadcast(context, shiftedAppWidgetId + 14, intent, 0));

        intent.setAction(EQUALS);
        remoteViews.setOnClickPendingIntent(R.id.equal, PendingIntent.getBroadcast(context, shiftedAppWidgetId + 15, intent, 0));

        intent.setAction(DEL);
        remoteViews.setOnClickPendingIntent(R.id.delete, PendingIntent.getBroadcast(context, shiftedAppWidgetId + 16, intent, 0));

        intent.setAction(CLR);
        remoteViews.setOnClickPendingIntent(R.id.clear, PendingIntent.getBroadcast(context, shiftedAppWidgetId + 17, intent, 0));

//        intent.setAction(CATEGORY);
//        remoteViews.setOnClickPendingIntent(R.id.toggle_category, PendingIntent.getBroadcast(context, shiftedAppWidgetId + 18, intent, 0));

//        intent.setAction(CATEGORY_BACK);
//        remoteViews.setOnClickPendingIntent(R.id.toggle_category_side_button, PendingIntent.getBroadcast(context, shiftedAppWidgetId + 19, intent, 0));

        intent.setAction(EXPENSE_BUTTON);
        remoteViews.setOnClickPendingIntent(R.id.expense_button, PendingIntent.getBroadcast(context, shiftedAppWidgetId + 20, intent, 0));

        intent.setAction(INCOME_BUTTON);
        remoteViews.setOnClickPendingIntent(R.id.income_button, PendingIntent.getBroadcast(context, shiftedAppWidgetId + 20, intent, 0));
    }

}

class UI {
    private Context context;
    private RemoteViews remoteViews;
    private String TAG = "UI";
    enum InputMode { EXPENSE, INCOME }

    public UI(Context context, RemoteViews remoteViews) {
        this.context = context;
        this.remoteViews = remoteViews;
    }

//    public void updateCategory(Category category) {
//        remoteViews.setInt(R.id.toggle_category, "setBackgroundColor", category.getColor());
//        remoteViews.setTextViewText(R.id.toggle_category, category.getName());
//        pushWidgetUpdate();
//    }

    public void setInputMode(InputMode inputMode) {
        switch (inputMode) {
            case EXPENSE:
                remoteViews.setInt(R.id.expense_button, "setBackgroundResource", R.color.expense_on);
                remoteViews.setInt(R.id.income_button, "setBackgroundResource", R.color.income_off);
                break;
            case INCOME:
                remoteViews.setInt(R.id.expense_button, "setBackgroundResource", R.color.expense_off);
                remoteViews.setInt(R.id.income_button, "setBackgroundResource", R.color.income_on);
                break;
            default:
                throw new NotImplementedException();
        }
        pushWidgetUpdate();
    }

    private void pushWidgetUpdate() {
        ComponentName widget = new ComponentName(context, CalculatorWidget.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(widget, remoteViews);
        Log.i(TAG, "pushWidgetUpdate");
    }
}

