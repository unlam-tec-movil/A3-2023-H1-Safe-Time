package ar.edu.unlam.mobile2.hilt
import ar.edu.unlam.mobile2.pantallaHome.data.ContactRepository
import ar.edu.unlam.mobile2.pantallaMapa.data.repository.RouteRepository
import ar.edu.unlam.mobile2.pantallaMapa.data.repository.RoutesRestRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class MainModule {

    @Binds
    abstract fun bindRoutesRepository(routeResRepository: RoutesRestRepository): RouteRepository
}