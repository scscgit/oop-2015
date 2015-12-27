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

package sk.tuke.yolkfolk.actors.characters;

import sk.tuke.gamelib2.*;
import sk.tuke.yolkfolk.actors.items.BirdBorderLeft;
import sk.tuke.yolkfolk.actors.items.BirdBorderRight;
import sk.tuke.yolkfolk.actors.items.Rubbish;
import sk.tuke.yolkfolk.actors.player.AbstractAnimatedActor;
import sk.tuke.yolkfolk.actors.player.players.dizzy.Dizzy;

/**
 * Animal, that is just flying around horizontally.
 * <p/>
 * Created by Steve on 27.12.2015.
 */
public class Bird extends AbstractAnimatedActor implements NoGravity
{
	//Constants
	public static final String name = "Bird";

	//Variables
	//Smer, false=vlavo, true=vpravo
	private boolean direction;
	private boolean nextVelocity;

	public Bird()
	{
		super(Bird.name, "sprites/birdright.png", 16, 16);
		this.direction = true;
		setStep(0.5f);

		//Spustenie pohybu
		setVelocity();

		//Animacia na smer dolava
		Animation animationLeft = new Animation("sprites/birdleft.png", 16, 16);
		setAnimationLeft(animationLeft);
		setAnimationJumpLeft(animationLeft);
		runAnimationLeft();
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
			runAnimationRight();
		}
		else
		{
			runAnimationLeft();
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
		Rubbish rubbish = new Rubbish(Bird.name);
		rubbish.setPosition(getX(), getY());
		getWorld().addActor(rubbish);
		this.removeFromWorld();
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

		//Vykonanie relevantnych intersect operacii s ostatnymi actormi
		for (Actor actor : getWorld())
		{
			//Po narazeni na neviditelnu prekazku sa otoci
			if
				(
				(
					(!this.direction && actor instanceof BirdBorderLeft)
					||
					(this.direction && actor instanceof BirdBorderRight)
				)
				&& actor.intersects(this)
				)
			{
				intersectsBorder();
			}

			//Ak ho Dizzy chytil, tak bude chyteny ako odpad
			if (actor instanceof Dizzy && isNear(actor, getWidth(), getHeight()))
			{
				Dizzy dizzy = (Dizzy) actor;
				dizzy.catchBird();
				putInRubbish();
				break;
			}
		}
	}
}
