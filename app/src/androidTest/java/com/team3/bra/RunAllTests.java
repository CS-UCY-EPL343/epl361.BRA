package com.team3.bra;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ChefLoginTest.class,
        OpenWindowsChef.class,
        EditOrderTest.class,
        InsertOrderTest.class,
        ManagerLoginTest.class,
        NotificationsWaiter.class

})
public class RunAllTests {
    static {
        MainActivity.REFRESH = false;
    }
}
