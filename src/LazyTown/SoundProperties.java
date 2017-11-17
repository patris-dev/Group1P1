package LazyTown;

public class SoundProperties {
    private boolean isMuted;
    private double volume;

    public SoundProperties(boolean isMuted, double volume) {
        this.isMuted = isMuted;
        this.volume = volume;
    }

    public boolean isMuted() {
        return isMuted;
    }

    public void setMuted(boolean muted) {
        isMuted = muted;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }
}
