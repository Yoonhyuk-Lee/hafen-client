package gugiman.musician;

import haven.res.ui.music.MusicWnd;

import java.util.HashSet;

public class MusicNotes
{
    HashSet<Integer> notes;

    public MusicNotes()
    {
	this.notes = new HashSet<>();
    }
    
    public void Put(int chord)
    {
	notes.add(chord);
    }
    
    public void playNotes(MusicWnd musicWnd)
    {
	notes.forEach( k -> musicWnd.PlayAndStopNote(k) );
    }
}
