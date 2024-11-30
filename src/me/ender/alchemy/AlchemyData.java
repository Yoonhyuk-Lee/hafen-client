package me.ender.alchemy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import haven.*;
import haven.res.ui.tt.alch.ingr_buff.BuffAttr;
import haven.res.ui.tt.alch.ingr_heal.HealWound;
import haven.res.ui.tt.alch.ingr_time_less.LessTime;
import haven.res.ui.tt.alch.ingr_time_more.MoreTime;
import haven.res.ui.tt.attrmod.AttrMod;
import haven.rx.Reactor;
import me.ender.Reflect;

import java.util.*;
import java.util.stream.Collectors;

public class AlchemyData {
    private static final String INGREDIENTS_JSON = "ingredients.json";
    private static final String ELIXIRS_JSON = "elixirs.json";
    private static final String ALL_INGREDIENTS_JSON = "all_ingredients.json";
    private static final String COMBOS_JSON = "combos.json";

    public static boolean DBG = Config.get().getprop("ender.debug.alchemy", "off").equals("on");

    public static final String INGREDIENTS_UPDATED = "ALCHEMY:INGREDIENTS:UPDATED";
    public static final String ELIXIRS_UPDATED = "ALCHEMY:ELIXIRS:UPDATED";
    public static final String COMBOS_UPDATED = "ALCHEMY:COMBOS:UPDATED";


    public static final String HERBAL_GRIND = "/herbalgrind";
    public static final String LYE_ABLUTION = "/lyeablution";
    public static final String MINERAL_CALCINATION = "/mineralcalcination";
    public static final String MEASURED_DISTILLATE = "/measureddistillate";
    public static final String FIERY_COMBUSTION = "/fierycombustion";

    private static final Gson GSON = new GsonBuilder()
	.registerTypeAdapter(Effect.class, new Effect.Adapter())
	.create();

    private static boolean initializedIngredients = false;
    private static boolean initializedElixirs = false;
    private static boolean initializedCombos = false;
    private static final Map<String, Ingredient> INGREDIENTS = new HashMap<>();
    private static final Set<Elixir> ELIXIRS = new HashSet<>();
    private static final Set<String> INGREDIENT_LIST = new HashSet<>();
    private static final Map<String, Set<String>> COMBOS = new HashMap<>();


    private static void initIngredients() {
	if(initializedIngredients) {return;}
	initializedIngredients = true;
	loadIngredients(Config.loadJarFile(INGREDIENTS_JSON));
	loadIngredients(Config.loadFSFile(INGREDIENTS_JSON));
    }

    private static void initElixirs() {
	if(initializedElixirs) {return;}
	initializedElixirs = true;
	loadElixirs(Config.loadFile(ELIXIRS_JSON));
    }

    private static void initCombos() {
	if(initializedCombos) {return;}
	initializedCombos = true;
	loadIngredientList(Config.loadJarFile(ALL_INGREDIENTS_JSON));
	loadIngredientList(Config.loadFSFile(ALL_INGREDIENTS_JSON));
	loadCombos(Config.loadFile(COMBOS_JSON));
    }

    private static void loadIngredients(String json) {
	if(json == null) {return;}
	try {
	    Map<String, Ingredient> tmp = GSON.fromJson(json, new TypeToken<Map<String, Ingredient>>() {
	    }.getType());
	    for (Map.Entry<String, Ingredient> entry : tmp.entrySet()) {
		String key = entry.getKey();
		INGREDIENTS.put(key, new Ingredient(entry.getValue().effects, INGREDIENTS.get(key)));
	    }
	} catch (Exception ignore) {}
    }

    private static void loadElixirs(String json) {
	if(json == null) {return;}
	try {
	    Set<Elixir> tmp = GSON.fromJson(json, new TypeToken<Set<Elixir>>() {
	    }.getType());
	    ELIXIRS.addAll(tmp);
	} catch (Exception ignore) {}
    }

    private static void loadIngredientList(String json) {
	if(json == null) {return;}
	try {
	    Set<String> tmp = GSON.fromJson(json, new TypeToken<Set<String>>() {
	    }.getType());
	    INGREDIENT_LIST.addAll(tmp);
	} catch (Exception ignore) {}
    }

