package com.br.widgettest.ui.extensions.cli;

import android.content.Context;
import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public abstract class CliBaseWidget<T extends View> {
    protected T view;

    protected final Class<T> klazz;
    protected final List<Class> argsClasses;
    protected final List<Object> argsList;

    protected CliBaseWidget(Class klazz, Object...args) {
        this.klazz = klazz;
        argsClasses = new ArrayList<>();
        argsList = new ArrayList<>();
        for (Object arg : args) {
            if (arg instanceof Context) {
                argsClasses.add(Context.class);
                argsList.add(arg);
            } else if (arg instanceof  List) {
                argsClasses.add(List.class);
                argsList.add(arg);//TODO FUCKED
           } else if (arg instanceof CharSequence) {
                argsClasses.add(CharSequence.class);
                argsList.add(arg);
            } else {
                argsClasses.add(arg.getClass());
                argsList.add(arg);
            }
        }
    }

    public abstract String cliView();

    public T getView() {
        if (view != null) return view;

        try {
            Constructor<T> constructor = klazz.getConstructor(argsClasses.toArray(new Class[argsClasses.size()]));

            view = constructor.newInstance(argsList.toArray());
            return view;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(String.format("NoSuchMethodException for %s", klazz));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("IllegalAccessException for %s", klazz));
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("InstantiationException for %s", klazz));
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("InvocationTargetException for %s", klazz));
        }
    }

}
