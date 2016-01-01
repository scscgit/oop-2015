/***********************************************************
 * Zadanie na predmet Objektove Programovanie
 * <p/>
 * scsc
 * Technicka univerzita v Kosiciach, Fakulta elektrotechniky a informatiky
 * <p/>
 * Licencia: Volny softver, Open-Source GNU GPL v3+
 * Vseobecna verejna licencia. Program je dovolene volne sirit a upravovat.
 * Upraveny program / cast programu moze ktokolvek vyuzit ako na osobne,
 * tak aj komercne ucely, ale nemoze ho vydat s vlastnym copyrightom,
 * ktory nie je kompatibilny s GNU GPL v3+.
 * gnu.org/licenses/gpl-faq.html
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see < http://www.gnu.org/licenses/ >.
 */

package sk.tuke.yolkfolk.actors;

import sk.tuke.gamelib2.Actor;
import sk.tuke.gamelib2.ActorFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Godlike powers of developers; creating sentient beings, cats and objects just by saying their name.
 * <p/>
 * Created by Steve on 24.12.2015.
 */
public class ActorFactoryImpl /*extends ActionQueue<String, Actor>*/ implements ActorFactory
{

	//Static variables
	private static String registerPath;

	//Internal static Objects
	private static Map<String, Class> hashmap;

	//Staticka inicializacia hashovacej tabulky tovarne cez registraciu jednotlivych tried
	static
	{
		//Initialization
		ActorFactoryImpl.hashmap = null;
		ActorFactoryImpl.registerPath = null;

		//Actor characters
		setRegisterPath("sk.tuke.yolkfolk.actors.characters");
		register("Bird");
		register("Daisy");
		register("Devil");
		register("Prince");
		register("RobinHood");
		register("Troll");
		register("Witch");
		register("Witcher");

		//Special actor character: A default Ghost
		//Should be instead created using Decorator
		setRegisterPath("sk.tuke.yolkfolk.actors.characters.ghost");
		register("GhostImpl");

		//Special actor character: The Monkey
		setRegisterPath("sk.tuke.yolkfolk.actors.characters.monkey");
		register("Monkey");

		//Actor items
		setRegisterPath("sk.tuke.yolkfolk.actors.items");
		register("Diamond");
		register("FinalDoor");
		register("Fire");
		register("GreenDiamond");
		register("Lever");
		register("MagicDoor");
		register("SilverDiamond");
		register("SimpleDoor");
		register("Splash");

		//Actor objects
		setRegisterPath("sk.tuke.yolkfolk.actors.objects");
		register("DaisyElevator");
		register("Elevator");
		register("HorizontalPlatform");
		register("RobinHoodPlatform");
		register("Rubbish");
		register("Well");

		//Players
		setRegisterPath("sk.tuke.yolkfolk.actors.player.players.dizzy");
		register("Dizzy");

		//Spaces
		setRegisterPath("sk.tuke.yolkfolk.spaces");
		register("BirdBorderLeft");
		register("BirdBorderRight");
		register("CinematicZone");
		register("ExitZone");
		register("FlyZone");
		register("InsideZone");
		register("MonkeyStoneSpace");
		register("NoFlyZone");
		register("NoSecretZone");
		register("NoWaterZone");
		register("OutsideZone");
		register("PrinceSpace");
		register("SecretZone");
		register("WaterZone");

		//Collectables
		setRegisterPath("sk.tuke.yolkfolk.collectables");
		register("Apple");
		register("FinalKey");
		register("Key");
		register("MagicKey");
		register("Potion");
		register("Ring");
		register("Stone");

		/*
		Stara verzia:
		//Actor characters
		ActorFactoryImpl.registerClass(Bird.NAME, Bird.class);
		ActorFactoryImpl.registerClass(Daisy.NAME, Daisy.class);
		ActorFactoryImpl.registerClass(Devil.NAME, Devil.class);
		ActorFactoryImpl.registerClass(Monkey.NAME, Monkey.class);
		ActorFactoryImpl.registerClass(Prince.NAME, Prince.class);
		ActorFactoryImpl.registerClass(RobinHood.NAME, RobinHood.class);
		ActorFactoryImpl.registerClass(Troll.NAME, Troll.class);

		//Actor items
		ActorFactoryImpl.registerClass(Diamond.NAME, Diamond.class);
		ActorFactoryImpl.registerClass(Fire.NAME, Fire.class);
		ActorFactoryImpl.registerClass(Lever.NAME, Lever.class);
		ActorFactoryImpl.registerClass(MagicDoor.NAME, MagicDoor.class);
		ActorFactoryImpl.registerClass(SimpleDoor.NAME, SimpleDoor.class);
		ActorFactoryImpl.registerClass(Splash.NAME, Splash.class);

		//Actor objects
		ActorFactoryImpl.registerClass(DaisyElevator.NAME, DaisyElevator.class);
		ActorFactoryImpl.registerClass(Rubbish.NAME, Rubbish.class);
		ActorFactoryImpl.registerClass(Well.NAME, Well.class);

		//Players
		ActorFactoryImpl.registerClass(Dizzy.NAME, Dizzy.class);

		//Spaces
		ActorFactoryImpl.registerClass(BirdBorderLeft.NAME, BirdBorderLeft.class);
		ActorFactoryImpl.registerClass(BirdBorderRight.NAME, BirdBorderRight.class);
		ActorFactoryImpl.registerClass(ExitZone.NAME, ExitZone.class);
		ActorFactoryImpl.registerClass(FlyZone.NAME, FlyZone.class);
		ActorFactoryImpl.registerClass(MonkeyStoneSpace.NAME, MonkeyStoneSpace.class);
		ActorFactoryImpl.registerClass(NoFlyZone.NAME, NoFlyZone.class);
		ActorFactoryImpl.registerClass(NoWaterZone.NAME, NoWaterZone.class);
		ActorFactoryImpl.registerClass(NoSecretZone.NAME, NoSecretZone.class);
		ActorFactoryImpl.registerClass(SecretZone.NAME, SecretZone.class);
		ActorFactoryImpl.registerClass(WaterZone.NAME, WaterZone.class);

		//Collectables
		ActorFactoryImpl.registerClass(Apple.NAME, Apple.class);
		ActorFactoryImpl.registerClass(Key.NAME, Key.class);
		ActorFactoryImpl.registerClass(MagicKey.NAME, MagicKey.class);
		ActorFactoryImpl.registerClass(Stone.NAME, Stone.class);
		*/
	}

