package com.epicodus.example.myclimbingapp;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class MainActivityInstrumentationTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void validateRoutesButton() {
        onView(withId(R.id.routesButton)).perform(click());
        onView(withId(R.id.appMyRoutesTitle)).check(matches(withText("My Routes")));
    }

    @Test
    public void validateAboutButton() {
        onView(withId(R.id.aboutButton)).perform(click());
        onView(withId(R.id.appAboutTitle)).check(matches(withText("About")));
    }

    @Test
    public void validateContactButton() {
        onView(withId(R.id.contactButton)).perform(click());
        onView(withId(R.id.appContactTextView)).check(matches(withText("Contact")));
    }
}
