package gugiman.automation;

import auto.Bot;
import haven.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static haven.OCache.posres;

public class AutoCombatDistance
{
    private static final Map<String, Double> animalDistances = new HashMap<>();
    private static final Map<String, Double> vehicleDistance = new HashMap<>();

    static
    {
	animalDistances.put("gfx/kritter/adder/adder", 17.1);
	animalDistances.put("gfx/kritter/ant/ant", 15.2);
	animalDistances.put("gfx/kritter/aurochs/aurochs", 27.0);
	animalDistances.put("gfx/kritter/badger/badger", 19.9);
	animalDistances.put("gfx/kritter/bear/bear", 24.7);
	animalDistances.put("gfx/kritter/boar/boar", 25.1);
	animalDistances.put("gfx/kritter/caveangler/caveangler", 27.2);
	animalDistances.put("gfx/kritter/cavelouse/cavelouse", 22.0);
	animalDistances.put("gfx/kritter/fox/fox", 18.1);
	animalDistances.put("gfx/kritter/horse/horse", 23.0);
	animalDistances.put("gfx/kritter/lynx/lynx", 20.0);
	animalDistances.put("gfx/kritter/mammoth/mammoth", 30.3);
	animalDistances.put("gfx/kritter/moose/moose", 25.0);
	animalDistances.put("gfx/kritter/orca/orca", 49.25);
	animalDistances.put("gfx/kritter/reddeer/reddeer", 25.0);
	animalDistances.put("gfx/kritter/roedeer/roedeer", 22.0);
	animalDistances.put("gfx/kritter/spermwhale/spermwhale", 112.2);
	animalDistances.put("gfx/kritter/goat/wildgoat", 18.9);
	animalDistances.put("gfx/kritter/wolf/wolf", 25.0);
	animalDistances.put("gfx/kritter/wolverine/wolverine", 21.0);
	animalDistances.put("gfx/borka/body", 55.0);

	vehicleDistance.put("gfx/terobjs/vehicle/rowboat", 13.3);
	vehicleDistance.put("gfx/terobjs/vehicle/dugout", 7.4);
	vehicleDistance.put("gfx/terobjs/vehicle/snekkja", 29.35);
	vehicleDistance.put("gfx/terobjs/vehicle/knarr", 54.5);
	vehicleDistance.put("gfx/kritter/horse/stallion", 5.4);
	vehicleDistance.put("gfx/kritter/horse/mare", 5.4);
    }

    public static void autoDistance(GameUI gui)
    {
	Bot.execute((t, b) -> tryToAutoDistance(gui))
	    .start(gui.ui, true);
    }

    private static void tryToAutoDistance(GameUI gui)
    {
	if (gui != null && gui.map != null && gui.map.player() != null && gui.fv.current != null)
	{
	    Double value = null;
	    double addedValue = 0.0;

	    synchronized (gui.ui.sess.glob.oc)
	    {
		for (Gob gob : gui.ui.sess.glob.oc)
		{
		    if(gob.getres() == null)
			continue;

		    final String gobName = gob.getres().name;
		    if (gob.rc.dist(gui.map.player().rc) < 11)
		    {
			if(vehicleDistance.containsKey(gobName))
			{
			    gui.msg(String.format("On the vehicle :: %s", gobName), GameUI.MsgType.INFO);
			    addedValue = vehicleDistance.getOrDefault(gobName, 0.0);
			    continue;
			}
		    }

		    if(gob.id == gui.fv.current.gobid)
		    {
			value = animalDistances.get(gobName);
		    }
		}
	    }

	    if(value == null)
	    {
		gui.error("Can't find the enemy!");
		return;
	    }

	    if(value > 0)
	    {
		moveToDistance(gui, value + addedValue);
	    }
	}
    }

    private static void moveToDistance(GameUI gui, double distance)
    {
	gui.msg(String.format("Adjust distance :: %f", distance), GameUI.MsgType.INFO);
	try
	{
	    final Gob enemy = getEnemy(gui);
	    if(enemy == null)
	    {
		gui.error("No visible target.");
		return;
	    }

	    final Gob player = gui.map.player();
	    if (player == null)
	    {
		gui.error("No visible player.");
		return;
	    }

	    final double angle = enemy.rc.angle(gui.map.player().rc);
	    gui.map.wdgmsg("click", Coord.z, getNewCoord(enemy, distance, angle).floor(posres), 1, 0);
	}
	catch (NumberFormatException e)
	{
	    gui.error("Wrong distance format. Use ##.###");
	}
    }

    private static Coord2d getNewCoord(Gob enemy, double distance, double angle)
    {
	return new Coord2d(enemy.rc.x + distance * Math.cos(angle), enemy.rc.y + distance * Math.sin(angle));
    }

    private static Gob getEnemy(GameUI gui)
    {
	if (gui.fv.current == null)
	{
	    gui.error("Not in combat!");
	    return null;
	}

	final long id = gui.fv.current.gobid;
	synchronized (gui.map.glob.oc)
	{
	    for (final Gob gob : gui.map.glob.oc)
		if (gob.id == id)
		    return gob;
	}

	return null;
    }

    private static double getDistance(final GameUI gui, long gobId)
    {
	synchronized (gui.map.glob.oc)
	{
	    for (Gob gob : gui.map.glob.oc)
	    {
		if (gob.id == gobId && gui.map.player() != null)
		{
		    return gob.rc.dist(gui.map.player().rc);
		}
	    }
	}
	return -1;
    }
}