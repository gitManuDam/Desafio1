package Api

import Modelo.Usuario
import Modelo.UsuarioLogIn
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UsuarioAPI {
    // Obtener todos los usuarios
    @GET("listarUsuario")
    suspend fun getUsuarios(): Response<MutableList<Usuario>>

    // Obtener un usuario por ID
    @GET("listarUsuario/{id}")
    suspend fun getUsuarioPorId(@Path("id") id: Int): Response<Usuario>

    // Login de usuario
    @POST("loginUsuario")
    suspend fun loginUsuario(@Body userData: UsuarioLogIn): Response<Usuario?>

    // Insertar un nuevo usuario
    @POST("insertarUsuario")
    suspend fun addUsuario(@Body userData: Usuario): Response<Usuario?>

    // Eliminar un usuario por ID
    @DELETE("borrarUsuario/{id}")
    suspend fun deleteUsuario(@Path("id") id: Int): Response<Boolean>
}