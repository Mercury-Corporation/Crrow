package com.ko610

import kotlin.test.Test
import kotlin.test.assertTrue

class LibraryTest {
    @Test fun sampleLibraryMethodReturnsTrue() {
        val classUnderTest = Library()
        assertTrue(classUnderTest.sampleLibraryMethod(), "sampleLibraryMethod should return 'true'")
    }
}