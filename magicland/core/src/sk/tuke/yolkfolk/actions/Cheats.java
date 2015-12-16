/***********************************************************
 * Zadanie na predmet Objektove Programovanie
 *
 * Stefan Ciberaj, ZS 2015/2016
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

/**
 * Power Overwhelming.
 *
 * Created by Steve on 3.12.2015.
 */
public class Cheats extends AbstractAction
{
	private static String currentCheat = "";

	@Override
	public void doAction(final Actor actor)
	{
		//Try to read cheat commands from keyboard and execute them
		Cheats.cheatFly(actor);
		Cheats.cheatPos(actor);

		//Do implicit action
		super.doAction(actor);
	}

	//Allows current Player to fly
	private static void cheatFly(final Actor actor)
	{
		if (Input.isKeyPressed(Input.Key.F))
		{
			Cheats.currentCheat = "F";
		}

		if (Input.isKeyPressed(Input.Key.L) && Cheats.currentCheat.compareTo("F") == 0)
		{
			Cheats.currentCheat = "FL";
		}

		if (Input.isKeyPressed(Input.Key.Y) && Cheats.currentCheat.compareTo("FL") == 0)
		{
			Cheats.currentCheat = "";
			if(actor instanceof Player)
			{
				final Player player = (Player) actor;
				player.setFlyable(!player.isFlyable()); //Changes current state of flying
				new Message((player.isFlyable()?"Cheat activated.":"Cheat deactivated."), actor.getName()+(player.isFlyable()?" can now fly!":" can no longer fly :("), actor);
			}
		}
	}

	//Makes current player output his positions for debugging or other purposes
	public static void cheatPos(final Actor actor)
	{
		if (Input.isKeyPressed(Input.Key.P))
		{
			Cheats.currentCheat = "P";
		}

		if (Input.isKeyPressed(Input.Key.O) && Cheats.currentCheat.compareTo("P") == 0)
		{
			Cheats.currentCheat = "PO";
		}

		if (Input.isKeyPressed(Input.Key.S) && Cheats.currentCheat.compareTo("PO") == 0)
		{
			Cheats.currentCheat = "";
			new Message("Positional debugger","X="+actor.getX()+" Y="+actor.getY(),actor);
		}
	}
}
