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

package sk.tuke.yolkfolk.actors.player.states;

import sk.tuke.yolkfolk.actors.player.Player;

/**
 * List of states a normal Player can usually choose from to be in.
 * <p/>
 * Created by Steve on 23.12.2015.
 */
public class PlayerStates
{
	//Variables
	//Reprezentacia hraca, ktoreho stav sa bude nastavovat
	private Player player;

	public PlayerStates(Player player)
	{
		this.player = player;
	}

	protected Player getPlayer()
	{
		return this.player;
	}

	public void setStateJumping(PlayerState.Direction direction)
	{
		getPlayer().setState(new PlayerJumping(getPlayer(), direction));
	}

	public void setStateWalking(PlayerState.Direction direction)
	{
		getPlayer().setState(new PlayerWalking(getPlayer(), direction));
	}

	public void setStateStanding()
	{
		getPlayer().setState(new PlayerStanding(getPlayer(), null));
	}

	public void setStateDying()
	{
		getPlayer().setState(new PlayerDying(getPlayer(), null));
	}

	public void setStateFalling()
	{
		getPlayer().setState(new PlayerFalling(getPlayer()));
	}

	public void setStateFrozen()
	{
		getPlayer().setState(new PlayerFrozen(getPlayer()));
	}
}
