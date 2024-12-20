package gugiman.musician;

import javax.sound.midi.*;
import java.util.*;

public class Music {

    private final int pitch;

    public TreeMap<Integer, MusicNotes> music; // millisecond, note
    public Boolean[] noteStatus;

    public Music(Track[] tracks, long PPQ, int pitch)
    {
	this.pitch 	= pitch;
	this.music 	= new TreeMap<>();
	this.noteStatus = new Boolean[36];

	final float ms_per_tick = (getMicrosecondsPerQuarterNote(tracks) / PPQ) / 1000.f;

	for(final Track track : tracks)
	    processTrack(track, ms_per_tick);
    }

    protected int tickToMS(final long tick, final float ms_per_tick)
    {
	final int millisecond = (int)(tick * ms_per_tick);
	return millisecond - (millisecond % 32);
    }

    protected int noteToChord(final int note)
    {
	final int chord = note % 12 + 12;
	final int octave = note / 12;

	if (octave < this.pitch)
	    return chord - 12;

	if (octave > this.pitch)
	    return chord + 12;

	return chord;
    }

    protected void processTrack(final Track track, final float ms_per_tick)
    {
	Arrays.fill(noteStatus, false);
	for (int i = 0; i < track.size(); i++)
	{
	    MidiEvent event = track.get(i);
	    MidiMessage message = event.getMessage();

	    final long tick = event.getTick();

	    final int millisecond = tickToMS(tick, ms_per_tick);

	    if(message instanceof ShortMessage)
	    {
		ShortMessage sm = (ShortMessage) message;

		final int note = sm.getData1();
		final int chord = noteToChord(note);

		if(sm.getCommand() == ShortMessage.NOTE_ON && sm.getData2() > 0)
		{
		    if(!this.noteStatus[chord])
		    {
			if(!music.containsKey(millisecond))
			    music.put(millisecond, new MusicNotes());

			music.get(millisecond).Put(chord);
			this.noteStatus[chord] = true;
		    }
		}
		else if(sm.getCommand() == ShortMessage.NOTE_OFF || (sm.getCommand() == ShortMessage.NOTE_ON && sm.getData2() == 0))
		{
		    this.noteStatus[chord] = false;
		}
	    }
	}
    }


    protected float getMicrosecondsPerQuarterNote(Track[] tracks)
    {
	float microsecondsPerQuarterNote = 500000;
	for(Track track : tracks)
	{
	    for (int i = 0; i < track.size(); i++)
	    {
		MidiEvent event = track.get(i);
		MidiMessage message = event.getMessage();
		if (message instanceof MetaMessage)
		{
		    MetaMessage metaMessage = (MetaMessage) message;
		    if (metaMessage.getType() == 0x51)
		    {
			byte[] data = metaMessage.getData();
			microsecondsPerQuarterNote = (data[0] & 0xFF) << 16 | (data[1] & 0xFF) << 8 | (data[2] & 0xFF);
			break;
		    }
		}
	    }
	}
	return  microsecondsPerQuarterNote;
    }
}