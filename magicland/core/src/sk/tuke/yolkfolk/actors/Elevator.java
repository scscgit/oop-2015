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

import java.util.ArrayList;
import java.util.List;

/**
 * Stairway to heaven, elevator to hell. (Pun intended, you will actually meet the devil above the elevator)
 * A.K.A VerticalMovingPlatform
 *
 * Created by Steve on 26.11.2015.
 */
public class Elevator extends AbstractMovingPlatform
{
	public Elevator()
	{
		super("Elevator");
	}

	@Override
	protected float nextX()
	{
		return 0f;
	}

	@Override
	protected float nextY()
	{
		return getDirection()?getSpeed():-getSpeed();
	}
}
