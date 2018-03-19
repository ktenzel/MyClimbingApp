package com.epicodus.example.myclimbingapp;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;



public class RouteListInstrumentationTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void validateEditText() {
        String name = "Red V1";
        String location = "Circuit";
        String grade = "V1";
        onView(withId(R.id.routesButton)).perform(click());
        onView(withId(R.id.routeNameEditText)).perform(typeText(name));
        onView(withId(R.id.routeLocationEditText)).perform(typeText(location));
        onView(withId(R.id.routeGradeEditText)).perform(typeText(grade));
        onView(withId(R.id.addRoutesButton)).perform(click());
        onView(withId(R.id.newRouteTextView)).check(matches(withText("Route name: Red V1 Location: Circuit Grade: V1")));
    }
}
