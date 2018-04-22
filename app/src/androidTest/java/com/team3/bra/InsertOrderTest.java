package com.team3.bra;


import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class InsertOrderTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void insertOrderTest() {
        ViewInteraction editText = onView(
                allOf(withId(R.id.txtUser),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                0),
                        isDisplayed()));
        editText.perform(click());

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.txtUser),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                0),
                        isDisplayed()));
        editText2.perform(replaceText("waiter1"), closeSoftKeyboard());

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.txtPass),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                0),
                        isDisplayed()));
        editText3.perform(replaceText("waiter"), closeSoftKeyboard());

        ViewInteraction button = onView(
                allOf(withId(R.id.button2), withText("LOGIN"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                0),
                        isDisplayed()));
        button.perform(click());

        DataInteraction linearLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.listOrders),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                2)))
                .atPosition(0);
        linearLayout.perform(click());

        ViewInteraction button2 = onView(
                allOf(withId(R.id.btn1), withText("Add Table"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                0),
                        isDisplayed()));
        button2.perform(click());

        ViewInteraction button3 = onView(
                allOf(withText("MEZE"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.categoryLayout),
                                        2),
                                1)));
        button3.perform(scrollTo(), click());

        ViewInteraction button4 = onView(
                allOf(withText("MEZE  PER PERSON"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.itemLayout),
                                        0),
                                1)));
        button4.perform(scrollTo(), click());

        ViewInteraction button5 = onView(
                allOf(withId(R.id.btnAddItem), withText("Add Item"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                0),
                        isDisplayed()));
        button5.perform(click());

        ViewInteraction button6 = onView(
                allOf(withId(R.id.button12),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        button6.check(matches(isDisplayed()));

        ViewInteraction button7 = onView(
                allOf(withId(R.id.button12), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0),
                        isDisplayed()));
        button7.perform(click());

        ViewInteraction button8 = onView(
                allOf(withId(R.id.btnBack), withText("Back"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0),
                        isDisplayed()));
        button8.perform(click());

        ViewInteraction editText4 = onView(
                allOf(withId(R.id.txtUser), withText("waiter1"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                0),
                        isDisplayed()));
        editText4.perform(replaceText("coo"));

        ViewInteraction editText5 = onView(
                allOf(withId(R.id.txtUser), withText("coo"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                0),
                        isDisplayed()));
        editText5.perform(closeSoftKeyboard());

        ViewInteraction editText6 = onView(
                allOf(withId(R.id.txtPass), withText("waiter"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                0),
                        isDisplayed()));
        editText6.perform(replaceText("cook"));

        ViewInteraction editText7 = onView(
                allOf(withId(R.id.txtPass), withText("cook"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                0),
                        isDisplayed()));
        editText7.perform(closeSoftKeyboard());

        ViewInteraction editText8 = onView(
                allOf(withId(R.id.txtUser), withText("coo"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                0),
                        isDisplayed()));
        editText8.perform(replaceText("cook1"));

        ViewInteraction editText9 = onView(
                allOf(withId(R.id.txtUser), withText("cook1"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                0),
                        isDisplayed()));
        editText9.perform(closeSoftKeyboard());

        ViewInteraction button9 = onView(
                allOf(withId(R.id.button2), withText("LOGIN"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                0),
                        isDisplayed()));
        button9.perform(click());


        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.imageButton), withText("Back"),
                        childAtPosition(
                                allOf(withId(R.id.backlogo),
                                        childAtPosition(
                                                withId(R.id.first),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btnyes), withText("Log Out"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.custom),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton2.perform(click());

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
