package com.example.tmdbapp.exo

import com.intuit.sdp.BuildConfig.APPLICATION_ID


object Constants {
    const val EXO_PLAYER_USER_AGENT = APPLICATION_ID
    const val EXO_PLAYER_VIDEO_CACHE_DURATION = 10 * 1024 * 1024
    const val EXO_PLAYER_PROGRESSIVE_SAMPLE_URL =
        "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
    const val EXO_PLAYER_HLS_SAMPLE_URL =
        "http://176.9.110.12:8080/FB_TV_HD/index.m3u8"

}
