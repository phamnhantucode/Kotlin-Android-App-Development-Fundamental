package com.example.wordsapp

import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider

class NavigationTests {

    fun navigate_to_words_nav_component() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
    }

}
