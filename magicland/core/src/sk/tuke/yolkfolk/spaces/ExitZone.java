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

package sk.tuke.yolkfolk.spaces;

import sk.tuke.gamelib2.Actor;
import sk.tuke.gamelib2.Message;
import sk.tuke.yolkfolk.actors.characters.Daisy;
import sk.tuke.yolkfolk.actors.player.Player;

/**
 * Game over, good version.
 * <p/>
 * Created by Steve on 28.12.2015.
 */
public class ExitZone extends AbstractSpace
{
	//Constants
	public static final String NAME = "ExitZone";

	//Objects
	Message message;

	//Inicializacia
	{
		this.message = null;
	}

	public ExitZone()
	{
		super(ExitZone.NAME);
	}

	protected void removeMessage()
	{
		if (this.message != null)
		{
			this.message.remove();
		}
	}

	protected Message newMessage(String title, String message, Actor actor)
	{
		removeMessage();
		return this.message = new Message(title, message, actor);
	}

	//Operacie, ktore nastanu ked je hrac na konci. Ak daisy nie je null, tak je s nim tiez.
	protected void playerAtExit(Player player, Daisy daisy)
	{
		//Ak je tam spolu s Daisy
		if (daisy != null)
		{
			//Hra skoncila uspechom
			player.wonTheGame();

			//Exit zona uz nie je potrebna
			getWorld().removeActor(this);
			//return;
		}
		//Ked je hrac konci sam, tak je problem.
		else
		{
			newMessage("Time to go home?",
			           getPlayer().getName() + " feels alone.\nHe should not run away like this.",
			           player);
		}
	}

	@Override
	protected void playerIntersects(Player player)
	{
	}
	@Override
	public void act()
	{
		Player player = null;
		Daisy daisy = null;

		//Sprava neostane zobrazena ked hrac nie je v Exit zone
		removeMessage();

		//Dynamicke nacitanie actorov, aktualne sa nachadzajucich na konci
		for (Actor actor : getWorld())
		{
			if (player == null && actor instanceof Player && actor.intersects(this))
			{
				player = (Player) actor;
			}
			if (daisy == null && actor instanceof Daisy && actor.intersects(this))
			{
				daisy = (Daisy) actor;
			}
		}

		//Ak je hrac na konci
		if (player != null)
		{
			playerAtExit(player, daisy);
		}
	}
}
