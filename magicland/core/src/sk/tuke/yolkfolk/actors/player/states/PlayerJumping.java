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

import sk.tuke.gamelib2.PhysicsHelper;
import sk.tuke.yolkfolk.input.CustomInput;
import sk.tuke.yolkfolk.actions.*;
import sk.tuke.yolkfolk.actors.player.Player;

/**
 * State of being high.
 * Cannot get higher or change direction.
 *
 * Created by Steve on 11.12.2015.
 */
public class PlayerJumping extends AbstractPlayerState
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
		//V pripade skoku v zadanom horizontalnom smere explicitne zacnem dany pohyb
		setVelocity(direction);

		//Vyvinie silu v smere proti gravitacii
		PhysicsHelper.applyForce(getPlayer(), 0f, getPlayer().getJumpHeight());

		//Necha playera vykonat svoje operacie po novom pohybe
		getPlayer().afterMovement();
	}

	//Podla stlacenej klavesy umozni dodatocne skoky z dovodu lietania
	protected void fly()
	{
		if(CustomInput.left())
		{
			setStateJumping(PlayerWalking.Direction.LEFT);
		}
		else if(CustomInput.right())
		{
			setStateJumping(PlayerWalking.Direction.RIGHT);
		}
		else
		{
			setStateJumping(PlayerWalking.Direction.UP);
		}
	}

	//Nastavi horizontalnu rychlost hraca na pohyb v danom smere
	protected void setVelocity(PlayerWalking.Direction direction)
	{
		if(direction == PlayerWalking.Direction.LEFT)
		{
			PhysicsHelper.setLinearVelocity(getPlayer(), -getPlayer().getStep(), PhysicsHelper.getLinearVelocity(getPlayer())[1]);
		}
		else if(direction == PlayerWalking.Direction.RIGHT)
		{
			PhysicsHelper.setLinearVelocity(getPlayer(), getPlayer().getStep(), PhysicsHelper.getLinearVelocity(getPlayer())[1]);
		}
	}

	protected void setStateJumping(PlayerWalking.Direction direction)
	{
		getPlayer().setState(new PlayerJumping(getPlayer(),direction));
	}

	protected void setStateStanding()
	{
		getPlayer().setState(new PlayerStanding(getPlayer(),null));
	}

	@Override
	public void act()
	{
		resetActions();
		addAction(new Use());
		addAction(new Exit());
		addAction(new Cheats());
		runActions();

		//Pripad lietania
		if(getPlayer().isFlyable() && CustomInput.up())
		{
			fly();
		}

		//Zastavi skok iba po dopade na zem //TODO: reimplement after gradle fix gets released
		float ySpeed = PhysicsHelper.getLinearVelocity(getPlayer())[1];
		if(ySpeed==0)
		//if(getPlayer().isOnGround())
		{
			//getPlayer().stopAnimationJump();
			setStateStanding();
		}

		//Necha playera vykonat svoje operacie po novom pohybe
		getPlayer().afterMovement();
	}
}
