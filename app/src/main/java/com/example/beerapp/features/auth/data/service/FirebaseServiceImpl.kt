package com.example.beerapp.features.auth.data.service

import com.example.beerapp.features.auth.data.model.User
import com.example.beerapp.features.auth.data.model.UserData
import com.example.beerapp.features.auth.domain.AccountService
import com.example.beerapp.features.auth.domain.FirebaseService
import com.example.beerapp.features.auth.domain.StorageService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class FirebaseServiceImpl @Inject constructor(
    private val accountService: AccountService,
    private val storageService: StorageService,
): FirebaseService {

    private val _user = MutableStateFlow(User())
    override val user = _user.asStateFlow()

    private val _userData = MutableStateFlow(UserData())
    override val userData = _userData.asStateFlow()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            accountService.currentUser.collectLatest { currentUser ->
                _user.value = currentUser
                if(!currentUser.isAnonymous)
                    storageService.watchUserData(currentUser.id).collectLatest { currentUserData ->
                        _userData.value = currentUserData?: UserData()
                    }
                else{
                    _user.value = User()
                    _userData.value = UserData()
                }
            }
        }
    }
}