package com.example.memes

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.memes.model.Meme
import org.hamcrest.CoreMatchers
import org.hamcrest.core.AllOf.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MemeListTests {

    private lateinit var mIdlingResource: IdlingResource

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    /**
     * Use [to launch and get access to the activity.][ActivityScenario.onActivity]
     */
    @Before
    fun registerIdlingResource() {
        val activityScenario: ActivityScenario<*> =
            ActivityScenario.launch(MainActivity::class.java)
        activityScenario.onActivity { activity ->
            mIdlingResource = (activity as MainActivity).getIdlingResource()
            // To prove that the test fails, omit this call:
            IdlingRegistry.getInstance().register(mIdlingResource)
        }
    }

    private fun hasListItemContent(meme: Meme) {
        onView(withText(meme.id)).check(matches(isDisplayed()))
        onView(withText(meme.name)).check(matches(isDisplayed()))
        onView(allOf(withParent(withChild(withText(meme.id))), withId(R.id.checkBox))).check(
            matches(
                if (meme.checked) {
                    isChecked()
                } else {
                    isNotChecked()
                }
            )
        )
    }

    /**
     * Custom view assertion to check:
     * The RecyclerView exists
     * The RecyclerView has an adapter
     * The adapter contains the expected number of items
     *
     * @param count The expected number of adapter items
     */
    private class RecyclerViewAssertion(private val count: Int) : ViewAssertion {
        override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
            if (noViewFoundException != null) {
                throw noViewFoundException
            }

            if (view !is RecyclerView) {
                throw IllegalStateException("The view is not a RecyclerView")
            }

            if (view.adapter == null) {
                throw IllegalStateException("No adapter assigned to RecyclerView")
            }

            // Check item count
            assertThat(
                "RecyclerView item count",
                view.adapter?.itemCount,
                CoreMatchers.equalTo(count)
            )
        }
    }

    @Test
    fun verify_content_at_first_position() {
        hasListItemContent(FIRST_MEME)
    }

    @Test
    fun verify_content_at_last_position() {
        onView(withId(R.id.memesRecyclerView)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                CURRENT_MEME_COUNT - 1
            )
        )
        hasListItemContent(LAST_MEME)
    }

    @Test
    fun verify_item_count() {
        onView(withId(R.id.memesRecyclerView)).check(RecyclerViewAssertion(CURRENT_MEME_COUNT))
    }

    @Test
    fun verify_checkbox_is_checked_on_click_for_first_meme() {
        onView(
            allOf(
                withParent(withChild(withText(FIRST_MEME.id))),
                withId(R.id.checkBox)
            )
        ).perform(click())

        onView(allOf(withParent(withChild(withText(FIRST_MEME.id))), withId(R.id.checkBox))).check(
            matches(
                isChecked()
            )
        )
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(mIdlingResource)
    }

    companion object {
        private const val CURRENT_MEME_COUNT = 100
        private val FIRST_MEME = Meme(
            "181913649",
            "Drake Hotline Bling",
            "https://i.imgflip.com/30b1gx.jpg",
            1200,
            1200,
            2
        )
        private val LAST_MEME = Meme(
            "1464444",
            "Happy Star Congratulations",
            "https://i.imgflip.com/vdz0.jpg",
            450,
            292,
            4
        )
    }
}