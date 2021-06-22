package com.todo.notes.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.todo.notes.ui.home.add_note.AddNoteViewModel
import com.todo.notes.di.annotations.ViewModelKey
import com.todo.notes.ui.home.HomeActivityViewModel
import com.todo.notes.ui.home.details.DetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @IntoMap annotation says that Provider object for this service will be inserted into Map,
 * and @ViewModelKey annotation specifies under which key it will reside.
 * */
@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeActivityViewModel::class)
    internal abstract fun bindHomeActivityViewModel(viewModel: HomeActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    internal abstract fun bindDetailsViewModel(viewModel: DetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddNoteViewModel::class)
    internal abstract fun bindAddNoteViewModel(viewModel: AddNoteViewModel): ViewModel

}
