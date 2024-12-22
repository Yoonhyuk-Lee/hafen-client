/* Preprocessed source code */
package haven.res.ui.music;

import haven.*;

import java.awt.Color;
import java.util.*;
import java.awt.event.KeyEvent;

import gugiman.musician.MidiInfo;
import gugiman.musician.MidiList;

/* >wdg: MusicWnd */
@haven.FromResource(name = "ui/music", version = 34)
public class MusicWnd extends Window
{
    public final double start;
    public static int pitchValue = 6;

    protected MidiList midiList;
    protected Button play, stop;
    protected HSlider pitch;

    public MusicWnd() {
	super(new Coord(320, 240), "MIDI Player", true);

	this.start = System.currentTimeMillis() / 1000.;

	int width = 320;

	Coord c = new Coord(0, 20);

	pitch = new HSlider(width, 3, 8, 6) {
	    public void changed() {
		MusicWnd.pitchValue = val;
	    }
	};

	add(pitch, c);

	midiList = new MidiList(width, 6);
	midiList.scanMidiFiles();


	add(midiList, c.add(0, 20));

	play = new Button(UI.scale(80), "Play");
	add(play, c.add(0, 180));
	
	stop = new Button(UI.scale(80), "Stop");
	add(stop, c.add(240, 180));
    }

    @Override
    public void wdgmsg(Widget sender, String msg, Object... args)
    {
	if(sender == play)
	{
	    MidiInfo item = midiList.sel;
	    if(item != null)
	    {
		ui.message(String.format("Now Playing... [%s]", item.stringName), GameUI.MsgType.INFO);
		item.Play(this);
	    }
	}
	else if(sender == stop)
	{
	    MidiInfo item = midiList.sel;
	    if(item != null)
	    {
		ui.message("Stopped", GameUI.MsgType.INFO);
		item.Stop();
	    }
	}

	super.wdgmsg(sender, msg, args);
    }

    public static Widget mkwidget(UI ui, Object[] args)
    {
	return(new MusicWnd());
    }

//    protected void added()
//    {
//	super.added();
//	// ui.grabkeys(this);
//    }

    public void PlayAndStopNote(final int key)
    {
	final double nowTime = System.currentTimeMillis() / 1000. - this.start;
	wdgmsg("play", new Object[]{key, nowTime});
	wdgmsg("stop", new Object[]{key, nowTime});
    }
}
