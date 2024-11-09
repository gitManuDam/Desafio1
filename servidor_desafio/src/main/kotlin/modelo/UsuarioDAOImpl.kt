package modelo

import dao.Database
import modelo.Usuario

class UsuarioDAOImpl:UsuarioDAO {
    override fun insertar(usuario: Usuario): Boolean {
        val sql ="INSERT INTO usuario (nombre, edad, experiencia, rol_id, clave, activo, foto) VALUES (?, ?, ?, ?, ?, ?, ?);"
        val connection = Database.getConnection()
        connection?.use{
            val statement = it.prepareStatement(sql)
            statement.setString(1, usuario.nombre)
            statement.setInt(2, usuario.edad)
            statement.setInt(3, usuario.experiencia)
            statement.setInt(4,usuario.rol_id)
            statement.setString(5,usuario.clave)
            statement.setInt(6, usuario.activo)
            statement.setString(7, usuario.foto)

            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun obtener(id: Int): Usuario? {
        val sql = "SELECT * FROM usuario WHERE id = ?"
        val connection = Database.getConnection()
        connection?.use{
            val statement = it.prepareStatement(sql)
            statement.setInt(1, id)
            val resultSet = statement.executeQuery()

            if (resultSet.next()) {
                return Usuario(
                    id = resultSet.getInt("id"),
                    nombre = resultSet.getString("nombre"),
                    edad = resultSet.getInt("edad"),
                    experiencia= resultSet.getInt("experiencia") ,
                    rol_id=  resultSet.getInt("rol_id"),
                    clave=  resultSet.getString("clave"),
                    activo= resultSet.getInt("activo") ,
                    foto = resultSet.getString("foto")
                )
            }
        }
        return null
    }

    override fun cambiarClave(usuario: Usuario, nuevaClave:String): Boolean {
        val sql = "UPDATE usuario SET clave = ? WHERE id = ?"
        val connection = Database.getConnection()
        connection?.use{
            val statement = it.prepareStatement(sql)
            statement.setString(1, nuevaClave)
            statement.setInt(2,usuario.id)

            val resultSet = statement.executeUpdate()
            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun eliminar(id: Int): Boolean {
        val sql = "DELETE FROM usuario WHERE id = ?"
        val connection = Database.getConnection()
        connection?.use{
            val statement = it.prepareStatement(sql)
            statement.setInt(1, id)

            return statement.executeUpdate() > 0

        }
        return false
    }

    override fun obtenerTodos(): List<Usuario> {
        val usuarios = mutableListOf<Usuario>()
        val sql = "SELECT * FROM usuarios"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            val resultSet = statement.executeQuery()

            while (resultSet.next()) {
                val usuario = Usuario(
                    id = resultSet.getInt("id"),
                    nombre = resultSet.getString("nombre"),
                    edad = resultSet.getInt("edad"),
                    experiencia= resultSet.getInt("experiencia") ,
                    rol_id=  resultSet.getInt("rol_id"),
                    clave=  resultSet.getString("clave"),
                    activo= resultSet.getInt("activo") ,
                    foto = resultSet.getString("foto")
                )
                usuarios.add(usuario)
            }
        }
        return usuarios
    }

}