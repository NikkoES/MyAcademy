package com.dicoding.academies.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dicoding.academies.data.source.remote.RemoteDataSource
import com.dicoding.academies.data.source.remote.response.ContentResponse
import com.dicoding.academies.data.source.remote.response.CourseResponse
import com.dicoding.academies.data.source.remote.response.ModuleResponse
import com.dicoding.academies.utils.DataDummy
import com.dicoding.academies.utils.LiveDataTestUtil
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AcademyRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val academyRepository = FakeAcademyRepository(remote)

    private val courseResponses = DataDummy.generateRemoteDummyCourses()
    private val courseId = courseResponses[0].id
    private val moduleResponses = DataDummy.generateRemoteDummyModules(courseId)
    private val moduleId = moduleResponses[0].moduleId
    private val content = DataDummy.generateRemoteDummyContent(moduleId)

    @Test
    fun getAllCourses() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadCoursesCallback)
                .onAllCoursesReceived(courseResponses)
            null
        }.`when`(remote).getAllCourses(object : RemoteDataSource.LoadCoursesCallback {
            override fun onAllCoursesReceived(courseResponses: List<CourseResponse>) {

            }
        })
        val courseEntities = LiveDataTestUtil.getValue(academyRepository.getAllCourses())
        verify(remote).getAllCourses(object : RemoteDataSource.LoadCoursesCallback {
            override fun onAllCoursesReceived(courseResponses: List<CourseResponse>) {

            }
        })
        assertNotNull(courseEntities)
        assertEquals(courseResponses.size.toLong(), courseEntities.size.toLong())
    }

    @Test
    fun getAllModulesByCourse() {
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadModulesCallback)
                .onAllModulesReceived(moduleResponses)
            null
        }.`when`(remote).getModules(eq(courseId), object : RemoteDataSource.LoadModulesCallback{
            override fun onAllModulesReceived(moduleResponses: List<ModuleResponse>) {

            }

        })

        val courseEntities = LiveDataTestUtil.getValue(academyRepository.getAllModulesByCourse(courseId))

        verify(remote).getModules(eq(courseId), object : RemoteDataSource.LoadModulesCallback{
            override fun onAllModulesReceived(moduleResponses: List<ModuleResponse>) {

            }

        })

        assertNotNull(courseEntities)
        assertEquals(moduleResponses.size.toLong(), courseEntities.size.toLong())
    }

    @Test
    fun getBookmarkedCourses() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadCoursesCallback)
                .onAllCoursesReceived(courseResponses)
            null
        }.`when`(remote).getAllCourses(object : RemoteDataSource.LoadCoursesCallback{
            override fun onAllCoursesReceived(courseResponses: List<CourseResponse>) {

            }

        })

        val courseEntities = LiveDataTestUtil.getValue(academyRepository.getBookmarkedCourses())

        verify(remote).getAllCourses(object : RemoteDataSource.LoadCoursesCallback{
            override fun onAllCoursesReceived(courseResponses: List<CourseResponse>) {

            }

        })

        assertNotNull(courseEntities)
        assertEquals(courseResponses.size.toLong(), courseEntities.size.toLong())
    }

    @Test
    fun getContent() {
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadModulesCallback)
                .onAllModulesReceived(moduleResponses)
            null
        }.`when`(remote).getModules(eq(courseId), object : RemoteDataSource.LoadModulesCallback{
            override fun onAllModulesReceived(moduleResponses: List<ModuleResponse>) {

            }

        })
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadContentCallback)
                .onContentReceived(content)
            null
        }.`when`(remote).getContent(eq(moduleId),  object : RemoteDataSource.LoadContentCallback{
            override fun onContentReceived(contentResponse: ContentResponse) {

            }


        })

        val courseEntitiesContent = LiveDataTestUtil.getValue(academyRepository.getContent(courseId, moduleId))

        verify(remote)
            .getModules(eq(courseId), object : RemoteDataSource.LoadModulesCallback{
            override fun onAllModulesReceived(moduleResponses: List<ModuleResponse>) {

            }

        })

        verify(remote)
            .getContent(eq(moduleId), object : RemoteDataSource.LoadContentCallback{
                override fun onContentReceived(contentResponse: ContentResponse) {

                }


            })

        assertNotNull(courseEntitiesContent)
        assertNotNull(courseEntitiesContent.contentEntity)
        assertNotNull(courseEntitiesContent.contentEntity?.content)
        assertEquals(content.content, courseEntitiesContent.contentEntity?.content)
    }

    @Test
    fun getCourseWithModules() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadCoursesCallback)
                .onAllCoursesReceived(courseResponses)
            null
        }.`when`(remote).getAllCourses(object : RemoteDataSource.LoadCoursesCallback{
            override fun onAllCoursesReceived(courseResponses: List<CourseResponse>) {

            }

        })

        val courseEntities = LiveDataTestUtil.getValue(academyRepository.getCourseWithModules(courseId))

        verify(remote).getAllCourses(object : RemoteDataSource.LoadCoursesCallback{
            override fun onAllCoursesReceived(courseResponses: List<CourseResponse>) {

            }

        })

        assertNotNull(courseEntities)
        assertNotNull(courseEntities.title)
        assertEquals(courseResponses[0].title, courseEntities.title)
    }
}