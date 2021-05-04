package com.dicoding.academies.ui.bookmark

import com.dicoding.academies.ui.academy.AcademyViewModel
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test

class BookmarkViewModelTest {

    private lateinit var viewModel: BookmarkViewModel
    @Before
    fun setUp() {
        viewModel = BookmarkViewModel()
    }

    @Test
    fun getCourses() {
        val courseEntities = viewModel.getBookmarks()
        TestCase.assertNotNull(courseEntities)
        TestCase.assertEquals(5, courseEntities.size)
    }
}