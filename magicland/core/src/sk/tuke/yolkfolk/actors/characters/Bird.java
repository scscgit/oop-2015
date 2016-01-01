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

package sk.tuke.yolkfolk.actors.characters;

import sk.tuke.gamelib2.*;
import sk.tuke.yolkfolk.actors.AbstractAnimatedActor;
import sk.tuke.yolkfolk.actors.objects.Rubbish;
import sk.tuke.yolkfolk.actors.player.players.dizzy.Dizzy;
import sk.tuke.yolkfolk.spaces.BirdBorderLeft;
import sk.tuke.yolkfolk.spaces.BirdBorderRight;

/**
 * Animal, that is just flying around horizontally.
 * <p/>
 * Created by Steve on 27.12.2015.
 */
public class Bird extends AbstractAnimatedActor implements NoGravity
{
	//Constants
	public static final String NAME = "Bird";

	//Variables
	//Smer, false=vlavo, true=vpravo
	private boolean direction;
	private boolean nextVelocity;
	private int chirpCounter;

	//Objects
	private Message chirpMessage;

	//Static animations
	private static Animation animationLeft;

	//Static initialization
	static
	{
		Bird.animationLeft = new Animation("sprites/birdleft.png", 16, 16);
	}

	public Bird()
	{
		super(Bird.NAME, "sprites/birdright.png", 16, 16);
		this.direction = true;
		this.nextVelocity = false;
		this.chirpCounter = 0;
		this.chirpMessage = null;
		setStep(0.5f);

		//Spustenie pohybu
		setVelocity();

		//Ulozi sa animacia na smer dolava
		setAnimationLeft(Bird.animationLeft);
		setAnimationJumpLeft(Bird.animationLeft);
	}

	//Spusti sa pohyb v zadanom smere
	public final void setVelocity()
	{
		this.nextVelocity = true;
	}

	//Otoci sa smer pohybu o 180 stupnov a nastavi sa relevantna animacia
	protected void reverse()
	{
		this.direction = !this.direction;
		if (this.direction)
		{
			stopAnimationLeft();
			runAnimationRight();
		}
		else
		{
			runAnimationLeft();
			stopAnimationRight();
		}
		setVelocity();
	}

	//Akcia, ktoru ma vykonat ked sa nachadza na kraji vyhradeneho priestoru pre lietanie
	protected void intersectsBorder()
	{
		reverse();
	}

	//Zabali vtaka do tasky s odpadkami
	protected void putInRubbish()
	{
		Rubbish rubbish = new Rubbish(Bird.NAME);
		rubbish.setPosition(getX(), getY());
		getWorld().addActor(rubbish);
		if (this.chirpMessage != null)
		{
			this.chirpMessage.remove();
		}
		this.removeFromWorld();
	}

	//Vykonanie relevantnych operacii s ostatnymi actormi.
	//Vrati true ak vykonal akciu, ktora zmeni stav actorov (z dovodu ConcurrentModification).
	protected boolean actionWithActor(Actor actor)
	{
		//Ak ho Dizzy chytil, tak bude chyteny ako odpad
		if (actor instanceof Dizzy && isNear(actor, getWidth(), getHeight()))
		{
			Dizzy dizzy = (Dizzy) actor;
			dizzy.catchBird();
			putInRubbish();
			return true;
		}

		//Po narazeni na neviditelnu prekazku sa otoci
		if
			(
			(
				(!this.direction && actor instanceof BirdBorderLeft)
				||
				(this.direction && actor instanceof BirdBorderRight)
			)
			&&
			actor.intersects(this)
			)
		{
			intersectsBorder();
		}

		//The bird will most certainly be noticed and visited.
		//A.K.A void accept(Visitor visitor), but more compact and automatic.
		if (actor instanceof HoodedVisitor)
		{
			((HoodedVisitor) actor).visit(this);
		}
		return false;
	}

	//Zacvirika
	public void doChirp(int time)
	{
		if (this.chirpMessage == null)
		{
			this.chirpCounter = time;
		}
	}

	@Override
	public void act()
	{
		//Ak bola poziadavka na jeho reset rychlosti, tak sa vykona
		if (this.nextVelocity)
		{
			this.nextVelocity = false;
			PhysicsHelper.setLinearVelocity(this, this.direction ? getStep() : -getStep(), 0);
		}

		//Cvirikat bude iba chvilu
		if (this.chirpMessage != null)
		{
			this.chirpMessage.remove();
			this.chirpMessage = null;
		}
		if (this.chirpCounter > 0)
		{
			this.chirpMessage = new Message("Chirp", "Chirp", this);
			this.chirpMessage.setPosition(getX() - getWidth(), getY() + getHeight());
			this.chirpCounter--;
		}

		//Vykonanie relevantnych intersect operacii s ostatnymi actormi, vratane visitu Visitorov
		for (Actor actor : getWorld())
		{
			if (actionWithActor(actor))
			{
				break;
			}
		}
	}
}
