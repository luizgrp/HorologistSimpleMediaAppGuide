/*
 * Copyright 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

@file:OptIn(ExperimentalHorologistApi::class)

package com.example.horologist.simplemediaapp.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Vibrator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.media3.exoplayer.ExoPlayer
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.audio.SystemAudioRepository
import com.google.android.horologist.audio.ui.VolumeViewModel
import com.google.android.horologist.media.ui.components.PodcastControlButtons
import com.google.android.horologist.media.ui.screens.player.DefaultMediaInfoDisplay
import com.google.android.horologist.media.ui.screens.player.PlayerScreen
import com.google.android.horologist.media.ui.state.PlayerUiController
import com.google.android.horologist.media.ui.state.PlayerUiState

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @SuppressLint("UnsafeOptInUsageError")
        val player = ExoPlayer.Builder(this)
            .setSeekForwardIncrementMs(5000L)
            .setSeekBackIncrementMs(5000L)
            .build()
        // ViewModels should NOT be created here like this
        val viewModel = MyViewModel(player)
        val volumeViewModel = createVolumeViewModel()

        setContent {
            PlayerScreen(
                playerViewModel = viewModel,
                volumeViewModel = volumeViewModel,
                mediaDisplay = { playerUiState: PlayerUiState ->
                    DefaultMediaInfoDisplay(playerUiState)
                },
                controlButtons = { playerUIController: PlayerUiController,
                                   playerUiState: PlayerUiState ->
                    PodcastControlButtons(
                        playerController = playerUIController,
                        playerUiState = playerUiState
                    )
                },
                buttons = { }
            )
        }
    }

    fun createVolumeViewModel(): VolumeViewModel {
        val audioRepository = SystemAudioRepository.fromContext(application)
        val vibrator: Vibrator = application.getSystemService(Vibrator::class.java)
        return VolumeViewModel(audioRepository, audioRepository, onCleared = {
            audioRepository.close()
        }, vibrator)
    }
}
