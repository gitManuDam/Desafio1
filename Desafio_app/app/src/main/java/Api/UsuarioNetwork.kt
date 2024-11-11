package Api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import Parametros.Parametros

object UsuarioNetwork {
    val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Parametros.url+":"+Parametros.puerto)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UsuarioAPI::class.java)
    }
}