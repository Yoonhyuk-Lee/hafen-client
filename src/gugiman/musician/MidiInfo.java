package gugiman.musician;

import haven.Text;
import haven.res.ui.music.MusicWnd;

import java.io.File;

public class MidiInfo
{
    public final Text 	name;
    public final String stringName;
    public final File 	midiFile;
    
    public MusicPlayer player;

    public MidiInfo(String name, Text.Foundry font, File file)
    {
	this.name 	= font.render(name);
	this.stringName = name;
	this.midiFile 	= file;
	this.player 	= null;
    }
    
    public void Play(MusicWnd wnd)
    {
	Stop();
	player = new MusicPlayer(midiFile, wnd);
	player.startPlaying();
    }
    
    public void Stop()
    {
	if(player != null)
	{
	    player.stopPlaying();
	    player = null;
	}
    }
}
