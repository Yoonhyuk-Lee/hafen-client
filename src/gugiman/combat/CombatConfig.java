package gugiman.combat;

import java.awt.*;
import java.util.*;

public class CombatConfig {

    public static final Map<String, Color> improvedOpeningsImageColor = new HashMap<String, Color>(4) {{
	put("paginae/atk/offbalance", new Color(0, 128, 3));
	put("paginae/atk/reeling", new Color(217, 177, 20));
	put("paginae/atk/dizzy", new Color(39, 82, 191));
	put("paginae/atk/cornered", new Color(192, 28, 28));
    }};

    public static final HashSet<String> maneuvers =  new HashSet<>(Arrays.asList(
	"paginae/atk/toarms", "paginae/atk/shield", "paginae/atk/parry",
	"paginae/atk/oakstance", "paginae/atk/dorg", "paginae/atk/chinup",
	"paginae/atk/bloodlust", "paginae/atk/combmed"));

    public static final HashMap<String, Long> nonAttackDefences = new HashMap<String, Long>()
    {{
	put("paginae/atk/regain", 2100L);
	put("paginae/atk/zigzag", 3000L);
	put("paginae/atk/yieldground", 1800L);
	put("paginae/atk/sidestep", 1500L);
	put("paginae/atk/qdodge", 1500L);
	put("paginae/atk/jump", 1500L);
	put("paginae/atk/artevade", 2400L);
	put("paginae/atk/dash", 3200L);
    }};


    public static final HashMap<String, Double> meleeAttackMoves = new HashMap<String, Double>()
    {{
	put("paginae/atk/cleave", 80D);
	put("paginae/atk/chop", 40D);
	put("paginae/atk/barrage", 20D);
	put("paginae/atk/ravenbite", 40D);
	put("paginae/atk/sideswipe", 25D);
	put("paginae/atk/sting", 50D);
    }};

    public static final HashMap<String, Double> unarmedAttackMoves = new HashMap<String, Double>()
    {{
	put("paginae/atk/flex", 30D);
	put("paginae/atk/gojug", 40D); // ND: Jugular tooltip says cooldown is 45, but it's actually 40. SMH.
	put("paginae/atk/haymaker", 50D);
	put("paginae/atk/kick", 45D);
	put("paginae/atk/knockteeth", 35D);
	put("paginae/atk/lefthook", 40D);
	put("paginae/atk/lowblow", 50D);
	put("paginae/atk/oppknock", 45D);
	put("paginae/atk/pow", 30D);
//		put("paginae/atk/punchboth", 40D); // ND: Punch 'em Both attacks 2 targets.
	put("paginae/atk/ripapart", 60D);
	put("paginae/atk/stealthunder", 40D);
	put("paginae/atk/takedown", 50D);
	put("paginae/atk/uppercut", 30D);
    }};

