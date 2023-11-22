package com.daveloper.rickandmortyapp.core.utils.config

import android.content.Context
import com.daveloper.rickandmortyapp.R

object ProjectConfigUtils {

    /**
     * This extension function gets the version name of the app, if is not found returns 'Unknown'.
     *
     * @param this ([Context] type)
     * @param withPrefix ([Boolean] type) - If is setup to true adds the prefix 'v.' to the app
     * version name.
     * @return [String]
     * */
    fun Context.getCurrentAppVersion(
        withPrefix: Boolean = true
    ): String = try {
        val versionName: String = packageManager
            .getPackageInfo(packageName, 0)
            .versionName
            .toString()
        if (withPrefix) {
            this.resources.getString(
                R.string.lab_app_version,
                versionName
            )
        } else {
            versionName
        }
    } catch (e: Exception) {
        if (withPrefix) {
            this.resources.getString(
                R.string.lab_app_version,
                this.resources.getString(R.string.lab_unknown)
            )
        } else {
            this.resources.getString(R.string.lab_unknown)
        }
    }
}