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

package sk.tuke.yolkfolk.actors.objects;

import sk.tuke.gamelib2.Actor;
import sk.tuke.gamelib2.NoGravity;
import sk.tuke.gamelib2.PhysicsHelper;
import sk.tuke.yolkfolk.actors.AbstractActor;
import sk.tuke.yolkfolk.actors.Observer;
import sk.tuke.yolkfolk.actors.Usable;
import sk.tuke.yolkfolk.actors.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Vseobecna abstraktna trieda reprezentujuca pohybujucu sa plosinu.
 * <p/>
 * Created by Steve on 15.12.2015.
 */
public abstract class AbstractMovingPlatform extends AbstractActor implements Usable, NoGravity
{
	//Variables
	private boolean state;
	private float start;
	private float end;
	//premenna direction je inicializovana funkciou initFinal(), urcuje aktualny smer, implicitne false = smer dole, true = smer hore
	private boolean direction;
	private float speed;
	private int paused;
	private int pauseDuration;
	private boolean initializedStart;
	private boolean initializedEnd;
	private boolean initializedBoth;

	//Objects
	private List<Observer<Boolean>> observerList;

	//Static objects
	private static AbstractMovingPlatform lastInstance;

	static
	{
		AbstractMovingPlatform.lastInstance = null;
	}

	//Pocas inicializacie ulozi referenciu na poslednu vytvorenu platformu
	{
		this.observerList = null;
		AbstractMovingPlatform.lastInstance = this;
	}

	public AbstractMovingPlatform(String name)
	{
		super(name, "sprites/elevator.png", 32, 9);
		this.state = false;
		this.paused = 0; //current remaining time of pauseDuration before continuing in opposite direction
		this.initializedStart = false;
		this.initializedEnd = false;
		this.initializedBoth = false;

		//Overridable variables that define a concrete elevator
		setPauseDuration(50); //default pauseDuration time set by manufacturer
		setSpeed(0.5f); //default speed set by manufacturer
	}

	//Ziska referenciu na poslednu vytvorenu platformu, pouzitelnu pocas automagickeho pripajania paky
	public static AbstractMovingPlatform getLastInstance()
	{
		return AbstractMovingPlatform.lastInstance;
	}

	//List of observers of a state change
	private List<Observer<Boolean>> getObserverList()
	{
		if (this.observerList == null)
		{
			this.observerList = new ArrayList<Observer<Boolean>>();
		}
		return this.observerList;
	}

	//Registers a new observer
	public final void register(Observer<Boolean> observer)
	{
		if (!getObserverList().contains(observer))
		{
			getObserverList().add(observer);
		}
	}

	//Metoda zabezpecujuca notifikaciu observerov o novej hodnote stavu
	protected final void notifyObservers()
	{
		for (Observer<Boolean> observer : getObserverList())
		{
			observer.notified(isOn());
		}
	}

	//Nasledujuca rychlost v smere X pocas pohybu
	protected abstract float nextX();

	//Nasledujuca rychlost v smere Y pocas pohybu
	protected abstract float nextY();

	@Override
	public void use(Actor actor)
	{
		if (actor instanceof Player)
		{
			this.state = !isOn();
			pause(/*getPauseDuration() / 2*/); //Delay before running after switching the lever or using the elevator directly
			notifyObservers();
		}
	}

	public final boolean isOn()
	{
		return this.state;
	}

	public final boolean initialized()
	{
		return this.initializedBoth;
	}

	public final boolean getDirection()
	{
		return this.direction;
	}

	//Skontroluje, ci niekto blokuje cestu a vykona prislusne opatrenia. Ak vrati true, pohyb nebude pokracovat.
	protected boolean blockedPath()
	{
		return false;
	}

	//Rutina, ktoru bude platforma pravidelne vykonavat pocas svojho pohybu
	protected void runningRoutine()
	{
		//Ked je vytah pripraveny, pohne sa pozadovanym smerom
		if
			(
			(this.direction && getY() > getEnd())
			||
			(!this.direction && getY() < getStart())
			)
		{
			reverse();
		}

		//Ak sa nikto pod platformou nezasekol, tak moze pokracovat, inak spravi pauzu
		if (!blockedPath())
		{
			PhysicsHelper.setLinearVelocity(this, nextX(), nextY());
		}
	}

	@Override
	public void act()
	{
		if (isOn() && initialized() && this.paused <= 0)
		{
			runningRoutine();
		}
		else
		{
			//Ked vytah nie je pripraveny, alebo je pozastaveny, bude cakat
			if (this.paused > 0)
			{
				this.paused--;
			}
			PhysicsHelper.setLinearVelocity(this, 0f, 0f);
		}
	}

	//setStart() is included in setPosition during initialization
	@Override
	public void setPosition(float x, float y)
	{
		super.setPosition(x, y);
		if (!this.initializedStart)
		{
			setStart(y);
		}
	}
	public void setStart(float start)
	{
		this.start = start;
		this.initializedStart = true;
		if (this.initializedEnd)
		{
			initFinal();
		}
	}
	public float getStart()
	{
		return this.start;
	}

	public void setEnd(float end)
	{
		this.end = end;
		this.initializedEnd = true;
		if (this.initializedStart)
		{
			initFinal();
		}
	}
	public float getEnd()
	{
		return this.end;
	}

	//V pripade, ze je nejaky rozdiel medzi zaciatkom a koncom, tak inicializuje platformu (V pripade ich rovnosti osetri chybu nekonecneho pohybu smerom prec z mapy jeho zastavenim)
	protected void initFinal()
	{
		if (this.end < this.start)
		{
			this.direction = false;
			this.initializedBoth = true;
		}
		else if (this.end > this.start)
		{
			this.direction = true;
			this.initializedBoth = true;
		}
		else
		{
			this.initializedBoth = false;
		}
	}

	public void setSpeed(float speed)
	{
		if (speed >= 0)
		{
			this.speed = speed;
		}
		else
		{
			this.speed = 0;
		}
	}
	protected float getSpeed()
	{
		return this.speed;
	}

	//Nastavi parameter urcujuci casovy interval na ako dlho sa pozastavi vytah pred jeho otocenim smeru pohybu na opacny pocas jeho rutiny pohybu
	public final void setPauseDuration(int pauseDuration)
	{
		if (pauseDuration >= 0)
		{
			//If the pauseDuration is currently processed, it gets incremented by the delta pauseDuration
			if (this.paused > 0 && pauseDuration > this.paused)
			{
				this.paused += (pauseDuration - this.paused);
			}
			this.pauseDuration = pauseDuration;
		}
		else
		{
			this.pauseDuration = 0;
		}
	}
	protected final int getPauseDuration()
	{
		return this.pauseDuration;
	}
	//Pocka pozadovany pocet cyklov pred pokracovanim v pohybe
	public final void pause(/*int duration*/)
	{
		if (this.pauseDuration > 0 && this.pauseDuration > this.paused)
		{
			this.paused = this.pauseDuration;
		}
	}

	//Obrati smer pohybu jazdy a podla potreby pozastavi vytah
	public void reverse()
	{
		this.direction = !this.direction;
		pause(/*getPauseDuration()*/);
	}
}
