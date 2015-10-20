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

import sk.tuke.gamelib2.Item;

/**
 * Created by Steve on 3.12.2015.
 */
public class Splash extends AbstractActor implements Item //TODO impl item or something like that, no collision
{
	//Constants
	public static final int ANIMATION_TIME = 60;

	private int deleteCounter;

	public Splash(String name)
	{
		super(name, "sprites/splash.png", 32, 26);
		this.deleteCounter = ANIMATION_TIME;
	}

	//Vykonaj animaciu o pozadovanej dlzke a odstran splash
	public void act()
	{
		if(--this.deleteCounter <= 0)
		{
			setPosition(-getWidth(),-getHeight()); //Teleport outside world so wrong collisions won't happen
			getWorld().removeActor(this);
		}
	}
}
