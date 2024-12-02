package com.br.widgettest;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

import com.aop.annotations.Trace;
import com.br.widgettest.core.Category;
import com.br.widgettest.core.DailyEntry;
import com.br.widgettest.core.ILedger;
import com.br.widgettest.core.dao.EntryDao;
import com.br.widgettest.core.ledger.LightLedger;

import static com.br.widgettest.AddEntryWidget.PREFERENCE_WIDGET_PREAMBLE;

/**
 * Created by Breno on 11/30/2015.
 */
@Trace
public class AddEntryWidget extends AppWidgetProvider {
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
//    public static final String DOT = "com.android.calculator2.dot";
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

    static ILedger ledger;

    @Override
    public void onReceive(Context context, Intent intent) {
        int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 0);

        ledger = new LightLedger(new EntryDao());
        UI ui = new UI(context, new RemoteViews(context.getPackageName(), R.layout.main_widget), appWidgetId);

        String value = ui.getValue();
        UI.InputMode inputMode = ui.getInputMode() != null ? ui.getInputMode() : UI.InputMode.EXPENSE;

        Log.i(TAG, intent.getAction() + " clicked");
        switch (intent.getAction()) {
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
//            case DOT:
//                value += ".";
//                break;
            case DEL:
                if(value.length() > 0) value = value.substring(0, value.length() - 1);
                break;
            case INCOME_BUTTON:
                inputMode = UI.InputMode.INCOME;
                break;
            case EXPENSE_BUTTON:
                inputMode = UI.InputMode.EXPENSE;
                break;
            case COMMIT:
                Double input = Double.valueOf(value);
                if (inputMode == UI.InputMode.EXPENSE) {
                    input = -input;
                }
                DailyEntry entry = new DailyEntry(input, Category.NULL);
                ledger.add(entry);
                value = "";
        }

        ui.setValue(value);
        ui.setInputMode(inputMode);

        ui.flush();

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, AddEntryWidget.class));
        for(int appWidgetID : appWidgetIds) {
            updateAppWidget(context, ui, appWidgetID);
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for(int appWidgetID : appWidgetIds) {
            UI ui = new UI(context, new RemoteViews(context.getPackageName(), R.layout.main_widget), appWidgetID);
            updateAppWidget(context, ui, appWidgetID);
        }
    }

    private void updateAppWidget(Context context, UI ui, int appWidgetId) {
        setOnClickListeners(context, appWidgetId, ui.getRemoteViews());
        ui.flush();
    }

    private void setOnClickListeners(Context context, int appWidgetId, RemoteViews remoteViews) {
        final Intent intent = new Intent(context, AddEntryWidget.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

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

//        intent.setAction(DOT);
//        remoteViews.setOnClickPendingIntent(R.id.dot, PendingIntent.getBroadcast(context, shiftedAppWidgetId + 10, intent, 0));

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

//        intent.setAction(CLR);
//        remoteViews.setOnClickPendingIntent(R.id.clear, PendingIntent.getBroadcast(context, shiftedAppWidgetId + 17, intent, 0));

        intent.setAction(EXPENSE_BUTTON);
        remoteViews.setOnClickPendingIntent(R.id.expense_button, PendingIntent.getBroadcast(context, shiftedAppWidgetId + 20, intent, 0));

        intent.setAction(INCOME_BUTTON);
        remoteViews.setOnClickPendingIntent(R.id.income_button, PendingIntent.getBroadcast(context, shiftedAppWidgetId + 20, intent, 0));
    }

}

class UI {
    private final Context context;
    private final RemoteViews remoteViews;
    private final int appWidgetId;
    private final String TAG = "UI";
    enum InputMode { EXPENSE, INCOME }

    private final String valueKey;
    private final String inputModeKey;

    public UI(Context context, RemoteViews remoteViews, int appWidgetId) {
        this.context = context;
        this.remoteViews = remoteViews;
        this.appWidgetId = appWidgetId;

        this.valueKey = PREFERENCE_WIDGET_PREAMBLE + appWidgetId;
        this.inputModeKey = PREFERENCE_WIDGET_PREAMBLE + "Mode" + appWidgetId;
    }

    public void setInputMode(InputMode inputMode) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(inputModeKey, inputMode.name()).commit();
    }

    public InputMode getInputMode() {
        return InputMode.valueOf(PreferenceManager.getDefaultSharedPreferences(context).getString(inputModeKey, InputMode.EXPENSE.name()));
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

        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(valueKey, newValue).commit();
    }

    public String getValue() {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(valueKey, "");
    }

    public RemoteViews getRemoteViews() {
        return remoteViews;
    }

    public void flush() {
        remoteViews.setTextViewText(R.id.display, getValue());
        switch (getInputMode()) {
            case EXPENSE:
                remoteViews.setInt(R.id.expense_button, "setBackgroundResource", R.color.expense_on);
                remoteViews.setInt(R.id.income_button, "setBackgroundResource", R.color.income_off);
                break;
            case INCOME:
                remoteViews.setInt(R.id.expense_button, "setBackgroundResource", R.color.expense_off);
                remoteViews.setInt(R.id.income_button, "setBackgroundResource", R.color.income_on);
                break;
        }
        ComponentName widget = new ComponentName(context, AddEntryWidget.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(widget, remoteViews);
        Log.i(TAG, "flush");
    }
}