    public static final HashMap<Double, HashMap<Double, ArrayList<Double>>> attackCooldownNumbers = new HashMap<Double, HashMap<Double, ArrayList<Double>>>(){{
	put(20D, new HashMap<Double, ArrayList<Double>>(){{
	    put (18D, new ArrayList<Double>(){{add(0D);add(0.579D);}});
	    put (19D, new ArrayList<Double>(){{add(0.580D);add(0.837D);}});
	    put (20D, new ArrayList<Double>(){{add(0.838D);add(1.188D);}});
	    put (21D, new ArrayList<Double>(){{add(1.189D);add(1.659D);}});
	    put (22D, new ArrayList<Double>(){{add(1.660D);add(2D);}});
	}});
	put(25D, new HashMap<Double, ArrayList<Double>>(){{
	    put (23D, new ArrayList<Double>(){{add(0D);add(0.648D);}});
	    put (24D, new ArrayList<Double>(){{add(0.649D);add(0.868D);}});
	    put (25D, new ArrayList<Double>(){{add(0.869D);add(1.148D);}});
	    put (26D, new ArrayList<Double>(){{add(1.149D);add(1.503D);}});
	    put (27D, new ArrayList<Double>(){{add(1.504D);add(1.948D);}});
	    put (28D, new ArrayList<Double>(){{add(1.949D);add(2D);}});
	}});
	put(30D, new HashMap<Double, ArrayList<Double>>(){{
	    put (27D, new ArrayList<Double>(){{add(0D);add(0.543D);}});
	    put (28D, new ArrayList<Double>(){{add(0.544D);add(0.698D);}});
	    put (29D, new ArrayList<Double>(){{add(0.699D);add(0.889D);}});
	    put (30D, new ArrayList<Double>(){{add(0.890D);add(1.122D);}});
	    put (31D, new ArrayList<Double>(){{add(1.123D);add(1.407D);}});
	    put (32D, new ArrayList<Double>(){{add(1.408D);add(1.751D);}});
	    put (33D, new ArrayList<Double>(){{add(1.752D);add(2D);}});
	}});
	put(35D, new HashMap<Double, ArrayList<Double>>(){{
	    put (32D, new ArrayList<Double>(){{add(0D);add(0.595D);}});
	    put (33D, new ArrayList<Double>(){{add(0.596D);add(0.735D);}});
	    put (34D, new ArrayList<Double>(){{add(0.736D);add(0.904D);}});
	    put (35D, new ArrayList<Double>(){{add(0.905D);add(1.104D);}});
	    put (36D, new ArrayList<Double>(){{add(1.105D);add(1.341D);}});
	    put (37D, new ArrayList<Double>(){{add(1.342D);add(1.620D);}});
	    put (38D, new ArrayList<Double>(){{add(1.621D);add(1.948D);}});
	    put (39D, new ArrayList<Double>(){{add(1.949D);add(2D);}});
	}});
	put(40D, new HashMap<Double, ArrayList<Double>>(){{
	    put (36D, new ArrayList<Double>(){{add(0D);add(0.526D);}});
	    put (37D, new ArrayList<Double>(){{add(0.527D);add(0.636D);}});
	    put (38D, new ArrayList<Double>(){{add(0.637D);add(0.765D);}});
	    put (39D, new ArrayList<Double>(){{add(0.766D);add(0.915D);}});
	    put (40D, new ArrayList<Double>(){{add(0.916D);add(1.090D);}});
	    put (41D, new ArrayList<Double>(){{add(1.091D);add(1.293D);}});
	    put (42D, new ArrayList<Double>(){{add(1.294D);add(1.528D);}});
	    put (43D, new ArrayList<Double>(){{add(1.529D);add(1.798D);}});
	    put (44D, new ArrayList<Double>(){{add(1.799D);add(2D);}});
	}});
	put(45D, new HashMap<Double, ArrayList<Double>>(){{
	    put (41D, new ArrayList<Double>(){{add(0D);add(0.567D);}});
	    put (42D, new ArrayList<Double>(){{add(0.568D);add(0.670D);}});
	    put (43D, new ArrayList<Double>(){{add(0.671D);add(0.788D);}});
	    put (44D, new ArrayList<Double>(){{add(0.789D);add(0.924D);}});
	    put (45D, new ArrayList<Double>(){{add(0.925D);add(1.080D);}});
	    put (46D, new ArrayList<Double>(){{add(1.081D);add(1.258D);}});
	    put (47D, new ArrayList<Double>(){{add(1.259D);add(1.460D);}});
	    put (48D, new ArrayList<Double>(){{add(1.461D);add(1.689D);}});
	    put (49D, new ArrayList<Double>(){{add(1.690D);add(1.948D);}});
	    put (50D, new ArrayList<Double>(){{add(1.949D);add(2D);}});

	}});
	put(50D, new HashMap<Double, ArrayList<Double>>(){{
	    put (45D, new ArrayList<Double>(){{add(0D);add(0.516D);}});
	    put (46D, new ArrayList<Double>(){{add(0.517D);add(0.601D);}});
	    put (47D, new ArrayList<Double>(){{add(0.602D);add(0.698D);}});
	    put (48D, new ArrayList<Double>(){{add(0.699D);add(0.807D);}});
	    put (49D, new ArrayList<Double>(){{add(0.808D);add(0.932D);}});
	    put (50D, new ArrayList<Double>(){{add(0.933D);add(1.072D);}});
	    put (51D, new ArrayList<Double>(){{add(1.073D);add(1.229D);}});
	    put (52D, new ArrayList<Double>(){{add(1.230D);add(1.407D);}});
	    put (53D, new ArrayList<Double>(){{add(1.408D);add(1.605D);}});
	    put (54D, new ArrayList<Double>(){{add(1.606D);add(1.828D);}});
	    put (55D, new ArrayList<Double>(){{add(1.829D);add(2D);}});
	}});
	put(60D, new HashMap<Double, ArrayList<Double>>(){{
	    put (54D, new ArrayList<Double>(){{add(0D);add(0.510D);}});
	    put (55D, new ArrayList<Double>(){{add(0.511D);add(0.579D);}});
	    put (56D, new ArrayList<Double>(){{add(0.580D);add(0.656D);}});
	    put (57D, new ArrayList<Double>(){{add(0.657D);add(0.742D);}});
	    put (58D, new ArrayList<Double>(){{add(0.743D);add(0.837D);}});
	    put (59D, new ArrayList<Double>(){{add(0.838D);add(0.943D);}});
	    put (60D, new ArrayList<Double>(){{add(0.944D);add(1.059D);}});
	    put (61D, new ArrayList<Double>(){{add(1.060D);add(1.188D);}});
	    put (62D, new ArrayList<Double>(){{add(1.189D);add(1.330D);}});
	    put (63D, new ArrayList<Double>(){{add(1.331D);add(1.487D);}});
	    put (64D, new ArrayList<Double>(){{add(1.488D);add(1.659D);}});
	    put (65D, new ArrayList<Double>(){{add(1.660D);add(1.847D);}});
	    put (66D, new ArrayList<Double>(){{add(1.848D);add(2D);}});
	}});
	put(80D, new HashMap<Double, ArrayList<Double>>(){{
	    put (72D, new ArrayList<Double>(){{add(0D);add(0.502D);}});
	    put (73D, new ArrayList<Double>(){{add(0.503D);add(0.552D);}});
	    put (74D, new ArrayList<Double>(){{add(0.553D);add(0.607D);}});
	    put (75D, new ArrayList<Double>(){{add(0.608D);add(0.666D);}});
	    put (76D, new ArrayList<Double>(){{add(0.667D);add(0.731D);}});
	    put (77D, new ArrayList<Double>(){{add(0.732D);add(0.800D);}});
	    put (78D, new ArrayList<Double>(){{add(0.801D);add(0.875D);}});
	    put (79D, new ArrayList<Double>(){{add(0.876D);add(0.957D);}});
	    put (80D, new ArrayList<Double>(){{add(0.958D);add(1.044D);}});
	    put (81D, new ArrayList<Double>(){{add(1.045D);add(1.138D);}});
	    put (82D, new ArrayList<Double>(){{add(1.139D);add(1.240D);}});
	    put (83D, new ArrayList<Double>(){{add(1.241D);add(1.349D);}});
	    put (84D, new ArrayList<Double>(){{add(1.350D);add(1.466D);}});
	    put (85D, new ArrayList<Double>(){{add(1.467D);add(1.592D);}});
	    put (86D, new ArrayList<Double>(){{add(1.593D);add(1.727D);}});
	    put (87D, new ArrayList<Double>(){{add(1.728D);add(1.872D);}});
	    put (88D, new ArrayList<Double>(){{add(1.873D);add(2D);}});
	}});
    }};

