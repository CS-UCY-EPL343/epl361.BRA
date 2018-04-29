package com.team3.bra;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        testManagerEditUsers.class,
        testManagerReports.class

})
public class RunAllTests2 {
    static {
        MainActivity.REFRESH = false;
    }
}
