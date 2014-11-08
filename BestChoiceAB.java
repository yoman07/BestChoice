package com.zdobywacz.ab;

import android.content.Context;
import android.util.Log;
import org.json.JSONArray;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by yoman on 08.11.14.
 */
public class BestChoiceAB {
    private static final String TAG = BestChoiceAB.class.getSimpleName();
    private static BestChoiceStats stats = null;
    private static Context context = null;
    private static Random random = new Random();
    private static BestChoiceABClient bestChoiceABClient = null;

    public static void setup(Context inContext, BestChoiceABClient client) {
        context = inContext;
        stats = new BestChoiceStats(inContext);
        bestChoiceABClient = client;
    }

    public static void test(String name, BestChoiceABTest testInstance) {
        Method m[] = testInstance.getClass().getDeclaredMethods();

        int choice = stats.getCachedChoice(name);
        if (choice == -1) {
            choice = randomTest(m.length);
            stats.setCachedChoice(name, choice);
            String methodName = m[choice].getName();
            bestChoiceABClient.sendChoicedTest(name, methodName);
        }

        try {
            m[choice].invoke(testInstance, null);
        } catch (IllegalAccessException e) {
            Log.e(TAG,"Problem with call method - IllegalAccessException");
            e.printStackTrace();
            stats.setCachedChoice(name,-1);
        } catch (InvocationTargetException e) {
            Log.e(TAG,"Problem with call method - InvocationTargetException");
            e.printStackTrace();
            stats.setCachedChoice(name,-1);
        }


    }

    private static int randomTest(int numberOfElements) {
        return random.nextInt(numberOfElements - 1);
    }

}
