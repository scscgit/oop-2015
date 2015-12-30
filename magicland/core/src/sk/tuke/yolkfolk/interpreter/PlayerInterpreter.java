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

package sk.tuke.yolkfolk.interpreter;

import sk.tuke.yolkfolk.actors.player.Player;
import sk.tuke.yolkfolk.actors.player.states.PlayerState;

/**
 * Interprets stuff mainly for our hero, Dizzy. Implementation works for any Player in general though, of course.
 * <p/>
 * Created by Steve on 23.12.2015.
 */
public class PlayerInterpreter extends ActorInterpreter
{
	public PlayerInterpreter(Player player)
	{
		super(player);
	}

	public Player getPlayer() throws InterpreterException
	{
		if(getActor() instanceof Player)
		{
			return (Player) getActor();
		}
		throw new InterpreterInvalidInstructionsException("Actor "+getActor().getName()+" is not a Player.");
	}

	//Command for understanding the direction
	protected PlayerState.Direction executeDirection() throws InterpreterException
	{
		assertNext();
		String cmd = remove();

		if (cmd.equals("left"))
		{
			return PlayerState.Direction.LEFT;
		}
		else if (cmd.equals("right"))
		{
			return PlayerState.Direction.RIGHT;
		}
		else if (cmd.equals("up"))
		{
			return PlayerState.Direction.UP;
		}
		else
		{
			throw new InterpreterInvalidInstructionsException("Direction <" + cmd + "> was not valid.");
		}
	}

	//Command for changing the state of a player
	protected void executeState() throws InterpreterException
	{
		assertNext();
		String cmd = remove();

		if (cmd.equals("dying"))
		{
			getPlayer().changeState().setStateDying();
		}
		else if (cmd.equals("falling"))
		{
			getPlayer().changeState().setStateFalling();
		}
		else if (cmd.equals("jumping"))
		{
			getPlayer().changeState().setStateJumping(executeDirection());
		}
		else if (cmd.equals("standing"))
		{
			getPlayer().changeState().setStateStanding();
		}
		else if (cmd.equals("walking"))
		{
			getPlayer().changeState().setStateWalking(executeDirection());
		}
		else if (cmd.equals("frozen"))
		{
			getPlayer().changeState().setStateFrozen();
		}
		else
		{
			throw new InterpreterInvalidInstructionsException("State <" + cmd + "> was not valid.");
		}
	}
}
