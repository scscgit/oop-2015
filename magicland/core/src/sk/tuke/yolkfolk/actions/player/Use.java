/***********************************************************
 * Zadanie na predmet Objektove Programovanie
 * <p/>
 * Stefan Ciberaj, ZS 2015/2016
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

package sk.tuke.yolkfolk.actions.player;

import sk.tuke.gamelib2.Actor;
import sk.tuke.gamelib2.Message;
import sk.tuke.yolkfolk.actions.AbstractAction;
import sk.tuke.yolkfolk.actors.Usable;
import sk.tuke.yolkfolk.actors.player.Player;
import sk.tuke.yolkfolk.collectables.Collectable;

/**
 * Akcia na pouzitie objektu, pripadne zodvihnutie predmetu a jeho ulozenie do inventara.
 * <p/>
 * Created by Steve on 2.12.2015.
 */
public class Use extends AbstractAction<Actor, Void>
{
	//Objects
	private static Message lastError;

	static
	{
		Use.lastError = null;
	}

	public Use()
	{
		super();
	}

	//Akcia, ktora pouzije vsetky Usable predmety v kolizii s actorom
	@Override
	public Void doAction(Actor actor)
	{
		//Do all enter key actions that are meant to be done by any Player
		if (actor instanceof Player)
		{
			Player player = (Player) actor;

			//Iterate over each actor. If, by some chance, no collision happened, then show the backpack
			if (playerInput(player).enterRising() && !collisionActions(player))
			{
				player.showBackpack();
			}
		}

		//Do implicit action
		return super.doAction(actor);
	}

	public static Message newMessage(String title, String message, Actor actor)
	{
		if(Use.lastError != null)
		{
			Use.lastError.remove();
		}
		return Use.lastError = new Message(title, message, actor);
	}

	//Tries to collect an actor
	protected boolean collect(Player player, Collectable collisionActor)
	{
		try
		{
			//Try to add actor into backpack, in case of success remove the actor from the world and go to next iteration
			if (player.addToBackpack(collisionActor))
			{
				player.getWorld().removeActor(collisionActor);

				//Only collect one item
				return true;
			}
			else
			{
				//This message should not appear if exception catching works properly
				newMessage("Inventory problem", player.getName() + ", your backpack is full!", player);
			}
		}
		catch (ArrayIndexOutOfBoundsException exception)
		{
			newMessage("Inventory problem", exception.getMessage(), player);
			//V pripade plneho inventara, resp. podobnej chyby vratim false a necham pouzit Usable actorov
		}
		return false;
	}

	//Tried to use an actor
	protected boolean use(Player player, Usable usable)
	{
		usable.use(player);

		//Assume success of usage (use returns void)
		return true;
	}

	//Operacie vykonane nad kazdym objektom v kolizii s Playerom
	//@return true if collision happened, in both successful action or error, e.g. picking up item to a full inventory
	private boolean collisionActions(Player player)
	{
		//Store information about happening of collision
		boolean collisionHappened = false;

		//First, try to collect any collectable items
		for (Actor collisionActor : player.getWorld())
		{
			//Collect collectable actor
			if (collisionActor instanceof Collectable && collisionActor.intersects(player) && collect(player, ((Collectable)collisionActor)))
			{
					collisionHappened = true;
					break;
			}
		}

		//If no collectable items were found, try to find usable items in the vicinity of the Player
		if(!collisionHappened)
		{
			for (Actor collisionActor : player.getWorld())
			{
				//Use usable actors
				if (collisionActor instanceof Usable && collisionActor.intersects(player) && use(player, ((Usable)collisionActor)))
				{
					collisionHappened = true;
					break;
				}
			}
		}

		return collisionHappened;
	}
}
