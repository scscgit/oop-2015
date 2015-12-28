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

package sk.tuke.yolkfolk.actions.player;

import sk.tuke.gamelib2.Actor;
import sk.tuke.gamelib2.Input;
import sk.tuke.gamelib2.Message;
import sk.tuke.yolkfolk.actions.AbstractAction;
import sk.tuke.yolkfolk.actors.AbstractActor;
import sk.tuke.yolkfolk.actors.characters.monkey.Monkey;
import sk.tuke.yolkfolk.actors.player.Player;

/**
 * Power Overwhelming.
 * <p/>
 * Created by Steve on 3.12.2015.
 */
public class Cheats extends AbstractAction<Actor, Void>
{
	//Variables
	private static String currentCheat;

	//Objects
	private static Message currentMessage;

	static
	{
		Cheats.currentCheat = "";
		Cheats.currentMessage = null;
	}

	@Override
	public Void doAction(final Actor actor)
	{
		//Try to read cheat commands from keyboard and execute them
		Cheats.cheatFly(actor);
		Cheats.cheatPos(actor);
		Cheats.cheatKill(actor);
		Cheats.cheatMon(actor);

		//Do implicit action
		return super.doAction(actor);
	}

	//Singleton sprava pre pouzivatela
	private static void newMessage(String title, String message, Actor actor)
	{
		if (Cheats.currentMessage != null)
		{
			Cheats.currentMessage.remove();
		}
		Cheats.currentMessage = new Message(title, message, actor);
	}

	//Allows current Player to fly
	private static void cheatFly(final Actor actor)
	{
		if (Input.isKeyJustPressed(Input.Key.F))
		{
			Cheats.currentCheat = "F";
		}

		if (Input.isKeyJustPressed(Input.Key.L) && Cheats.currentCheat.compareTo("F") == 0)
		{
			Cheats.currentCheat = "FL";
		}

		if (Input.isKeyJustPressed(Input.Key.Y) && Cheats.currentCheat.compareTo("FL") == 0)
		{
			Cheats.currentCheat = "";
			if (actor instanceof Player)
			{
				final Player player = (Player) actor;
				player.setFlyable(!player.isFlyable()); //Changes current state of flying
				Cheats.newMessage((player.isFlyable() ? "Cheat activated." : "Cheat deactivated."),
				                  actor.getName() + (player.isFlyable() ? " can now fly!" : " can no longer fly :("),
				                  actor);
			}
		}
	}

	//Makes current player output his positions for debugging or other purposes
	public static void cheatPos(final Actor actor)
	{
		Player player = (Player) actor;
		if (Input.isKeyJustPressed(Input.Key.P))
		{
			Cheats.currentCheat = "P";
		}

		if (Input.isKeyJustPressed(Input.Key.O) && Cheats.currentCheat.compareTo("P") == 0)
		{
			Cheats.currentCheat = "PO";
		}

		if (Input.isKeyJustPressed(Input.Key.S) && Cheats.currentCheat.compareTo("PO") == 0)
		{
			Cheats.currentCheat = "";
			Cheats.newMessage("Positional debugger",
			                  player.toString() + "\nX=" + player.getX() + " Y=" + actor.getY() + "\nState " +
			                  player.getState().toString()                                                   +
			                  "\nstandsOnSolid "                                                             +
			                  (player.standsOnSolid() ? "yes" : "no")                                        +
			                  "\nisOnGround "                                                                +
			                  (player.isOnGround() ? "yes" : "no"), player);
		}
	}

	//Makes current player commit seppuku
	public static void cheatKill(final Actor actor)
	{
		if (Input.isKeyJustPressed(Input.Key.K))
		{
			Cheats.currentCheat = "K";
		}

		if (Input.isKeyJustPressed(Input.Key.I) && Cheats.currentCheat.compareTo("K") == 0)
		{
			Cheats.currentCheat = "KI";
		}

		if (Input.isKeyJustPressed(Input.Key.L) && Cheats.currentCheat.compareTo("KI") == 0)
		{
			Cheats.currentCheat = "KIL";
		}

		if (Input.isKeyJustPressed(Input.Key.L) && Cheats.currentCheat.compareTo("KIL") == 0)
		{
			Cheats.currentCheat = "";
			if (actor instanceof Player)
			{
				final Player player = (Player) actor;
				player.decreaseEnergy(Player.MAX_HP);
			}
		}
	}

	//Finds a monkey in the world of an actor
	private static Monkey getMonkey(Actor actorInWorld)
	{
		if (actorInWorld instanceof AbstractActor)
		{
			final Actor monkey = ((AbstractActor) actorInWorld).getActorByName(Monkey.NAME);
			if (monkey instanceof Monkey)
			{
				return (Monkey) monkey;
			}
		}
		return null;
	}

	//Cheats the monkey
	private static void cheatMon(final Actor actor)
	{
		if (Input.isKeyJustPressed(Input.Key.M))
		{
			Cheats.currentCheat = "M";
		}

		if (Input.isKeyJustPressed(Input.Key.O) && Cheats.currentCheat.compareTo("M") == 0)
		{
			Cheats.currentCheat = "MO";
		}

		if (Input.isKeyJustPressed(Input.Key.N) && Cheats.currentCheat.compareTo("MO") == 0)
		{
			Cheats.currentCheat = "";
			final Monkey monkey = getMonkey(actor);
			if (monkey != null)
			{
				monkey.cheatMe();
			}
		}
	}
}
