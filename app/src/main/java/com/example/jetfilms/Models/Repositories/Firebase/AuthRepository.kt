package com.example.jetfilms.Models.Repositories.Firebase

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.example.jetfilms.Models.Firebase.Auth.AuthService
import com.example.jetfilms.Models.Firebase.Auth.Resource
import com.example.jetfilms.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val context: Context,
): AuthService {
    override fun loginUser(email: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading())
            val result = firebaseAuth.signInWithEmailAndPassword(email,password).await()
            emit(Resource.Success(data = result))
        }.catch {
            emit(Resource.Error(message = it.message.toString()))
        }
    }

    override fun signUp(name: String,email: String, password: String): Flow<Resource<AuthResult>> {
       return flow {
           emit(Resource.Loading())

           val result = firebaseAuth.createUserWithEmailAndPassword(email,password).await()
           val profile = UserProfileChangeRequest.Builder().setDisplayName(name).build()
           result.user?.run {
             updateProfile(profile)
           }

           emit(Resource.Success(data = result))
       }.catch {
           emit(Resource.Error(message = it.message.toString()))
       }
    }

    override fun createNonce(): String {
        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)

        return digest.fold("") { str,it ->
            str + "%02x".format(it)
        }
    }

    override fun logInWithGoogle(): Flow<Resource<AuthResult>> {


        return flow {

            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(context.getString(R.string.web_client_id))
                .setAutoSelectEnabled(false)
                .setNonce(createNonce())
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()


            try {
                val credentialManager = CredentialManager.create(context)
                val result = credentialManager.getCredential(
                    context = context,
                    request = request
                )

                val credential = result.credential
                if(credential is CustomCredential) {
                    if(credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                        try {
                            val googleIdTokenCredential = GoogleIdTokenCredential
                                .createFrom(credential.data)

                            val firebaseCredential = GoogleAuthProvider
                                .getCredential(
                                    googleIdTokenCredential.idToken,
                                    null
                                )
                            val signInResult = firebaseAuth.signInWithCredential(firebaseCredential)

                            val authResult = signInResult.await()

                            if(signInResult.isSuccessful) {
                                emit(Resource.Success(data = authResult))
                            } else {
                                emit(Resource.Error(message = signInResult.exception?.message.toString()))
                            }


                        } catch (e: Exception) {
                            Resource.Error(
                                message = e.message.toString(),
                                data = null
                            )
                        }
                    }
                }
            } catch (e:Exception) {
                Resource.Error(
                    message = e.message.toString(),
                    data = null
                )
            }
        }.catch {
          //  emit(Resource.Error(message = it.message.toString()))
        }
    }
}