    public static final HashMap<String, HashMap<Double, ArrayList<Double>>> b12AttackCooldownNumbers = new HashMap<String, HashMap<Double, ArrayList<Double>>>(){{
	put("paginae/atk/barrage", new HashMap<Double, ArrayList<Double>>(){{
	    put (23D, new ArrayList<Double>(){{add(0D);add(0.648D);}});
	    put (24D, new ArrayList<Double>(){{add(0.649D);add(0.868D);}});
	    put (25D, new ArrayList<Double>(){{add(0.869D);add(1.148D);}});
	    put (26D, new ArrayList<Double>(){{add(1.149D);add(1.503D);}});
	    put (27D, new ArrayList<Double>(){{add(1.504D);add(1.948D);}});
	    put (28D, new ArrayList<Double>(){{add(1.949D);add(2D);}});
	}});
	put("paginae/atk/sideswipe", new HashMap<Double, ArrayList<Double>>(){{
	    put (28D, new ArrayList<Double>(){{add(0D);add(0.524D);}});
	    put (29D, new ArrayList<Double>(){{add(0.525D);add(0.668D);}});
	    put (30D, new ArrayList<Double>(){{add(0.669D);add(0.843D);}});
	    put (31D, new ArrayList<Double>(){{add(0.844D);add(1.057D);}});
	    put (32D, new ArrayList<Double>(){{add(1.058D);add(1.315D);}});
	    put (33D, new ArrayList<Double>(){{add(1.316D);add(1.626D);}});
	    put (34D, new ArrayList<Double>(){{add(1.627D);add(1.998D);}});
	    put (35D, new ArrayList<Double>(){{add(1.999D);add(2D);}});
	}});
	put("paginae/atk/chop", new HashMap<Double, ArrayList<Double>>(){{
	    put (45D, new ArrayList<Double>(){{add(0D);add(0.516D);}});
	    put (46D, new ArrayList<Double>(){{add(0.517D);add(0.601D);}});
	    put (47D, new ArrayList<Double>(){{add(0.602D);add(0.698D);}});
	    put (48D, new ArrayList<Double>(){{add(0.699D);add(0.807D);}});
	    put (49D, new ArrayList<Double>(){{add(0.808D);add(0.932D);}});
	    put (50D, new ArrayList<Double>(){{add(0.933D);add(1.072D);}});
	    put (51D, new ArrayList<Double>(){{add(1.073D);add(1.229D);}});
	    put (52D, new ArrayList<Double>(){{add(1.230D);add(1.407D);}});
	    put (53D, new ArrayList<Double>(){{add(1.408D);add(1.605D);}});
	    put (54D, new ArrayList<Double>(){{add(1.606D);add(1.828D);}});
	    put (55D, new ArrayList<Double>(){{add(1.829D);add(2D);}});
	}});
	put("paginae/atk/ravenbite", new HashMap<Double, ArrayList<Double>>(){{
	    put (45D, new ArrayList<Double>(){{add(0D);add(0.516D);}});
	    put (46D, new ArrayList<Double>(){{add(0.517D);add(0.601D);}});
	    put (47D, new ArrayList<Double>(){{add(0.602D);add(0.698D);}});
	    put (48D, new ArrayList<Double>(){{add(0.699D);add(0.807D);}});
	    put (49D, new ArrayList<Double>(){{add(0.808D);add(0.932D);}});
	    put (50D, new ArrayList<Double>(){{add(0.933D);add(1.072D);}});
	    put (51D, new ArrayList<Double>(){{add(1.073D);add(1.229D);}});
	    put (52D, new ArrayList<Double>(){{add(1.230D);add(1.407D);}});
	    put (53D, new ArrayList<Double>(){{add(1.408D);add(1.605D);}});
	    put (54D, new ArrayList<Double>(){{add(1.606D);add(1.828D);}});
	    put (55D, new ArrayList<Double>(){{add(1.829D);add(2D);}});
	}});
	put("paginae/atk/cleave", new HashMap<Double, ArrayList<Double>>(){{
	    put (91D, new ArrayList<Double>(){{add(0D);add(0.536D);}});
	    put (92D, new ArrayList<Double>(){{add(0.537D);add(0.579D);}});
	    put (93D, new ArrayList<Double>(){{add(0.580D);add(0.624D);}});
	    put (94D, new ArrayList<Double>(){{add(0.625D);add(0.673D);}});
	    put (95D, new ArrayList<Double>(){{add(0.674D);add(0.724D);}});
	    put (96D, new ArrayList<Double>(){{add(0.725D);add(0.779D);}});
	    put (97D, new ArrayList<Double>(){{add(0.780D);add(0.837D);}});
	    put (98D, new ArrayList<Double>(){{add(0.838D);add(0.899D);}});
	    put (99D, new ArrayList<Double>(){{add(0.900D);add(0.965D);}});
	    put (100D, new ArrayList<Double>(){{add(0.966D);add(1.035D);}});
	    put (101D, new ArrayList<Double>(){{add(1.036D);add(1.109D);}});
	    put (102D, new ArrayList<Double>(){{add(1.110D);add(1.188D);}});
	    put (103D, new ArrayList<Double>(){{add(1.189D);add(1.272D);}});
	    put (104D, new ArrayList<Double>(){{add(1.273D);add(1.360D);}});
	    put (105D, new ArrayList<Double>(){{add(1.361D);add(1.454D);}});
	    put (106D, new ArrayList<Double>(){{add(1.455D);add(1.553D);}});
	    put (107D, new ArrayList<Double>(){{add(1.554D);add(1.659D);}});
	    put (108D, new ArrayList<Double>(){{add(1.660D);add(1.770D);}});
	    put (109D, new ArrayList<Double>(){{add(1.771D);add(1.887D);}});
	    put (110D, new ArrayList<Double>(){{add(1.888D);add(2D);}});
	}});
    }};

