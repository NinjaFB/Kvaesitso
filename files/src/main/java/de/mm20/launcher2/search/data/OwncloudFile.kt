package de.mm20.launcher2.search.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import de.mm20.launcher2.files.R
import de.mm20.launcher2.helper.NetworkUtils
import de.mm20.launcher2.ktx.jsonObjectOf
import de.mm20.launcher2.owncloud.OwncloudClient
import de.mm20.launcher2.preferences.LauncherPreferences
import org.json.JSONObject

class OwncloudFile(
        fileId: Long,
        override val label: String,
        path: String,
        mimeType: String,
        size: Long,
        isDirectory: Boolean,
        val server: String,
        metaData: List<Pair<Int, String>>
) : File(fileId, path, mimeType, size, isDirectory, metaData) {
    override val badgeKey: String = "owncloud://"

    override val key: String = "owncloud://$server/$fileId"

    override val isStoredInCloud: Boolean
        get() = true

    override fun getLaunchIntent(context: Context): Intent? {
        return Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("$server/f/$id")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
    }

    override fun serialize(): String {
        return jsonObjectOf(
                "id" to id,
                "label" to label,
                "path" to path,
                "mimeType" to mimeType,
                "size" to size,
                "isDirectory" to isDirectory,
                "server" to server
        ).apply {
            for ((k, v) in metaData) {
                put(when (k) {
                    R.string.file_meta_owner -> "owner"
                    else -> "other"
                }, v)
            }
        }.toString()
    }

    companion object {
        suspend fun search(context: Context, query: String, owncloudClient: OwncloudClient) : List<OwncloudFile> {
            if (!LauncherPreferences.instance.searchOwncloud) return emptyList()
            if (query.length < 4) return emptyList()
            val server = owncloudClient.getServer() ?: return emptyList()
            if (NetworkUtils.isOffline(context, LauncherPreferences.instance.searchGDriveMobileData)) return emptyList()
            return owncloudClient.files.query(query).map {
                OwncloudFile(
                        fileId = it.id,
                        label = it.name,
                        path = server + it.url,
                        mimeType = it.mimeType,
                        size = it.size,
                        isDirectory = it.isDirectory,
                        server = server,
                        metaData = it.owner?.let { listOf(R.string.file_meta_owner to it) } ?: emptyList()
                )
            }
        }

        fun deserialize(serialized: String): OwncloudFile? {
            val json = JSONObject(serialized)
            val id = json.getLong("id")
            val label = json.getString("label")
            val path = json.getString("path")
            val mimeType = json.getString("mimeType")
            val size = json.getLong("size")
            val isDirectory = json.getBoolean("isDirectory")
            val server = json.getString("server")
            val owner = json.optString("owner").takeIf { it.isNotEmpty() }

            return OwncloudFile(
                    fileId = id,
                    label = label,
                    path = path,
                    mimeType = mimeType,
                    size = size,
                    isDirectory = isDirectory,
                    server = server,
                    metaData = owner?.let { listOf(R.string.file_meta_owner to it) } ?: emptyList()

            )
        }

    }
}