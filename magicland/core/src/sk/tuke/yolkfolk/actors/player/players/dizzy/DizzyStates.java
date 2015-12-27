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

package sk.tuke.yolkfolk.actors.player.players.dizzy;

import sk.tuke.yolkfolk.actors.player.Player;
import sk.tuke.yolkfolk.actors.player.states.PlayerState;
import sk.tuke.yolkfolk.actors.player.states.PlayerStates;

/**
 * List of all states Dizzy can voluntarily choose from.
 * <p/>
 * Created by Steve on 23.12.2015.
 */
public class DizzyStates extends PlayerStates
{
	public DizzyStates(Player player)
	{
		super(player);
	}

	@Override
	public void setStateJumping(PlayerState.Direction direction)
	{
		getPlayer().setState(new Jumping(getPlayer(), direction));
	}

	@Override
	public void setStateWalking(PlayerState.Direction direction)
	{
		getPlayer().setState(new Walking(getPlayer(), direction));
	}

	@Override
	public void setStateStanding()
	{
		getPlayer().setState(new Standing(getPlayer()));
	}

	@Override
	public void setStateDying()
	{
		getPlayer().setState(new Dying(getPlayer()));
	}

	@Override
	public void setStateFalling()
	{
		getPlayer().setState(new Falling(getPlayer()));
	}
}
