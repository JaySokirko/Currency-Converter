package utils;

import android.util.Log;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class LogRule implements TestRule {
    private Description description;

    @Override
    public Statement apply(Statement base, Description description) {
        this.description = description;
        return new LogStatement(base);
    }

    public class LogStatement extends Statement {
        private final Statement base;

        public LogStatement(Statement base) {
            this.base = base;
        }

        @Override
        public void evaluate() throws Throwable {
            Log.w("TAG_TEST", description.getMethodName() + "Started");
            try {
                base.evaluate();
            } finally {
                Log.w("TAG_TEST", description.getMethodName() + "Finished");
            }
        }
    }
}
