package kaan.cashapp.extra

import kaan.cashapp.App
import kaan.cashapp.BuildConfig

val App.Companion.isDebug: Boolean
    get() = BuildConfig.BUILD_TYPE == "debug"
