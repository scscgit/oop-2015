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

package sk.tuke.yolkfolk.actors.characters.ghost;

import sk.tuke.gamelib2.Actor;
import sk.tuke.gamelib2.Animation;
import sk.tuke.gamelib2.PhysicsHelper;
import sk.tuke.gamelib2.World;
import sk.tuke.yolkfolk.actors.AbstractAnimatedActor;
import sk.tuke.yolkfolk.actors.characters.Prince;
import sk.tuke.yolkfolk.actors.player.Player;

/**
 * General type of a Ghost. By default, is White.
 * <p/>
 * Created by Steve on 30.12.2015.
 */
public class GhostImpl extends AbstractAnimatedActor implements Ghost
{
	//Constants
	public static final String NAME = "Ghost";

	//Variables
	private Direction direction;
	private boolean isEnemy;
	private boolean isAlly;
	private boolean inWorld;

	//Initialization
	{
		this.isEnemy = false;
		this.isAlly = false;
		this.inWorld = false;
	}

	public GhostImpl()
	{
		super(GhostImpl.NAME, "sprites/ghost_right.png", 48, 42);
		getAnimation().setPingPong(true);

		//Nastavenie animacie so smerom dolava
		Animation leftAnimation = new Animation("sprites/ghost_left.png", 48, 42);
		leftAnimation.setPingPong(true);
		setAnimationLeft(leftAnimation);
		setAnimationJumpLeft(leftAnimation);

		//Inicializacia bez smeru pohybu, lieta na mieste bez pohybu
		stop();
	}

	//Hotfix pre GameLib kniznicu, synchronizacia PhysicalActor - Actor
	@Override
	public void addToWorld(World world)
	{
		if(!this.inWorld)
		{
			super.addToWorld(world);
			this.inWorld =true;
		}
	}
	//Hotfix pre GameLib kniznicu, synchronizacia PhysicalActor - Actor
	@Override
	public void removeFromWorld()
	{
		if(this.inWorld)
		{
			super.removeFromWorld();
			this.inWorld =false;
		}
	}

	//Duchovia sa navzajom zabiju
	public boolean exchangeKills(Ghost ghost)
	{
		if (ghost != null)
		{
			ghost.removeFromWorld();
			removeFromWorld();
			return true;
		}
		return false;
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
			getAnimation().start();
		}
		else if (isRight())
		{
			stopAnimationLeft();
			runAnimationRight();
			getAnimation().start();
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

	//Becomes enemy of Player and will try to haunt him for eternity, then killing him
	public final void becomeEnemy()
	{
		this.isEnemy = true;
		this.isAlly = false;
	}
	public final boolean isEnemy()
	{
		return this.isEnemy;
	}
	//Becomes friend of Player and will try to kill Prince and his Ghosts
	public final void becomeAlly()
	{
		this.isEnemy = false;
		this.isAlly = true;
	}
	public final boolean isAlly()
	{
		return this.isAlly;
	}

	//Tries to kill player on sight
	private void hauntPlayer()
	{
		for (Actor actor : getWorld())
		{
			//Kills any player
			if (actor instanceof Player && intersects(actor))
			{
				((Player) actor).decreaseEnergy(Player.MAX_HP);
				break;
			}
		}
	}

	//Will sacrifice themselves to kill Prince's ghosts and go for the life of Prince himself
	private boolean hauntPrince()
	{
		for (Actor actor : getWorld())
		{
			//Ak narazi na zleho ducha, tak sa navzajom zabiju
			if (actor instanceof Ghost && actor.getName().equals(WhiteGhostDecorator.NAME) && intersects(actor))
			{
				((Ghost) actor).exchangeKills(this);
				return true;
			}

			//Ak narazi na Princa, tak ho porazi a zmizne
			if (actor instanceof Prince && intersects(actor))
			{
				((Prince) actor).defeat(); //todo impl defeated that lets dizzy find key and open the door
				removeFromWorld();
				return true;
			}
		}
		return false;
	}

	//Bude sa pohybovat iba horizontalne v pozadovanom smere svojou rychlostou
	private void doMovement()
	{
		if (isLeft())
		{
			PhysicsHelper.setLinearVelocity(this, -getStep(), 0);
		}
		else if (isRight())
		{
			PhysicsHelper.setLinearVelocity(this, getStep(), 0);
		}
		else
		{
			PhysicsHelper.setLinearVelocity(this, 0, 0);
		}
	}

	@Override
	public void act()
	{
		/*
		//Prvy act sa nevykona
		if (!this.initialized)
		{
			this.initialized = true;
			return;
		}
		*/

		//Will kill their enemy on sight
		if (this.isEnemy)
		{
			hauntPlayer();
		}
		else if (this.isAlly && hauntPrince())
		{
			return;
		}

		try
		{
			//Stale sa bude hybat v pozadovanom smere
			if(this.inWorld)
			{
				doMovement();
			}
		}
		//Chyba, ktora vznikala z dovodu, ze do fyzickeho sveta bol umiestneny Decorator a nie samotny Actor
		catch (IllegalStateException exception)
		{
			System.out.println(exception.toString());
		}
	}
}
