package ar.edu.unlam.mobile2.kitties

import ar.edu.unlam.mobile2.kitties.data.KittiesRepo
import ar.edu.unlam.mobile2.kitties.data.KittiesRestRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class MainModule {

    @Binds
    abstract fun bindKittiesRepo(kittiesRestRepo: KittiesRestRepo): KittiesRepo
}
