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

package sk.tuke.yolkfolk.actors.player.states;

import sk.tuke.gamelib2.Animation;
import sk.tuke.yolkfolk.actions.AbstractAction;
import sk.tuke.yolkfolk.actors.player.Player;
import sk.tuke.yolkfolk.actors.player.states.PlayerState;

/**
 * Abstract implementation for each of the Player States.
 * Defines methods for an easy queue of Actions.
 *
 * Created by Steve on 11.12.2015.
 */
public abstract class AbstractPlayerState implements PlayerState
{
	private Player player;
	private AbstractAction first;
	private AbstractAction last;

	public AbstractPlayerState(Player player)
	{
		this.player = player;
		this.first = null;
		this.last = null;
	}

	public Player getPlayer()
	{
		return this.player;
	}

	//Deletes all actions and lets garbage collector do the job it was meant to do
	public void resetActions()
	{
		this.first = null;
	}

	//Adds a new action to the "linked" list
	public void addAction(AbstractAction action)
	{
		//Implicitly throws exception in case of wrong type
		if(this.first == null)
		{
			//If no action was added yet,
			this.first = action;
			this.last = action;
		}
		else
		{
			this.last.setNextAction(action);
			this.last = action;
		}
	}

	//Runs all actions on player
	public void runActions()
	{
		if(this.first instanceof AbstractAction)
		{
			this.first.doAction(getPlayer());
		}
	}

	@Override
	public void act()
	{

	}
}
