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

package sk.tuke.yolkfolk.actors;

import sk.tuke.gamelib2.Actor;
import sk.tuke.gamelib2.Item;
import sk.tuke.yolkfolk.actors.player.Player;

/**
 * Paka, ktora sa pripoji na vytah a vyuziva obojsmernu komunikaciu na synchronizaciu stavu.
 * Je ale kompatibilna aj s inymi platformami.
 *
 * Created by Steve on 26.11.2015.
 */
public class Lever extends AbstractActor implements Item, Usable, Observer<Boolean>
{
	private boolean state;
	private AbstractMovingPlatform elevator;

	public Lever()
	{
		super("Lever", "sprites/lever.png", 16, 16);

		//Zastavena animacia, aktualna snimka reprezentuje binarne stav paky
		getAnimation().stop();

		setState(false);
		this.elevator=null;
	}

	public Lever(AbstractMovingPlatform elevator)
	{
		this();
		connectElevator(elevator);
	}

	public void connectElevator(AbstractMovingPlatform elevator)
	{
		if(elevator instanceof AbstractMovingPlatform)
		{
			this.elevator = elevator;
			setState(this.elevator.isOn());
			elevator.register(this);
		}
	}

	public boolean isOn()
	{
		return this.state;
	}

	private void setState(boolean state)
	{
		this.state = state;
		getAnimation().setCurrentFrame(this.state==false?0:1);
	}

	@Override
	public void use(Actor actor)
	{
		if(actor instanceof Player)
		{
			if(this.elevator instanceof AbstractMovingPlatform)
			{
				//if elevator state is the same state as lever, change state of elevator
				if(this.elevator.isOn() == state)
				{
					this.elevator.use(actor);
				}
			}
			//setState(!this.state); //Namiesto setState budeme spoliehat na notifikaciu ako observer
		}
	}

	@Override
	public void notified(Boolean newState)
	{
		setState(newState);
	}
}
