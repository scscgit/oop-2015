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

package sk.tuke.yolkfolk.actors.player.states;

import sk.tuke.gamelib2.PhysicsHelper;
import sk.tuke.yolkfolk.input.CustomInput;
import sk.tuke.yolkfolk.actions.*;
import sk.tuke.yolkfolk.actors.player.Player;

/**
 * State of being high.
 * Cannot get higher or change direction.
 * Also cannot use items.
 *
 * Created by Steve on 11.12.2015.
 */
public class PlayerJumping extends AbstractAirborneState
{
	public PlayerJumping(Player player, PlayerWalking.Direction direction)
	{
		super(player);

		//Vykona skok v zadanom smere
		jump(direction);

		//Nastavi relevantne animacie
		updateAnimation(direction);
	}

	protected void updateAnimation(PlayerWalking.Direction direction)
	{
		if(direction == PlayerWalking.Direction.UP)
		{
			getPlayer().stopAnimationLeft();
			getPlayer().stopAnimationRight();
		}
		else if(direction == PlayerWalking.Direction.LEFT)
		{
			getPlayer().runAnimationLeft();
			getPlayer().stopAnimationRight();
		}
		else if(direction == PlayerWalking.Direction.RIGHT)
		{
			getPlayer().stopAnimationLeft();
			getPlayer().runAnimationRight();
		}
		getPlayer().runAnimationJump();
	}

	//Vykona skok v zadanom smere
	protected void jump(PlayerWalking.Direction direction)
	{
		//Povodne rychlosti
		//float oldVerticalVelocity = PhysicsHelper.getLinearVelocity(getPlayer())[1];
		//float oldHorizontalVelocity = PhysicsHelper.getLinearVelocity(getPlayer())[0];

		//Zastavi pohyb pred skokom z dovodu ako funguje kniznica sprostredkujuca PhysicsHelper
		stopVelocity();

		//Vyvinie silu v smere proti gravitacii
		PhysicsHelper.applyForce(getPlayer(), 0f, getPlayer().getJumpHeight());

		//V pripade skoku v zadanom horizontalnom smere explicitne zacnem dany pohyb
		setVelocity(direction);
	}
}
