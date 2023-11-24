package app.mp.common.util.media

import android.content.Context
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C.AUDIO_CONTENT_TYPE_MUSIC
import androidx.media3.common.C.USAGE_MEDIA
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession

class AudioPlayer(context: Context) {
    var mediaSession: MediaSession? = null
        private set

    init {
        val player = ExoPlayer.Builder(context).build().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AUDIO_CONTENT_TYPE_MUSIC)
                    .setUsage(USAGE_MEDIA)
                    .build(),
                true
            )
            repeatMode = Player.REPEAT_MODE_ALL
        }
        mediaSession = MediaSession.Builder(context, player).build()
    }

    fun play() = mediaSession?.player?.apply {
        prepare()
        play()
    }

    fun release() {
        mediaSession?.apply {
            player.release()
            release()
            mediaSession = null
        }
    }

    fun addAudiosFromUri(uriList: List<String>) = mediaSession?.player?.apply {
        for (uri in uriList) {
            addMediaItem(
                MediaItem.Builder()
                    .setUri(uri)
                    .build()
            )
        }
    }
}