	//Singleton implementacia hashovacej tabulky
	private static Map<String, Class> getHashMap()
	{
		if (ActorFactoryImpl.hashmap == null)
		{
			ActorFactoryImpl.hashmap = new HashMap<String, Class>();
		}
		return ActorFactoryImpl.hashmap;
	}

	//Creates a new actor and returns the reference of it
	@Override
	public Actor create(String actorName)
	{
		//Checks for existence of the required actor by his name in the static hash table and uses his class
		Class actorClass = getHashMap().get(actorName);
		if (actorClass != null /*&& actorClass.isAssignableFrom(AbstractActor.class)*/)
		{
			try
			{
				Object object = (actorClass).newInstance();
				if (object instanceof Actor)
				{
					return (Actor) object;
				}
			}
			catch (InstantiationException exception)
			{
				System.out.println(exception.getMessage());
			}
			catch (IllegalAccessException exception)
			{
				System.out.println(exception.getMessage());
			}
		}
		return null;

		//Stara verzia cez akcie:
		//resetActions().addAction(Diamond(s)).addAction(Fire(s)).runActions();
	}

	//Nastavi cestu balicka pre nasledujucu registraciu pomocou statickej metody register()
	public static void setRegisterPath(String registerPath)
	{
		ActorFactoryImpl.registerPath = registerPath;
	}
	public static String getRegisterPath()
	{
		if (ActorFactoryImpl.registerPath != null)
		{
			return ActorFactoryImpl.registerPath + ".";
		}
		return "";
	}

	//Registers a new class, that can be created using a default parameterless constructor. Uses reflection.
	public static void register(String className)
	{
		try
		{
			final Class actorClass = Class.forName(ActorFactoryImpl.getRegisterPath() + className);
			final Field name = actorClass.getField("NAME");
			if (name.getType().isAssignableFrom(String.class))
			{
				ActorFactoryImpl.getHashMap().put((String) name.get(null), actorClass);
			}
		}
		catch (ClassNotFoundException exception)
		{
			System.out.println(exception.toString());
		}
		catch (NoSuchFieldException exception)
		{
			System.out.println(exception.toString());
		}
		catch (IllegalAccessException exception)
		{
			System.out.println(exception.toString());
		}
	}

	//Stara verzia registracie tried, ktora sa nepacila PMD:
	@Deprecated
	public static void registerClass(String actorName, Class actorClass)
	{
		ActorFactoryImpl.getHashMap().put(actorName, actorClass);
	}
}
