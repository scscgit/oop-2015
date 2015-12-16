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

package sk.tuke.yolkfolk.actions;

import sk.tuke.gamelib2.Actor;
import sk.tuke.gamelib2.Input;
import sk.tuke.yolkfolk.CustomInput;

/**
 * Akcia na utek do bezpecia operacneho systemu pre pripad, ze niekto nema okna a nedokaze stlacit tlacidlo X.
 *
 * Created by Steve on 2.12.2015.
 */
public class Exit extends AbstractAction
{
	public Exit()
	{
		super();
	}

	@Override
	public void doAction(Actor actor)
	{
		//On escape key press allow user to safely escape to the safe environment of Linux, or some other OS.
		if (CustomInput.escape())
		{
			System.exit(0);
		}

		//Do implicit action
		super.doAction(actor);
	}
}
