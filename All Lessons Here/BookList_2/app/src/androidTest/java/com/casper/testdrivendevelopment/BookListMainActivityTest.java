package com.casper.testdrivendevelopment;


import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
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
public class BookListMainActivityTest {

    @Rule
    public ActivityTestRule<BookListMainActivity> mActivityTestRule = new ActivityTestRule<>(BookListMainActivity.class);

    @Test
    public void showBookListMainActivityTest() {
        List<Book> listBooks = mActivityTestRule.getActivity().getListBooks();
        for (int iLoop = 0; iLoop < listBooks.size(); iLoop++) {
            ViewInteraction imageView = onView(
                    allOf(withId(R.id.image_view_book_cover),
                            childAtPosition(
                                    childAtPosition(
                                            withId(R.id.list_view_books),
                                            iLoop),
                                    0),
                            isDisplayed()));
            imageView.check(matches(hasDrawableResource(listBooks.get(iLoop).getCoverResourceId())));

            //if(iLoop==2)continue;
            ViewInteraction textView = onView(
                    allOf(withId(R.id.text_view_book_title),
                            childAtPosition(
                                    childAtPosition(
                                            withId(R.id.list_view_books),
                                            iLoop),
                                    1),
                            isDisplayed()));
            textView.check(matches(withText(listBooks.get(iLoop).getTitle())));
        }

        DataInteraction linearLayout3 = onData(anything())
                .inAdapterView(allOf(withId(R.id.list_view_books),
                        childAtPosition(
                                withClassName(is("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(1);
        linearLayout3.perform(click());
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

    private static Matcher<View> hasDrawableResource(final int resourceId) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Imageview has a resource id of " + resourceId);
            }

            @Override
            public boolean matchesSafely(View view) {
                ImageView imageView = (ImageView) view;
                return imageView.getDrawable() != null &&
                        imageView.getDrawable().getCurrent().getConstantState().equals(imageView.getResources().getDrawable(resourceId).getConstantState());
            }
        };
    }
}
