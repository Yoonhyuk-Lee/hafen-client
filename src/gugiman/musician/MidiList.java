package gugiman.musician;

import haven.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MidiList extends Listbox<MidiInfo> {
    private static final String MIDI_FILE_EXTENSION = ".mid";
    private static final Text.Foundry font = new Text.Foundry(Text.sans, 15).aa(true);
    private final List<MidiInfo> items = new ArrayList<>();
    
    private final Map<String, MidiInfo> map = new HashMap<>();
    
    public MidiList(int w, int h) {
	super(w, h, font.height() + 2);
    }
    
    public void scanMidiFiles() {
	scanAndAddMidiFiles(System.getProperty("user.dir"));
    }
    
    private void scanAndAddMidiFiles(String directory) {
	File dir = new File(directory);
	if (dir.isDirectory()) {
	    File[] files = dir.listFiles();
	    if (files != null) {
		for (File file : files) {
		    if (file.isDirectory()) {
			scanAndAddMidiFiles(file.getAbsolutePath());
		    } else if (file.getName().toLowerCase().endsWith(MIDI_FILE_EXTENSION)) {
			add(file.getName(), file);
		    }
		}
	    }
	}
    }
    
    private void add(String name, File file) {
	MidiInfo item = new MidiInfo(name, font, file);
	map.put(name, item);
	items.add(item);
    }
    
    public void remove(String name) {
	MidiInfo item = map.remove(name);
	if(item != null)
	    items.remove(item);
    }
    
    public void clear() {
	map.clear();
	items.clear();
    }
    
    @Override
    protected MidiInfo listitem(int i) {
	return items.get(i);
    }
    
    @Override
    protected int listitems() {
	return items.size();
    }
    
    @Override
    protected void drawitem(GOut g, MidiInfo item, int i) {
	g.aimage(item.name.tex(), new Coord(itemh + 5, itemh / 2), 0, 0.5);
    }
}
