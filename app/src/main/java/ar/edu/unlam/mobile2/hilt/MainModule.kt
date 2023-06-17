package ar.edu.unlam.mobile2.hilt

import android.content.Context
import androidx.room.Room
import ar.edu.unlam.mobile2.data.room.local.MarcadorDAO
import ar.edu.unlam.mobile2.data.room.local.MarcadorDatabase
import coil.ImageLoader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.inject.Singleton
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
object MainModule {


    @Provides
    @Singleton
    fun providesMarcadoresDatabase(@ApplicationContext app: Context) =
        Room.databaseBuilder(app, MarcadorDatabase::class.java, "marcadores_db").build()

    @Provides
    @Singleton
    fun providesMarcadoresDAO(marcadorDatabase: MarcadorDatabase) : MarcadorDAO = marcadorDatabase.marcadorDao()

    @Singleton
    @Provides
    fun retrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .client(OkHttpClient.Builder().apply { ignoreAllSSLErrors() }.build())
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Singleton
    @Provides
    fun imageLoader(context: Context): ImageLoader {
        return ImageLoader.Builder(context)
            .okHttpClient(OkHttpClient.Builder().apply { ignoreAllSSLErrors() }.build())
            .build()
    }

    @Singleton
    @Provides
    fun OkHttpClient.Builder.ignoreAllSSLErrors(): OkHttpClient.Builder {
        val naiveTrustManager = object : X509TrustManager {
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) = Unit
            override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) = Unit
        }

        val insecureSocketFactory = SSLContext.getInstance("TLSv1.2").apply {
            val trustAllCerts = arrayOf<TrustManager>(naiveTrustManager)
            init(null, trustAllCerts, SecureRandom())
        }.socketFactory

        sslSocketFactory(insecureSocketFactory, naiveTrustManager)
        hostnameVerifier(HostnameVerifier { _, _ -> true })
        return this
    }

    //@Binds
    //abstract fun bindRoutesRepository(routeResRepository: RoutesRestRepository): RouteRepository
}