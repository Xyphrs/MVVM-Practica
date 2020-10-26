package com.example.mvvm;

public class PythagorasSimulator {
    public static class Calc {
        public float a;
        public float b;

        public Calc(float a, float b) {
            this.a = a;
            this.b = b;
        }
    }

    interface Callback {
        void whenPythagorasIsCalculated(float c);
        void ifASideIsTooBig(float aSideMax);
        void ifBSideIsTooBig(float bSideMax);
        void startCalculating();
        void stopCalculating();
    }

    public void calculate(Calc calc, Callback callback) {

        callback.startCalculating();

        float aSideMax = 0;
        float bSideMax = 0;
        try {
            Thread.sleep(3000);
            aSideMax = 0.1f;
            bSideMax = 0.1f;
        } catch (InterruptedException e) {}

        boolean error = false;
        if (calc.a < aSideMax) {
            callback.ifASideIsTooBig(aSideMax);
            error = true;
        }

        if (calc.b < bSideMax) {
            callback.ifBSideIsTooBig(bSideMax);
            error = true;
        }

        if (!error) {
            callback.whenPythagorasIsCalculated((float) Math.sqrt((Math.pow(calc.a, 2) + Math.pow(calc.b, 2))));
        }

        callback.stopCalculating();
    }
}
