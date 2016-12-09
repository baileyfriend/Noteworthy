package com.dev.baileyfreund.notes;


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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.action_add_note), withContentDescription("Add Note"), isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.action_add_note), withContentDescription("Add Note"), isDisplayed()));
        actionMenuItemView2.perform(click());

        ViewInteraction editText = onView(
                allOf(withClassName(is("android.widget.EditText")),
                        withParent(allOf(withId(R.id.custom),
                                withParent(withId(R.id.customPanel)))),
                        isDisplayed()));
        editText.perform(click());

        ViewInteraction editText2 = onView(
                allOf(withClassName(is("android.widget.EditText")),
                        withParent(allOf(withId(R.id.custom),
                                withParent(withId(R.id.customPanel)))),
                        isDisplayed()));
        editText2.perform(click());

        ViewInteraction editText3 = onView(
                allOf(withClassName(is("android.widget.EditText")),
                        withParent(allOf(withId(R.id.custom),
                                withParent(withId(R.id.customPanel)))),
                        isDisplayed()));
        editText3.perform(replaceText("yayu"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(android.R.id.button1), withText("Add"),
                        withParent(allOf(withId(R.id.buttonPanel),
                                withParent(withId(R.id.parentPanel)))),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(android.R.id.button2), withText("Cancel"),
                        withParent(allOf(withId(R.id.buttonPanel),
                                withParent(withId(R.id.parentPanel)))),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.note_item), withText("yayu"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list_todo),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.note_item), withText("yayu"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list_todo),
                                        0),
                                0),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));

        ViewInteraction relativeLayout = onView(
                allOf(childAtPosition(
                        withId(R.id.list_todo),
                        0),
                        isDisplayed()));
        relativeLayout.perform(click());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.note_item), withText("yayu"),
                        withParent(childAtPosition(
                                withId(R.id.list_todo),
                                0)),
                        isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction actionMenuItemView3 = onView(
                allOf(withId(R.id.action_delete_note), withContentDescription("Delete Note"), isDisplayed()));
        actionMenuItemView3.perform(click());

        ViewInteraction relativeLayout2 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(android.R.id.content),
                                0),
                        0),
                        isDisplayed()));
        relativeLayout2.check(matches(isDisplayed()));

        ViewInteraction relativeLayout3 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(android.R.id.content),
                                0),
                        0),
                        isDisplayed()));
        relativeLayout3.check(matches(isDisplayed()));

        ViewInteraction actionMenuItemView4 = onView(
                allOf(withId(R.id.action_add_note), withContentDescription("Add Note"), isDisplayed()));
        actionMenuItemView4.perform(click());

        ViewInteraction actionMenuItemView5 = onView(
                allOf(withId(R.id.action_add_note), withContentDescription("Add Note"), isDisplayed()));
        actionMenuItemView5.perform(click());

        ViewInteraction editText4 = onView(
                allOf(withClassName(is("android.widget.EditText")),
                        withParent(allOf(withId(R.id.custom),
                                withParent(withId(R.id.customPanel)))),
                        isDisplayed()));
        editText4.perform(click());

        ViewInteraction editText5 = onView(
                allOf(withClassName(is("android.widget.EditText")),
                        withParent(allOf(withId(R.id.custom),
                                withParent(withId(R.id.customPanel)))),
                        isDisplayed()));
        editText5.perform(replaceText("I am a little bit of a a few days days and I will be a a few days ago"), closeSoftKeyboard());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(android.R.id.button2), withText("Cancel"),
                        withParent(allOf(withId(R.id.buttonPanel),
                                withParent(withId(R.id.parentPanel)))),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(android.R.id.button2), withText("Cancel"),
                        withParent(allOf(withId(R.id.buttonPanel),
                                withParent(withId(R.id.parentPanel)))),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction relativeLayout4 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(android.R.id.content),
                                0),
                        0),
                        isDisplayed()));
        relativeLayout4.check(matches(isDisplayed()));

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