    private static void loadCombos(String json) {
	if(json == null) {return;}
	try {
	    Map<String, Set<String>> tmp = GSON.fromJson(json, new TypeToken<Map<String, Set<String>>>() {
	    }.getType());
	    for (Map.Entry<String, Set<String>> entry : tmp.entrySet()) {
		String key = entry.getKey();
		Set<String> combos = COMBOS.computeIfAbsent(key, k -> new HashSet<>());
		combos.addAll(entry.getValue());
	    }
	} catch (Exception ignore) {}
    }

    private static void saveIngredients() {
	Config.saveFile(INGREDIENTS_JSON, GSON.toJson(INGREDIENTS));
    }

    private static void saveElixirs() {
	Config.saveFile(ELIXIRS_JSON, GSON.toJson(ELIXIRS));
    }

    private static void saveIngredientList() {
	Config.saveFile(ALL_INGREDIENTS_JSON, GSON.toJson(INGREDIENT_LIST));
    }

    private static void saveCombos() {
	Config.saveFile(COMBOS_JSON, GSON.toJson(COMBOS));
    }

    public static void categorize(GItem item, boolean storeRecipe) {
	String res = item.resname();
	String name = item.name.get();
	List<ItemInfo> infos = item.info();
	double q = item.itemq.get().single().value;
	double qc = q > 0 ? 1d / Math.sqrt(10 * q) : 1d;

	ItemInfo.Contents contents = ItemInfo.find(ItemInfo.Contents.class, infos);
	if(contents != null) {infos = contents.sub;}

	List<Effect> effects = new LinkedList<>();
	boolean isElixir = false;
	Recipe recipe = null;

	for (ItemInfo info : infos) {
	    if(Reflect.is(info, "Elixir")) {
		isElixir = true;
		//noinspection unchecked
		List<ItemInfo> effs = (List<ItemInfo>) Reflect.getFieldValue(info, "effs");
		for (ItemInfo eff : effs) {
		    tryAddEffect(qc, effects, eff);
		}
		//TODO: detect less/more time effects in elixirs?
	    } else if(info instanceof haven.res.ui.tt.alch.recipe.Recipe) {
		recipe = Recipe.from(res, (haven.res.ui.tt.alch.recipe.Recipe) info);
	    } else {
		tryAddEffect(qc, effects, info);
	    }
	}

	if(isElixir && recipe != null) {
	    //TODO: option to ignore bad-only elixirs?
	    Elixir elixir = new Elixir(recipe, effects);
	    if(storeRecipe) {
		initElixirs();
		ELIXIRS.add(elixir);
		saveElixirs();
		Reactor.event(ELIXIRS_UPDATED);
	    }
	    updateCombos(elixir);
	} else if(!isElixir && !effects.isEmpty() && isNatural(res)) {
	    initIngredients();
	    INGREDIENTS.put(res, new Ingredient(effects, INGREDIENTS.get(res)));
	    Reactor.event(INGREDIENTS_UPDATED);
	    saveIngredients();
	    updateIngredientList(res);
	}

	if(DBG) {
	    long wounds = effects.stream().filter(e -> e.type.equals(Effect.WOUND)).count();
	    boolean dud = wounds == effects.size();
	    String sEffects = effects.stream().map(e -> e.raw).collect(Collectors.joining(", "));

	    System.out.printf("'%s' => elixir:%b, wounds:%d, dud: %b, effects: [%s], recipe:%s %n",
		name, isElixir, wounds, dud, sEffects, recipe);
	}
    }

    private static void updateIngredientList(String ingredient) {
	initCombos();
	if(INGREDIENT_LIST.add(ingredient)) {
	    saveIngredientList();
	    Reactor.event(COMBOS_UPDATED);
	}
    }

