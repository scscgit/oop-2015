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

package sk.tuke.yolkfolk.cinematic;

import sk.tuke.gamelib2.Actor;
import sk.tuke.gamelib2.World;
import sk.tuke.yolkfolk.actors.player.Player;
import sk.tuke.yolkfolk.spaces.CinematicZone;

/**
 * A true cinematic experience for any Player.
 * <p/>
 * Created by Steve on 31.15.2015.
 */
public class CinematicEffect
{
	//Variables
	private boolean started;
	private boolean finished;
	private int counter;

	//Objects
	private Player player;
	private CinematicZone zone;
	private CinematicStrategy strategy;

	public CinematicEffect(CinematicStrategy strategy, Player player)
	{
		this.started = false;
		this.finished = false;
		this.counter = 0;
		this.player = player;
		this.zone = null;
		findZone();

		setStrategy(strategy);
	}

	public void setStrategy(CinematicStrategy strategy)
	{
		this.strategy = strategy;
		strategy.setPlayer(this.player);
		strategy.setZone(this.zone);
	}
	public CinematicStrategy getStrategy()
	{
		return this.strategy;
	}

	//Checks if the cinematic effect has ended already
	@Deprecated
	public boolean isFinished()
	{
		return this.finished;
	}

	//World is implicitly player's
	public World getWorld()
	{
		if(this.player != null)
		{
			return this.player.getWorld();
		}
		return null;
	}

	//Finds and assigns a new Cinematic Zone, returns true if it was found
	public boolean findZone()
	{
		for (Actor actor : getWorld())
		{
			if (actor instanceof CinematicZone)
			{
				this.zone = (CinematicZone) actor;
				return true;
			}
		}
		return false;
	}

	//Zoberie ovladanie hracovi
	public void interruptPlayer()
	{
		if (this.player != null && this.zone != null)
		{
			getWorld().centerOn(zone);
			this.player.interpret("set state frozen");
		}
	}

	//Vrati ovladanie hracovi
	public void unfreezePlayer()
	{
		if (this.player != null)
		{
			getWorld().centerOn(this.player);
			this.player.setState(this.player.defaultState());
		}
	}

	//Spusti cinematic obraz
	public void act()
	{
		if(!this.finished)
		{
			//Oboznamim strategiu o aktualnom pocitadle
			getStrategy().setCounter(this.counter);

			//Zaciatok cinematic efektu
			if (!this.started)
			{
				this.started = true;
				getStrategy().startup();
				//Zoberie ovladanie hracovi a presunie kameru na cinematic oblast
				interruptPlayer();
			}
			//Priebeh cinematic efektu
			else if (this.counter < getStrategy().getDuration())
			{
				getStrategy().act();

			}
			//Navrat kontroly nad hrou hracovi
			else
			{
				this.finished = true;
				getStrategy().cleanup();
				//Vrati kameru a ovladanie hracovi
				unfreezePlayer();
			}

			//Navysim pocitadlo
			this.counter++;
		}
	}
}
