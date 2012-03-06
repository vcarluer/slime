package gamers.associate.Slime.game;

import java.util.ArrayList;
import java.util.Random;

public class TitleGenerator {
	private static Random rand = new Random();	
	
	public static String generateNewTitle() {		
		
		String newtitle = "";
		
		String[] preposition1=new String[6];

		preposition1[0]="";
		preposition1[1]="The";
		preposition1[2]="";
		preposition1[3]="The";
		preposition1[4]="The";
		preposition1[5]="The";

		int aa= rand.nextInt(preposition1.length);


		String[] noun=new String[142];

		noun[0]="Cabinet";
		noun[1]="Toaster";
		noun[2]="Amulet";
		noun[3]="Stars";
		noun[4]="Pylons";
		noun[5]="Moon";
		noun[6]="Rockets";
		noun[7]="Crystal";
		noun[8]="World";
		noun[9]="Time Machine";
		noun[10]="Tree";
		noun[11]="Monument";
		noun[12]="Ship";
		noun[13]="Star";
		noun[14]="Tetrahedron";
		noun[15]="Vapor";
		noun[16]="Saucers";
		noun[17]="Machine";
		noun[18]="Instrument";
		noun[19]="Comet";
		noun[20]="Missile";
		noun[21]="Death Ray";
		noun[22]="Orb";
		noun[23]="City";
		noun[24]="Tesla Coils";
		noun[25]="Oscilloscope";
		noun[26]="Voltmeter";
		noun[27]="Vacuum Tube";
		noun[28]="Airship";
		noun[29]="Artifact";
		noun[30]="Waveform";
		noun[31]="Crystal";
		noun[32]="Engines";
		noun[33]="Gears";
		noun[34]="Space Ship";
		noun[35]="Meteors";
		noun[36]="Asteroid";
		noun[37]="Tower";
		noun[38]="Bridge";
		noun[39]="Crown";
		noun[40]="Lightning";
		noun[41]="Worlds";
		noun[42]="Moons";
		noun[43]="Planets";
		noun[44]="Galaxy";
		noun[45]="Pits";
		noun[46]="Satellite";
		noun[47]="Experiment";
		noun[48]="Valley";
		noun[49]="Canyon";
		noun[50]="Nebula";
		noun[51]="Laboratory";
		noun[52]="Fortress";
		noun[53]="Colony";
		noun[54]="Frigate";
		noun[55]="Forest";
		noun[56]="Crater";
		noun[57]="Future";
		noun[58]="Battlements";
		noun[59]="Gate";
		noun[60]="Obelisk";
		noun[61]="Swamp";
		noun[62]="Mist";
		noun[63]="Pool";
		noun[64]="Lake";
		noun[65]="Tetrahedron";
		noun[66]="Sphere";
		noun[67]="Altar";
		noun[68]="Island";
		noun[69]="Isle";
		noun[70]="Stone";
		noun[71]="Molecule";
		noun[72]="Mansion";
		noun[73]="Dome";
		noun[74]="Outpost";
		noun[75]="Citadel";
		noun[76]="Egg";
		noun[77]="Cube";
		noun[78]="Zoo";
		noun[79]="Museum";
		noun[80]="Library";
		noun[81]="Jungle";
		noun[82]="Glacier";
		noun[83]="Pyramid";
		noun[84]="Ziggurat";
		noun[85]="Monolith";
		noun[86]="Hovercar";
		noun[87]="Autogyro";
		noun[88]="Volcano";
		noun[89]="Mountain";
		noun[90]="Temple";
		noun[91]="Amoeba";
		noun[92]="Terror";
		noun[93]="Doorway";
		noun[94]="Skyscraper";
		noun[95]="Mystery";
		noun[96]="Doom";
		noun[97]="Rings";
		noun[98]="Beam";
		noun[99]="Pit";
		noun[100]="Starship";
		noun[101]="Love";
		noun[102]="Madness";
		noun[103]="Ray";
		noun[104]="Tripods";
		noun[105]="Trapezoid";
		noun[106]="Prism";
		noun[107]="Corridors";
		noun[108]="Halls";
		noun[109]="Eye";
		noun[110]="Flower";
		noun[111]="Chamber";
		noun[112]="Tomb";
		noun[113]="Storm";
		noun[114]="Crypt";
		noun[115]="Winds";
		noun[116]="Power";
		noun[117]="Radio";
		noun[118]="Catacombs";
		noun[119]="Silence";
		noun[120]="Leviathan";
		noun[121]="Parhelion";
		noun[122]="Device";
		noun[123]="Dreadnaught";
		noun[124]="Dolmen";
		noun[125]="Tome";
		noun[126]="Vortex";
		noun[127]="Prison";
		noun[128]="Asylum";
		noun[129]="Death Ray";
		noun[130]="Apparatus";
		noun[131]="Lock";
		noun[132]="Keys";
		noun[133]="Stairway";
		noun[134]="Specimens";
		noun[135]="Delicatessen";
		noun[136]="Claws";
		noun[137]="Talons";
		noun[138]="Carnival";
		noun[139]="Dimension";
		noun[140]="Bathysphere";
		noun[141]="Vizigraph";

		int bb=rand.nextInt(noun.length);
			
		String[] noun2=new String[142];

		noun2[0]="Dust";
		noun2[1]="Gem";
		noun2[2]="Jewel";
		noun2[3]="Stars";
		noun2[4]="Pylons";
		noun2[5]="Moon";
		noun2[6]="Rockets";
		noun2[7]="Crystal";
		noun2[8]="World";
		noun2[9]="Time Machine";
		noun2[10]="Fog";
		noun2[11]="Monument";
		noun2[12]="Vessel";
		noun2[13]="Star";
		noun2[14]="Mechanism";
		noun2[15]="Tabernacle";
		noun2[16]="Saucers";
		noun2[17]="Machine";
		noun2[18]="Prism";
		noun2[19]="Comet";
		noun2[20]="Rocket";
		noun2[21]="Death Ray";
		noun2[22]="Airship";
		noun2[23]="City";
		noun2[24]="Tesla Coils";
		noun2[25]="Whirlpool";
		noun2[26]="Voltmeter";
		noun2[27]="Vacuum Tube";
		noun2[28]="Airship";
		noun2[29]="Artifact";
		noun2[30]="Waveform";
		noun2[31]="Ray Gun";
		noun2[32]="Engines";
		noun2[33]="Flames";
		noun2[34]="Space Ship";
		noun2[35]="Meteors";
		noun2[36]="Asteroid";
		noun2[37]="Tower";
		noun2[38]="Bridge";
		noun2[39]="Palace";
		noun2[40]="Lightning";
		noun2[41]="Worlds";
		noun2[42]="Moons";
		noun2[43]="Planets";
		noun2[44]="Galaxy";
		noun2[45]="Universe";
		noun2[46]="Satellite";
		noun2[47]="Experiment";
		noun2[48]="Valley";
		noun2[49]="Canyon";
		noun2[50]="Nebula";
		noun2[51]="Laboratory";
		noun2[52]="Fortress";
		noun2[53]="Colony";
		noun2[54]="Eidolon";
		noun2[55]="Forest";
		noun2[56]="Crater";
		noun2[57]="Future";
		noun2[58]="Marshes";
		noun2[59]="Gate";
		noun2[60]="Obelisk";
		noun2[61]="Swamp";
		noun2[62]="Mist";
		noun2[63]="Pool";
		noun2[64]="Ocean";
		noun2[65]="Tetrahedron";
		noun2[66]="Caravan";
		noun2[67]="Lens";
		noun2[68]="Island";
		noun2[69]="Isle";
		noun2[70]="Stone";
		noun2[71]="Molecule";
		noun2[72]="Mansion";
		noun2[73]="Dome";
		noun2[74]="Outpost";
		noun2[75]="Citadel";
		noun2[76]="Egg";
		noun2[77]="Cube";
		noun2[78]="Zoo";
		noun2[79]="Museum";
		noun2[80]="Library";
		noun2[81]="Jungle";
		noun2[82]="Glacier";
		noun2[83]="Pyramid";
		noun2[84]="Ziggurat";
		noun2[85]="Monolith";
		noun2[86]="Hovercar";
		noun2[87]="Crevasse";
		noun2[88]="Volcano";
		noun2[89]="Mountain";
		noun2[90]="Cavern";
		noun2[91]="Blossoms";
		noun2[92]="Cliffs";
		noun2[93]="Chasm";
		noun2[94]="Tomb";
		noun2[95]="Observatory";
		noun2[96]="Turbines";
		noun2[97]="Cyclone";
		noun2[98]="Crypt";
		noun2[99]="Wind";
		noun2[100]="Empire";
		noun2[101]="Trylon";
		noun2[102]="Perisphere";
		noun2[103]="Spires";
		noun2[104]="Mines";
		noun2[105]="Wasteland";
		noun2[106]="Desert";
		noun2[107]="Depths";
		noun2[108]="River";
		noun2[109]="Pinnacle";
		noun2[110]="Portal";
		noun2[111]="Perihelion";
		noun2[112]="Apex";
		noun2[113]="Equation";
		noun2[114]="Throne";
		noun2[115]="Web";
		noun2[116]="Map";
		noun2[117]="Inscription";
		noun2[118]="Tunnels";
		noun2[119]="Book";
		noun2[120]="Window";
		noun2[121]="Accordion";
		noun2[122]="Test Tubes";
		noun2[123]="Skies";
		noun2[124]="Cromlech";
		noun2[125]="Codex";
		noun2[126]="Hurricane";
		noun2[127]="Maelstrom";
		noun2[128]="Tubes";
		noun2[129]="Aeroplane";
		noun2[130]="Vaults";
		noun2[131]="Labyrinth";
		noun2[132]="Mazes";
		noun2[133]="Dungeons";
		noun2[134]="Spaceport";
		noun2[135]="Inn";
		noun2[136]="Fangs";
		noun2[137]="Head";
		noun2[138]="Bazaar";
		noun2[139]="Planetoid";
		noun2[140]="Cult";
		noun2[141]="Juggernaut";

		int bc=rand.nextInt(noun2.length);

		// ?
		if (noun2[bc]==noun[bb]) {
			bc=rand.nextInt(noun2.length);
		}		  
			
		String[] entity=new String[128];

		entity[0]="Fleet";
		entity[1]="Curator";
		entity[2]="Scientists";
		entity[3]="Space Pirates";
		entity[4]="Moto-Men";
		entity[5]="Sky Pirates";
		entity[6]="Spacemen";
		entity[7]="Women";
		entity[8]="Doctor";
		entity[9]="Spacemen";
		entity[10]="Creature";
		entity[11]="Mind Readers";
		entity[12]="Brain Eaters";
		entity[13]="Empress";
		entity[14]="Thieves";
		entity[15]="Architect";
		entity[16]="Robot";
		entity[17]="Robots";
		entity[18]="Monster";
		entity[19]="Space Beast";
		entity[20]="Ferret";
		entity[21]="Thing";
		entity[22]="Squid";
		entity[23]="Accountant";
		entity[24]="Doctor";
		entity[25]="Librarian";
		entity[26]="Cat";
		entity[27]="Professor";
		entity[28]="Tyrant";
		entity[29]="Inventor";
		entity[30]="Doctor";
		entity[31]="Pilot";
		entity[32]="Being";
		entity[33]="Seamstress";
		entity[34]="Prophet";
		entity[35]="Parrot";
		entity[36]="Pirate";
		entity[37]="Thief";
		entity[38]="Puppeteer";
		entity[39]="Engineer";
		entity[40]="Fungus";
		entity[41]="Insect";
		entity[42]="Idol";
		entity[43]="Brain";
		entity[44]="Brains";
		entity[45]="Queen";
		entity[46]="Princess";
		entity[47]="Oracle";
		entity[48]="Scholar";
		entity[49]="Brain Thieves";
		entity[50]="Weapon";
		entity[51]="Skull";
		entity[52]="Tentacle";
		entity[53]="Tentacles";
		entity[54]="King";
		entity[55]="Children";
		entity[56]="People";
		entity[55]="Tribe";
		entity[56]="Bride";
		entity[57]="Servant";
		entity[58]="Mind";
		entity[59]="Statue";
		entity[60]="Detective";
		entity[61]="Machinist";
		entity[62]="Midshipman";
		entity[63]="Lieutenant";
		entity[64]="Captain";
		entity[65]="Spider";
		entity[66]="Lobster";
		entity[67]="Ape";
		entity[68]="Amazons";
		entity[69]="Outlaw";
		entity[70]="Girl";
		entity[71]="Merchant";
		entity[72]="Priestess";
		entity[73]="Mathematician";
		entity[74]="Chemist";
		entity[75]="Astronomer";
		entity[76]="Behemoth";
		entity[77]="Hounds";
		entity[78]="Courtesan";
		entity[79]="Guards";
		entity[80]="Sentinel";
		entity[81]="Explorer";
		entity[82]="Plant Men";
		entity[83]="Lizard";
		entity[84]="Artisan";
		entity[85]="Repairman";
		entity[86]="Automaton";
		entity[87]="Cartographer";
		entity[88]="Whale";
		entity[89]="Lyre";
		entity[90]="Eidolon";
		entity[91]="Harp";
		entity[92]="Sword";
		entity[93]="Fiends";
		entity[94]="Squadron";
		entity[95]="Myrmidons";
		entity[96]="Buccaneers";
		entity[97]="Smuggler";
		entity[98]="Cephalopod";
		entity[99]="Aviator";
		entity[100]="Aviatrix";
		entity[101]="Henchman";
		entity[102]="Philosopher";
		entity[103]="Alchemist";
		entity[104]="Prisoner";
		entity[105]="Jailer";
		entity[106]="Tutor";
		entity[107]="Headmaster";
		entity[108]="Electrician";
		entity[109]="Alienist";
		entity[110]="Policeman";
		entity[111]="Frog";
		entity[112]="Toad";
		entity[113]="Gambler";
		entity[114]="Apparition";
		entity[115]="Sculptor";
		entity[116]="Poet";
		entity[117]="Historian";
		entity[118]="Waitress";
		entity[119]="Gatekeeper";
		entity[120]="Sentry";
		entity[121]="Editor";
		entity[122]="Linguist";
		entity[123]="Champion";
		entity[124]="Exile";
		entity[125]="Luthier";
		entity[126]="Entomologist";
		entity[127]="Nymphs";

		int cc=rand.nextInt(entity.length);
			
		int cd=rand.nextInt(entity.length);
		if (entity[cc]==entity[cd]) {
			cd=rand.nextInt(entity.length);
		}
		  
		String[] noun_final=new String[110];

		noun_final[0]="Uncertainty";
		noun_final[1]="Destiny";
		noun_final[2]="Terror";
		noun_final[3]="Wonder";
		noun_final[4]="Liberation";
		noun_final[5]="Mystery";
		noun_final[6]="the Future";
		noun_final[7]="Time";
		noun_final[8]="Doom";
		noun_final[9]="Infinity";
		noun_final[10]="Despair";
		noun_final[11]="Enlightenment";
		noun_final[12]="Retropolis";
		noun_final[13]="the Universe";
		noun_final[14]="the Planets";
		noun_final[15]="Mercy";
		noun_final[16]="the Unknown";
		noun_final[17]="the Unknowable";
		noun_final[18]="the Unthinkable";
		noun_final[19]="Doom";
		noun_final[20]="Space";
		noun_final[21]="Civilization";
		noun_final[22]="the Galaxy";
		noun_final[23]="Fear";
		noun_final[24]="the Asteroids";
		noun_final[25]="Yesterday";
		noun_final[26]="Tomorrow";
		noun_final[27]="the Ages";
		noun_final[28]="Earth";
		noun_final[29]="the Stars";
		noun_final[30]="the Worlds";
		noun_final[31]="the Planets";
		noun_final[32]="Chaos";
		noun_final[33]="the Moon";
		noun_final[34]="the Gods";
		noun_final[35]="the Sun";
		noun_final[36]="the Comets";
		noun_final[37]="War";
		noun_final[38]="Fate";
		noun_final[39]="Love";
		noun_final[40]="Death";
		noun_final[41]="Desire";
		noun_final[42]="the Brain";
		noun_final[43]="the Sea";
		noun_final[44]="Mars";
		noun_final[45]="the Martians";
		noun_final[46]="the Brains";
		noun_final[47]="the Atom";
		noun_final[48]="the Cosmos";
		noun_final[49]="Outer Space";
		noun_final[50]="Space";
		noun_final[51]="History";
		noun_final[52]="Starvation";
		noun_final[53]="the Abyss";
		noun_final[54]="the Void";
		noun_final[55]="Neptune";
		noun_final[56]="Orion";
		noun_final[57]="Venus";
		noun_final[58]="Jupiter";
		noun_final[59]="Saturn";
		noun_final[60]="Andromeda";
		noun_final[61]="Altair";
		noun_final[62]="Sirius";
		noun_final[63]="Mercury";
		noun_final[64]="Phobos";
		noun_final[65]="Deimos";
		noun_final[66]="Titan";
		noun_final[67]="Callisto";
		noun_final[68]="Ganymede";
		noun_final[69]="Europa";
		noun_final[70]="Io";
		noun_final[71]="Paradox";
		noun_final[72]="the Clouds";
		noun_final[73]="Argon";
		noun_final[74]="Destruction";
		noun_final[75]="Science";
		noun_final[76]="Canopus";
		noun_final[77]="Illusion";
		noun_final[78]="Twilight";
		noun_final[79]="Arcturus";
		noun_final[80]="Antares";
		noun_final[81]="Atlantis";
		noun_final[82]="Lemuria";
		noun_final[83]="Cygnus";
		noun_final[84]="Betelgeuse";
		noun_final[85]="Bellatrix";
		noun_final[86]="the Mountains";
		noun_final[87]="Iapetus";
		noun_final[88]="the Mind";
		noun_final[89]="Pluto";
		noun_final[90]="the Earth's Core";
		noun_final[91]="Armageddon";
		noun_final[92]="the Aether";
		noun_final[93]="Darkness";
		noun_final[94]="Metaluna";
		noun_final[95]="Dread";
		noun_final[96]="the Enigmascope";
		noun_final[97]="the Morning";
		noun_final[98]="the Evening";
		noun_final[99]="the Night";
		noun_final[100]="Danger";
		noun_final[101]="Peril";
		noun_final[102]="Azenomei";
		noun_final[103]="Light";
		noun_final[104]="Fomalhaut";
		noun_final[105]="Achernar";
		noun_final[106]="Capella";
		noun_final[107]="Bellus";
		noun_final[108]="Zyra";
		noun_final[109]="the Damned";

		int dd=rand.nextInt(noun_final.length);

		String[] preposition2=new String[13];

		preposition2[0]="from the";
		preposition2[1]="from the";
		preposition2[2]="from Beyond the";
		preposition2[3]="of the";
		preposition2[4]="from Below the";
		preposition2[5]="from Beneath the";
		preposition2[6]="from Above the";
		preposition2[7]="from Outside the";
		preposition2[8]="from Inside the";
		preposition2[9]="of the";
		preposition2[10]="of the";
		preposition2[11]="of the";
		preposition2[12]="from Within the";

		int ee=rand.nextInt(preposition2.length);


		String[] adjective=new String[198];

		adjective[0]="Shimmering";
		adjective[1]="Vermilion";
		adjective[2]="Crimson";
		adjective[3]="Amazing";
		adjective[4]="Threatening";
		adjective[5]="Great";
		adjective[6]="Circling";
		adjective[7]="Azure";
		adjective[8]="Violet";
		adjective[9]="Impossible";
		adjective[10]="Alternate";
		adjective[11]="Exploding";
		adjective[12]="Pulsating";
		adjective[13]="Shrinking";
		adjective[14]="Duplicate";
		adjective[15]="Miniature";
		adjective[16]="Atomic";
		adjective[17]="Astounding";
		adjective[18]="Terrifying";
		adjective[19]="Beautiful";
		adjective[20]="Joyful";
		adjective[21]="Lesser";
		adjective[22]="Evil";
		adjective[23]="Sacred";
		adjective[24]="Profane";
		adjective[25]="Lost";
		adjective[26]="Glowing";
		adjective[27]="Twisted";
		adjective[28]="Abominable";
		adjective[29]="Phantom";
		adjective[30]="Timeless";
		adjective[31]="Primordial";
		adjective[32]="Swollen";
		adjective[33]="Revolving";
		adjective[34]="Unlikely";
		adjective[35]="Transforming";
		adjective[36]="Dark";
		adjective[37]="Singing";
		adjective[38]="Bright";
		adjective[39]="Lifeless";
		adjective[40]="Invisible";
		adjective[41]="Unforgettable";
		adjective[42]="Mechanical";
		adjective[43]="Forgotten";
		adjective[44]="Mysterious";
		adjective[45]="Accursed";
		adjective[46]="Colossal";
		adjective[47]="Microscopic";
		adjective[48]="Peculiar";
		adjective[49]="Golden";
		adjective[50]="Silver";
		adjective[51]="Metallic";
		adjective[52]="Ominous";
		adjective[53]="Ancient";
		adjective[54]="Invincible";
		adjective[55]="Alien";
		adjective[56]="Unknown";
		adjective[57]="Alarming";
		adjective[58]="Unnatural";
		adjective[59]="Unusual";
		adjective[60]="Uncanny";
		adjective[61]="Artificial";
		adjective[62]="Luminous";
		adjective[63]="Unknowable";
		adjective[64]="Unthinkable";
		adjective[65]="Improbable";
		adjective[66]="Shadowy";
		adjective[67]="Strange";
		adjective[68]="Swollen";
		adjective[69]="Glowing";
		adjective[70]="Radiant";
		adjective[71]="Eldritch";
		adjective[72]="Ultimate";
		adjective[73]="Gleaming";
		adjective[74]="Shining";
		adjective[75]="Terrible";
		adjective[76]="Cosmic";
		adjective[77]="Venomous";
		adjective[78]="Forbidden";
		adjective[79]="Giant";
		adjective[80]="Gigantic";
		adjective[81]="Prismatic";
		adjective[82]="Wrong";
		adjective[83]="Crystalline";
		adjective[84]="Scarlet";
		adjective[85]="Ebon";
		adjective[86]="Ivory";
		adjective[87]="Carnelian";
		adjective[88]="Poisonous";
		adjective[89]="Mad";
		adjective[90]="Pitiless";
		adjective[91]="Merciless";
		adjective[92]="Electrical";
		adjective[93]="Perilous";
		adjective[94]="Sinister";
		adjective[95]="Flying";
		adjective[96]="Hovering";
		adjective[97]="Winged";
		adjective[98]="Expanding";
		adjective[99]="Tarnished";
		adjective[100]="Mental";
		adjective[101]="Spectral";
		adjective[102]="Shrieking";
		adjective[103]="Screaming";
		adjective[104]="Undying";
		adjective[105]="Floating";
		adjective[106]="Dancing";
		adjective[107]="Soaring";
		adjective[108]="Dying";
		adjective[109]="Streamlined";
		adjective[110]="Sentient";
		adjective[111]="Hidden";
		adjective[112]="Howling";
		adjective[113]="Chitinous";
		adjective[114]="Echoing";
		adjective[115]="Quivering";
		adjective[116]="Celestial";
		adjective[117]="Creeping";
		adjective[118]="Wonderful";
		adjective[119]="Purple";
		adjective[120]="Fearless";
		adjective[121]="Clockwork";
		adjective[122]="Obsidian";
		adjective[123]="Fatal";
		adjective[124]="Burrowing";
		adjective[125]="Calculating";
		adjective[126]="Steaming";
		adjective[127]="Automatic";
		adjective[128]="Scintillating";
		adjective[129]="Obedient";
		adjective[130]="Dreadful";
		adjective[131]="Legendary";
		adjective[132]="Other";
		adjective[133]="Magnetic";
		adjective[134]="Thoughtful";
		adjective[135]="Clever";
		adjective[136]="Delicate";
		adjective[137]="Burning";
		adjective[138]="Ponderous";
		adjective[139]="Despicable";
		adjective[140]="Villainous";
		adjective[141]="Frozen";
		adjective[142]="Laughing";
		adjective[143]="Silent";
		adjective[144]="Peaceful";
		adjective[145]="Unfortunate";
		adjective[146]="Fortunate";
		adjective[147]="Magnificent";
		adjective[148]="Malevolent";
		adjective[149]="Benevolent";
		adjective[150]="Fire Breathing";
		adjective[151]="Flaming";
		adjective[152]="Dextrous";
		adjective[153]="Agile";
		adjective[154]="Haunted";
		adjective[155]="Gaseous";
		adjective[156]="Disturbing";
		adjective[157]="Unsettling";
		adjective[158]="Uneasy";
		adjective[159]="Horrifying";
		adjective[160]="Horrible";
		adjective[161]="Awful";
		adjective[162]="Noble";
		adjective[163]="Inspiring";
		adjective[164]="Brazen";
		adjective[165]="Bronze";
		adjective[166]="Platinum";
		adjective[167]="Aluminum";
		adjective[168]="Titanium";
		adjective[169]="Deathless";
		adjective[170]="Immortal";
		adjective[171]="Faceless";
		adjective[172]="Malodorous";
		adjective[173]="Whirling";
		adjective[174]="Spinning";
		adjective[175]="Swirling";
		adjective[176]="Skeletal";
		adjective[177]="Titanic";
		adjective[178]="Untimely";
		adjective[179]="Unforseen";
		adjective[180]="Immense";
		adjective[181]="Hydraulic";
		adjective[182]="Immovable";
		adjective[183]="Steadfast";
		adjective[184]="Obdurate";
		adjective[185]="Adamant";
		adjective[186]="Obstinate";
		adjective[187]="Disagreeable";
		adjective[188]="Mindless";
		adjective[189]="Madcap";
		adjective[190]="Crawling";
		adjective[191]="Eternal";
		adjective[192]="Enormous";
		adjective[193]="Imitation";
		adjective[194]="False";
		adjective[195]="Imperfect";
		adjective[196]="Furious";
		adjective[197]="Endless";


		int ff=rand.nextInt(adjective.length);


		String[] verb=new String[98];

		verb[0]="Misplaced";
		verb[1]="Conquered";
		verb[2]="Saw";
		verb[3]="Outlived";
		verb[4]="Captured";
		verb[5]="Assaulted";
		verb[6]="Learned of";
		verb[7]="Wept for";
		verb[8]="Ate";
		verb[9]="Destroyed";
		verb[10]="Revealed";
		verb[11]="Hid";
		verb[12]="Transformed";
		verb[13]="Avenged";
		verb[14]="Came from";
		verb[15]="Invented";
		verb[16]="Rose from";
		verb[17]="Abandoned";
		verb[18]="Created";
		verb[19]="Discovered";
		verb[20]="Concealed";
		verb[21]="Loved";
		verb[22]="Hated";
		verb[23]="Spurned";
		verb[24]="Forgot";
		verb[25]="Betrayed";
		verb[26]="Lost";
		verb[27]="Understood";
		verb[28]="Explained";
		verb[29]="Disintegrated";
		verb[30]="Vanquished";
		verb[31]="Astounded";
		verb[32]="Devoured";
		verb[33]="Blasted";
		verb[34]="Amazed";
		verb[35]="Outraced";
		verb[36]="Saved";
		verb[37]="Angered";
		verb[38]="Denied";
		verb[39]="Worshipped";
		verb[40]="Dreaded";
		verb[41]="Remembered";
		verb[42]="Retrieved";
		verb[43]="Feared";
		verb[44]="Ruled";
		verb[45]="Terrified";
		verb[46]="Mocked";
		verb[47]="Saved";
		verb[48]="Rescued";
		verb[49]="Bought";
		verb[50]="Sold";
		verb[51]="Enslaved";
		verb[52]="Freed";
		verb[53]="Engulfed";
		verb[54]="Pacified";
		verb[55]="Enlarged";
		verb[56]="Shrank";
		verb[57]="Returned from";
		verb[58]="Came from";
		verb[59]="Survived";
		verb[60]="Yearned for";
		verb[61]="Dismantled";
		verb[62]="Taught";
		verb[63]="Befriended";
		verb[64]="Encircled";
		verb[65]="Knew";
		verb[66]="Escaped";
		verb[67]="Menaced";
		verb[68]="Bewitched";
		verb[69]="Enchanted";
		verb[70]="Slew";
		verb[71]="Collected";
		verb[72]="Followed";
		verb[73]="Shattered";
		verb[74]="Mended";
		verb[75]="Eclipsed";
		verb[76]="Stole";
		verb[77]="Unveiled";
		verb[78]="Shrouded";
		verb[79]="Caged";
		verb[80]="Pursued";
		verb[81]="Confused";
		verb[82]="Unleashed";
		verb[83]="Haunted";
		verb[84]="Engulfed";
		verb[85]="Despoiled";
		verb[86]="Melted";
		verb[87]="Summoned";
		verb[88]="Decoded";
		verb[89]="Served";
		verb[90]="Defeated";
		verb[91]="Awaited";
		verb[92]="Foretold";
		verb[93]="Taunted";
		verb[94]="Tempted";
		verb[95]="Amused";
		verb[96]="Swindled";
		verb[97]="Threatened";

		int gg=rand.nextInt(verb.length);
			

		String[] preposition3=new String[1];

		preposition3[0]="that";

		int hh=rand.nextInt(preposition3.length);

		String[] intro=new String[94];

		intro[0]="The Enigma of the";
		intro[1]="The Day of the";
		intro[2]="The Coming of the";
		intro[3]="Dawn of the";
		intro[4]="The Day of the";
		intro[5]="The Night of the";
		intro[6]="Hour of the";
		intro[7]="The Strange";
		intro[8]="The";
		intro[9]="The Terrifying";
		intro[10]="Birth of the";
		intro[11]="Attack of the";
		intro[12]="Revenge of the";
		intro[13]="The Mystery of the";
		intro[14]="The Year of the";
		intro[15]="Invasion of the";
		intro[16]="The Amazing";
		intro[17]="The Incredible";
		intro[18]="Onslaught of the";
		intro[19]="Twilight of the";
		intro[20]="The Tale of the";
		intro[21]="Last Days of the";
		intro[22]="Escape From the";
		intro[23]="Terror of the";
		intro[24]="Rescue from the";
		intro[25]="Triumph of the";
		intro[26]="The First";
		intro[27]="The Last";
		intro[28]="The Final";
		intro[29]="Rebellion of the";
		intro[30]="The Song of the";
		intro[31]="The Tale of the";
		intro[32]="Death of the";
		intro[33]="Revolt of the";
		intro[34]="Slaves of the";
		intro[35]="Captives of the";
		intro[36]="Slaves of the";
		intro[37]="The Secret of the";
		intro[38]="Horror of the";
		intro[39]="Victory of the";
		intro[40]="Den of the";
		intro[41]="Lair of the";
		intro[42]="The Trail of the";
		intro[43]="Uprising of the";
		intro[44]="Lament for the";
		intro[45]="Race Against the";
		intro[46]="War of the";
		intro[47]="Betrayal of the";
		intro[48]="Discovery of the";
		intro[49]="Harem of the";
		intro[50]="Women of the";
		intro[51]="Men of the";
		intro[52]="Gods of the";
		intro[53]="Slaves of the";
		intro[54]="Captives of the";
		intro[55]="Enslaved by the";
		intro[56]="Defeat of the";
		intro[57]="Shadow of the";
		intro[58]="Return of the";
		intro[59]="Riddle of the";
		intro[60]="The Fantastic";
		intro[61]="The Sleeping";
		intro[62]="The Immortal";
		intro[63]="Dreams of the";
		intro[64]="The Dreaming";
		intro[65]="League of the";
		intro[66]="The Cipher of the";
		intro[67]="Downfall of the";
		intro[68]="Embrace of the";
		intro[69]="Vengeance of the";
		intro[70]="Master of the";
		intro[71]="Mistress of the";
		intro[72]="Hordes of the";
		intro[73]="The Plague of the";
		intro[74]="Nightmare of the";
		intro[75]="Battle of the";
		intro[76]="Daughter of the";
		intro[77]="Son of the";
		intro[78]="The Music of the";
		intro[79]="The Rise of the";
		intro[80]="The Fall of the";
		intro[81]="The Curse of the";
		intro[82]="The Stalker of the";
		intro[83]="A Message From the";
		intro[84]="The Transmission from the";
		intro[85]="Minions of the";
		intro[86]="Outcasts of the";
		intro[87]="The Quest of the";
		intro[88]="Search for the";
		intro[89]="The Seventh";
		intro[90]="Vassals of the";
		intro[91]="Thralls of the";
		intro[92]="Spawn of the";
		intro[93]="Exiles of the";

		int ii=rand.nextInt(intro.length);

		String[] preposition4=new String[2];

		preposition4[0]="that";
		preposition4[1]="that";

		int jj=rand.nextInt(preposition4.length);

		String[] possessive=new String[57];

		possessive[0]="Neptune's";
		possessive[1]="Time's";
		possessive[2]="Fate's";
		possessive[3]="History's";
		possessive[4]="Doom's";
		possessive[5]="The Asteroid's";
		possessive[6]="The World's";
		possessive[7]="The Sun's";
		possessive[8]="The Moon's";
		possessive[9]="Tumithak's";
		possessive[10]="The Galaxy's";
		possessive[11]="The Planet's";
		possessive[12]="Mankind's";
		possessive[13]="The Martian";
		possessive[14]="Gernsback's";
		possessive[15]="Krenkel's";
		possessive[16]="Bok's";
		possessive[17]="Zeno's";
		possessive[18]="The Venusian";
		possessive[19]="Secord's";
		possessive[20]="Jupiter's";
		possessive[21]="Saturn's";
		possessive[22]="Overshaw's";
		possessive[23]="Kaluta's";
		possessive[24]="Williamson's";
		possessive[25]="The Technocrat's";
		possessive[26]="Motter's";
		possessive[27]="Pluto's";
		possessive[28]="Gallegher's";
		possessive[29]="Bisson's";
		possessive[30]="Hamilton's";
		possessive[31]="Leinster's";
		possessive[32]="Weinbaum's";
		possessive[33]="Simak's";
		possessive[34]="Finlay's";
		possessive[35]="Mercury's";
		possessive[36]="Bradbury's";
		possessive[37]="Graybill's";
		possessive[38]="Brackett's";
		possessive[39]="Kornbluth's";
		possessive[40]="Findriddy's";
		possessive[41]="Jameson's";
		possessive[42]="Evanston's";
		possessive[43]="Kuttner's";
		possessive[44]="Pohl's";
		possessive[45]="Dargo's";
		possessive[46]="Rigel's";
		possessive[47]="Swyney's";
		possessive[48]="Rotwang's";
		possessive[49]="Cabal's";
		possessive[50]="Exeter's";
		possessive[51]="Gort's";
		possessive[52]="Klaatu's";
		possessive[53]="Eando's";
		possessive[54]="Nuth's";
		possessive[55]="Yarol's";
		possessive[56]="Nahrgang's";


		int kk=rand.nextInt(possessive.length);

		String[] verb_start=new String[52];

		verb_start[0]="Ensnared";
		verb_start[1]="Trapped";
		verb_start[2]="Hunted";
		verb_start[3]="Stalked";
		verb_start[4]="Enslaved";
		verb_start[5]="Kidnapped";
		verb_start[6]="Imprisoned";
		verb_start[7]="Vanquished";
		verb_start[8]="Lost";
		verb_start[9]="Captured";
		verb_start[10]="Entombed";
		verb_start[11]="Found";
		verb_start[12]="Discovered";
		verb_start[13]="Watched";
		verb_start[14]="Bewitched";
		verb_start[15]="Enchanted";
		verb_start[16]="Shrunken";
		verb_start[17]="Conquered";
		verb_start[18]="Assaulted";
		verb_start[19]="Ambushed";
		verb_start[20]="Destroyed";
		verb_start[21]="Disguised";
		verb_start[22]="Devoured";
		verb_start[23]="Avenged";
		verb_start[24]="Abandoned";
		verb_start[25]="Marooned";
		verb_start[26]="Concealed";
		verb_start[27]="Betrayed";
		verb_start[28]="Terrorized";
		verb_start[29]="Rescued";
		verb_start[30]="Engulfed";
		verb_start[31]="Blinded";
		verb_start[32]="Forgotten";
		verb_start[33]="Robbed";
		verb_start[34]="Embattled";
		verb_start[35]="Transformed";
		verb_start[36]="Surprised";
		verb_start[37]="Preserved";
		verb_start[38]="Frozen";
		verb_start[39]="Paralyzed";
		verb_start[40]="Surrounded";
		verb_start[41]="Menaced";
		verb_start[42]="Spared";
		verb_start[43]="Dissolved";
		verb_start[44]="Disgraced";
		verb_start[45]="Marooned";
		verb_start[46]="Pursued";
		verb_start[47]="Mesmerized";
		verb_start[48]="Summoned";
		verb_start[49]="Revealed";
		verb_start[50]="Beguiled";
		verb_start[51]="Entranced";

		int ll=rand.nextInt(verb_start.length);

		String[] critters_a=new String[127];

		critters_a[0]="Cat";
		critters_a[1]="Dog";
		critters_a[2]="Ant";
		critters_a[3]="Plant";
		critters_a[4]="Lizard";
		critters_a[5]="Reptile";
		critters_a[6]="Squid";
		critters_a[7]="Frog";
		critters_a[8]="Ice";
		critters_a[9]="Flame";
		critters_a[10]="Bat";
		critters_a[11]="Space";
		critters_a[12]="Star";
		critters_a[13]="Bear";
		critters_a[14]="Tentacle";
		critters_a[15]="Dinosaur";
		critters_a[16]="Ghost";
		critters_a[17]="Insect";
		critters_a[18]="Galactic";
		critters_a[19]="Solar";
		critters_a[20]="Lunar";
		critters_a[21]="Stellar";
		critters_a[22]="Bee";
		critters_a[23]="Wasp";
		critters_a[24]="Wolf";
		critters_a[25]="Rabbit";
		critters_a[26]="Snake";
		critters_a[27]="Serpent";
		critters_a[28]="Unholy";
		critters_a[29]="Locust";
		critters_a[30]="Rat";
		critters_a[31]="Squirrel";
		critters_a[32]="Beetle";
		critters_a[33]="Savage";
		critters_a[34]="Ruthless";
		critters_a[35]="Lion";
		critters_a[36]="Phoenix";
		critters_a[37]="Spectral";
		critters_a[38]="Bird";
		critters_a[39]="Centaur";
		critters_a[40]="Dragon";
		critters_a[41]="Panther";
		critters_a[42]="Leopard";
		critters_a[43]="Tiger";
		critters_a[44]="Ape";
		critters_a[45]="Gorilla";
		critters_a[46]="Monkey";
		critters_a[47]="Hyena";
		critters_a[48]="Invisible";
		critters_a[49]="Hawk";
		critters_a[50]="Eagle";
		critters_a[51]="Falcon";
		critters_a[52]="Vulture";
		critters_a[53]="Heron";
		critters_a[54]="Ferret";
		critters_a[55]="Weasel";
		critters_a[56]="Otter";
		critters_a[57]="Meerkat";
		critters_a[58]="Whale";
		critters_a[59]="Porpoise";
		critters_a[60]="Dolphin";
		critters_a[61]="Fish";
		critters_a[62]="Shark";
		critters_a[63]="Elephant";
		critters_a[64]="Tree";
		critters_a[65]="Clockwork";
		critters_a[66]="Mechanical";
		critters_a[67]="Robotic";
		critters_a[68]="Metal";
		critters_a[69]="Radio";
		critters_a[70]="Shadow";
		critters_a[71]="Machine";
		critters_a[72]="Sand";
		critters_a[73]="Wombat";
		critters_a[74]="Turtle";
		critters_a[75]="Toad";
		critters_a[76]="Sparrow";
		critters_a[77]="Crab";
		critters_a[78]="Lobster";
		critters_a[79]="Spider";
		critters_a[80]="Skeleton";
		critters_a[81]="Platypus";
		critters_a[82]="Rodent";
		critters_a[83]="Phantom";
		critters_a[84]="Penguin";
		critters_a[85]="Mole";
		critters_a[86]="Moth";
		critters_a[87]="Octopus";
		critters_a[88]="Cyclops";
		critters_a[89]="Crocodile";
		critters_a[90]="Alligator";
		critters_a[91]="Cobra";
		critters_a[92]="Mongoose";
		critters_a[93]="Ghostly";
		critters_a[94]="Swan";
		critters_a[95]="Mythic";
		critters_a[96]="All-Knowing";
		critters_a[97]="Educated";
		critters_a[98]="Adventurous";
		critters_a[99]="Cowardly";
		critters_a[100]="Inventive";
		critters_a[101]="Demented";
		critters_a[102]="Evolving";
		critters_a[103]="Unlicensed";
		critters_a[104]="Curious";
		critters_a[105]="Feline";
		critters_a[106]="Canine";
		critters_a[107]="Fox";
		critters_a[108]="Mouse";
		critters_a[109]="Feral";
		critters_a[110]="Wild";
		critters_a[111]="Tortoise";
		critters_a[112]="Isotope";
		critters_a[113]="Cosmic";
		critters_a[114]="Avian";
		critters_a[115]="Undead";
		critters_a[116]="Zombie";
		critters_a[117]="Pygmy";
		critters_a[118]="Swimming";
		critters_a[119]="Amphibious";
		critters_a[120]="Bizarre";
		critters_a[121]="Ravenous";
		critters_a[122]="Flesh Eating";
		critters_a[123]="Reluctant";
		critters_a[124]="Faithful";
		critters_a[125]="Unfaithful";
		critters_a[126]="Dutiful";


		int xca=rand.nextInt(critters_a.length);

		String[] critters_b=new String[114];

		critters_b[0]="Men";
		critters_b[1]="Women";
		critters_b[2]="Man";
		critters_b[3]="Woman";
		critters_b[4]="Creatures";
		critters_b[5]="Beasts";
		critters_b[6]="Boy";
		critters_b[7]="Girl";
		critters_b[8]="Priest";
		critters_b[9]="King";
		critters_b[10]="Queen";
		critters_b[11]="Warriors";
		critters_b[12]="People";
		critters_b[13]="Kingdom";
		critters_b[14]="Soldiers";
		critters_b[15]="Fleet";
		critters_b[16]="Vikings";
		critters_b[17]="Witch";
		critters_b[18]="Sorcerer";
		critters_b[19]="Sorceress";
		critters_b[20]="Wizard";
		critters_b[21]="Scientists";
		critters_b[22]="Merchants";
		critters_b[23]="Guards";
		critters_b[24]="Empire";
		critters_b[25]="Guild";
		critters_b[26]="Army";
		critters_b[27]="Navy";
		critters_b[28]="Children";
		critters_b[29]="Child";
		critters_b[30]="Patrol";
		critters_b[31]="Squad";
		critters_b[32]="Beings";
		critters_b[33]="Folk";
		critters_b[34]="Tribe";
		critters_b[35]="Clan";
		critters_b[36]="Horde";
		critters_b[37]="Nation";
		critters_b[38]="Planet";
		critters_b[39]="World";
		critters_b[40]="Pirates";
		critters_b[41]="Corsairs";
		critters_b[42]="Reavers";
		critters_b[43]="Thieves";
		critters_b[44]="City";
		critters_b[45]="Monsters";
		critters_b[46]="Doctor";
		critters_b[47]="Rovers";
		critters_b[48]="Dancer";
		critters_b[49]="Prophets";
		critters_b[50]="Shaman";
		critters_b[51]="Pilot";
		critters_b[52]="Hunter";
		critters_b[53]="Chief";
		critters_b[54]="Chieftain";
		critters_b[55]="Riders";
		critters_b[56]="Spirits";
		critters_b[57]="Sage";
		critters_b[58]="Bard";
		critters_b[59]="Ships";
		critters_b[60]="Gods";
		critters_b[61]="Sailors";
		critters_b[62]="Legion";
		critters_b[63]="Academy";
		critters_b[64]="Mariners";
		critters_b[65]="Freebooters";
		critters_b[66]="Robots";
		critters_b[67]="Airmen";
		critters_b[68]="Collectors";
		critters_b[69]="Herders";
		critters_b[70]="Scholars";
		critters_b[71]="Farmers";
		critters_b[72]="College";
		critters_b[73]="Senate";
		critters_b[74]="Engineers";
		critters_b[75]="Technician";
		critters_b[76]="Capsules";
		critters_b[77]="Airships";
		critters_b[78]="Odyssey";
		critters_b[79]="Warrens";
		critters_b[80]="Burrows";
		critters_b[81]="Gypsies";
		critters_b[82]="Cult";
		critters_b[83]="Choir";
		critters_b[84]="Archaeologists";
		critters_b[85]="Keepers";
		critters_b[86]="Antiquarians";
		critters_b[87]="Dentists";
		critters_b[88]="Nurses";
		critters_b[89]="Carpenters";
		critters_b[90]="Tinkers";
		critters_b[91]="Ventriloquist";
		critters_b[92]="Illusionist";
		critters_b[93]="Tailor";
		critters_b[94]="Navigator";
		critters_b[95]="Gardener";
		critters_b[96]="Vintners";
		critters_b[97]="Biologist";
		critters_b[98]="Assistant";
		critters_b[99]="Balloonists";
		critters_b[100]="Shipwrights";
		critters_b[101]="Drummer";
		critters_b[102]="Assassins";
		critters_b[103]="Gunsmith";
		critters_b[104]="Locksmiths";
		critters_b[105]="Vampire";
		critters_b[106]="Mutants";
		critters_b[107]="Killers";
		critters_b[108]="Ghouls";
		critters_b[109]="Dolls";
		critters_b[110]="Incubator";
		critters_b[111]="Nomads";
		critters_b[112]="Raiders";
		critters_b[113]="Cult";

		int xcb=rand.nextInt(critters_b.length);

		int structure=rand.nextInt(44);

		if (structure==0)
			newtitle=(preposition1[aa]+" "+noun[bb]+" of the "+adjective[ff]+" "+entity[cc]);
		if (structure==6)
			newtitle=(preposition1[aa]+" "+noun2[bc]+" of the "+adjective[ff]+" "+entity[cc]);
		if (structure==8)
			newtitle=(preposition1[aa]+" "+noun[bb]+" of the "+adjective[ff]+" "+noun2[bc]);
		if (structure==11)
			newtitle=(preposition1[aa]+" "+noun2[bc]+" from the "+adjective[ff]+" "+noun[bb]);
		if (structure==12)
			newtitle=(preposition1[aa]+" "+entity[cc]+" of the "+adjective[ff]+" "+noun2[bc]);
		if (structure==25)
			newtitle=(preposition1[aa]+" "+entity[cc]+" from the "+adjective[ff]+" "+noun[bb]);

		if (structure==1)
			newtitle=("The "+noun[bb]+" of "+possessive[kk]+" "+noun2[bc]);
		if (structure==2)
			newtitle=("The "+entity[cc]+" of "+possessive[kk]+" "+noun2[bc]);
		if (structure==3)
			newtitle=("The "+noun[bb]+" of "+possessive[kk]+" "+entity[cc]);
		if (structure==4)
			newtitle=(possessive[kk]+" "+adjective[ff]+" "+noun2[bc]);
		if (structure==5)
			newtitle=(verb_start[ll]+" in the "+noun[bb]+" of the "+entity[cc]);

		if (structure==7)
			newtitle=(verb_start[ll]+" by the "+entity[cc]+" of the "+noun2[bc]);

		if (structure==9)
			newtitle=("The "+noun[bb]+" of "+possessive[kk]+" "+noun2[bc]);
		if (structure==10)
			newtitle=(preposition1[aa]+" "+entity[cc]+" "+preposition4[jj]+" "+verb[gg]+" "+noun_final[dd]);
		if (structure==13)
			newtitle=(intro[ii]+" "+adjective[ff]+" "+noun2[bc]);
		if (structure==14)
			newtitle=(intro[ii]+" "+adjective[ff]+" "+entity[cd]);
		if (structure==15)
			newtitle=("The "+noun[bb]+" "+preposition3[hh]+" "+verb[gg]+" the "+entity[cd]);
		if (structure==16)
			newtitle=("The "+entity[cc]+" "+preposition4[jj]+" "+verb[gg]+" the "+entity[cd]);
		if (structure==17)
			newtitle=(intro[ii]+" "+entity[cc]);
		if (structure==18)
			newtitle=(intro[ii]+" "+noun[bb]);
		if (structure==19)
			newtitle=(intro[ii]+" "+noun2[bc]);
		if (structure==20)
			newtitle=(preposition1[aa]+" "+adjective[ff]+" "+noun2[bc]+" of "+noun_final[dd]);
		if (structure==21)
			newtitle=(verb_start[ll]+" in the "+adjective[ff]+" "+noun2[bc]);
		if (structure==22)
			newtitle=(verb_start[ll]+" by the "+entity[cc]+" of "+noun_final[dd]);
		if (structure==23)
			newtitle=(verb_start[ll]+" by the "+adjective[ff]+" "+entity[cc]);
		if (structure==24)
			newtitle=(verb_start[ll]+" in the "+noun[bb]+" of "+noun_final[dd]);


		if (structure==26)
			newtitle=(preposition1[aa]+" "+noun[bb]+" "+preposition3[hh]+" "+verb[gg]+" the "+noun2[bc]);
		if (structure==27)
			newtitle=(preposition1[aa]+" "+entity[cc]+" "+preposition4[jj]+" "+verb[gg]+" the "+noun2[bc]);
		if (structure==28)
			newtitle=(preposition1[aa]+" "+entity[cc]+" "+preposition2[ee]+" "+noun2[bc]);
		if (structure==29)
			newtitle=(preposition1[aa]+" "+noun[bb]+" "+preposition2[ee]+" "+noun2[bc]);
		if (structure==30)
			newtitle=(preposition1[aa]+" "+adjective[ff]+" "+entity[cc]);
		if (structure==31)
			newtitle=(preposition1[aa]+" "+adjective[ff]+" "+noun[bb]);
		if (structure==32)
			newtitle=(preposition1[aa]+" "+adjective[ff]+" "+entity[cc]+" of "+noun_final[dd]);
		if (structure==33)
			newtitle=(intro[ii]+" "+noun[bb]+" of "+noun_final[dd]);
		if (structure==34)
			newtitle=(intro[ii]+" "+entity[cc]+" of "+noun_final[dd]);
		if (structure==35)
			newtitle=("The "+adjective[ff]+" "+entity[cc]+" of "+noun_final[dd]); 
		if (structure==36)
			newtitle=("The "+adjective[ff]+" "+noun2[bc]+" of "+noun_final[dd]);

		if (structure==37)
			newtitle=("The "+noun[bb]+" of the "+critters_a[xca]+" "+critters_b[xcb]);
		if (structure==38)
			newtitle=("The "+noun2[bc]+" of the "+critters_a[xca]+" "+critters_b[xcb]);

		if (structure==39)
			newtitle=(preposition1[aa]+" "+critters_a[xca]+" "+critters_b[xcb]);
		if (structure==40)
			newtitle=(intro[ii]+" "+critters_a[xca]+" "+critters_b[xcb]);
		if (structure==41)
			newtitle=(preposition1[aa]+" "+adjective[ff]+" "+critters_a[xca]+" "+critters_b[xcb]);
		if (structure==42)
			newtitle=(possessive[kk]+" "+critters_a[xca]+" "+critters_b[xcb]);
		if (structure==43)
			newtitle=(preposition1[aa]+" "+critters_a[xca]+" "+critters_b[xcb]+" of "+noun_final[dd]);
		  		
		  
		return newtitle;  
	}
}