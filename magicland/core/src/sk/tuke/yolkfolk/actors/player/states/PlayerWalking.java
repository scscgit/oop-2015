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
 * State of walking in any direction, but not upwards.
 *
 * Created by Steve on 11.12.2015.
 */
public class PlayerWalking extends AbstractPlayerState
{
	private Direction direction;

	//Enumerator vyuzivany aj v tejto, aj v ostatnych triedach pracujucich so smerom pohybu
	public enum Direction
	{
		LEFT, RIGHT, UP
	}

	public PlayerWalking(Player player, Direction direction)
	{
		super(player);
		this.direction = direction;

		//Spusti pohyb v zadanom smere
		setVelocity(direction);

		//Nastavi relevantne animacie
		updateAnimation();
	}

	//Nastavi sa pohyb v pozadovanom smere
	protected void updateAnimation()
	{
		getPlayer().stopAnimationLeft();
		getPlayer().stopAnimationRight();
		getPlayer().stopAnimationJump();

		if(this.direction == Direction.LEFT)
		{
			getPlayer().runAnimationLeft();
		}
		else if(this.direction == Direction.RIGHT)
		{
			getPlayer().runAnimationRight();
		}
	}

	//Vykona pohyb podla stlacenej klavesy, pricom zachova rychlost v osi y
	protected void walk()
	{
		if(CustomInput.left())
		{
			if(CustomInput.up())
			{
				setStateJumping(Direction.LEFT);
			}
			else
			{
				setStateWalking(Direction.LEFT);
			}
		}
		else if(CustomInput.right())
		{
			if(CustomInput.up())
			{
				setStateJumping(Direction.RIGHT);
			}
			else
			{
				setStateWalking(Direction.RIGHT);
			}
		}
		else
		{
			setStateStanding();
		}
	}

	//Nastavi horizontalnu rychlost hraca na pohyb v danom smere
	protected void setVelocity(Direction direction)
	{
		if(direction == Direction.LEFT)
		{
			PhysicsHelper.setLinearVelocity(getPlayer(), -getPlayer().getStep(), PhysicsHelper.getLinearVelocity(getPlayer())[1]);
		}
		else if(direction == Direction.RIGHT)
		{
			PhysicsHelper.setLinearVelocity(getPlayer(), getPlayer().getStep(), PhysicsHelper.getLinearVelocity(getPlayer())[1]);
		}
	}

	protected void setStateJumping(Direction direction)
	{
		getPlayer().setState(new PlayerJumping(getPlayer(),direction));
	}

	protected void setStateStanding()
	{
		getPlayer().setState(new PlayerStanding(getPlayer(),null));
	}

	protected void setStateWalking(Direction direction)
	{
		getPlayer().setState(new PlayerWalking(getPlayer(), direction));
	}

	@Override
	public void act()
	{
		//Vykona vsetky zakladne operacie
		resetActions();
		addAction(new Use());
		addAction(new Exit());
		addAction(new Cheats());
		runActions();

		//Necha playera vykonat svoje operacie po novom pohybe
		getPlayer().afterMovement();

		walk();
	}
}
