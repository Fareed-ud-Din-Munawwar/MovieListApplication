package com.example.assignmentoptimized.dependencyinjection

import androidx.databinding.DataBindingComponent
import com.example.assignmentoptimized.utility.PicassoImageAdapter
import dagger.hilt.DefineComponent
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.BINARY)
annotation class BindingScope

@BindingScope
@DefineComponent(parent = SingletonComponent::class)
interface BindingComponent

@DefineComponent.Builder
interface BindingComponentBuilder {
    fun build(): BindingComponent
}

@EntryPoint
@BindingScope
@InstallIn(BindingComponent::class)
interface DataBindingEntryPoint : DataBindingComponent {
    @BindingScope
    override fun getPicassoImageAdapter(): PicassoImageAdapter
}