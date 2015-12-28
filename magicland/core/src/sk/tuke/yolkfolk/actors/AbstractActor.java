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

package sk.tuke.yolkfolk.actors;

import sk.tuke.gamelib2.Actor;
import sk.tuke.gamelib2.Animation;
import sk.tuke.gamelib2.World;
import sk.tuke.yolkfolk.actors.player.Player;
import sk.tuke.yolkfolk.interpreter.ActorInterpreter;

import java.awt.geom.Rectangle2D;

/**
 * Basic functionality for any Actor existing in a physical World.
 * <p/>
 * Created by Steve on 9.11.2015.
 */
public abstract class AbstractActor implements Actor
{
	//Premenne kazdeho actora
	private String name;
	private float x, y;
	private boolean onGround;
	private float step;

	//Zakladna animacia actora a svet, v ktorom sa dany actor nachadza
	private Animation currentAnimation;
	private final Animation defaultAnimation;
	private World world;

	public AbstractActor(String name, String animationString, int animationX, int animationY)
	{
		//Povinne meno noveho actora
		setName(name);

		setOnGround(
			false); //Tato hodnota je ale automaticky prepisovana kniznicami.

		//Defaultna animacia sa ulozi na neskorsie potreby reinicializacie
		this.defaultAnimation = new Animation(animationString, animationX, animationY);

		//Povinna defaultna animacia sa nastavi ako aktualna animacia
		setAnimation();
	}

	@Override
	public final float getX()
	{
		return this.x;
	}

	@Override
	public final float getY()
	{
		return this.y;
	}

	@Override
	public final float getWidth()
	{
		if (this.currentAnimation != null)
		{
			return this.currentAnimation.getWidth();
		}
		else
		{
			return 0;
		}
	}

	@Override
	public final float getHeight()
	{
		if (this.currentAnimation != null)
		{
			return this.currentAnimation.getHeight();
		}
		else
		{
			return 0;
		}
	}

	@Override
	public void setPosition(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	//Ziskanie rychlosti pohybu actora, vyznam nie je definovany pre ineho actora nez Playera
	public float getStep()
	{
		return this.step;
	}

	//Nastavenie rychlosti pohybu actora, vyznam nie je definovany pre ineho actora nez Playera
	public void setStep(float step)
	{
		this.step = step;
	}

	//Ziskanie aktualnej animacie aktora
	@Override
	public Animation getAnimation()
	{
		return this.currentAnimation;
	}

	//Nastavenie pozadovanej animacie ako novej aktualnej animacie actora
	public void setAnimation(Animation animation)
	{
		this.currentAnimation = animation;
	}

	//Nastavenie defaultnej animacie ako novej aktualnej animacie actora
	public final void setAnimation()
	{
		this.currentAnimation = this.defaultAnimation;
	}

	//Funkcia zabezpecujuca periodicke vykonavanie pozadovanych akcii
	/*@Override
	public void act()
	{
    }*/

	private boolean intersectsX(Actor actor)
	{
		return
			Math.abs
				(
					(actor.getX() + actor.getWidth() / 2) - (this.getX() + this.getWidth() / 2)
				)
			<=
			(actor.getWidth() / 2) + (this.getWidth() / 2);
	}

	private boolean intersectsY(Actor actor)
	{
		return
			Math.abs
				(
					(actor.getY() + actor.getHeight() / 2) - (this.getY() + this.getHeight() / 2)
				)
			<=
			(actor.getHeight() / 2) + (this.getHeight() / 2);
	}

	//Zistenie pravdivosti dotyku s inym actorom
	@Override
	public boolean intersects(Actor actor)
	{
		return
			//Exists
			actor != null
			&&
			//X intersects
			intersectsX(actor)
			&&
			//Y intersects
			intersectsY(actor);
	}

	//Zistenie pravdivosti dotyku s inym actorom vo vyske yAbove nad actorom
	public boolean intersectsAbove(Actor actor, float yAbove)
	{
		if (actor != null)
		{
			float yDelta = actor.getY() - this.getY() - this.getHeight();
			return
				//X intersects
				intersectsX(actor)
				&&
				//Y intersects above
				yDelta <= yAbove
				&&
				yDelta >= 0;
		}
		return false;
	}

	//Zistenie, ci sa actori nachadzaju v urcitej blizkosti
	public boolean isNear(Actor actor, float xNear, float yNear)
	{
		if (actor != null)
		{
			Rectangle2D rectangle = new Rectangle2D.Double(getX() - xNear / 2, getY() - yNear / 2, getWidth() + xNear,
			                                               getHeight() + yNear);

			return rectangle.intersects(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
		}
		return false;
	}

	//Defined by lower half of the body standing on the actor
	public boolean standsOn(Actor actor)
	{
		final int distance = 1; //getHeight()/2
		//Defined by lower half of the body standing on the actor within the specified distance
		if (actor != null)
		{
			Rectangle2D rectangle = new Rectangle2D.Double(getX(), getY() - getHeight() / 2, getWidth(),
			                                               getHeight() / 2 - 1);

			return rectangle
				.intersects(actor.getX() /*- getWidth()*/, actor.getY(), actor.getWidth() /*+ 2 * getWidth()*/,
				            distance);
		}
		return false;
	}

	@Override
	public void addedToWorld(World world)
	{
		this.world = world;
	}

	@Override
	public World getWorld()
	{
		return this.world;
	}

	//Removes this actor from the current world
	public final void removeFromWorld()
	{
		getWorld().removeActor(this);
	}

	@Override
	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return getName() + " " + (int) getX() + " " + (int) getY();
	}

	@Override
	public boolean isOnGround()
	{
		return this.onGround;
	}

	@Override
	public final void setOnGround(boolean onGround)
	{
		this.onGround = onGround;
	}

	public Player getPlayer()
	{
		if (getWorld() != null)
		{
			for (Actor actor : getWorld())
			{
				if (actor instanceof Player)
				{
					return (Player) actor;
				}
			}
		}
		return null;
	}

	//Doplnkova uloha: hladanie Actora podla jeho mena. Staticku verziu kazdy actor overloaduje a hlada vo svojom svete.
	public static Actor getActorByName(String name, World world)
	{
		if (name != null && world != null)
		{
			for (Actor actor : world)
			{
				if (actor != null && actor.getName().equals(name))
				{
					return actor;
				}
			}
		}
		return null;
	}

	//Implicitne cez overload hladam aktora vo svojom svete
	public Actor getActorByName(String name)
	{
		return AbstractActor.getActorByName(name, getWorld());
	}

	//Interprets command for this Actor
	public void interpret(String commands)
	{
		new ActorInterpreter(this).interpret(commands);
	}
}
