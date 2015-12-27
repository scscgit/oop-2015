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

package sk.tuke.yolkfolk.interpreter;

import sk.tuke.gamelib2.Actor;
import sk.tuke.gamelib2.World;
import sk.tuke.yolkfolk.actors.AbstractActor;
import sk.tuke.yolkfolk.actors.player.Player;

/**
 * Interprets stuff for Actors.
 * <p/>
 * Created by Steve on 23.12.2015.
 */
public class ActorInterpreter extends VM
{
	//Objects
	//Current actor, that is the subject of commands
	private Actor actor;

	public ActorInterpreter(Actor actor)
	{
		this.actor = actor;
	}

	protected World getWorld()
	{
		return getActor().getWorld();
	}

	public Actor getActor()
	{
		return this.actor;
	}

	protected final Actor getActorByName(String name)
	{
		return AbstractActor.getActorByName(name, getWorld());
	}

	@Override
	protected void executePlayer() throws InterpreterException
	{
		assertNext();
		String cmd = remove();
		Player player = (Player) getActorByName(cmd);

		if (player != null)
		{
			//Lets player execute his instructions
			new PlayerInterpreter(player).execute();
		}
		else
		{
			throw new InterpreterInvalidInstructionsException("Player <" + cmd + "> could not be found.");
		}
	}
}
