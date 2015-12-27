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

package sk.tuke.yolkfolk.actors.player.states;

import sk.tuke.yolkfolk.actors.player.Player;

/**
 * State of walking in pretty much any direction, but not upwards.
 * <p/>
 * Created by Steve on 11.12.2015.
 */
public class PlayerWalking extends AbstractGroundState
{
	//Variables
	private PlayerState.Direction direction;

	public PlayerWalking(Player player, PlayerState.Direction direction)
	{
		super(player);
		this.direction = direction;

		//Spusti pohyb v zadanom smere
		refreshVelocity();

		//Nastavi relevantne animacie
		updateAnimation();
	}

	//Nastavi sa animacia pohybu v pozadovanom smere
	private void updateAnimation()
	{
		//Vypne animaciu skoku
		getPlayer().stopAnimationJump();

		//Aktualizuje smer animacie
		updateAnimDir(this.direction);
	}

	//Zahajenie pohybu v pozadovanom smere
	private void refreshVelocity()
	{
		if (!getPlayer().noHorizontalChange() || getPlayer().standsOnSolid())
		{
			setVelocity(this.direction);
		}
		else
		{
			//Is practically Standing, but really just walking into a wall
			stopVelocity(true);
		}
	}

	//Hrac vykona pohyb podla stlacenej klavesy, pricom zachova rychlost v osi Y
	@Override
	protected void keyboardActions()
	{
		if (input().leftNotRight())
		{
			if (input().upwardNotDownward())
			{
				//changeState().setStateJumping(Direction.LEFT);
				interpret("set state jumping left");
			}
			else
			{
				//changeState().setStateWalking(Direction.LEFT);
				interpret("set state walking left");
			}
		}
		else if (input().rightNotLeft())
		{
			if (input().upwardNotDownward())
			{
				//changeState().setStateJumping(Direction.RIGHT);
				interpret("set state jumping right");
			}
			else
			{
				//changeState().setStateWalking(Direction.RIGHT);
				interpret("set state walking right");
			}
		}
		//V pripade ziadneho pohybu zmeni stav na Standing
		else
		{
			//changeState().setStateStanding();
			interpret("set state standing");
		}
	}

	@Override
	public void act()
	{
		//Aby sa niekde nezasekol, tak reinicializuje svoju rychlost
		refreshVelocity();

		//Necha hraca vykonat svoje operacie po novom pohybe
		getPlayer().afterMovement();

		//Vykona operacie, ktore je potrebne vykonat na zemi
		super.act();
	}
}
