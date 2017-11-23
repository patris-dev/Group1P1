package LazyTown.source.sound;

/**
 * This class is used for the sound properties used in SoundEngine for volume and muting.
 * The class should not be used anywhere else but the SoundEngine.
 * Since it is an object, we can bind it's values to another SoundProperties object; This is useful while trying to mute
 * all tracks at once, or to change the volume of all tracks.
 */
class SoundProperties {
    private boolean isMuted;
    private double volume;

    SoundProperties(boolean isMuted, double volume) {
        this.isMuted = isMuted;
        this.volume = volume;
    }

    boolean isMuted() {
        return isMuted;
    }

    void setMuted(boolean muted) {
        isMuted = muted;
    }

    double getVolume() {
        return volume;
    }

    void setVolume(double volume) {
        this.volume = volume;
    }
}
