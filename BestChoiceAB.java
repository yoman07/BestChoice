package com.zdobywacz.ab;

import android.content.Context;
import android.util.Log;
import org.json.JSONArray;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by yoman on 08.11.14.
 */
public class BestChoiceAB {

    private static final boolean DEBUG = false;
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

    private static List<Double> doubleJSONArrayToList(JSONArray ary) {
        ArrayList<Double> resp = new ArrayList<Double>(ary.length());
        for (int i = 0; i < ary.length(); ++i) {
            resp.add(ary.optDouble(i));
        }
        return resp;
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

        printDebug("Choiced test " + Integer.toString(choice));

        switch (choice) {
            case 0:
                testInstance.A();
                break;
            case 1:
                testInstance.B();
                break;
            case 2:
                testInstance.C();
                break;
            case 3:
                testInstance.D();
                break;
            case 4:
                testInstance.E();
                break;
            case 5:
                testInstance.F();
                break;
            case 6:
                testInstance.G();
                break;
            case 7:
                testInstance.H();
                break;
            case 8:
                testInstance.I();
                break;
            case 9:
                testInstance.J();
                break;
        }
    }

    private static int randomTest(int numberOfElements) {
        return random.nextInt(numberOfElements - 1);
    }

    private static void printDebug(String debugString) {
        if (DEBUG) {
            Log.i(TAG, debugString);
        }
    }
}
