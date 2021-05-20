package com.dicoding.academies.ui.bookmark

import com.dicoding.academies.data.model.CourseEntity
import com.dicoding.academies.data.source.AcademyRepository
import com.dicoding.academies.ui.academy.AcademyViewModel
import com.dicoding.academies.utils.DataDummy
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BookmarkViewModelTest {

    private lateinit var viewModel: BookmarkViewModel

    @Mock
    private lateinit var academyRepository: AcademyRepository

    @Before
    fun setUp() {
        viewModel = BookmarkViewModel(academyRepository)
    }

    @Test
    fun getCourses() {
        Mockito.`when`<ArrayList<CourseEntity>>(academyRepository.getBookmarkedCourses()).thenReturn(DataDummy.generateDummyCourses() as ArrayList<CourseEntity>)
        val courseEntities = viewModel.getBookmarks()
        Mockito.verify<AcademyRepository>(academyRepository).getBookmarkedCourses()
        TestCase.assertNotNull(courseEntities)
        TestCase.assertEquals(5, courseEntities.size)
    }
}