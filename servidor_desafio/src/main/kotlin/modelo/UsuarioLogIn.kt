package modelo

import kotlinx.serialization.Serializable

@Serializable
data class UsuarioLogIn(val nombre:String,val clave:String)
