package model;

import java.util.ArrayList;

/**
 * Created by mara on 1/29/15.
 */
public class Calculator {

    public static void recalculateDiff(ArrayList<Record> input) {
        if(input == null || input.size() == 0 || input.size() == 1){
            return;
        }

        for(int i = 0; i < input.size() - 1; ++i) {
            int value1 = input.get(i).getValue();
            int value2 = input.get(i + 1).getValue();
            int diff = value2 - value1;
            input.get(i + 1).setDiff(diff);
        }
        return;
    }
}
