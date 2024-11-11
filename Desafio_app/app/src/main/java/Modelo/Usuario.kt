package Modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Usuario(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("nombre")
    val nombre: String? = null,

    @SerializedName("edad")
    val edad: Int? = null,

    @SerializedName("experiencia")
    val experiencia: Int? = null,

    @SerializedName("rol_id")
    val rolId: Int? = null,

    @SerializedName("clave")
    val clave: String? = null,

    @SerializedName("activo")
    val activo: Int? = null,

    @SerializedName("foto")
    val foto: String? = null
) : Serializable