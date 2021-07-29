package com.cappielloantonio.play.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cappielloantonio.play.interfaces.MediaCallback;
import com.cappielloantonio.play.model.Album;
import com.cappielloantonio.play.model.Artist;
import com.cappielloantonio.play.model.Song;
import com.cappielloantonio.play.repository.AlbumRepository;
import com.cappielloantonio.play.repository.ArtistRepository;
import com.cappielloantonio.play.repository.SongRepository;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    private static final String TAG = "HomeViewModel";
    private SongRepository songRepository;
    private AlbumRepository albumRepository;
    private ArtistRepository artistRepository;

    private LiveData<List<Song>> downloadedSongSample;
    private List<Integer> years;

    private MutableLiveData<List<Song>> dicoverSongSample;
    private LiveData<List<Album>> mostPlayedAlbumSample;
    private LiveData<List<Album>> recentlyAddedAlbumSample;
    private LiveData<List<Album>> recentlyPlayedAlbumSample;

    private LiveData<List<Song>> starredTracks;
    private LiveData<List<Album>> starredAlbums;
    private LiveData<List<Artist>> starredArtists;

    public HomeViewModel(@NonNull Application application) {
        super(application);

        songRepository = new SongRepository(application);
        albumRepository = new AlbumRepository(application);
        artistRepository = new ArtistRepository(application);

        // favoritesSongSample = songRepository.getListLiveFavoritesSampleSong(20);
        // downloadedSongSample = songRepository.getListLiveDownloadedSampleSong(20);
        // years = songRepository.getYearList();

        setDicoverSongSample();
        dicoverSongSample = new MutableLiveData<>();
        downloadedSongSample = new MutableLiveData<>();
        years = new ArrayList<>();

        mostPlayedAlbumSample = albumRepository.getAlbums("frequent", 20);
        recentlyAddedAlbumSample = albumRepository.getAlbums("newest", 20);
        recentlyPlayedAlbumSample = albumRepository.getAlbums("recent", 20);

        starredTracks = songRepository.getStarredSongs();
        starredAlbums = albumRepository.getStarredAlbums();
        starredArtists = artistRepository.getStarredArtists();
    }

    public SongRepository getSongRepository() {
        return songRepository;
    }

    public LiveData<List<Song>> getDiscoverSongSample() {
        return dicoverSongSample;
    }

    public List<Integer> getYearList() {
        return years;
    }

    public LiveData<List<Song>> getStarredTracks() {
        return starredTracks;
    }

    public LiveData<List<Album>> getStarredAlbums() {
        return starredAlbums;
    }

    public LiveData<List<Artist>> getStarredArtists() {
        return starredArtists;
    }

    public LiveData<List<Song>> getDownloaded() {
        return downloadedSongSample;
    }

    public LiveData<List<Album>> getMostPlayedAlbums()  {
        return mostPlayedAlbumSample;
    }

    public LiveData<List<Album>> getMostRecentlyAddedAlbums()  {
        return recentlyAddedAlbumSample;
    }

    public LiveData<List<Album>> getRecentlyPlayedAlbumList()  {
        return recentlyPlayedAlbumSample;
    }

    private void setDicoverSongSample() {
        songRepository.getRandomSample(10, new MediaCallback() {
            @Override
            public void onError(Exception exception) {

            }

            @Override
            public void onLoadMedia(List<?> media) {
                dicoverSongSample.setValue((List<Song>) media);
            }
        });
    }
}