    private static void updateCombos(Elixir elixir) {
	List<String> natural = elixir.recipe.ingredients.stream()
	    .map(i -> i.res)
	    .filter(AlchemyData::isNatural)
	    .collect(Collectors.toList());

	if(natural.size() < 2) {return;}
	initCombos();
	boolean listUpdated = false;
	boolean combosUpdated = false;
	for (String ingredient : natural) {
	    if(INGREDIENT_LIST.add(ingredient)) {listUpdated = true;}
	    Set<String> combos = COMBOS.computeIfAbsent(ingredient, k -> new HashSet<>());
	    if(combos.addAll(natural)) {combosUpdated = true;}
	}

	if(listUpdated) {saveIngredientList();}
	if(combosUpdated) {saveCombos();}

	if(listUpdated || combosUpdated) {Reactor.event(COMBOS_UPDATED);}
    }

    public static List<String> ingredients() {
	initIngredients();
	return new ArrayList<>(INGREDIENTS.keySet());
    }

    public static Ingredient ingredient(String res) {
	initIngredients();
	return INGREDIENTS.getOrDefault(res, null);
    }

    public static List<Elixir> elixirs() {
	initElixirs();
	return ELIXIRS.stream().sorted().collect(Collectors.toList());
    }

    public static void rename(Elixir elixir, String name) {
	initElixirs();
	elixir.name(name);
	saveElixirs();
	Reactor.event(ELIXIRS_UPDATED);
    }

    public static void remove(Elixir elixir) {
	initElixirs();
	ELIXIRS.remove(elixir);
	saveElixirs();
	Reactor.event(ELIXIRS_UPDATED);
    }

    public static List<String> allIngredients() {
	initCombos();
	return new ArrayList<>(INGREDIENT_LIST);
    }

    public static Set<String> combos(String target) {
	initCombos();
	return COMBOS.getOrDefault(target, Collections.emptySet());
    }

    public static Tex tex(Collection<Effect> effects) {
	try {
	    List<ItemInfo> tips = Effect.ingredientInfo(effects);
	    if(tips.isEmpty()) {return null;}
	    return new TexI(ItemInfo.longtip(tips));

	} catch (Loading ignore) {}
	return null;
    }

    private static void tryAddEffect(double qc, Collection<Effect> effects, ItemInfo info) {
	if(info instanceof BuffAttr) {
	    effects.add(new Effect(Effect.BUFF, ((BuffAttr) info).res));
	} else if(info instanceof AttrMod) {
	    for (AttrMod.Mod mod : ((AttrMod) info).mods) {
		long a = Math.round(qc * mod.mod);
		effects.add(new Effect(Effect.BUFF, mod.attr.name, Long.toString(a)));
	    }
	} else if(info instanceof HealWound) {
	    effects.add(new Effect(Effect.HEAL, ((HealWound) info).res));
	} else if(Reflect.is(info, "HealWound")) {
	    //this is from elixir, it uses different resource and has value
	    //noinspection unchecked
	    Indir<Resource> res = (Indir<Resource>) Reflect.getFieldValue(info, "res");
	    long a = Math.round(qc * Reflect.getFieldValueInt(info, "a"));
	    effects.add(new Effect(Effect.HEAL, res, Long.toString(a)));
	} else if(Reflect.is(info, "AddWound")) {
	    //this is from elixir
	    //noinspection unchecked
	    Indir<Resource> res = (Indir<Resource>) Reflect.getFieldValue(info, "res");
	    //TODO: try to find base wound magnitude
	    int a = Reflect.getFieldValueInt(info, "a");
	    effects.add(new Effect(Effect.WOUND, res, Long.toString(a)));
	} else if(info instanceof LessTime) {
	    effects.add(new Effect(Effect.TIME, Effect.LESS));
	} else if(info instanceof MoreTime) {
	    effects.add(new Effect(Effect.TIME, Effect.MORE));
	}
    }

    public static boolean isNatural(String res) {
	return !res.contains(HERBAL_GRIND)
	    && !res.contains(LYE_ABLUTION)
	    && !res.contains(MINERAL_CALCINATION)
	    && !res.contains(MEASURED_DISTILLATE)
	    && !res.contains(FIERY_COMBUSTION);
    }

    public static boolean isMineral(String res) {
	return GobIconCategoryList.GobCategory.isRock(res) || GobIconCategoryList.GobCategory.isOre(res);
    }
}