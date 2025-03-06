package org.d3ifcool.twily

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.d3ifcool.twily.db.Note
import org.d3ifcool.twily.ui.HalamanUtama
import org.d3ifcool.twily.util.Date
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.contrib.PickerActions.setDate

import android.widget.DatePicker
import androidx.recyclerview.widget.RecyclerView

import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import org.d3ifcool.twily.db.NoteDB
import org.junit.Before


@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    companion object {
        private val NOTE_DUMMY = Note(0, "Tugas Mobpro", "Mengerjakan Asessment 3", "")
        private val UPDATE_DUMMY = Note(1, "Tugas Multimedia", "Mengerjakan Tugas Besar", "")
    }
    

    @Test
    fun testInsert() {
        // Jalankan MainActivity
        val activityScenario = ActivityScenario.launch(
            HalamanUtama::class.java
        )
        // Lakukan aksi menambah data baru
        onView(withId(R.id.add)).perform(
            ViewActions.click())
        onView(withId(R.id.etAddDetailsTask)).perform(
            ViewActions.typeText(NOTE_DUMMY.title)
        )

        onView(withId(R.id.btnAddDateTask)).perform(click())
        onView(isAssignableFrom(DatePicker::class.java)).perform(setDate(2021, 11, 3))
        onView(withId(android.R.id.button1)).perform(click())

        onView(withId(R.id.etAddDetailsTask2)).perform(
            ViewActions.typeText(NOTE_DUMMY.details)
        )
        onView(withId(R.id.btnSubmit)).perform(
            ViewActions.click())
        activityScenario.close()

    }

    @Test
    fun deleteAction(){
        // Jalankan MainActivity
        val activityScenario = ActivityScenario.launch(
            HalamanUtama::class.java
        )
//        onView(withId(R.id.recylcerView)).atItem (0, ViewActions.longClick())

        onView(withId(R.id.delete)).perform(
            ViewActions.click())
        onView(withId(android.R.id.button1)).perform(click())
    }


    @Test
    fun updateAction(){
        // Jalankan MainActivity
        val activityScenario = ActivityScenario.launch(
            HalamanUtama::class.java
        )
        onView(withId(R.id.update)).perform(
            ViewActions.click())
        onView(withId(R.id.etAddDetailsTask)).perform(
            clearText()
        )
        onView(withId(R.id.etAddDetailsTask)).perform(
            ViewActions.typeText(UPDATE_DUMMY.title)
        )
        onView(withId(R.id.btnAddDateTask)).perform(click())
        onView(isAssignableFrom(DatePicker::class.java)).perform(setDate(2021, 12, 15))
        onView(withId(android.R.id.button1)).perform(click())

        onView(withId(R.id.etAddDetailsTask2)).perform(
            clearText()
        )

        onView(withId(R.id.etAddDetailsTask2)).perform(
            ViewActions.typeText(UPDATE_DUMMY.details)
        )
        onView(withId(R.id.btnEdit)).perform(
            ViewActions.click())
    }


    private fun ViewInteraction.atItem(pos: Int, action: ViewAction) {
        perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                pos, action
            )
        )
    }
}