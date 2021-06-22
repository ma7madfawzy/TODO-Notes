package com.todo.notes.di.modules

import com.example.contacts.ui.home.add_contact.AddNoteActivity
import com.todo.notes.ui.home.HomeActivity
import com.todo.notes.ui.home.details.DetailsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * ActivityBuildersModule which is responsible for injecting activities
 * and creating subcomponents for them.
 * */

@Module
abstract class ActivityBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeHomeActivity(): HomeActivity

    @ContributesAndroidInjector
    abstract fun contributeArticleDetailsActivity(): DetailsActivity

    @ContributesAndroidInjector
    abstract fun contributeAddNoteActivity(): AddNoteActivity

}