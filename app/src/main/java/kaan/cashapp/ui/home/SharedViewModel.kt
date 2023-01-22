package kaan.cashapp.ui.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


/**
 * Holds the in-memory shared resources between activity and fragment screens.
 */
@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {}