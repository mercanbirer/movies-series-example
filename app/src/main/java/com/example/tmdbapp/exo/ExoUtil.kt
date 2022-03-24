package com.example.tmdbapp.exo

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import javax.inject.Inject

class ExoUtil @Inject constructor(
    val exoPlayer: SimpleExoPlayer,
    val factory: ProgressiveMediaSource.Factory,
    val hlsfactory: HlsMediaSource.Factory,
    val mediaItemBuilder: MediaItem.Builder
) : Player.EventListener {
    private var simpleExoPlayer: SimpleExoPlayer? = null

    // private var mPlayerStateListener: PlayerStateListener? = null
    private var mPlayerView: PlayerView? = null

    private var mShouldAutoPlay: Boolean = true
    private var mUrl: String? = null

    private fun mediaItem(uri: Uri?): MediaItem {
        return mediaItemBuilder.setUri(uri).build()
    }

    private fun buildMediaSource(uri: Uri?): MediaSource {
        return if (uri.toString().contains("mp4")) {
            factory.createMediaSource(mediaItem(uri))
        } else {
            hlsfactory.createMediaSource(mediaItem(uri))
        }
    }

    private fun preparePlayer(url: String) {
        val uri = Uri.parse(url)
        val mediaSource = buildMediaSource(uri)
        simpleExoPlayer = exoPlayer
        simpleExoPlayer?.setMediaSource(mediaSource)
        simpleExoPlayer?.prepare()
        Log.i("EXOPLAYER", "Exo has been prepared")
    }

    @SuppressLint("LogNotTimber")
    private fun initializePlayer() {
        preparePlayer(mUrl!!)
        mPlayerView?.player = simpleExoPlayer
        simpleExoPlayer?.seekTo(0)
        simpleExoPlayer?.playWhenReady = mShouldAutoPlay
        simpleExoPlayer?.addListener(this@ExoUtil)
        Log.i("EXOPLAYER", "Exo has been initialized")
    }

    private fun releasePlayer() {
        simpleExoPlayer?.stop()
        simpleExoPlayer?.release()
        simpleExoPlayer?.removeListener(this)
        simpleExoPlayer = null
    }

    fun setPlayerView(playerView: PlayerView) {
        mPlayerView = playerView
    }

    fun setUrl(url: String) {
        mUrl = url
        Log.i("EXOPLAYER", "url has been prepared")
    }

    override fun onPlayerError(error: PlaybackException) {
        super.onPlayerError(error)
        Log.e("EXOPLAYER", error.toString())
    }

    fun onStart() {
        initializePlayer()
        mPlayerView?.onResume()
    }


    fun onPause() {
        releasePlayer()
        mPlayerView?.onPause()
    }

    fun onStop() {
        if (mPlayerView?.player?.isPlaying == true) {
            releasePlayer()
            mPlayerView?.onPause()
        }
    }
}
