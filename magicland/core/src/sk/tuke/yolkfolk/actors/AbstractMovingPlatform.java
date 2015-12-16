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

package sk.tuke.yolkfolk.actors;

import sk.tuke.gamelib2.Actor;
import sk.tuke.gamelib2.NoGravity;
import sk.tuke.gamelib2.PhysicsHelper;
import sk.tuke.yolkfolk.actors.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Vseobecna abstraktna trieda reprezentujuca pohybujucu sa plosinu.
 *
 * Created by Steve on 15.12.2015.
 */
public abstract class AbstractMovingPlatform extends AbstractActor implements Usable, NoGravity
{
	private boolean state;
	private float start;
	private float end;
	private boolean direction; //inicializovane funkciou initFinal(), urcuje aktualny smer, false = smer dole, true = smer hore
	private float speed;
	private int paused;
	private int pause;
	private boolean initializedStart;
	private boolean initializedEnd;
	private boolean initialized;
	private List<Observer<Boolean>> observerList = null;

	public AbstractMovingPlatform(String name)
	{
		super(name,"sprites/elevator.png",32,9);
		this.state=false;
		this.paused = 0; //current remaining time of pause before continuing in opposite direction
		this.initializedStart=false;
		this.initializedEnd=false;
		this.initialized=false;

		//Overridable variables that define a concrete elevator
		setPause(50); //default pause time set by manufacturer
		setSpeed(0.5f); //default speed set by manufacturer
	}

	//List of observers of a state change
	private List<Observer<Boolean>> getObserverList()
	{
		if(this.observerList == null)
		{
			this.observerList = new ArrayList<Observer<Boolean>>();
		}
		return this.observerList;
	}

	//Registers a new observer
	public final void register(Observer<Boolean> observer)
	{
		if(!getObserverList().contains(observer))
		{
			getObserverList().add(observer);
		}
	}

	//Metoda zabezpecujuca notifikaciu observerov o novej hodnote stavu
	protected final void notifyObservers()
	{
		for(Observer<Boolean> observer: getObserverList())
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
		if(actor instanceof Player)
		{
			this.state = !isOn();
			setPause(getPause()/2); //Delay before running is a half of the standard pause duration
			notifyObservers();
		}
	}

	public final boolean isOn()
	{
		return this.state;
	}

	public final boolean initialized()
	{
		return this.initialized;
	}

	public final boolean getDirection()
	{
		return this.direction;
	}

	@Override
	public void act()
	{
		if(isOn() && initialized() && this.paused<=0)
		{
			//Ked je vytah pripraveny, pohne sa pozadovanym smerom
			if(this.direction && getY()>getEnd())
			{
				reverse();
			}
			else if(!this.direction && getY()<getStart())
			{
				reverse();
			}
			PhysicsHelper.setLinearVelocity(this, nextX(), nextY());
		}
		else
		{
			//Ked vytah nie je pripraveny, alebo je pozastaveny, bude cakat
			if(this.paused>0)
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
		if(!this.initializedStart)
		{
			setStart(y);
		}
	}
	public void setStart(float start)
	{
		this.start = start;
		this.initializedStart = true;
		if(this.initializedEnd)
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
		this.end=end;
		this.initializedEnd = true;
		if(this.initializedStart)
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
			this.initialized = true;
		}
		else if (this.end > this.start)
		{
			this.direction = true;
			this.initialized = true;
		}
		else
		{
			this.initialized = false;
		}
	}

	public void setSpeed(float speed)
	{
		if(speed>=0)
		{
			this.speed=speed;
		}
		else
		{
			this.speed=0;
		}
	}
	protected float getSpeed()
	{
		return this.speed;
	}

	//Nastavi casovy interval, ako dlho sa pozastavi vytah pred jeho otocenim smeru na opacny pocas jeho rutiny pohybu
	public void setPause(int pause)
	{
		if(pause>=0)
		{
			//If the pause is currently pending, it gets incremented by the delta pause
			if (this.paused > 0 && pause > this.paused)
			{
				this.paused += (pause - this.paused);
			}
			this.pause = pause;
		}
		else
		{
			this.pause = 0;
		}
	}
	protected int getPause()
	{
		return this.pause;
	}

	//Obrati smer pohybu jazdy a podla potreby pozastavi vytah
	public void reverse()
	{
		this.direction=!this.direction;
		this.paused = getPause();
	}
}
