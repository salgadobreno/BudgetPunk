package com.br.widgettest.ui.extensions.cli.widgets;

import android.support.annotation.NonNull;

/**
 * Single Char String.Format
 * Format using THE_CHAR so it doesn't break the CLI Layouts
 */
public class SCStringFormat {
    public static final String THE_CHAR = "#";

    public enum Type {
        ChangeInPlace {
            @Override
            String apply(String base, String s) {
                StringBuilder sb = new StringBuilder(base);
                int indexOfChar = base.indexOf('#');

                for (int i = indexOfChar, j = 0; i < indexOfChar + s.length(); i++, j++) {
                    sb.setCharAt(i, s.charAt(j));
                }
                return sb.toString();
            }
        },
        ChangeFromPlace {
            @Override
            String apply(String base, String s) {
                StringBuilder sb = new StringBuilder(base);
                int indexOfChar = base.indexOf('#');

                for (int i = indexOfChar - s.length(), j = 0; i < indexOfChar; i++, j++) {
                    sb.setCharAt(i + 1, s.charAt(j));
                }
                return sb.toString();
            }
        },
        ChangeFromMiddle {
            @Override
            String apply(String base, String s) {
                StringBuilder sb = new StringBuilder(base);
                int indexOfChar = base.indexOf('#');
                int remainder = s.length() % 2;

                for (int i = (indexOfChar - s.length()/2) + 1 - remainder, j = 0; i < (indexOfChar + s.length()/2) + 1; i++, j++) {
                    sb.setCharAt(i, s.charAt(j));
                }
                return sb.toString();
            }
        },
        None {
            @Override
            String apply(String base, String s) {
                return String.format(
                        base.replace(THE_CHAR, "%s"),
                        s
                );
            }
        };

        abstract String apply(String base, String s);

    }

    public static String format(@NonNull String base, String param) {
        return format(Type.None, base, param);
    }

    public static String format(Type type, @NonNull String base, String param) {
        return type.apply(base, param);
    }
}
