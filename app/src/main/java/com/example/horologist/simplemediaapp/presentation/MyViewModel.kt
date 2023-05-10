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

package com.example.horologist.simplemediaapp.presentation

import androidx.lifecycle.viewModelScope
import androidx.media3.common.Player
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.media.data.repository.PlayerRepositoryImpl
import com.google.android.horologist.media.model.Media
import com.google.android.horologist.media.ui.state.PlayerViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalHorologistApi::class)
class MyViewModel(
    player: Player,
    playerRepository: PlayerRepositoryImpl = PlayerRepositoryImpl()
) : PlayerViewModel(playerRepository) {

    init {
        viewModelScope.launch {
            playerRepository.connect(player) {}

            playerRepository.setMedia(
                Media(
                    id = "wake_up_02",
                    uri = "https://storage.googleapis.com/uamp/The_Kyoto_Connection_-_Wake_Up/02_-_Geisha.mp3",
                    title = "Geisha",
                    artist = "The Kyoto Connection"
                )
            )
        }
    }

}
