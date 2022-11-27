package com.example.myapplication.viewmodels

import android.net.Uri
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.R
import com.example.myapplication.nicknameUsable

class LoginMainViewModel : ViewModel() {
    private val ID_ = MutableLiveData<String>("")
    private val pw = MutableLiveData<String>("")
    val id = LiveData<String> = ID_
    val password: LiveData<String> = pw

    private val loginFormats = MutableLiveData<LoginFormState>()
    val loginFormState:LiveData<LoginFormState> = loginFormats

    val nickname = MutableLiveData<String>("")

    val uri = MutableLiveData<Uri?>()

    fun setURI(URI:Uri?){
        if(URI!=null) this.uri.value = URI
    }

    fun loginDataChanged(username:String, password:String){
        if(!UserNameValidation(username)){
            loginFormats.value = LoginFormState(usernameError = R.string.invalid_username)
        }else if(!PasswordValidation(password)){
            loginFormats.value = LoginFormState(passwordError = R.string.invalid_password)
        }else{
            loginFormats.value = LoginFormState(DataValidation = true)
        }
    }

    private fun UserNameValidation(username: String):Boolean{
        return if(username.contains("@")){
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        }else{
            username.isNotBlank()
        }
    }

    private fun PasswordValidation(password: String):Boolean{
        return password.length > 6
    }

}
data class LoginFormState(
    val usernameError:Int? = null,
    val passwordError:Int? = null,
    val DataValidation:Boolean = false
)
