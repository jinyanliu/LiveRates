package com.lung.demo.presentation

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.lung.demo.*
import com.lung.demo.data.remote.CurrenciesResponse

import com.lung.demo.utils.EspressoIdlingResource
import org.hamcrest.CoreMatchers

import org.junit.runner.RunWith

import com.lung.demo.presentation.list.CurrencyViewHolder
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.hamcrest.Matchers
import org.junit.*
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4::class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class CurrenciesActivityTest {

    @Rule
    @JvmField
    val activity = ActivityTestRule(CurrenciesActivity::class.java, false, false)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun currenciesActivity_test_a_defaultIsEuro() {
        whenever(TestApp.appComponent().currenciesApiService().getCurrencies(any()))
            .thenReturn(Single.just(CurrenciesResponse(emptyMap())))
        activity.launchActivity(null)

        onView(withId(R.id.loader)).check(matches(CoreMatchers.not(isDisplayed())))
        onView(withId(R.id.currencies_list)).check(
            matches(
                recyclerViewAtPositionOnView(
                    0,
                    withText("EUR"),
                    R.id.currency_title
                )
            )
        )
        onView(withId(R.id.currencies_list)).check(
            matches(
                recyclerViewAtPositionOnView(
                    0,
                    withText(R.string.euro),
                    R.id.currency_description
                )
            )
        )
    }


    @Test
    fun currenciesActivity_test_b_amountHintIsZero() {
        whenever(TestApp.appComponent().currenciesApiService().getCurrencies(any()))
            .thenReturn(Single.just(CurrenciesResponse(emptyMap())))
        activity.launchActivity(null)

        onView(withId(R.id.currencies_list)).perform(
            actionOnItemAtPosition<CurrencyViewHolder>(
                0,
                clearTextOnViewChild(R.id.currency_amount)
            )
        )
        onView(withId(R.id.currencies_list)).check(
            matches(
                recyclerViewAtPositionOnView(
                    0,
                    editTextWithItemHint("0"),
                    R.id.currency_amount
                )
            )
        )
    }


    @Test
    fun currenciesActivity_test_c_onlyCanInputTenDigit() {
        whenever(TestApp.appComponent().currenciesApiService().getCurrencies(any()))
            .thenReturn(Single.just(CurrenciesResponse(emptyMap())))
        activity.launchActivity(null)

        onView(withId(R.id.currencies_list)).perform(
            actionOnItemAtPosition<CurrencyViewHolder>(
                0,
                typeTextOnViewChild(
                    R.id.currency_amount,
                    "100000000000000000000000000"
                )
            )
        )
        onView(withId(R.id.currencies_list)).check(
            matches(
                recyclerViewAtPositionOnView(
                    0,
                    withText("1000000000"),
                    R.id.currency_amount
                )
            )
        )

    }

    @Test
    fun currenciesActivity_test_d_changeSubCurrencyAmount() {
        whenever(TestApp.appComponent().currenciesApiService().getCurrencies(any()))
            .thenReturn(Single.just(CurrenciesResponse(mapOf("GBP" to 0.901691111121212131313123))))
        activity.launchActivity(null)
        onView(withId(R.id.currencies_list)).perform(
            actionOnItemAtPosition<CurrencyViewHolder>(
                0,
                clearTextOnViewChild(R.id.currency_amount)
            )
        )
        onView(withId(R.id.currencies_list)).perform(
            actionOnItemAtPosition<CurrencyViewHolder>(
                0,
                typeTextOnViewChild(R.id.currency_amount, "100")
            )
        )
        onView(withId(R.id.currencies_list)).check(
            matches(
                recyclerViewAtPositionOnView(
                    1,
                    withText("90.1691"),
                    R.id.currency_amount
                )
            )
        )
    }


    @Test
    fun currenciesActivity_test_e_changeBaseAfterClickedSubCurrency() {
        whenever(TestApp.appComponent().currenciesApiService().getCurrencies(any()))
            .thenReturn(
                Single.just(
                    CurrenciesResponse(
                        mapOf(
                            "HKD" to 9.16688888888888,
                            "GBP" to 0.901691111121212131313123,
                            "USD" to 1.1616
                        )
                    )
                )
            )

        activity.launchActivity(null)

        onView(withId(R.id.currencies_list)).perform(
            actionOnItemAtPosition<CurrencyViewHolder>(
                2, click()
            )
        )
        onView(withId(R.id.currencies_list)).check(
            matches(
                recyclerViewAtPositionOnView(
                    0,
                    withText("GBP"),
                    R.id.currency_title
                )
            )
        )
    }


    @Test
    fun currenciesActivity_test_f_showError() {

        whenever(TestApp.appComponent().currenciesApiService().getCurrencies(any()))
            .thenReturn(Single.error(Throwable()))

        activity.launchActivity(null)

        onView(withId(R.id.loader)).check(matches(CoreMatchers.not(isDisplayed())))
        onView(Matchers.allOf(withId(R.id.snackbar_text), withText(R.string.error_text))).check(
            matches(isDisplayed())
        )
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        activity.finishActivity()
    }

}
