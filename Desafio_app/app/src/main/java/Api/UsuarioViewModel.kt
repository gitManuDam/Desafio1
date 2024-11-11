package Api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import Modelo.Usuario
import Modelo.UsuarioLogIn
import Api.UsuarioNetwork
import androidx.lifecycle.ViewModel

class UsuarioViewModel:ViewModel (){
    private val _myResponse = MutableLiveData<Usuario?>()
    val myResponse: LiveData<Usuario?> get() = _myResponse

    private val _myResponseList = MutableLiveData<List<Usuario>>()
    val myResponseList: LiveData<List<Usuario>> get() = _myResponseList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _resOperacion = MutableLiveData<Boolean>()
    val resOperacion: LiveData<Boolean> get() = _resOperacion

    private val _errorCode = MutableLiveData<Int?>()
    val errorCode: LiveData<Int?> get() = _errorCode

    fun getUsuarioPorIdVM(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            var response: Response<Usuario> = UsuarioNetwork.retrofit.getUsuarioPorId(id)

            if (response.isSuccessful) {
                _myResponse.value = response.body()
            } else {
                _myResponse.value = null
                _errorCode.value = response.code()
            }
            _isLoading.value = false
        }
    }

    fun loginVM(user: UsuarioLogIn){
        viewModelScope.launch {
            _isLoading.value = true

            var response: Response<Usuario?> = UsuarioNetwork.retrofit.loginUsuario(user)

            if (response.isSuccessful) {
                _myResponse.value = response.body()
                _errorCode.value = response.code()
            } else {
                _myResponse.value = null
                _errorCode.value = response.code()
            }
            _isLoading.value = false
        }
    }

    fun getUsuariosVM() {
        viewModelScope.launch {
            _isLoading.value = true
            var response: Response<MutableList<Usuario>> = UsuarioNetwork.retrofit.getUsuarios()

            if (response.isSuccessful) {
                _myResponseList.value = response.body()
            } else {
                _myResponseList.value = emptyList()
                _errorCode.value = response.code()
            }
            _isLoading.value = false
        }
    }

    fun addUserVM(user: Usuario) {
        viewModelScope.launch {
            val response: Response<Usuario?> = UsuarioNetwork.retrofit.addUsuario(user)

            if (response.isSuccessful) {
                _myResponse.value = response.body()
                _errorCode.value = response.code()
                obtenerTodosLosUsuarios()
            } else {
                _myResponse.value = null
                _errorCode.value = response.code()
            }
        }
    }

    fun deleteUserVM(id: Int) {
        viewModelScope.launch {
            val response: Response<Boolean> = UsuarioNetwork.retrofit.deleteUsuario(id)

            if (response.isSuccessful) {
                _resOperacion.value = response.body()
                _errorCode.value = response.code()
                obtenerTodosLosUsuarios()
            } else {
                _resOperacion.value = false
                _errorCode.value = response.code()
            }
        }
    }

    fun obtenerTodosLosUsuarios(){
        viewModelScope.launch {
            _myResponseList.value = UsuarioNetwork.retrofit.getUsuarios().body()
        }
    }

    fun limpiarRespuesta (){
        _myResponse.value = null
    }

    fun limpiarError(){
        _errorCode.value = null
    }
}