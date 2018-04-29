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
public class testManagerEditUsers {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testManagerEditUsers() {
        ViewInteraction editText = onView(
                allOf(withId(R.id.txtUser),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                0),
                        isDisplayed()));
        editText.perform(replaceText("1"), closeSoftKeyboard());

        ViewInteraction button = onView(
                allOf(withId(R.id.button2), withText("LOGIN"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                0),
                        isDisplayed()));
        button.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.textView5), withText("Manager"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                0),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.button9), withText("Edit Users"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                3),
                        isDisplayed()));
        button2.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.txtUsers), withText("Users"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));

        DataInteraction linearLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.listUsers),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                2)))
                .atPosition(1);
        linearLayout.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withId(android.R.id.text1), withText("Manager"),
                        childAtPosition(
                                allOf(withId(R.id.spnr_roleList),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText("Manager")));

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.txt_editName), withText("Andreas"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1),
                        isDisplayed()));
        editText2.perform(click());

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.txt_editName), withText("Andreas"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1),
                        isDisplayed()));
        editText3.perform(replaceText("Andreas1"));

        ViewInteraction editText4 = onView(
                allOf(withId(R.id.txt_editName), withText("Andreas1"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1),
                        isDisplayed()));
        editText4.perform(closeSoftKeyboard());

        ViewInteraction button3 = onView(
                allOf(withId(R.id.btn_save), withText("SAVE"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        5),
                                0),
                        isDisplayed()));
        button3.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.txtUsers), withText("Users"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        textView4.check(matches(isDisplayed()));

        DataInteraction linearLayout2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.listUsers),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                2)))
                .atPosition(1);
        linearLayout2.perform(click());

        ViewInteraction editText5 = onView(
                allOf(withId(R.id.txt_editName), withText("Andreas1"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1),
                        isDisplayed()));
        editText5.perform(click());

        ViewInteraction editText6 = onView(
                allOf(withId(R.id.txt_editName), withText("Andreas1"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                1),
                        isDisplayed()));
        editText6.check(matches(withText("Andreas1")));

        ViewInteraction editText7 = onView(
                allOf(withId(R.id.txt_editName), withText("Andreas1"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1),
                        isDisplayed()));
        editText7.perform(click());

        ViewInteraction editText8 = onView(
                allOf(withId(R.id.txt_editName), withText("Andreas1"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1),
                        isDisplayed()));
        editText8.perform(replaceText("Andreas"));

        ViewInteraction editText9 = onView(
                allOf(withId(R.id.txt_editName), withText("Andreas"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1),
                        isDisplayed()));
        editText9.perform(closeSoftKeyboard());

        ViewInteraction button4 = onView(
                allOf(withId(R.id.btn_save), withText("SAVE"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        5),
                                0),
                        isDisplayed()));
        button4.perform(click());

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.txtUsers), withText("Users"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        textView5.check(matches(isDisplayed()));

        ViewInteraction button5 = onView(
                allOf(withId(R.id.btnBack), withText("Back"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0),
                        isDisplayed()));
        button5.perform(click());

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.textView5), withText("Manager"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                0),
                        isDisplayed()));
        textView6.check(matches(isDisplayed()));

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
