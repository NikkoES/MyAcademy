package com.dicoding.academies.ui.bookmark


import com.dicoding.academies.data.model.CourseEntity

interface BookmarkFragmentCallback {
    fun onShareClick(course: CourseEntity)
}

