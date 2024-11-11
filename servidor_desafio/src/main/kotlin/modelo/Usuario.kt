package modelo

import kotlinx.serialization.Serializable

@Serializable
data class Usuario(val id:Int,val nombre:String, val edad:Int, val experiencia:Int, val rol_id:Int,val clave:String,val activo:Int,val foto:String)
