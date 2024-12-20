package gugiman.musician;

import haven.GameUI;
import haven.res.ui.music.MusicWnd;

import javax.sound.midi.*;
import java.io.File;
import java.util.*;
import java.util.concurrent.*;


public class MusicPlayer extends Thread
{
    private final MusicWnd musicWnd;
    private final ExecutorService executor;
    private Music music = null;

    private volatile boolean running = true;
    
    public MusicPlayer(File midiFile, MusicWnd wnd)
    {
	this.musicWnd = wnd;
	this.executor = Executors.newSingleThreadExecutor();
	try
	{
	    Sequence sequence = MidiSystem.getSequence(midiFile);
	    final float divisionType = sequence.getDivisionType();
	    if (divisionType != Sequence.PPQ)
		throw new Exception("Unsupported division type: " + divisionType);

	    this.music = this.initializeMusic(sequence);
	}
	catch (Exception e)
	{
	    musicWnd.ui.message(e.getMessage(), GameUI.MsgType.ERROR);
	}
    }
    
    @Override
    public void run()
    {
	try
	{
	    this.playMusic(music);
	}
	catch (Exception e)
	{
	    musicWnd.ui.message(e.getMessage(), GameUI.MsgType.ERROR);
	}
    }

    private Music initializeMusic(Sequence sequence)
    {
	return new Music(sequence.getTracks(), sequence.getResolution(), MusicWnd.pitchValue);
    }

    private void playMusic(Music music) throws InterruptedException
    {
	final long startTime = System.currentTimeMillis();

	for (final Map.Entry<Integer, MusicNotes> entry : music.music.entrySet())
	{
	    if (!running)
		return;

	    final MusicNotes notes = entry.getValue();
	    final long targetTime = entry.getKey() + startTime;

	    preciseSleep(targetTime);

	    notes.playNotes(musicWnd);
	}
    }

    // Method to stop MIDI player thread
    public void startPlaying() {
	executor.submit(this::run);
    }

    public void stopPlaying() {
	running = false;
	executor.shutdownNow();
    }

    private void preciseSleep(final long targetTime) throws InterruptedException
    {
	long currentTime, remaining;
	while (true)
	{
	    currentTime = System.currentTimeMillis();
	    remaining = targetTime - currentTime;
	    if (remaining <= 0)
		break;

	    if (remaining > 2)
	    {
		Thread.sleep(remaining - 1);
	    }
	    else
	    {
		Thread.yield();
	    }
	}
    }
}