    public static final HashMap<String, HashMap<Double, ArrayList<Double>>> cutbladeAttackCooldownNumbers = new HashMap<String, HashMap<Double, ArrayList<Double>>>(){{
	put("paginae/atk/barrage", new HashMap<Double, ArrayList<Double>>(){{
	    put (22D, new ArrayList<Double>(){{add(0D);add(0.636D);}});
	    put (23D, new ArrayList<Double>(){{add(0.637D);add(0.862D);}});
	    put (24D, new ArrayList<Double>(){{add(0.863D);add(1.155D);}});
	    put (25D, new ArrayList<Double>(){{add(1.156D);add(1.528D);}});
	    put (26D, new ArrayList<Double>(){{add(1.529D);add(2D);}});

	}});
	put("paginae/atk/sideswipe", new HashMap<Double, ArrayList<Double>>(){{
	    put (27D, new ArrayList<Double>(){{add(0D);add(0.543D);}});
	    put (28D, new ArrayList<Double>(){{add(0.544D);add(0.698D);}});
	    put (29D, new ArrayList<Double>(){{add(0.699D);add(0.889D);}});
	    put (30D, new ArrayList<Double>(){{add(0.890D);add(1.122D);}});
	    put (31D, new ArrayList<Double>(){{add(1.123D);add(1.407D);}});
	    put (32D, new ArrayList<Double>(){{add(1.408D);add(1.751D);}});
	    put (33D, new ArrayList<Double>(){{add(1.752D);add(2D);}});
	}});
	put("paginae/atk/chop", new HashMap<Double, ArrayList<Double>>(){{
	    put (43D, new ArrayList<Double>(){{add(0D);add(0.502D);}});
	    put (44D, new ArrayList<Double>(){{add(0.503D);add(0.588D);}});
	    put (45D, new ArrayList<Double>(){{add(0.589D);add(0.687D);}});
	    put (46D, new ArrayList<Double>(){{add(0.688D);add(0.800D);}});
	    put (47D, new ArrayList<Double>(){{add(0.801D);add(0.929D);}});
	    put (48D, new ArrayList<Double>(){{add(0.930D);add(1.075D);}});
	    put (49D, new ArrayList<Double>(){{add(1.076D);add(1.240D);}});
	    put (50D, new ArrayList<Double>(){{add(1.241D);add(1.426D);}});
	    put (51D, new ArrayList<Double>(){{add(1.427D);add(1.636D);}});
	    put (52D, new ArrayList<Double>(){{add(1.637D);add(1.872D);}});
	    put (53D, new ArrayList<Double>(){{add(1.873D);add(2D);}});
	}});
	put("paginae/atk/sting", new HashMap<Double, ArrayList<Double>>(){{
	    put (54D, new ArrayList<Double>(){{add(0D);add(0.510D);}});
	    put (55D, new ArrayList<Double>(){{add(0.510D);add(0.579D);}});
	    put (56D, new ArrayList<Double>(){{add(0.580D);add(0.656D);}});
	    put (57D, new ArrayList<Double>(){{add(0.657D);add(0.742D);}});
	    put (58D, new ArrayList<Double>(){{add(0.743D);add(0.837D);}});
	    put (59D, new ArrayList<Double>(){{add(0.838D);add(0.943D);}});
	    put (60D, new ArrayList<Double>(){{add(0.944D);add(1.059D);}});
	    put (61D, new ArrayList<Double>(){{add(1.060D);add(1.188D);}});
	    put (62D, new ArrayList<Double>(){{add(1.189D);add(1.330D);}});
	    put (63D, new ArrayList<Double>(){{add(1.331D);add(1.487D);}});
	    put (64D, new ArrayList<Double>(){{add(1.488D);add(1.659D);}});
	    put (65D, new ArrayList<Double>(){{add(1.660D);add(1.847D);}});
	    put (66D, new ArrayList<Double>(){{add(1.848D);add(2D);}});
	}});
	put("paginae/atk/cleave", new HashMap<Double, ArrayList<Double>>(){{
	    put (87D, new ArrayList<Double>(){{add(0D);add(0.522D);}});
	    put (88D, new ArrayList<Double>(){{add(0.523D);add(0.565D);}});
	    put (89D, new ArrayList<Double>(){{add(0.566D);add(0.612D);}});
	    put (90D, new ArrayList<Double>(){{add(0.613D);add(0.661D);}});
	    put (91D, new ArrayList<Double>(){{add(0.662D);add(0.714D);}});
	    put (92D, new ArrayList<Double>(){{add(0.715D);add(0.771D);}});
	    put (93D, new ArrayList<Double>(){{add(0.772D);add(0.831D);}});
	    put (94D, new ArrayList<Double>(){{add(0.832D);add(0.895D);}});
	    put (95D, new ArrayList<Double>(){{add(0.896D);add(0.964D);}});
	    put (96D, new ArrayList<Double>(){{add(0.965D);add(1.037D);}});
	    put (97D, new ArrayList<Double>(){{add(1.038D);add(1.114D);}});
	    put (98D, new ArrayList<Double>(){{add(1.115D);add(1.197D);}});
	    put (99D, new ArrayList<Double>(){{add(1.198D);add(1.284D);}});
	    put (100D, new ArrayList<Double>(){{add(1.285D);add(1.378D);}});
	    put (101D, new ArrayList<Double>(){{add(1.379D);add(1.476D);}});
	    put (102D, new ArrayList<Double>(){{add(1.477D);add(1.581D);}});
	    put (103D, new ArrayList<Double>(){{add(1.582D);add(1.693D);}});
	    put (104D, new ArrayList<Double>(){{add(1.694D);add(1.810D);}});
	    put (105D, new ArrayList<Double>(){{add(1.811D);add(1.935D);}});
	    put (106D, new ArrayList<Double>(){{add(1.936D);add(2D);}});
	}});
    }};
}
