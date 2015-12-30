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

package sk.tuke.yolkfolk.actors.characters.ghost;

import sk.tuke.gamelib2.Animation;
import sk.tuke.gamelib2.PhysicsHelper;
import sk.tuke.yolkfolk.actors.AbstractAnimatedActor;

/**
 * General type of a Ghost. By default, is white.
 * <p/>
 * Created by Steve on 30.12.2015.
 */
public class GhostImpl extends AbstractAnimatedActor implements Ghost
{
	//Constants
	public static final String NAME = "Ghost";

	//Variables
	private Direction direction;
	private boolean initialized;

	public GhostImpl()
	{
		super(GhostImpl.NAME, "sprites/ghost_right.png", 48, 42);
		getAnimation().setPingPong(true);

		//Inicializacia premennych
		this.initialized = false;

		//Nastavenie animacie so smerom dolava
		Animation leftAnimation = new Animation("sprites/ghost_left.png", 48, 42);
		leftAnimation.setPingPong(true);
		setAnimationLeft(leftAnimation);
		setAnimationJumpLeft(leftAnimation);

		//Inicializacia bez smeru pohybu, lieta na mieste bez pohybu
		stop();
	}

	//Duchovia sa navzajom zabiju
	public void exchangeKills(Ghost ghost)
	{
		if(ghost != null)
		{
			ghost.removeFromWorld();
			removeFromWorld();
		}
	}

	public final boolean isLeft()
	{
		return this.direction == Direction.LEFT;
	}

	public final boolean isRight()
	{
		return this.direction == Direction.RIGHT;
	}

	public final boolean isStopped()
	{
		return this.direction == Direction.STOPPED;
	}

	protected final void updateAnimation()
	{
		if (isLeft())
		{
			runAnimationLeft();
			stopAnimationRight();
		}
		else if (isRight())
		{
			stopAnimationLeft();
			runAnimationRight();
		}
		else
		{
			stopAnimationLeft();
			stopAnimationRight();
			getAnimation().stop();
			getAnimation().setCurrentFrame(0);
		}
	}

	public final void runLeft()
	{
		this.direction = Direction.LEFT;
		updateAnimation();
	}

	public final void runRight()
	{
		this.direction = Direction.RIGHT;
		updateAnimation();
	}

	public final void stop()
	{
		this.direction = Direction.STOPPED;
		updateAnimation();
	}

	@Override
	public void act()
	{
		//Prvy act sa nevykona
		if(!this.initialized)
		{
			this.initialized = true;
			return;
		}

		try
		{
			//Bude sa pohybovat iba horizontalne v pozadovanom smere svojou rychlostou
			if (isLeft())
			{
				PhysicsHelper.setLinearVelocity(this, -getStep(), 0);
			}
			else if (isRight())
			{
				System.out.println(getWorld().toString());
				PhysicsHelper.setLinearVelocity(this, getStep(), 0);
			}
			else
			{
				PhysicsHelper.setLinearVelocity(this, 0, 0);
			}
		}
		//Chyba, ktora vznikala z dovodu TODO dopln po fixe
		catch (IllegalStateException exception)
		{
			System.out.println(exception.getMessage());
		}
	}
}
