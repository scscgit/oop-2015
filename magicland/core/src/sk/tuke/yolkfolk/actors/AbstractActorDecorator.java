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

/**
 * Vzor pre dekorovanie hercov.
 * <p/>
 * Created by Steve on 30.12.2015.
 */
public abstract class AbstractActorDecorator implements Actor, ActorDecorator
{
	//Dekorovany herec
	private Actor actor;

	public AbstractActorDecorator(Actor actor)
	{
		this.actor = actor;
	}

	//Praca so svetom priamo nad Actorom a nie nad dekoratorom
	//Je potrebne pouzivat tento sposob pristupu, inak nastava crash z dovodu, ze Actor nie je vo svete
	public void addToWorld(World world)
	{
		if (this.actor instanceof ActorDecorator)
		{
			((ActorDecorator) this.actor).addToWorld(world);
		}
		else
		{
			world.addActor(this.actor);
		}
	}
	public void removeFromWorld()
	{
		if (this.actor instanceof ActorDecorator)
		{
			((ActorDecorator) this.actor).removeFromWorld();
		}
		else
		{
			getWorld().removeActor(this.actor);
		}
	}

	@Override
	public float getX()
	{
		return this.actor.getX();
	}
	@Override
	public float getY()
	{
		return this.actor.getY();
	}
	@Override
	public float getWidth()
	{
		return this.actor.getWidth();
	}
	@Override
	public float getHeight()
	{
		return this.actor.getHeight();
	}
	@Override
	public void setPosition(float v, float v1)
	{
		this.actor.setPosition(v, v1);
	}
	@Override
	public Animation getAnimation()
	{
		return this.actor.getAnimation();
	}
	@Override
	public void setAnimation(Animation animation)
	{
		this.actor.setAnimation(animation);
	}
	@Override
	public void act()
	{
		this.actor.act();
	}
	@Override
	public boolean intersects(Actor actor)
	{
		return this.actor.intersects(actor);
	}
	@Override
	public void addedToWorld(World world)
	{
		//world.addActor(this.actor);
		this.actor.addedToWorld(world);
	}
	@Override
	public World getWorld()
	{
		return this.actor.getWorld();
	}
	@Override
	public String getName()
	{
		return this.actor.getName();
	}
	@Override
	public boolean isOnGround()
	{
		return this.actor.isOnGround();
	}
	@Override
	public void setOnGround(boolean b)
	{
		this.actor.setOnGround(b);
	}
}
