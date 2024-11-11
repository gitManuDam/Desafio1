package Modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UsuarioLogIn(
    @SerializedName("nombre")
    val nombre: String? = null,
    @SerializedName("clave")
    val clave: String? = null
): Serializable


