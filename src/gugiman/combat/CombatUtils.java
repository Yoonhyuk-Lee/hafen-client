package gugiman.combat;

import haven.*;

import java.util.*;

public class CombatUtils
{
    public static void updateEnemyAgility(Fightview fv, final String lastMoveName, final Double lastMoveCooldown, final Gob me)
    {
	if(lastMoveName == null || lastMoveCooldown == null || fv == null)
	    return;

	if (updateAGIfromUA(fv, lastMoveName, lastMoveCooldown))
	    return;

	updateAGIfromMC(fv, lastMoveName, lastMoveCooldown, me);
    }

    private static boolean updateAGIfromUA(Fightview fv, String lastMoveName, Double lastMoveCooldown)
    {
	if (CombatConfig.unarmedAttackMoves.keySet().stream().noneMatch(lastMoveName::matches))
		return false;

	Double moveDefaultCooldown = CombatConfig.unarmedAttackMoves.get(lastMoveName);
	return updateAgilityFromCooldownNumbers(fv, moveDefaultCooldown, lastMoveCooldown);
    }

    private static boolean updateAGIfromMC(Fightview fv, String lastMoveName, Double lastMoveCooldown, Gob me)
    {
	if (CombatConfig.meleeAttackMoves.keySet().stream().noneMatch(lastMoveName::matches))
	    return false;

	Double moveDefaultCooldown = CombatConfig.meleeAttackMoves.get(lastMoveName);

	if (me == null || !updateAGIFromEquippedWeapons(fv, lastMoveName, lastMoveCooldown, me))
	    return updateAgilityFromCooldownNumbers(fv, moveDefaultCooldown, lastMoveCooldown);

	return false;
    }

    private static Set<String> getEnemyEquipments(final Gob me)
    {
	Set<String> enemyEquipments = new HashSet<>();
	for (GAttrib gobAttrib : me.attr.values())
	{
	    if(gobAttrib instanceof Composite)
	    {
		Composite c = (Composite) gobAttrib;
		for (Composited.ED item : c.comp.cequ)
		{
		    enemyEquipments.add(item.res.res.get().basename());
		}
	    }
	}
	return enemyEquipments;
    }

    private static boolean updateAGIFromEquippedWeapons(Fightview fv, String lastMoveName, Double lastMoveCooldown, Gob enemy)
    {
	Set<String> enemyEquipments = getEnemyEquipments(enemy);

	if (enemyEquipments.contains("b12axe"))
	    return updateAgilityFromWeaponConfig(fv, lastMoveName, lastMoveCooldown, CombatConfig.b12AttackCooldownNumbers);

	if (enemyEquipments.contains("cutblade"))
	    return updateAgilityFromWeaponConfig(fv, lastMoveName, lastMoveCooldown, CombatConfig.cutbladeAttackCooldownNumbers);

	return false;
    }

    private static boolean updateAgilityFromWeaponConfig(Fightview fv, String lastMoveName, Double lastMoveCooldown, Map<String, HashMap<Double, ArrayList<Double>>> weaponConfig) {
	if (weaponConfig.keySet().stream().noneMatch(lastMoveName::matches))
	    return false;

	HashMap<Double, ArrayList<Double>> cooldowns = weaponConfig.get(lastMoveName);
	return updateAgilityFromCooldownMap(fv, lastMoveCooldown, cooldowns);
    }

    private static boolean updateAgilityFromCooldownNumbers(Fightview fv, Double moveDefaultCooldown, Double lastMoveCooldown)
    {
	if (CombatConfig.attackCooldownNumbers.keySet().stream().noneMatch(moveDefaultCooldown::equals))
	    return false;

	HashMap<Double, ArrayList<Double>> cooldowns = CombatConfig.attackCooldownNumbers.get(moveDefaultCooldown);
	return updateAgilityFromCooldownMap(fv, lastMoveCooldown, cooldowns);
    }

    private static boolean updateAgilityFromCooldownMap(Fightview fv, Double lastMoveCooldown, final HashMap<Double, ArrayList<Double>> cooldowns)
    {
	if (cooldowns == null || !cooldowns.containsKey(lastMoveCooldown))
	    return false;

	ArrayList<Double> agilityRange = cooldowns.get(lastMoveCooldown);
	if (agilityRange == null || agilityRange.size() < 2)
	    return false;

	Double minAgi = agilityRange.get(0);
	Double maxAgi = agilityRange.get(1);

	if (minAgi == null || maxAgi == null)
	    return false;

	fv.current.minAgi = Math.max(fv.current.minAgi, minAgi);
	fv.current.maxAgi = Math.min(fv.current.maxAgi, maxAgi);

	return true;
    }
}