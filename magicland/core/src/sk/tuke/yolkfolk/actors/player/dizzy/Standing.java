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

package sk.tuke.yolkfolk.actors.player.dizzy;

import sk.tuke.gamelib2.Animation;
import sk.tuke.yolkfolk.actors.player.Player;
import sk.tuke.yolkfolk.actors.player.states.PlayerStanding;
import sk.tuke.yolkfolk.actors.player.states.PlayerWalking;

/**
 * Created by Steve on 15.12.2015.
 */
public class Standing extends PlayerStanding implements DizzyState
{
	public Standing(Player player)
	{
		super(player, new Animation("sprites/dozy.png",22,15));
		System.out.println(getPlayer().getName()+" is in state Standing");
	}

	@Override
	protected void setStateJumping(PlayerWalking.Direction direction)
	{
		getPlayer().setState(new Jumping(getPlayer(),direction));
	}

	@Override
	protected void setStateWaking(PlayerWalking.Direction direction)
	{
		getPlayer().setState(new Walking(getPlayer(),direction));
	}
}
