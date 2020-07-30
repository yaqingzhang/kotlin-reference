package com.runningmessage.kotref.java.discuss;

import com.runningmessage.kotref.kotlin.discuss.Discuss003;

/**
 * Created by Lorss on 19-1-4.
 */
public class Discuss001 {

    public static void test() {

        try {
            Discuss003.Companion.t03(); // TODO will crash
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
