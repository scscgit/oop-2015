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
import sk.tuke.yolkfolk.actors.characters.*;
import sk.tuke.yolkfolk.actors.items.*;
import sk.tuke.yolkfolk.actors.objects.Elevator;
import sk.tuke.yolkfolk.actors.player.players.dizzy.Dizzy;
import sk.tuke.yolkfolk.collectables.Apple;
import sk.tuke.yolkfolk.collectables.Key;

import java.util.HashMap;
import java.util.Map;

/**
 * Godlike powers of developers; creating sentient beings, cats and objects just by saying their name.
 * <p/>
 * Created by Steve on 24.12.2015.
 */
public class ActorFactoryImpl /*extends ActionQueue<String, Actor>*/ implements ActorFactory
{
	//Internal static Objects
	private static Map<String, Class> hashmap;

	//Staticka inicializacia hashovacej tabulky tovarne cez registraciu jednotlivych tried
	static
	{
		//Initialization
		ActorFactoryImpl.hashmap = null;

		//Actor characters
		ActorFactoryImpl.register(Bird.name, Bird.class);
		ActorFactoryImpl.register(Daisy.name, Daisy.class);
		ActorFactoryImpl.register(Devil.name, Devil.class);
		ActorFactoryImpl.register(Monkey.name, Monkey.class);
		ActorFactoryImpl.register(Troll.name, Troll.class);

		//Actor items
		ActorFactoryImpl.register(BirdBorderLeft.name, BirdBorderLeft.class);
		ActorFactoryImpl.register(BirdBorderRight.name, BirdBorderRight.class);
		ActorFactoryImpl.register(Diamond.name, Diamond.class);
		ActorFactoryImpl.register(Door.name, Door.class);
		ActorFactoryImpl.register(Fire.name, Fire.class);
		ActorFactoryImpl.register(Lever.name, Lever.class);
		ActorFactoryImpl.register(Splash.name, Splash.class);

		//Actor objects
		ActorFactoryImpl.register(Elevator.name, Elevator.class);

		//Players
		ActorFactoryImpl.register(Dizzy.name, Dizzy.class);

		//Collectables
		ActorFactoryImpl.register(Apple.name, Apple.class);
		ActorFactoryImpl.register(Key.name, Key.class);
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

	//Registers a new class, that can be created using a default parameterless constructor
	public static void register(String actorName, Class actorClass)
	{
		ActorFactoryImpl.getHashMap().put(actorName, actorClass);
	}
}
