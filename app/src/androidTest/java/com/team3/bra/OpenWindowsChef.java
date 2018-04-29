package com.team3.bra;


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
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class OpenWindowsChef {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void openWindowsChef() {
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
        editText2.perform(click());

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.txtUser),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                0),
                        isDisplayed()));
        editText3.perform(replaceText("3"), closeSoftKeyboard());

        ViewInteraction button = onView(
                allOf(withId(R.id.button2), withText("LOGIN"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                0),
                        isDisplayed()));
        button.perform(click());



        ViewInteraction textView2 = onView(
                allOf(withText("Table 14"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.column1),
                                        0),
                                0)));
        textView2.perform(scrollTo(), click());

        ViewInteraction textView3 = onView(
                allOf(withText("Table 15"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.column2),
                                        0),
                                0)));
        textView3.perform(scrollTo(), click());

        ViewInteraction textView4 = onView(
                allOf(withText("Table 12"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.column3),
                                        0),
                                0)));
        textView4.perform(scrollTo(), click());

        ViewInteraction button2 = onView(
                allOf(withId(R.id.imageButton),
                        childAtPosition(
                                allOf(withId(R.id.backlogo),
                                        childAtPosition(
                                                withId(R.id.first),
                                                0)),
                                0),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction textView5 = onView(
                allOf(childAtPosition(
                                childAtPosition(
                                        withId(R.id.column2),
                                        0),
                                1)));
        textView5.perform(scrollTo(), click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btnReceipt), withText("Cancel"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.custom),
                                        0),
                                1),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction button3 = onView(
                allOf(withId(R.id.imageButton),
                        childAtPosition(
                                allOf(withId(R.id.backlogo),
                                        childAtPosition(
                                                withId(R.id.first),
                                                0)),
                                0),
                        isDisplayed()));
        button3.check(matches(isDisplayed()));

        ViewInteraction textView6 = onView(
                allOf(childAtPosition(
                                childAtPosition(
                                        withId(R.id.column1),
                                        0),
                                1)));
        textView6.perform(scrollTo(), click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btnReceipt), withText("Cancel"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.custom),
                                        0),
                                1),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction button5 = onView(
                allOf(withId(R.id.imageButton),
                        childAtPosition(
                                allOf(withId(R.id.backlogo),
                                        childAtPosition(
                                                withId(R.id.first),
                                                0)),
                                0),
                        isDisplayed()));
        button5.check(matches(isDisplayed()));

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.imageButton), withText("Back"),
                        childAtPosition(
                                allOf(withId(R.id.backlogo),
                                        childAtPosition(
                                                withId(R.id.first),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.btnyes), withText("Log Out"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.custom),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction linearLayout = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(android.R.id.content),
                                0),
                        0),
                        isDisplayed()));
        linearLayout.check(matches(isDisplayed()));

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
