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

import sk.tuke.gamelib2.Item;
import sk.tuke.yolkfolk.NewWorldOrder;
import sk.tuke.yolkfolk.actors.AbstractActor;

/**
 * Po smrti Devila reprezentuje pekelne ohne.
 * <p/>
 * Created by Steve on 3.12.2015.
 */
public class Splash extends AbstractActor implements Item
{
	//Constants
	public static final String NAME = "Splash";
	public static final int ANIMATION_TIME = 60;

	//Variables
	private int deleteCounter;
	private boolean deleted;

	//Inicializacia
	{
		this.deleteCounter = ANIMATION_TIME;
		this.deleted = false;
	}

	//Splash moze reprezentovat iny objekt jeho menom
	public Splash(String name)
	{
		super(name, "sprites/splash.png", 32, 26);
	}

	//Implicitne sa Splash vytvara s vlastnym menom
	public Splash()
	{
		this(Splash.NAME);
	}

	//Hotfix proti crashu - teleport outside world so wrong collisions won't happen
	public final void safeDeleteStopAct()
	{
		this.deleted = true;
		NewWorldOrder.teleportOutside(this);
		//removeFromWorld();
	}

	//Vykonaj animaciu o pozadovanej dlzke a odstran splash
	public void act()
	{
		if (this.deleted)
		{
			return;
		}

		if (this.deleteCounter <= 0)
		{

			safeDeleteStopAct();
		}
		else
		{
			this.deleteCounter--;
		}
	}
}
