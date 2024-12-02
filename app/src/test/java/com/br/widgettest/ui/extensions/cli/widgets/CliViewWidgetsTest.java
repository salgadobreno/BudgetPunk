package com.br.widgettest.ui.extensions.cli.widgets;

import android.content.Context;

import com.br.widgettest.core.BuyEntry;
import com.br.widgettest.core.DailyEntry;
import com.br.widgettest.core.FixedEntry;
import com.br.widgettest.ui.extensions.cli.CliBaseWidget;

import org.joda.time.Instant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CliViewWidgetsTest {
    @Mock
    Context context;

    class CliViewWidgetTester<T extends CliBaseWidget> {
        private T type;

        public CliViewWidgetTester(T type) {
            this.type = type;
        }

        public void matches(String expected) {
            Assert.assertEquals(expected, type.cliView());
        }
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testCliTextView() {
        String rep = "| abcde                         |\n";

        new CliViewWidgetTester<>(new CliTextViewWidget(context, "abcde"))
                .matches(rep);
    }

    @Test
    public void testCenteredTextView() {
        String rep = "|              xxxx             |\n";

        new CliViewWidgetTester<CenteredTextViewWidget>(new CenteredTextViewWidget(context, "xxxx"))
                .matches(rep);
    }

    @Test
    public void testDashedTextView() {
        String rep = "| ----------------------------- |\n";

        new CliViewWidgetTester<DashedTextViewWidget>(new DashedTextViewWidget(context))
                .matches(rep);
    }

    @Test
    public void testSplitTextView() {
        String rep = "| xxxx                     xxxx |\n";

        new CliViewWidgetTester<SplitTextViewWidget>(new SplitTextViewWidget(context, "xxxx", "xxxx"))
                .matches(rep);
    }

    @Test
    public void testDateSeparator() {
        String rep = "|             29/08             |\n" +
                     "| ----------------------------- |\n";

        new CliViewWidgetTester<DateSeparatorViewWidget>(new DateSeparatorViewWidget(context, Instant.parse("2018-08-29").toDate()))
                .matches(rep);
    }

    @Test
    public void testDailyEntryView() {
        String rep = "| 29/08                R$ 21,32 |\n";

        Instant startDate = Instant.parse("2018-08-29");
        new CliViewWidgetTester<DailyEntryViewWidget>(new DailyEntryViewWidget(context, new DailyEntry(21.32, null, startDate.toDate())))
                .matches(rep);
    }

    @Test
    public void testSummaryWithBalance() {
        String rep = "|                  ------------ |\n" +
                     "| TOTAL:            -R$ 34,12/d |\n" +
                     "| PREV BALANCE:     R$ 123,12/d |\n" +
                     "|                  ------------ |\n" +
                     "| BALANCE:           R$ 89,00/d |\n";//TODO: fix alignment

        new CliViewWidgetTester<SummaryWithBalanceViewWidget>(new SummaryWithBalanceViewWidget(context, -34.12, 123.12))
                .matches(rep);
    }

    @Test
    public void testTitleView() {
        String rep = "| IN                            |\n" +
                     "| ----------------------------- |\n";

        new CliViewWidgetTester<TitleViewWidget>(new TitleViewWidget(context, "IN"))
                .matches(rep);
    }

    @Test
    public void testFixedEntryView() {
//        String rep = "| Salario         R$321.32...   |\n" +
//                     "|                 R$3.2/d ...   |\n";
        String rep = "| Salario             R$ 321,32 |\n" +
                     "|                    R$ 10,71/d |\n";//TODO

        new CliViewWidgetTester<FixedEntryViewWidget>(new FixedEntryViewWidget(context, new FixedEntry("Salario", 321.32, null)))
                .matches(rep);
    }

    @Test
    public void testFixedEntrySumView() {
        String rep = "|                   ----------- |\n" +
                     "| TOTAL:            R$ 123,12/d |\n";

        new CliViewWidgetTester<FixedEntrySumViewWidget>(new FixedEntrySumViewWidget(context, 123.12))
                .matches(rep);
    }

    @Test
    public void testFixedEntrySummary() {
        String rep = "| ----------------------------- |\n" +
                     "| V/D:                          |\n" +
                     "| - Fixed V/D:       R$ 53,12/d |\n" +
                     "| - MODIFIER:        -R$ 3,12/d |\n" + //TODO: alignment
                     "|                    ---------- |\n" +
                     "|                    R$ 50,00/d |\n";

        new CliViewWidgetTester<FixedEntrySummaryViewWidget>(new FixedEntrySummaryViewWidget(context, 53.12, -3.12))
                .matches(rep);
    }

    @Test
    public void testBentryDateSeparator() {
        String rep = "| 02/2018                       |\n" +
                     "| ----------------------------- |\n";

        Instant time = Instant.parse("2018-02-01");
        new CliViewWidgetTester<BentryDateSeparatorViewWidget>(new BentryDateSeparatorViewWidget(context, time.toDate()))
                .matches(rep);
    }

    @Test
    public void testBuyEntryView() {
//        String rep = "| Overstreet     -R$150.00/6m   |\n" +
//                     "|                -R$1.5/d       |\n" +
//                     "| 4/6 █|█|█|█|▒|▒               |\n";
        String rep = "| Overstreet      -R$ 150,00/6m |\n" + //TODO: alignment
                     "|                    -R$ 0,83/d |\n" +
                     "| 4/6 █|█|█|█|▒|▒|              |\n";

        Instant startDate = Instant.parse("2018-01-01");
        Instant endDate = Instant.parse("2018-07-01");
        new CliViewWidgetTester<BuyEntryViewWidget>(new BuyEntryViewWidget(context, new BuyEntry("Overstreet", -150.00, startDate.toDate(), endDate.toDate(), null)))
                .matches(rep);
    }

    @Test
    public void testModifierEntryView() {
//        String rep = "| ---------------------------   |\n" +
//                     "| PREV MOD:    ⇩ -R$100.00/d    |\n";
        String rep = "| ----------------------------- |\n" +
                     "| PREV MOD:        -R$ 100,00/d |\n";

        new CliViewWidgetTester<ModifierEntryViewWidget>(new ModifierEntryViewWidget(context, -100.00))
                .matches(rep);

    }

}