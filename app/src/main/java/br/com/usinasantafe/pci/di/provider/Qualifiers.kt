package br.com.usinasantafe.pci.di.provider

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ShortTimeoutHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ShortTimeoutRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OSApiDefault

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OSApiShortTimeout