buildscript {
    extra.apply {
        set("compose_compiler_version", "1.5.2")
        set("lifecycle_version", "2.6.1")
        set("retrofit2_version", "2.9.0")
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.4" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.android.library") version "8.1.4" apply false
}