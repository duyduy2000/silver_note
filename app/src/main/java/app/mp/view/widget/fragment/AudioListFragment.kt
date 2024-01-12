package app.mp.view.widget.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import app.mp.common.util.media.PlayerServiceBinder
import app.mp.view.widget.list.AudioListView
import app.mp.viewmodel.audio.AudioViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class AudioListFragment : Fragment() {
    protected val viewModel by viewModels<AudioViewModel>()
    private lateinit var audioListView: AudioListView

    @Inject
    lateinit var playerServiceBinder: PlayerServiceBinder

    fun initView(listView: AudioListView) {
        audioListView = listView
        audioListView.init()
    }

    fun setUpdateListOnDataChange() {
        viewModel.audioList.observe(viewLifecycleOwner) {
            playerServiceBinder.usePlayer {
                if (it.isNotEmpty()) {
                    replaceAllWithNewAudios(it)
                    audioListView.adapter.submitList(it)
                }
            }
        }
    }

    fun setChangeAudioOnItemClick() {
        audioListView.adapter.onItemClick { _, index ->
            playerServiceBinder.usePlayer { playAudioByIndex(index) }
        }
    }
}