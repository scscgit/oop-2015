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

package sk.tuke.yolkfolk.actors.items;

import sk.tuke.yolkfolk.actors.AbstractActor;

/**
 * A mysterious bag containing something unknown, usually a bird.
 * <p/>
 * Created by Steve on 27.12.2015.
 */
public class Rubbish extends AbstractActor
{
	//Constants
	public static final String name = "Rubbish";
	public static final int DECAY_TIME = 150;

	//Variables
	private int decayCounter;

	{
		this.decayCounter = 0;
	}

	public Rubbish(String name)
	{
		super(name, "sprites/rubbish.png", 16, 16);
	}

	//Implicitne sa Rubbish vytvara so svojim menom
	public Rubbish()
	{
		this(Rubbish.name);
	}

	//Zisti, ci bol objekt vytvoreny ako obycajny odpad a nie ako iny zabaleny objekt
	public boolean isGenericRubbish()
	{
		return getName().equals(Rubbish.name);
	}

	@Override
	public void act()
	{
		if(!isGenericRubbish())
		{
			//All random rubbish instances get deleted after some constant time passes
			if(this.decayCounter<Rubbish.DECAY_TIME)
			{
				this.decayCounter++;
			}
			else
			{
				removeFromWorld();
			}
		}
	}
}
