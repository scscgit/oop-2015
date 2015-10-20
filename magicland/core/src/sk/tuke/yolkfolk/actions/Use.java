/***********************************************************
 * Zadanie na predmet Objektove Programovanie
 *
 * scsc
 * Technicka univerzita v Kosiciach, Fakulta elektrotechniky a informatiky
 *
 * Licencia: Volny softver, Open-Source GNU GPL v3+
 * Vseobecna verejna licencia. Program je dovolene volne sirit a upravovat.
 * Upraveny program / cast programu moze ktokolvek vyuzit ako na osobne,
 * tak aj komercne ucely, ale nemoze ho vydat s vlastnym copyrightom,
 * ktory nie je kompatibilny s GNU GPL v3+.
 * gnu.org/licenses/gpl-faq.html
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see < http://www.gnu.org/licenses/ >.
 */

package sk.tuke.yolkfolk.actions;

import sk.tuke.gamelib2.Actor;
import sk.tuke.gamelib2.Input;
import sk.tuke.gamelib2.Message;
import sk.tuke.yolkfolk.actors.player.Player;
import sk.tuke.yolkfolk.actors.Usable;
import sk.tuke.yolkfolk.collectables.Collectable;

/**
 * Akcia na pouzitie, pripadne zodvihnutie predmetu do inventara.
 *
 * Created by Steve on 2.12.2015.
 */
public class Use extends AbstractAction
{
	public Use()
	{
		super();
	}

	//Akcia, ktora pouzije vsetky Usable predmety v kolizii s actorom
	@Override
	public void doAction(Actor actor)
	{
		//Do all enter key actions that are meant to be done by any Player
		if (actor instanceof Player && (Input.isKeyJustPressed(Input.Key.ENTER) || Input.isKeyJustPressed(Input.Key.E)))
		{
			Player player = (Player) actor;

			//Iterate over each actor. If, by some chance, no collision happened, then show the backpack
			if (!collisionActions(player))
			{
				player.showBackpack();
			}
		}

		//Do implicit action
		super.doAction(actor);
	}

	//Operacie vykonane nad kazdym objektom v kolizii s Playerom
	//@return true if collision happened, in both successful action or error, e.g. picking up item to a full inventory
	private boolean collisionActions(Player player)
	{
		//Store information about happening of collission
		boolean collisionHappened = false;

		for (Actor collisionActor : player.getWorld())
		{
			if (collisionActor != null && collisionActor.intersects(player))
			{
				//Collect collectable actor
				if (collisionActor instanceof Collectable)
				{
					try
					{
						//Try to add actor into backpack, in case of success remove the actor from the world and go to next iteration
						if (player.addToBackpack(collisionActor))
						{
							player.getWorld().removeActor(collisionActor);
							collisionHappened = true;

							//Only collect one
							break;
						}
						else
						{
							//This message should not appear if exception catching works properly
							new Message("Inventory problem", player.getName() + ", your backpack is full!", player);
						}
					}
					catch(ArrayIndexOutOfBoundsException exception)
					{
						collisionHappened = true;
						new Message("Inventory problem", exception.getMessage(), player);
						break;
					}
				}

				//Use usable actors
				if (collisionActor instanceof Usable)
				{
					Usable usable = (Usable) collisionActor;
					usable.use(player);

					//Assume success of usage
					collisionHappened = true;

					//Go to next item
					break;
				}
			}
		}

		return collisionHappened;
	}
}
