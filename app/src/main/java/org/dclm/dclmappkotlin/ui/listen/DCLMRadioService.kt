package org.dclm.dclmappkotlin.ui.listen

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import androidx.media.session.MediaButtonReceiver
import androidx.navigation.NavDeepLinkBuilder
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import org.dclm.dclmappkotlin.R
import org.dclm.dclmappkotlin.util.C

class DCLMRadioService : Service() {

    var player: SimpleExoPlayer? = null
    private var playerNotificationManager: PlayerNotificationManager? = null
    private var mediaSession: MediaSessionCompat? = null
    private var mediaSessionConnector: MediaSessionConnector? = null
    private var context: Context? = null
    private var check = false
    private var mediaSource: MediaSource? = null
    private var preferences: SharedPreferences? = null
    private val PREFRENCES = "org.dclm.radio"


    class RadioBinder : Binder() {

        fun getService(): DCLMRadioService {
            return DCLMRadioService()
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return RadioBinder()
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        preferences = applicationContext.getSharedPreferences(PREFRENCES, Context.MODE_PRIVATE)
        val attribute = AudioAttributes.Builder().setUsage(com.google.android.exoplayer2.C.USAGE_MEDIA)
            .setContentType(com.google.android.exoplayer2.C.CONTENT_TYPE_SPEECH)
            .build()

        player =ExoPlayerFactory.newSimpleInstance(context)
        player?.setAudioAttributes(attribute, true)
        mediaSession = MediaSessionCompat(this, getString(R.string.app_name))
        player?.addListener(object : Player.EventListener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (!playWhenReady){
                    ListenFragment().state = false
                    check = false
                    stopForeground(false)
                } else{
                    ListenFragment().state = true
                    startForeground()
                    check = true
                }
            }
        })

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var url = ListenFragment().link
        check = true
        val httpDataSourceFactory = DefaultHttpDataSourceFactory(Util.getUserAgent(this, getString(R.string.app_name))
        , null, 30 *1000, 30*1000
        , true)
        mediaSource = url?.let { ProgressiveMediaSource.Factory(httpDataSourceFactory).createMediaSource(
            Uri.parse(it))}
        player?.prepare(mediaSource)
        startForeground()
        playerNotificationManager?.setSmallIcon(R.drawable.nlogo)
        playerNotificationManager?.setUseStopAction(true)
        playerNotificationManager?.setUseNavigationActions(false)
        playerNotificationManager?.setUseStopAction(false)
        playerNotificationManager?.setPlayer(player)

        mediaSession?.isActive = true
        playerNotificationManager?.setMediaSessionToken(mediaSession?.sessionToken)
        mediaSessionConnector = MediaSessionConnector(mediaSession)
        mediaSessionConnector?.setPlayer(player)
        MediaButtonReceiver.handleIntent(mediaSession, intent)
        player?.playWhenReady = true
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        if (check){
            mediaSession?.release()
            mediaSessionConnector?.setPlayer(null)
            playerNotificationManager?.setPlayer(null)
            player?.release()
        }
        player = null
        super.onDestroy()
    }
    private fun startForeground(){
        playerNotificationManager = PlayerNotificationManager.createWithNotificationChannel(context,
        C().PLAYBACK_CHANNEL_ID,  R.string.app_name, R.string.app_describe, C().PLAYBACK_NOTIFICATION_ID,
            object : PlayerNotificationManager.MediaDescriptionAdapter {
                override fun createCurrentContentIntent(player: Player?): PendingIntent? {
                    val bundle = Bundle()
                    bundle.putString("link", preferences!!.getString("language", getString(R.string.select_french_language)))
                    bundle.putString("url", preferences!!.getString("url", getString(R.string.dclm_api)))

                    return context?.let { NavDeepLinkBuilder(it)
                        .setGraph(R.navigation.mobile_navigation)
                        .setDestination(R.id.radioFragment)
                        .setArguments(bundle)
                        .createPendingIntent()}
                }

                override fun getCurrentContentText(player: Player?): String? {
                    return getString(R.string.app_describe)
                }

                override fun getCurrentContentTitle(player: Player?): String {
                    return getString(R.string.app_name)
                }

                override fun getCurrentLargeIcon(
                    player: Player?,
                    callback: PlayerNotificationManager.BitmapCallback?
                ): Bitmap? {
                    return context?.let { getBitmap(it) }
                }

            },
            object : PlayerNotificationManager.NotificationListener {
                override fun onNotificationCancelled(
                    notificationId: Int,
                    dismissedByUser: Boolean
                ) {
                    if (dismissedByUser){
                        stopSelf()
                    }
                    stopSelf()
                }

                override fun onNotificationPosted(
                    notificationId: Int,
                    notification: Notification?,
                    ongoing: Boolean
                ) {
                   if (ongoing){
                       startForeground(notificationId, notification)
                   } else{
                       stopForeground(false)
                   }
                }
            })
    }
    
    private fun getBitmap(context: Context): Bitmap {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            (context.resources.getDrawable(R.drawable.nlogo, null) as BitmapDrawable).bitmap
        } else {
            (context.resources.getDrawable(R.drawable.nlogo) as BitmapDrawable).bitmap
        }
    }

    fun pausePlayer() {
        if (player!!.isPlaying) {
            player!!.playWhenReady = false
            player!!.playbackState
            //RadioFragment.state = false;
        }
    }

    fun startPlayer() {
        if (!player!!.isPlaying) {
            player!!.playWhenReady = true
            player!!.playbackState
            //RadioFragment.state = true;
        }
    }
}