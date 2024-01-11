package com.daveloper.rickandmortyapp.core.utils.providers

import android.app.Application
import com.daveloper.rickandmortyapp.core.utils.network.NetworkUtils.isConnected
import javax.inject.Inject

class ResourceProvider @Inject constructor(
    private val app: Application
) {
    /** Function that gets an [String] from the local resources based on the identification. Also
     * could receive extra arguments that could be needed or are required by some resource.
     *
     * @param strResource ([Int] type)
     * @param formatArgs (vararg [Any] type)
     * @return [String]
     * */
    fun getStringResource (
        strResource: Int,
        vararg formatArgs: Any
    ): String {
        return app.getString(strResource, *formatArgs)
    }

    /** Bridge function [isConnected].
     *
     * @return [Boolean]
     * */
    fun isConnectedToNetwork(): Boolean = app.isConnected()
}