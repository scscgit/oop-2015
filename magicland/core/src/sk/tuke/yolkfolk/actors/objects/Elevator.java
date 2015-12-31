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

package sk.tuke.yolkfolk.actors.objects;

import sk.tuke.gamelib2.Actor;
import sk.tuke.gamelib2.Item;
import sk.tuke.yolkfolk.actors.AbstractActor;

/**
 * Stairway to heaven, elevator to hell. (Pun intended, you will actually meet the devil above the elevator)
 * A.K.A VerticalMovingPlatform
 * <p/>
 * Created by Steve on 26.11.2015.
 */
public class Elevator extends AbstractMovingPlatform
{
	//Constants
	public static final String NAME = "Elevator";

	//Konstruktor pre ucely dedenia
	public Elevator(String name)
	{
		super(name);
	}

	public Elevator()
	{
		super(Elevator.NAME);
	}

	@Override
	protected boolean blockedPath()
	{
		//V pripade pohybu smerom hore neriesi blokovanie actorov
		if (getDirection())
		{
			return false;
		}

		//V pripade pohybu smerom dole povoli pohyb iba ak sa pod vytahom nikto nenachadza
		for (Actor actor : getWorld())
		{
			if
				(
				//Nezastavi pri kolizii s objektom typu Item
				!(actor instanceof Item)
				&&
				//Kontrolu vykonava iba ak je actor odvodeny od abstraktneho actora a ma jeho metodu na zistenie kolizie
				actor instanceof AbstractActor
				&&
				//Ak sa nad actorom nachadza vytah vo vzdialenosti vysky vytahu, tak sa obrati, aby sa actor nezasekol
				((AbstractActor) actor).intersectsAbove(this, getHeight())
				)
			{
				reverse();
				return true;
			}
		}
		return false;
	}

	@Override
	protected float nextX()
	{
		return 0f;
	}
	@Override
	protected float nextY()
	{
		return getDirection() ? getSpeed() : -getSpeed();
	}
}
