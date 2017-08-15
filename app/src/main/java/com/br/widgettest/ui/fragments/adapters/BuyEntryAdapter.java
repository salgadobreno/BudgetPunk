package com.br.widgettest.ui.fragments.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.br.widgettest.AddEntryActivity;
import com.br.widgettest.R;
import com.br.widgettest.core.BuyEntry;
import com.br.widgettest.core.Entry;
import com.br.widgettest.core.ILedger;
import com.br.widgettest.core.dao.CategoryDao;
import com.br.widgettest.core.dao.EntryDao;
import com.br.widgettest.core.ledger.Ledger;
import com.br.widgettest.ui.extensions.CurrencyFormattedText;
import com.br.widgettest.ui.fragments.util.FixedEntriesWithSeparatorAndMofifierList;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Breno on 4/8/2016.
 */
public class BuyEntryAdapter extends ArrayAdapter<Object> implements BuyEntryUI {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yyyy");
    private FixedEntriesWithSeparatorAndMofifierList fixedEntriesWithSeparatorAndMofifierList;
    private ILedger ledger;

    public BuyEntryAdapter(Context context, FixedEntriesWithSeparatorAndMofifierList fixedEntriesWithSeparatorAndMofifierList) {
        super(context, R.layout.buy_entry_row, fixedEntriesWithSeparatorAndMofifierList);
        this.fixedEntriesWithSeparatorAndMofifierList = fixedEntriesWithSeparatorAndMofifierList;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        return fixedEntriesWithSeparatorAndMofifierList.getEntryViewType(position).getI();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int type = getItemViewType(position);
        int[] layouts = new int[] {R.layout.entries_date_separator, R.layout.buy_entry_row, R.layout.buy_summary};

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layouts[type], null);
        }

        switch (type) {
            case 0: //DATE_SEPARATOR
                Date date = (Date) fixedEntriesWithSeparatorAndMofifierList.get(position);

                TextView dateView = (TextView) convertView.findViewById(R.id.date_separator_text);
                dateView.setText(simpleDateFormat.format(date));

                break;
            case 1: //ENTRY
                final BuyEntry entry = (BuyEntry) getItem(position);

                TextView entryName = (TextView)convertView.findViewById(R.id.buy_entry_name);
                TextView entryVal = (TextView)convertView.findViewById(R.id.buy_entry_val);
                TextView entryDateInfo = (TextView)convertView.findViewById(R.id.buy_entry_date_info);
                TextView entryModifier = (TextView)convertView.findViewById(R.id.buy_entry_modifier);
                Button popupButton = (Button) convertView.findViewById(R.id.buy_entry_popup);

                entryName.setText(entry.getName());
                entryVal.setText(new CurrencyFormattedText(entry.getValue()));
                entryDateInfo.setText(entry.getPeriod());
                entryModifier.setText(new CurrencyFormattedText(entry.getModifier()) + "/d");

                popupButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popupMenu = new PopupMenu(getContext(), v);
                        popupMenu.getMenuInflater().inflate(R.menu.buy_popup_menu, popupMenu.getMenu());
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {

                                switch (item.getItemId()) {
                                    case R.id.bmenu_edit:
                                        return edit(entry);
                                    case R.id.bmenu_delete:
                                        return delete(entry);
                                    case R.id.bmenu_toggle:
                                        return toggleActive(entry);
                                    default:
                                        throw new IllegalArgumentException("unexpected param");
                                }
                            }
                        });
                        popupMenu.show();
                    }
                });

                break;
            case 2: //SUMMARY
                Double modifier = (Double) fixedEntriesWithSeparatorAndMofifierList.get(position);

                TextView modifierView = (TextView) convertView.findViewById(R.id.buy_entry_modifier);

                modifierView.setText("Modifier: " + new CurrencyFormattedText(modifier));
                break;
        }


        return convertView;
    }

    public void setFixedEntriesWithSeparatorAndMofifierList(FixedEntriesWithSeparatorAndMofifierList fixedEntriesWithSeparatorAndMofifierList) {
        this.fixedEntriesWithSeparatorAndMofifierList = fixedEntriesWithSeparatorAndMofifierList;
    }

    @Override
    public boolean delete(Entry entry) {
        fixedEntriesWithSeparatorAndMofifierList.remove(entry);

        getLedger().rm(entry);
        notifyDataSetChanged();
        return true;
    }

    @Override
    public boolean edit(Entry entry) {
        Intent intent = new Intent(getContext(), AddEntryActivity.class);
        intent.putExtra("id", entry.toEntity().getId());
        intent.putExtra("entryType", entry.getEntryType().name());
        getContext().startActivity(intent);
        return true;
    }

    @Override
    public boolean toggleActive(Entry entry) {
        return false;
    }

    private ILedger getLedger() {
        if (ledger == null) {
            ledger = new Ledger(new EntryDao(), new CategoryDao(getContext()));
        }
        return ledger;
    }
}
